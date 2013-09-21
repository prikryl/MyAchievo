package cz.admin24.myachievo.web.calendar;

import java.util.Date;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.EventDateTime;

public class EventDateTimeBuilder {

    public static EventDateTime build(Date start) {
        DateTime dateTime = new DateTime(start);
        EventDateTime eventDateTime = new EventDateTime();
        eventDateTime.setDateTime(dateTime);
        return eventDateTime;
    }

}
