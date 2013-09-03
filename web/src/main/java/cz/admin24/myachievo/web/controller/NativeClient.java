package cz.admin24.myachievo.web.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.Calendar.CalendarList;
import com.google.api.services.calendar.CalendarScopes;

@Service
public class NativeClient {
    private static final Logger LOG = LoggerFactory.getLogger(NativeClient.class);

    @Value("${com.google.clientId}")
    private String              clientId;
    @Value("${com.google.clientSecret}")
    private String              clientSecret;


    public void x() throws IOException {
        LOG.trace("clientId: '{}' clientSecret: '{}'", clientId, clientSecret);
        HttpTransport httpTransport = new NetHttpTransport();
        JacksonFactory jsonFactory = new JacksonFactory();

        // The clientId and clientSecret are copied from the API Access tab on
        // the Google APIs Console

        // Or your redirect URL for web based applications.
        String redirectUrl = "urn:ietf:wg:oauth:2.0:oob";
        String scope = "https://www.googleapis.com/auth/calendar";

        // Step 1: Authorize -->
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow
                .Builder(
                        httpTransport, jsonFactory, clientId, clientSecret,
                        Arrays.asList(CalendarScopes.CALENDAR))
                        .setAccessType("online")
                        .setApprovalPrompt("auto")
                        .build();

        String url = flow.newAuthorizationUrl().setRedirectUri(redirectUrl).build();
        System.out.println("Please open the following URL in your browser then type the authorization code:");

        System.out.println("  " + url);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String code = br.readLine();

        GoogleTokenResponse response = flow.newTokenRequest(code).setRedirectUri(redirectUrl).execute();
        GoogleCredential credential = new GoogleCredential()
                .setFromTokenResponse(response);

        // Create a new authorized API client
        Calendar service = new Calendar.Builder(httpTransport, jsonFactory,
                credential).build();
        CalendarList calendarList = service.calendarList();
    }

}
