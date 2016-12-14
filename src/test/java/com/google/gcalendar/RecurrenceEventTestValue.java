package com.google.gcalendar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JIeIIIa on 14.12.2016.
 */
public class RecurrenceEventTestValue {
    private RecurrenceEvent recurrenceEvent;
    private List<String> correctResult;
    private String testName = "";

    public RecurrenceEventTestValue() {
        correctResult = new ArrayList<>();
    }

    public RecurrenceEventTestValue(RecurrenceEvent recurrenceEvent, List<String> correctResult) {
        this.recurrenceEvent = recurrenceEvent;
        this.correctResult = correctResult;
    }

    public RecurrenceEventTestValue(RecurrenceEvent recurrenceEvent, List<String> correctResult, String testName) {
        this.recurrenceEvent = recurrenceEvent;
        this.correctResult = correctResult;
        this.testName = testName;
    }

    public RecurrenceEvent getRecurrenceEvent() {
        return recurrenceEvent;
    }

    public void setRecurrenceEvent(RecurrenceEvent recurrenceEvent) {
        this.recurrenceEvent = recurrenceEvent;
    }

    public List<String> getCorrectResult() {
        return correctResult;
    }

    public void setCorrectResult(List<String> correctResult) {
        this.correctResult = correctResult;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }
}
