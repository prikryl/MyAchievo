package cz.admin24.myachievo.web.calendar;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.google.api.Google;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.Calendar;

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
        Calendar ret = new Calendar.Builder(httpTransport, jsonFactory, credential).build();
        return ret;
    }


    @Override
    public Class<?> getObjectType() {
        return Calendar.class;
    }


    @Override
    public boolean isSingleton() {
        return false;
    }

}
