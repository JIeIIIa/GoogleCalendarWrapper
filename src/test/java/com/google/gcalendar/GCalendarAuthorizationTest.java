package com.google.gcalendar;

import java.io.File;
import java.lang.reflect.Field;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by JIeIIIa on 30.11.2016.
 */
public class GCalendarAuthorizationTest {
    String jsonKey = "/client_secret_603944093158-tr62li4i083rrfi80mqojs3aioobl786.apps.googleusercontent.com.json";
    @org.junit.Before
    public void setUp() throws Exception {

    }

    @org.junit.After
    public void tearDown() throws Exception {

    }


    @org.junit.Test
    public void authorize() throws Exception {
        String credentialFileName = "StoredCredential";

        Field f = Class.forName(GCalendarAuthorization.class.getName()).getDeclaredField("DEFAULT_DATA_STORE_DIR");
        f.setAccessible(true);
        String path = (String) f.get(null);

        File credentialFile = new File(path, credentialFileName);
        if (credentialFile.exists()) {
            assertTrue(credentialFile.delete());
        }
        assertTrue(!credentialFile.exists());


        assertNotNull(GCalendarAuthorization.authorize(jsonKey));
    }

    @org.junit.Test
    public void getCalendarService() throws Exception {
        assertNotNull(GCalendarAuthorization.getCalendarService(jsonKey));
    }

}