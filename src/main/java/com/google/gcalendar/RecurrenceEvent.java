package com.google.gcalendar;

import java.util.Collections;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by JIeIIIa on 13.12.2016.
 */
public class RecurrenceEvent {
    public static final int NEVER = 2048;
    public static final int HOURLY = 4096;
    public static final int DAILY = 8192;
    public static final int WEEKLY = 16384;
    public static final int MONTHLY = 327688;
    public static final int YEARLY = 65536;

    //const for BYDAY
    public static final int MONDAY = 16;
    public static final int TUESDAY = 32;
    public static final int WEDNESDAY = 64;
    public static final int THURSDAY = 128;
    public static final int FRIDAY = 256;
    public static final int SATURDAY = 512;
    public static final int SUNDAY = 1024;

    //const for BYMONTH
    public static final int JANUARY = 1;
    public static final int FEBRUARY = 2;
    public static final int MARCH = 3;
    public static final int APRIL = 4;
    public static final int MAY = 5;
    public static final int JUNE = 6;
    public static final int JYLY = 7;
    public static final int AUGUST = 8;
    public static final int SEPTEMBER = 9;
    public static final int OCTOBER = 10;
    public static final int NOVEMBER = 11;
    public static final int DECEMBER = 12;


    private int frequency;
    private int interval;
    private int byDay;
    private Set<Integer> byMonth;
    private Set<Integer> byMonthDay;
    private int frequencyCount;

    private Date startDate;
    private Date endDate;



    public RecurrenceEvent setFrequency(int frequency) throws GCalendarException {
        switch (frequency) {
            case DAILY:case WEEKLY:
            case MONTHLY:case YEARLY: {
                this.frequency = frequency;
                break;
            }
            default:
                throw new GCalendarException("Wrong frequency: " + frequency);
        }
        return this;
    }

    public void setInterval(int interval) throws GCalendarException {
        if (interval <= 0) {
            throw new GCalendarException("Wrong interval: " + interval);
        }
        this.interval = interval;
    }

    private Set<Integer> createNumberSet(int low, int up, String messageError, int ... args) throws GCalendarException {
        Set<Integer>localSet = new TreeSet<>();
        for (int arg : args) {
            if(arg < low || arg > up) throw new GCalendarException(messageError);
            localSet.add(arg);
        }
        return localSet;
    }

    public RecurrenceEvent setByMonth(int... months) throws GCalendarException {
        byMonth = createNumberSet(0, 12, "Wrong month number", months);
        return this;
    }

    public RecurrenceEvent setByMonthDay(int ... monthDays) throws GCalendarException {
        byMonthDay = createNumberSet(0, 31, "Wrong number day in month", monthDays);
        return this;
    }

    public RecurrenceEvent setByDay(int... days) throws GCalendarException {
        int byDayLocal = 0;
        for (int day : days) {
            switch (day) {
                case SUNDAY:case MONDAY:case TUESDAY:case WEDNESDAY:
                case THURSDAY:case FRIDAY:case SATURDAY: {
                    byDayLocal += day;
                    break;
                }
                default:
                    throw new GCalendarException("Wrong day constant");
            }
        }
        byDay = byDayLocal;
        return this;
    }

    public RecurrenceEvent setFrequencyCount(int frequencyCount) {
        this.frequencyCount = frequencyCount;
        return this;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public RecurrenceEvent setEndDate(Date endDate) {
        this.endDate = endDate;
        return this;
    }

    public int getFrequency() {
        return frequency;
    }

    public int getInterval() {
        return interval;
    }


    public boolean checkDays(int day) {
        switch (day) {
            case SUNDAY:case MONDAY:case TUESDAY:case WEDNESDAY:
            case THURSDAY:case FRIDAY:case SATURDAY: {

                return (day & byDay) > 0;
            }

        }
        return false;
    }


    public Set<Integer> getDays() {
        Set<Integer> daySet = new TreeSet<>();
        if (checkDays(MONDAY)) {
            daySet.add(MONDAY);
        }
        if (checkDays(TUESDAY)) {
            daySet.add(TUESDAY);
        }
        if (checkDays(WEDNESDAY)) {
            daySet.add(WEDNESDAY);
        }
        if (checkDays(THURSDAY)) {
            daySet.add(THURSDAY);
        }
        if (checkDays(FRIDAY)) {
            daySet.add(FRIDAY);
        }
        if (checkDays(SATURDAY)) {
            daySet.add(SATURDAY);
        }
        if (checkDays(SUNDAY)) {
            daySet.add(SUNDAY);
        }
        if (daySet.size() == 0) {
            return null;
        } else {
            return daySet;
        }
    }

    public Set<Integer> getByMonth() {
        if (byMonth == null) {
            return null;
        } else {
            return Collections.unmodifiableSet(byMonth);
        }
    }

    public Set<Integer> getByMonthDay() {
        if (byMonthDay == null) {
            return null;
        } else {
            return Collections.unmodifiableSet(byMonthDay);
        }
    }

    public int getFrequencyCount() {
        return frequencyCount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }


}
