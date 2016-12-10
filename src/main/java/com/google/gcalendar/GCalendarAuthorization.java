package com.google.gcalendar;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.CalendarScopes;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;

public class GCalendarAuthorization {

    /** Application name. */
    private static final String APPLICATION_NAME = "CalendarApi";

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport HTTP_TRANSPORT;
    private static Throwable HTTP_TRANSPORT_EXCEPTION = null;

    /** Global instance of the scopes required by this application.
     */
    private static final List<String> SCOPES = Arrays.asList(CalendarScopes.CALENDAR);

    static {
        HTTP_TRANSPORT_EXCEPTION = setHttpTransport();
    }

    private static Throwable setHttpTransport() {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        } catch (Throwable t) {
             return t;
        }
        return null;
    }

    /**
     * Creates an authorized Credential object.
     * @return an authorized Credential object.
     * @throws IOException
     */
    public static Credential authorize(String serviceAccountId, String privateKeyFromP12) throws GCalendarException {
        if (HTTP_TRANSPORT_EXCEPTION != null) {
            HTTP_TRANSPORT_EXCEPTION = setHttpTransport();
            if (HTTP_TRANSPORT_EXCEPTION != null) {
                throw new GCalendarException("Authorization error", HTTP_TRANSPORT_EXCEPTION);
            }
        }

        GoogleCredential credential;
        try {
            credential = new GoogleCredential.Builder()
                    .setTransport(HTTP_TRANSPORT)
                    .setJsonFactory(JSON_FACTORY)
                    .setServiceAccountId(serviceAccountId)
                    .setServiceAccountPrivateKeyFromP12File(new File(privateKeyFromP12))
                    .setServiceAccountScopes(SCOPES)
                    .build();
        } catch (GeneralSecurityException | IOException e) {
            throw new GCalendarException("Credential error", e);
        }

        return credential;
    }

    /**
     * Build and return an authorized Calendar client service.
     * @return an authorized Calendar client service
     * @throws IOException,
     * @throws GeneralSecurityException
     */

    public static com.google.api.services.calendar.Calendar getCalendarServiceP12(String serviceAccountId, String privateKeyFromP12) throws GCalendarException {

        Credential credential = authorize(serviceAccountId, privateKeyFromP12);

        com.google.api.services.calendar.Calendar.Builder builder = new com.google.api.services.calendar.Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential);
        builder.setApplicationName(APPLICATION_NAME);
        com.google.api.services.calendar.Calendar client = builder.build();

        return client;
    }
}
