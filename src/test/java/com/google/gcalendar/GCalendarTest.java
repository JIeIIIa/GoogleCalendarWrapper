package com.google.gcalendar;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by JIeIIIa on 01.12.2016.
 */
public class GCalendarTest {
    private final String jsonKey = "/client_secret_603944093158-tr62li4i083rrfi80mqojs3aioobl786.apps.googleusercontent.com.json";
    private final String calendarID = "t2a31bigtnl18r4kpit26vrm84@group.calendar.google.com";

    SimpleDateFormat sdf;
    Date start;
    GCalendar gCalendar;

    @Before
    public void setUp() throws Exception {
        gCalendar = new GCalendar(jsonKey, calendarID).connect();
        sdf = new SimpleDateFormat("dd.MM.yyyy");
        start = new Date();
    }

    @Test
    @Ignore
    public void infoAboutEvent() throws Exception {
        String title = "Every week by 3";
        List<GEvent> list = gCalendar.getEvents(new GEventChecker()
                .setTitle(title)
                .setStartAfterDate(sdf.parse("01.03.2017"))
                .setFinishBeforeDate(sdf.parse("01.05.2017")));
        for (GEvent gEvent : list) {
            //System.out.println(gEvent);
        }
    }


    @Test
    public void delEvents() throws Exception {

        GEvent gEvent = new GEvent("Event from app", start, new Date(start.getTime() + 10000));
        addEvents();
        List<GEvent> readEvents = gCalendar.getEvents(new GEventChecker()
                        .setTitle(gEvent.getTitle())
                        .setStartAfterDate(gEvent.getStart())
                        .setFinishBeforeDate(gEvent.getFinish()));
        assertNotNull(readEvents);
        assertEquals(1, readEvents.size());
        List<GEvent> nonDeleted = gCalendar.delEvents(readEvents);
        assertNotNull(nonDeleted);
        assertEquals(0, nonDeleted.size());
    }

    @Ignore
    @Test
    public void addEvents() throws Exception {
        GEvent gEvent = new GEvent("Event from app", start, new Date(start.getTime() + 10000) );
        List<GEvent> list = new ArrayList<>();
        list.add(gEvent);
        List<GEvent> nonAddedEvent = gCalendar.addEvents(list);
        assertNotNull(nonAddedEvent.isEmpty());
    }

    @Test
    public void getEvents() throws Exception {
        List<GEvent> list = gCalendar.getEvents(new GEventChecker()
                                                        .setStartAfterDate(sdf.parse("01.10.2016"))
                                                        .setFinishBeforeDate(sdf.parse("01.01.2017"))
                                                        .setTitle("один")
        );

        assertTrue(list.size() > 0);
    }



}