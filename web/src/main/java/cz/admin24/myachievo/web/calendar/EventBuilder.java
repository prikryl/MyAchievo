package cz.admin24.myachievo.web.calendar;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Event.ExtendedProperties;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.common.collect.Maps;

import cz.admin24.myachievo.connector.http.dto.WorkReport;
import cz.admin24.myachievo.web.CalendarConstants;

public class EventBuilder {

    public Event buildEvent(WorkReport report) {
        Event event = new Event();

        Calendar c = Calendar.getInstance();
        c.setTime(report.getDate());

        // work usually start at 8AM
        c.add(Calendar.HOUR, 8);
        Date start = c.getTime();
        EventDateTime startEventDateTime = EventDateTimeBuilder.build(start);
        event.setStart(startEventDateTime);

        merge(report, event);

        return event;
    }


    private ExtendedProperties buildExtendedProperties(WorkReport report) {
        ExtendedProperties ret = new ExtendedProperties();
        Map<String, String> shared = Maps.newHashMap();

        ret.setShared(shared);
        shared.put(CalendarConstants.Event.ID, report.getId());
        // ret.set(CalendarConstants.Event.ID, report.getPId());

        return ret;
    }


    public boolean merge(WorkReport from, Event to) {
        boolean changed = false;

        if (!StringUtils.equals(to.getSummary(), from.getRemark())) {
            to.setSummary(from.getRemark());
            changed = true;
        }
        if (!StringUtils.equals(to.getDescription(), from.toString())) {
            to.setDescription(from.toString());
            changed = true;
        }

        ExtendedProperties extendedProperties = buildExtendedProperties(from);
        if (!extendedProperties.equals(to.getExtendedProperties())) {
            to.setExtendedProperties(extendedProperties);
            changed = true;
        }

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(to.getStart().getDateTime().getValue());

        // add reported time
        c.add(Calendar.HOUR, from.getHours());
        c.add(Calendar.MINUTE, from.getMinutes());
        Date stop = c.getTime();
        EventDateTime stopEventDateTime = EventDateTimeBuilder.build(stop);

        if (to.getEnd() == null || stop.getTime() != to.getEnd().getDateTime().getValue()) {
            to.setEnd(stopEventDateTime);
            changed = true;
        }

        return changed;
    }

}
