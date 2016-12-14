package com.google.gcalendar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JIeIIIa on 14.12.2016.
 */
public class JsonGRecurrenceTestValue {
    private List<GRecurrenceTestValue> list;

    public JsonGRecurrenceTestValue() {
        list = new ArrayList<>();
    }

    public JsonGRecurrenceTestValue(List<GRecurrenceTestValue> sourceList) {
        list = new ArrayList<>(sourceList);
    }

    public List<GRecurrenceTestValue> getList() {
        return list;
    }

    public void setList(List<GRecurrenceTestValue> list) {
        this.list = list;
    }
}
