package com.google.gcalendar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JIeIIIa on 01.12.2016.
 */
public class JsonGEvent {
    private final List<GEvent> list;

    public JsonGEvent(List<GEvent> sourceList) {
        list = new ArrayList<GEvent>(sourceList);
    }

    public JsonGEvent(List<GEvent> sourceList, GEventCheckable eventChecker) throws GCalendarException {
        if (eventChecker == null) {
            throw new GCalendarException("");
        } else if (sourceList == null) {
            list = null;
        } else {
            list = new ArrayList<GEvent>();
            for (GEvent gEvent : sourceList) {
                if (eventChecker.check(gEvent)) {
                    list.add(gEvent);
                }
            }
        }
    }
}
