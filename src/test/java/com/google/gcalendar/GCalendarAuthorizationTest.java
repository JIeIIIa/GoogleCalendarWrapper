package com.google.gcalendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Created by JIeIIIa on 30.11.2016.
 */
public class GCalendarAuthorizationTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void authorize() throws Exception {
        assertNotNull(GCalendarAuthorization.authorize(Configuration.SERVICE_ACCOUNT_ID, Configuration.PRIVATE_KEY_P12));
    }

    @Test
    public void getCalendarService() throws Exception {
        assertNotNull(GCalendarAuthorization.getCalendarServiceP12(Configuration.SERVICE_ACCOUNT_ID, Configuration.PRIVATE_KEY_P12));
    }

}