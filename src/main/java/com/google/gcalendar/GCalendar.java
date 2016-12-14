package com.google.gcalendar;

import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GCalendar {
    private static final String PRIVATE_KEY_P12 = "D:\\Java\\GoogleAPI\\GoogleCalendarWrapper\\src\\main\\resources\\CalendarApi-1ef3864ed125.p12";
    private static final String SERVICE_ACCOUNT_ID = "newcalendarapi@calendarapi-prog-kiev-ua.iam.gserviceaccount.com";

    private com.google.api.services.calendar.Calendar service;
    private String privateKeyFromP12;
    private String serviceAccountId;
    private String calendarID;

    public GCalendar(String serviceAccountId, String privateKeyFromP12, String calendarID) {
        this.privateKeyFromP12 = privateKeyFromP12;
        this.serviceAccountId = serviceAccountId;
        this.calendarID = calendarID;
    }

    public GCalendar(String calendarID) {
        this.privateKeyFromP12 = PRIVATE_KEY_P12; //GCalendarAuthorization.class.getResource(PRIVATE_KEY_P12).getFile();
        this.serviceAccountId = SERVICE_ACCOUNT_ID;
        this.calendarID = calendarID;
    }

    public GCalendar connect() throws GCalendarException {
        service = GCalendarAuthorization.getCalendarServiceP12(serviceAccountId, privateKeyFromP12);
        return this;
    }

    public GCalendar disconnect() {
        service = null;
        return this;
    }

    public List<GEvent> getEvents(GEventChecker checker, boolean isGrouped) throws GCalendarException {
        List<GEvent> list = new ArrayList<GEvent>();

        Events events;
        try {
            com.google.api.services.calendar.Calendar.Events.List readEvents = Utils.GEventCheckerGoogleEventList(service.events().list(calendarID), checker);
            if (!isGrouped) {
                readEvents = readEvents.setSingleEvents(true);
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


    public List<GEvent> addEvents(List<GEvent> list) throws GCalendarException {
        List<GEvent> nonAddedEvents = new ArrayList<>();

        for (GEvent gEvent : list) {
            try {
                service.events().insert(calendarID, Utils.toGoogleEvent(gEvent)).execute();
            } catch (IOException e) {
                nonAddedEvents.add(gEvent);
            }

        }
        return nonAddedEvents;
    }

    public List<GEvent> updateEvents(List<GEvent> list) throws GCalendarException {
        List<GEvent> nonUpdatedEvents = new ArrayList<>();
        for (GEvent gEvent : list) {
            try {
                if (gEvent.getId() != null && gEvent.getId() != "") {
                    service.events().update(calendarID, gEvent.getId(), Utils.toGoogleEvent(gEvent)).execute();
                } else {
                    nonUpdatedEvents.add(gEvent);
                }
            } catch (IOException e) {
                nonUpdatedEvents.add(gEvent);
            }
        }
        return nonUpdatedEvents;
    }

    public List<GEvent> delEvents(List<GEvent> list) {
        List<GEvent> nonDeletedEvents = new ArrayList<>();
        for (GEvent gEvent : list) {
            try {
                if (gEvent.getId() == null || gEvent.getId().isEmpty()) {
                    nonDeletedEvents.add(gEvent);
                } else {
                    service.events().delete(calendarID, gEvent.getId()).execute();
                }
            } catch (IOException e) {
                nonDeletedEvents.add(gEvent);
            }
        }
        return nonDeletedEvents;
    }
}
