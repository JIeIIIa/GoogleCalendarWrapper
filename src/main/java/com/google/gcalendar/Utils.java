package com.google.gcalendar;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

import java.util.Date;

public class Utils {
    public static GEvent toGEvent(Event event) {
//        System.out.println(event.getStart().getDateTime().);
        GEvent result = new GEvent(event.getSummary(),
                                        new Date(event.getStart().getDateTime().getValue()),
                                        new Date(event.getEnd().getDateTime().getValue()))
                                .setId(event.getId());
        return result;
    }

    public static Event toGoogleEvent(GEvent event) {
        Event result = new Event();
        result = result.setSummary(event.getTitle());

        if (event.getStart() != null) {
            result = result.setStart(toEventDateTime(event.getStart().getTime()));
        }
        if (event.getFinish() != null) {
            result = result.setEnd(toEventDateTime(event.getFinish().getTime()));
        }
        if (event.getId() != null) {
            result = result.setId(event.getId());
        }
        return result;
    }

    private static EventDateTime toEventDateTime(long n) {
        return new EventDateTime().setDateTime(new DateTime(n));
    }
}
