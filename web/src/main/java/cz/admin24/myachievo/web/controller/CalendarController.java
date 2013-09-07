package cz.admin24.myachievo.web.controller;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.api.services.calendar.Calendar.Calendars;
import com.google.api.services.calendar.model.Calendar;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.common.collect.Lists;

import cz.admin24.myachievo.web.CalendarConstants;
import cz.admin24.myachievo.web.service.CalendarFactory.GoogleCalendarService;

@Controller()
@RequestMapping("/calendar/")
public class CalendarController {
    private static final Logger   LOG = LoggerFactory.getLogger(CalendarController.class);
    @Autowired
    private GoogleCalendarService googleCalendarService;


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
        createCalendarIfRequired();
        return null;
    }


    private void createCalendarIfRequired() throws IOException {
        LOG.debug("Searching for calendar {}.", CalendarConstants.CALENDAR_ID_ACHIEVO);
        Calendars calendars = googleCalendarService.calendars();
        String calendarId = findAchievoCalendarId();

        if (calendarId == null) {

            Calendar calendar = new Calendar();
            calendar.setSummary("Achievo");
            // calendar.setTimeZone("America/Los_Angeles");
            calendar.setKind(CalendarConstants.CALENDAR_ID_ACHIEVO);

            LOG.debug("Calendar {} not found creating new: {}", CalendarConstants.CALENDAR_ID_ACHIEVO, calendar);
            calendars.insert(calendar).execute();
        }
    }


    private String findAchievoCalendarId() throws IOException {
        String pageToken = null;
        do {
            com.google.api.services.calendar.model.CalendarList calendarList = googleCalendarService.calendarList().list().setPageToken(pageToken).execute();
            List<CalendarListEntry> items = calendarList.getItems();

            for (CalendarListEntry calendarListEntry : items) {
                if (CalendarConstants.CALENDAR_ID_ACHIEVO.equals(calendarListEntry.getKind())) {
                    return calendarListEntry.getId();
                }
            }
            pageToken = calendarList.getNextPageToken();
        } while (pageToken != null);
        return null;
    }

}
