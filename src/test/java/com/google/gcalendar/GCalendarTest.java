package com.google.gcalendar;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by JIeIIIa on 01.12.2016.
 */
public class GCalendarTest {

    SimpleDateFormat sdf;
    Date start;
    GCalendar gCalendar;

    @Before
    public void setUp() throws Exception {
        gCalendar = new GCalendar(Configuration.CALENDAR_ID).connect();
        sdf = new SimpleDateFormat("dd.MM.yyyy");
        start = new Date();
    }



    @Test
    public void delEvents() throws Exception {

        GEvent gEvent = new GEvent("Event from GCalendarApp", start, new Date(start.getTime() + 10000));
        addEvents();
        List<GEvent> readEvents = gCalendar.getEvents(new GEventChecker()
                        .setTitle(gEvent.getTitle())
                        .setStartAfterDate(gEvent.getStart())
                        .setFinishBeforeDate(gEvent.getFinish()), false);
        assertNotNull(readEvents);
        assertEquals(1, readEvents.size());
        List<GEvent> nonDeleted = gCalendar.delEvents(readEvents);
        assertNotNull(nonDeleted);
        assertEquals(0, nonDeleted.size());
    }

    @Ignore
    @Test
    public void addEvents() throws Exception {
        GEvent gEvent = new GEvent("Event from GCalendarApp", start, new Date(start.getTime() + 10000) );
        List<GEvent> list = new ArrayList<>();
        list.add(gEvent);
        List<GEvent> nonAddedEvent = gCalendar.addEvents(list);
        assertEquals(0, nonAddedEvent.size());
    }

    @Test
    public void getEvents() throws Exception {
        List<GEvent> list = gCalendar.getEvents(new GEventChecker()
                                                        .setStartAfterDate(sdf.parse("01.10.2016"))
                                                        .setFinishBeforeDate(sdf.parse("01.06.2017"))
                                                        .setTitle("Every week by 3"),
                                                true
        );
        for (GEvent gEvent : list) {
            System.out.println(gEvent);
        }

        assertTrue(list.size() > 0);
    }

    @Test
    public void updateEvent() throws Exception {
        String titleEvents = "UPDATE: Event from  GCalendarApp";
        GEvent gEvent = new GEvent(titleEvents, start, new Date(start.getTime() + 10000) );
        List<GEvent> list = new ArrayList<>();
        list.add(gEvent);
        List<GEvent> nonAddedEvent = gCalendar.addEvents(list);
        assertEquals(0, nonAddedEvent.size());

        List<GEvent> newList = gCalendar.getEvents(new GEventChecker()
                        .setTitle(titleEvents),
                true
        );
        assertNotNull(newList);
        assertEquals(1, newList.size());

        for (GEvent event : newList) {
            event.setFinish(sdf.parse("01.05.2017"));
        }
        List<GEvent> res = gCalendar.updateEvents(newList);
        assertEquals(0, res.size());

        list = gCalendar.getEvents(new GEventChecker().setTitle(titleEvents), false);
        for (GEvent event : list) {
            assertEquals(sdf.parse("01.05.2017"), event.getFinish());
            res = gCalendar.delEvents(Arrays.asList(event));
            assertEquals(0, res.size());
        }


    }



}