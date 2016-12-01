package com.google.gcalendar;

import com.google.api.services.calendar.Calendar;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by JIeIIIa on 01.12.2016.
 */
public class GCalendarTest {
    private final String jsonKey = "/client_secret_603944093158-tr62li4i083rrfi80mqojs3aioobl786.apps.googleusercontent.com.json";
    private final String calendarID = "t2a31bigtnl18r4kpit26vrm84@group.calendar.google.com";
    GCalendar gCalendar;
    @Before
    public void setUp() throws Exception {
        gCalendar = new GCalendar(jsonKey, calendarID).connect();
    }

    @Test
    public void getEvents() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        List<GEvent> list = gCalendar.getEvents(new GEventChecker()
                                                        .setStartAfterDate(sdf.parse("01.10.2016"))
                                                        .setFinishBeforeDate(sdf.parse("01.01.2017"))
                                                        .setTitle("один")
        );

        assertTrue(list.size() > 0);
    }

}