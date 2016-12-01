package com.google.gcalendar;

import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GCalendar {
    private com.google.api.services.calendar.Calendar service;
    private String jsonCredential;
    private String calendarID;

    public GCalendar(String jsonCredential, String calendarID) {
        this.jsonCredential = jsonCredential;
        this.calendarID = calendarID;
    }

    public GCalendar connect() throws GCalendarException {
        try {
            service = GCalendarAuthorization.getCalendarService(jsonCredential);
        } catch (IOException e) {
            throw new GCalendarException("Failed connect", e);
        }
        return this;
    }

    public GCalendar disconnect() {
        service = null;
        return this;
    }

    public List<GEvent> getEvents(GEventChecker checker) throws GCalendarException {
        List<GEvent> list = new ArrayList<GEvent>();


        Events events;
        try {
            com.google.api.services.calendar.Calendar.Events.List readEvents = service.events().list(calendarID);
            if (checker.getStartAfterDate() != null) {
                readEvents = readEvents.setTimeMin(new com.google.api.client.util.DateTime(checker.getStartAfterDate()));
            }
            if (checker.getFinishBeforeDate() != null) {
                readEvents = readEvents.setTimeMax(new com.google.api.client.util.DateTime(checker.getFinishBeforeDate()));
            }
            if (checker.getTitle() != null) {
                readEvents = readEvents.setQ(checker.getTitle());
            }
            events = readEvents.execute();
        } catch (IOException e) {
            throw new GCalendarException("Can't get event list.", e);
        }

        List<Event> items = events.getItems();

        for (Event event : items) {
            GEvent current = Utils.toGEvent(event);
            if (checker.check(current)) {
                list.add(current);
            }
        }
        return list;
    }

}
