package cz.admin24.myachievo.web.calendar;

import java.util.Calendar;
import java.util.Date;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

import cz.admin24.myachievo.connector.http.dto.WorkReport;

public class EventBuilder {

    public Event buildEvent(WorkReport report) {
        Event event = new Event();

        event.setSummary(report.getRemark());
        event.setDescription(report.toString());
        // event.setLocation("Somewhere");

        // ArrayList<EventAttendee> attendees = new ArrayList<EventAttendee>();
        // attendees.add(new EventAttendee().setEmail("attendeeEmail"));
        // // ...
        // event.setAttendees(attendees);

        Calendar c = Calendar.getInstance();
        c.setTime(report.getDate());

        // work usually start at 8AM
        c.add(Calendar.HOUR, 8);
        Date start = c.getTime();

        // add reported time
        c.add(Calendar.HOUR, report.getHours());
        c.add(Calendar.MINUTE, report.getMinutes());
        Date stop = c.getTime();

        DateTime startDateTime = new DateTime(start);
        DateTime stopDateTime = new DateTime(stop);

        EventDateTime startEventDateTime = new EventDateTime();
        EventDateTime stopEventDateTime = new EventDateTime();

        startEventDateTime.setDateTime(startDateTime);
        stopEventDateTime.setDateTime(stopDateTime);

        event.setStart(startEventDateTime);
        event.setEnd(stopEventDateTime);

        return event;
    }

}
