package com.google.gcalendar;

import com.google.api.services.calendar.model.Event;

import java.util.Date;

public class Utils {
    public static GEvent toGEvent(Event event) {
//        System.out.println(event.getStart().getDateTime().);
        GEvent result = new GEvent(event.getSummary(),
                                        new Date(event.getStart().getDateTime().getValue()),
                                        new Date(event.getEnd().getDateTime().getValue()));
        return result;
    }
}
