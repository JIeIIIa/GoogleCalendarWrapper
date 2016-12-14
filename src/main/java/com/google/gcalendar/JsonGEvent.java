package com.google.gcalendar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JIeIIIa on 01.12.2016.
 */
public class JsonGEvent {
    private List<GEvent> list;

    public JsonGEvent() {
        list = new ArrayList<>();
    }

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

    public List<GEvent> getList() {
        return list;
    }

    public void setList(List<GEvent> list) {
        this.list = new ArrayList<GEvent>(list);
    }
}
