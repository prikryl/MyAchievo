package cz.admin24.myachievo.web.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.common.collect.Lists;

import cz.admin24.myachievo.web.calendar.CalendarFactory;

@Controller()
@RequestMapping("/calendar/")
public class CalendarController {
    @Autowired
    private CalendarFactory calendarFactory;


    @RequestMapping("list")
    public @ResponseBody
    List<String> list() throws IOException {
        List<String> ret = Lists.newArrayList();
        Calendar service = calendarFactory.getObject();

        String pageToken = null;
        do {
            com.google.api.services.calendar.model.CalendarList calendarList = service.calendarList().list().setPageToken(pageToken).execute();
            List<CalendarListEntry> items = calendarList.getItems();

            for (CalendarListEntry calendarListEntry : items) {
                ret.add(calendarListEntry.getSummary());
            }
            pageToken = calendarList.getNextPageToken();
        } while (pageToken != null);
        return ret;
    }
}
