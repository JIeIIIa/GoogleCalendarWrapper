package com.google.gcalendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Created by JIeIIIa on 30.11.2016.
 */
public class GCalendarAuthorizationTest {
    private static final String PRIVATE_KEY_P12 = "/CalendarApi-1ef3864ed125.p12";
    private static final String SERVICE_ACCOUNT_ID = "newcalendarapi@calendarapi-prog-kiev-ua.iam.gserviceaccount.com";

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void authorize() throws Exception {
        assertNotNull(GCalendarAuthorization.authorize(SERVICE_ACCOUNT_ID, PRIVATE_KEY_P12));
    }

    @Test
    public void getCalendarService() throws Exception {
        assertNotNull(GCalendarAuthorization.getCalendarServiceP12(SERVICE_ACCOUNT_ID, PRIVATE_KEY_P12));
    }

}