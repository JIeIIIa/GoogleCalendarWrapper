package com.google.gcalendar;

/**
 * Created by JIeIIIa on 30.11.2016.
 */
public class GCalendarException extends Exception {
    public GCalendarException() {
    }

    public GCalendarException(String message) {
        super(message);
    }

    public GCalendarException(String message, Throwable cause) {
        super(message, cause);
    }

    public GCalendarException(Throwable cause) {
        super(cause);
    }


}
