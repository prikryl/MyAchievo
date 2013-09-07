package cz.admin24.myachievo.web.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.google.api.Google;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.Calendar.Acl;
import com.google.api.services.calendar.Calendar.CalendarList;
import com.google.api.services.calendar.Calendar.Calendars;
import com.google.api.services.calendar.Calendar.Channels;
import com.google.api.services.calendar.Calendar.Colors;
import com.google.api.services.calendar.Calendar.Events;
import com.google.api.services.calendar.Calendar.Freebusy;
import com.google.api.services.calendar.Calendar.Settings;

@Service
public class CalendarFactory implements FactoryBean<Calendar> {
    @Autowired
    private HttpTransport        httpTransport;
    @Autowired
    private JacksonFactory       jsonFactory;
    @Autowired
    private ConnectionRepository connectionRepository;


    @Override
    public Calendar getObject() {
        Connection<Google> conn = connectionRepository.getPrimaryConnection(Google.class);
        Google api = conn.getApi();
        GoogleCredential credential = new GoogleCredential();
        credential.setAccessToken(api.getAccessToken());
        Calendar c = new Calendar.Builder(httpTransport, jsonFactory, credential).build();
        return c;
    }


    @Override
    public Class<?> getObjectType() {
        return Calendar.class;
    }


    @Override
    public boolean isSingleton() {
        return false;
    }

    /**
     * @author pprikryl
     *
     */
    public static class GoogleCalendarService {
        @Autowired
        private CalendarFactory cf;
        private Calendar        c;


        @PostConstruct
        protected void init() {
            c = cf.getObject();
        }


        public Acl acl() {
            return c.acl();
        }


        public CalendarList calendarList() {
            return c.calendarList();
        }


        public Calendars calendars() {
            return c.calendars();
        }


        public Channels channels() {
            return c.channels();
        }


        public Colors colors() {
            return c.colors();
        }


        public Events events() {
            return c.events();
        }


        public Freebusy freebusy() {
            return c.freebusy();
        }


        public Settings settings() {
            return c.settings();
        }
    }

}
