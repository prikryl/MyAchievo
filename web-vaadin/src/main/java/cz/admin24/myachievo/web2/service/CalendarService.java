/*
 * Copyright (c) 2013 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package cz.admin24.myachievo.web2.service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.client.util.store.DataStoreFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;

/**
 * Main class for the Calendar API command line sample.
 * Demonstrates how to make an authenticated API call using OAuth 2 helper classes.
 */
public class CalendarService {

  /**
   * Be sure to specify the name of your application. If the application name is {@code null} or
   * blank, the application will log a warning. Suggested format is "MyCompany-ProductName/1.0".
   */
  private static final String APPLICATION_NAME = "";

  /** Directory to store user credentials. */
  private static final java.io.File DATA_STORE_DIR =
      new java.io.File(System.getProperty("user.home"), ".store/calendar_sample");

  /**
   * Global instance of the {@link DataStoreFactory}. The best practice is to make it a single
   * globally shared instance across your application.
   */
  private static FileDataStoreFactory dataStoreFactory;

  /** Global instance of the JSON factory. */
//  private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
  private static final JsonFactory JSON_FACTORY = new JacksonFactory();

  /** Global instance of the HTTP transport. */
  private static HttpTransport httpTransport;

  @SuppressWarnings("unused")
  private static Calendar client;

  /** Authorizes the installed application to access user's protected data. */
  private static Credential authorize() throws Exception {
    // load client secrets
    GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
        new InputStreamReader(CalendarService.class.getResourceAsStream("/client_secrets.json")));
    if (clientSecrets.getDetails().getClientId().startsWith("Enter") ||
        clientSecrets.getDetails().getClientSecret().startsWith("Enter ")) {
      System.out.println(
          "Overwrite the src/main/resources/client_secrets.json file with the client secrets file "
          + "you downloaded from the Quickstart tool or manually enter your Client ID and Secret "
          + "from https://code.google.com/apis/console/?api=calendar#project:265728818031 "
          + "into src/main/resources/client_secrets.json");
      System.exit(1);
    }

    // Set up authorization code flow.
    // Ask for only the permissions you need. Asking for more permissions will
    // reduce the number of users who finish the process for giving you access
    // to their accounts. It will also increase the amount of effort you will
    // have to spend explaining to users what you are doing with their data.
    // Here we are listing all of the available scopes. You should remove scopes
    // that you are not actually using.
    Set<String> scopes = new HashSet<String>();
    scopes.add(CalendarScopes.CALENDAR);
    scopes.add(CalendarScopes.CALENDAR_READONLY);

    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
        httpTransport, JSON_FACTORY, clientSecrets, scopes)
        .setDataStoreFactory(dataStoreFactory)
        .build();
    // authorize
    return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
  }

  public static void main(String[] args) {
    try {
      // initialize the transport
      httpTransport = GoogleNetHttpTransport.newTrustedTransport();

      // initialize the data store factory
      dataStoreFactory = new FileDataStoreFactory(DATA_STORE_DIR);

      // authorization
      Credential credential = authorize();

      // set up global Calendar instance
      client = new Calendar.Builder(httpTransport, JSON_FACTORY, credential)
          .setApplicationName(APPLICATION_NAME).build();

      System.out.println("Success! Now add code here.");

    } catch (IOException e) {
      System.err.println(e.getMessage());
    } catch (Throwable t) {
      t.printStackTrace();
    }
    System.exit(1);
  }
}
//
//        // Set up the main Google+ class
//        Calendar gCal = new Calendar(httpTransport, jsonFactory, null);
////        Plus plus = new Plus(httpTransport, jsonFactory, credential);
//
//        // Make a request to access your profile and display it to console
////        Person profile = plus.people().get("me").execute();
////        System.out.println("ID: " + profile.getId());
////        System.out.println("Name: " + profile.getDisplayName());
////        System.out.println("Image URL: " + profile.getImage().getUrl());
////        System.out.println("Profile URL: " + profile.getUrl());
//        Events events = gCal.events();
//        try {
//            List list = events.list("cs.czech#holiday@group.v.calendar.google.com");
//            DateTime from = new DateTime(DateUtils.addMonths(new Date(), -3));
//            DateTime to = new DateTime(DateUtils.addMonths(new Date(), 3));
//            list.setTimeMin(from);
//            list.setTimeMax(to);
//            com.google.api.services.calendar.model.Events result = list.execute();
//            System.out.println(result);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//}
