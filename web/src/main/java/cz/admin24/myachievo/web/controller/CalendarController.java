package cz.admin24.myachievo.web.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.api.services.calendar.Calendar.Calendars;
import com.google.api.services.calendar.model.Calendar;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.api.services.calendar.model.Event;
import com.google.common.collect.Lists;

import cz.admin24.myachievo.connector.http.AchievoConnector;
import cz.admin24.myachievo.connector.http.dto.WorkReport;
import cz.admin24.myachievo.web.CalendarConstants;
import cz.admin24.myachievo.web.calendar.EventBuilder;
import cz.admin24.myachievo.web.service.CalendarFactory.GoogleCalendarService;

@Controller()
@RequestMapping("/calendar/")
public class CalendarController {
    private static final Logger   LOG = LoggerFactory.getLogger(CalendarController.class);
    @Autowired
    private GoogleCalendarService googleCalendarService;
    @Autowired
    private AchievoConnector      achievoConnector;


    @RequestMapping("list")
    public @ResponseBody
    List<String> list() throws IOException {
        List<String> ret = Lists.newArrayList();

        String pageToken = null;
        do {
            com.google.api.services.calendar.model.CalendarList calendarList = googleCalendarService.calendarList().list().setPageToken(pageToken).execute();
            List<CalendarListEntry> items = calendarList.getItems();

            for (CalendarListEntry calendarListEntry : items) {
                ret.add(calendarListEntry.getSummary());
            }
            pageToken = calendarList.getNextPageToken();
        } while (pageToken != null);
        return ret;
    }


    @RequestMapping("synchronize")
    public @ResponseBody
    List<String> synchronize() throws IOException {
        String calendarId = getOrCreateCalendar();
        updateReports(calendarId);

        return null;
    }


    private void updateReports(String calendarId) throws IOException {
        EventBuilder builder = new EventBuilder();
        Date to = new Date();
        Date from = DateUtils.addDays(to, -10);
        List<WorkReport> reports = achievoConnector.getHours(from, to);
        for (WorkReport report : reports) {
            Event newEvent = builder.buildEvent(report);
            Event persistedEvent = googleCalendarService.events().insert(calendarId, newEvent).execute();
            LOG.debug("Persisted event: {}", persistedEvent);
        }

    }


    private String getOrCreateCalendar() throws IOException {
        LOG.debug("Searching for calendar {}.", CalendarConstants.CALENDAR_ID_ACHIEVO);
        Calendars calendars = googleCalendarService.calendars();
        String calendarId = findAchievoCalendarId();

        if (calendarId == null) {

            Calendar calendar = new Calendar();
            calendar.setSummary(CalendarConstants.CALENDAR_ID_ACHIEVO);

            LOG.debug("Calendar {} not found creating new: {}", CalendarConstants.CALENDAR_ID_ACHIEVO, calendar);
            Calendar result = calendars.insert(calendar).execute();
            calendarId = result.getId();
            LOG.trace("Request:\n{}", calendar);
            LOG.trace("Response:\n{}", result);
        }
        return calendarId;
    }


    private String findAchievoCalendarId() throws IOException {
        String pageToken = null;
        do {
            com.google.api.services.calendar.model.CalendarList calendarList = googleCalendarService.calendarList().list().setPageToken(pageToken).execute();
            List<CalendarListEntry> items = calendarList.getItems();

            for (CalendarListEntry calendarListEntry : items) {

                if (CalendarConstants.CALENDAR_ID_ACHIEVO.equals(calendarListEntry.getSummary())) {
                    return calendarListEntry.getId();
                }
            }
            pageToken = calendarList.getNextPageToken();
        } while (pageToken != null);
        return null;
    }
}
