package com.google.gcalendar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JIeIIIa on 14.12.2016.
 */
public class GRecurrenceTestValue {
    private GRecurrence gRecurrence;
    private List<String> correctResult;
    private String testName = "";

    public GRecurrenceTestValue() {
        correctResult = new ArrayList<>();
    }

    public GRecurrenceTestValue(GRecurrence gRecurrence, List<String> correctResult) {
        this.gRecurrence = gRecurrence;
        this.correctResult = correctResult;
    }

    public GRecurrenceTestValue(GRecurrence gRecurrence, List<String> correctResult, String testName) {
        this.gRecurrence = gRecurrence;
        this.correctResult = correctResult;
        this.testName = testName;
    }

    public GRecurrence getgRecurrence() {
        return gRecurrence;
    }

    public void setgRecurrence(GRecurrence gRecurrence) {
        this.gRecurrence = gRecurrence;
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
