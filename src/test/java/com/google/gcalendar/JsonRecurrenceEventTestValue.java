package com.google.gcalendar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JIeIIIa on 14.12.2016.
 */
public class JsonRecurrenceEventTestValue {
    private List<RecurrenceEventTestValue> list;

    public JsonRecurrenceEventTestValue() {
        list = new ArrayList<>();
    }

    public JsonRecurrenceEventTestValue(List<RecurrenceEventTestValue> sourceList) {
        list = new ArrayList<>(sourceList);
    }

    public List<RecurrenceEventTestValue> getList() {
        return list;
    }

    public void setList(List<RecurrenceEventTestValue> list) {
        this.list = list;
    }
}
