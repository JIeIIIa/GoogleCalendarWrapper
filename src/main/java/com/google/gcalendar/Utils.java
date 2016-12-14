package com.google.gcalendar;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.InputStreamReader;
import java.util.Date;

public class Utils {
    public static GEvent toGEvent(Event event) throws GCalendarException {
        GEvent result = new GEvent(event.getSummary(),
                new Date(event.getStart().getDateTime().getValue()),
                new Date(event.getEnd().getDateTime().getValue()))
                .setId(event.getId());
        if (event.getRecurrence() != null) {
            result.setReccurence(GRecurrenceUtils.parseRecurrence(event.getRecurrence()));
        }
        return result;
    }

    public static Event toGoogleEvent(GEvent event) {
        Event result = new Event();
        result.setSummary(event.getTitle());

        if (event.getStart() != null) {
            result.setStart(toEventDateTime(event.getStart().getTime()));
        }
        if (event.getFinish() != null) {
            result.setEnd(toEventDateTime(event.getFinish().getTime()));
        }
        if (event.getId() != null) {
            result.setId(event.getId());
        }
        if (event.getRecurrenceList() != null) {
            result.setRecurrence(event.getRecurrenceList());
        }

        return result;
    }

    private static EventDateTime toEventDateTime(long n) {
        return new EventDateTime().setDateTime(new DateTime(n)).setTimeZone("Europe/Kiev");
    }

    public static com.google.api.services.calendar.Calendar.Events.List
    GEventCheckerGoogleEventList(com.google.api.services.calendar.Calendar.Events.List readEvents, GEventChecker checker) {

        if (checker.getStartAfterDate() != null) {
            readEvents = readEvents.setTimeMin(new com.google.api.client.util.DateTime(checker.getStartAfterDate()));
        }
        if (checker.getFinishBeforeDate() != null) {
            readEvents = readEvents.setTimeMax(new com.google.api.client.util.DateTime(checker.getFinishBeforeDate()));
        }
        if (checker.getTitle() != null) {
            readEvents = readEvents.setQ(checker.getTitle());
        }

        return readEvents;
    }

    public static String toJsonGEvent(JsonGEvent list) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(list);

        return json;
    }

    public static JsonGEvent fromJsonGEvent(InputStreamReader in) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonGEvent result = gson.fromJson(in, JsonGEvent.class);
        return result;
    }
}
