package cz.admin24.myachievo.web.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
//import java.util.Calendar;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;

@Controller
@RequestMapping("/")
public class BaseController {

    @Autowired
    private NativeClient calendarApi;
    @Autowired
    private SpringSocialClient ss;


    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public String welcome(ModelMap model) throws IOException {
//        calendarApi.x();
ss.api();
        model.addAttribute("message", "Maven Web Project + Spring 3 MVC - welcome()");

        // Spring uses InternalResourceViewResolver and return back index.jsp
        return "index";

    }


    @RequestMapping(value = "/welcome/{name}", method = RequestMethod.GET)
    public String welcomeName(@PathVariable String name, ModelMap model) {

        model.addAttribute("message", "Maven Web Project + Spring 3 MVC - " + name);
        return "index";

    }


    private void calendar() throws IOException {
        HttpTransport httpTransport = new NetHttpTransport();
        JacksonFactory jsonFactory = new JacksonFactory();

        // The clientId and clientSecret are copied from the API Access tab on
        // the Google APIs Console
        String clientId = "YOUR_CLIENT_ID";
        String clientSecret = "YOUR_CLIENT_SECRET";

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
    }
}