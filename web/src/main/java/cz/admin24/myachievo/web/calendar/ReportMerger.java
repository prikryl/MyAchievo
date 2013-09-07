package cz.admin24.myachievo.web.calendar;

import java.io.IOException;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.api.client.googleapis.batch.BatchRequest;
import com.google.api.client.googleapis.batch.json.JsonBatchCallback;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.http.HttpHeaders;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Event.ExtendedProperties;
import com.google.api.services.calendar.model.Events;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;

import cz.admin24.myachievo.connector.http.dto.WorkReport;
import cz.admin24.myachievo.web.CalendarConstants;
import cz.admin24.myachievo.web.service.CalendarFactory.GoogleCalendarService;

public class ReportMerger {
    private static final Logger         LOG          = LoggerFactory.getLogger(ReportMerger.class);
    //
    private final Deque<BatchRequest>   batches      = Lists.newLinkedList();
    private final GoogleCalendarService googleCalendarService;
    private final String                calendarId;
    private final EventBuilder          eventBuilder = new EventBuilder();

    //
    private int                         batchCounter = 999999;


    public ReportMerger(GoogleCalendarService googleCalendarService, String calendarId) {
        this.googleCalendarService = googleCalendarService;
        this.calendarId = calendarId;

    }


    public void merge(List<WorkReport> reports, Events events) throws IOException {
        // convert structures
        Map<String, Event> eventMap = convert(events);
        Map<String, WorkReport> reportMap = convert(reports);

        // coarse merge
        // 1. remove not existing events
        removeNotExisting(reportMap, eventMap);
        // 2. add missing events
        addMissing(reportMap, eventMap);

        // fine merge
        // for each report set:
        // // correct duration, remark, project, phase, activity
        fineMerge(reportMap, eventMap);

        // optimalize
        // find overlaps for each day and optimalize
        // TODO implement this last step

        // execute batches
        for (BatchRequest bach : batches) {
            bach.execute();
        }

    }


    private void fineMerge(Map<String, WorkReport> reportMap, Map<String, Event> eventMap) throws IOException {
        for (Entry<String, WorkReport> entry : reportMap.entrySet()) {
            String key = entry.getKey();
            WorkReport workReport = entry.getValue();
            Event event = eventMap.get(key);

            boolean changed = eventBuilder.merge(workReport, event);
            // new events can't be different to report-> they will not appear in update-> we will
            // not need their ID
            if (changed) {
                googleCalendarService.events().update(calendarId, event.getId(), event).queue(getBatch(), new UpdateEventCallback(event));
            }
        }
    }


    private void addMissing(Map<String, WorkReport> reportMap, Map<String, Event> eventMap) throws IOException {
        Set<String> reportIds = reportMap.keySet();
        Set<String> eventIds = eventMap.keySet();

        SetView<String> missingReports = Sets.difference(reportIds, eventIds);

        for (String missingReport : missingReports) {
            WorkReport report = reportMap.get(missingReport);
            Event newEvent = eventBuilder.buildEvent(report);
            googleCalendarService.events().insert(calendarId, newEvent).queue(getBatch(), new InsertEventCallback(newEvent));
            eventMap.put(report.getId(), newEvent);
        }
    }


    private void removeNotExisting(Map<String, WorkReport> reportMap, Map<String, Event> eventMap) throws IOException {
        Set<String> reportIds = reportMap.keySet();
        Set<String> eventIds = eventMap.keySet();

        SetView<String> deletedEvents = Sets.difference(eventIds, reportIds);

        for (String deletedEvent : deletedEvents) {
            Event event = eventMap.get(deletedEvent);
            googleCalendarService.events().delete(calendarId, event.getId()).queue(getBatch(), new DeleteEventCallback(event));
        }
    }


    private BatchRequest getBatch() {
        batchCounter++;

        if (batchCounter >= CalendarConstants.MAX_BATCH_SIZE) {
            BatchRequest batch = googleCalendarService.batch();
            batches.add(batch);
            batchCounter = 0;
            return batch;
        }

        return batches.getLast();

    }


    private Map<String, Event> convert(Events events) {
        Map<String, Event> ret = Maps.newHashMap();
        for (Event event : events.getItems()) {
            String eventId = UUID.randomUUID().toString();
            ExtendedProperties extendedProperties = event.getExtendedProperties();
            if (extendedProperties != null) {
                Map<String, String> shared = extendedProperties.getShared();
                if (shared != null) {
                    Object o = shared.get(CalendarConstants.Event.ID);
                    if (o != null) {
                        eventId = o.toString();
                    }
                }
            }

            ret.put(eventId, event);
        }
        return ret;
    }


    private Map<String, WorkReport> convert(List<WorkReport> reports) {
        Map<String, WorkReport> ret = Maps.newHashMap();
        for (WorkReport report : reports) {
            ret.put(report.getId(), report);
        }
        return ret;
    }

    private static class DeleteEventCallback extends JsonBatchCallback<Void> {

        private final Event event;


        public DeleteEventCallback(Event event) {
            this.event = event;
        }


        @Override
        public void onSuccess(Void t, HttpHeaders responseHeaders) throws IOException {
            LOG.trace("Event {} sucessfully deleted", event);
        }


        @Override
        public void onFailure(GoogleJsonError e, HttpHeaders responseHeaders) throws IOException {
            LOG.trace("Failed to delete event {}. Error: {}", event, e);
        }
    }

    private static class InsertEventCallback extends JsonBatchCallback<Event> {

        private final Event event;


        public InsertEventCallback(Event event) {
            this.event = event;
        }


        @Override
        public void onSuccess(Event t, HttpHeaders responseHeaders) throws IOException {
            LOG.trace("Event {} sucessfully inserted", event);
        }


        @Override
        public void onFailure(GoogleJsonError e, HttpHeaders responseHeaders) throws IOException {
            LOG.trace("Failed to insert event {}. Error: {}", event, e);
        }
    }

    private static class UpdateEventCallback extends JsonBatchCallback<Event> {

        private final Event event;


        public UpdateEventCallback(Event event) {
            this.event = event;
        }


        @Override
        public void onSuccess(Event t, HttpHeaders responseHeaders) throws IOException {
            LOG.trace("Event {} sucessfully udated", event);
        }


        @Override
        public void onFailure(GoogleJsonError e, HttpHeaders responseHeaders) throws IOException {
            LOG.trace("Failed to udate event {}. Error: {}", event, e);
        }
    }
}
