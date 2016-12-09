package com.google.gcalendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.lang.reflect.Field;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by JIeIIIa on 30.11.2016.
 */
public class GCalendarAuthorizationTest {
    String jsonKey = "/client_secret_603944093158-tr62li4i083rrfi80mqojs3aioobl786.apps.googleusercontent.com.json";
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }


    @Test
    public void authorize() throws Exception {
        String credentialFileName = "StoredCredential";

        File credentialFile = deleteCredential(credentialFileName);
        assertTrue(!credentialFile.exists());


        assertNotNull(GCalendarAuthorization.authorize(jsonKey));
    }

    @Ignore
    public File deleteCredential(String credentialFileName) throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException {
        Field f = Class.forName(GCalendarAuthorization.class.getName()).getDeclaredField("DEFAULT_DATA_STORE_DIR");
        f.setAccessible(true);
        String path = (String) f.get(null);

        File credentialFile = new File(path, credentialFileName);
        if (credentialFile.exists()) {
            assertTrue(credentialFile.delete());
        }
        return credentialFile;
    }

    @Test
    @Ignore
    public void deleteCredential() throws IllegalAccessException, NoSuchFieldException, ClassNotFoundException {
        String credentialFileName = "StoredCredential";
        deleteCredential(credentialFileName);
    }

    @Test
    public void getCalendarService() throws Exception {
        assertNotNull(GCalendarAuthorization.getCalendarService(jsonKey));
    }

    @Test
    @Ignore
    public void newTestAuth() throws Exception {
        com.google.api.services.calendar.Calendar calendar = GCalendarAuthorization.getCalendarServiceP12("");
        assertNotNull(calendar);

    }
}