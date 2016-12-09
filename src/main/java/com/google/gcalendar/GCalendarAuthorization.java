package com.google.gcalendar;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.client.googleapis.extensions.appengine.auth.oauth2.AppIdentityCredential;
import com.google.api.services.sqladmin.SQLAdmin;
import com.google.api.services.sqladmin.SQLAdminScopes;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.services.sqladmin.SQLAdminScopes;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.*;

public class GCalendarAuthorization {

   /* private static Collection<String> collections = new ArrayList<>();

    static {

        collections.add(SQLAdminScopes.SQLSERVICE_ADMIN);
    }
        private static AppIdentityCredential newCredential = new AppIdentityCredential(collections);
*/

    /** Application name. */
    private static final String APPLICATION_NAME =
            "CalendarApi";
    private static final String DEFAULT_DATA_STORE_DIR = new java.io.File(
            System.getProperty("user.home"), ".credentials/calendar-java-api").getPath();

    /** Directory to store user credentials for this application. */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(DEFAULT_DATA_STORE_DIR);

    /** Global instance of the {@link FileDataStoreFactory}. */
    private static FileDataStoreFactory DATA_STORE_FACTORY;

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY =
            JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport HTTP_TRANSPORT;

    /** Global instance of the scopes required by this quickstart.
     *
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/calendar-java-quickstart
     */
    private static final List<String> SCOPES =
            Arrays.asList(CalendarScopes.CALENDAR);

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Creates an authorized Credential object.
     * @return an authorized Credential object.
     * @throws IOException
     */
    public static Credential authorize(String clientSecretJSON) throws IOException {

        InputStream in = GCalendarAuthorization.class.getResourceAsStream(clientSecretJSON);

        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                        .setDataStoreFactory(DATA_STORE_FACTORY)
                        .setAccessType("offline")
                        .setApprovalPrompt("force")
                        .build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
//        System.out.println("Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());

        return credential;
    }

    /**
     * Build and return an authorized Calendar client service.
     * @return an authorized Calendar client service
     * @throws IOException
     */

    public static com.google.api.services.calendar.Calendar getCalendarService(String clientSecretJSON) throws IOException {
        Credential credential = authorize(clientSecretJSON);
        return new com.google.api.services.calendar.Calendar.Builder(
                                                                HTTP_TRANSPORT, JSON_FACTORY, credential)
                                                                .setApplicationName(APPLICATION_NAME)
                                                                .build();
    }

    public static com.google.api.services.calendar.Calendar getCalendarServiceP12(String clientSecretP12) throws GeneralSecurityException, IOException {

        GoogleCredential credential = new GoogleCredential.Builder()
                .setTransport(HTTP_TRANSPORT)
                .setJsonFactory(JSON_FACTORY)
//                .setServiceAccountId("calendarapi@calendarapi-prog-kiev-ua.iam.gserviceaccount.com")
//                .setServiceAccountPrivateKeyFromP12File(new File("D:\\Java\\GoogleAPI\\GoogleCalendarWrapper\\src\\main\\resources\\CalendarApi-77c68292720c.p12"))
                .setServiceAccountId("newcalendarapi@calendarapi-prog-kiev-ua.iam.gserviceaccount.com")
                .setServiceAccountPrivateKeyFromP12File(new File("D:\\Java\\GoogleAPI\\GoogleCalendarWrapper\\src\\main\\resources\\CalendarApi-1ef3864ed125.p12"))
                //.setServiceAccountScopes(Collections.singleton(SQLAdminScopes.SQLSERVICE_ADMIN))
                .setServiceAccountScopes(SCOPES)
//                .setServiceAccountUser("calendarapi@calendarapi-prog-kiev-ua.iam.gserviceaccount.com")
                //.setServiceAccountUser("newcalendarapi@calendarapi-prog-kiev-ua.iam.gserviceaccount.com")
                .build();
        SQLAdmin sqladmin =
                new SQLAdmin.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential).build();

        com.google.api.services.calendar.Calendar.Builder builder = new com.google.api.services.calendar.Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential);
        builder.setApplicationName(APPLICATION_NAME);
        com.google.api.services.calendar.Calendar client = builder.build();

        /*return new com.google.api.services.calendar.Calendar.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();*/
        return client;
    }
}
