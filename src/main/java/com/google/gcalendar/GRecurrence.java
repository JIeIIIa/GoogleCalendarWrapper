package com.google.gcalendar;

import java.util.Collections;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by JIeIIIa on 13.12.2016.
 */
public class GRecurrence {
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
    public static final int JULY = 7;
    public static final int AUGUST = 8;
    public static final int SEPTEMBER = 9;
    public static final int OCTOBER = 10;
    public static final int NOVEMBER = 11;
    public static final int DECEMBER = 12;

    public static String constant(int value) {
        switch (value) {
            case JANUARY:
                return "JANUARY";
            case FEBRUARY:
                return "FEBRUARY";
            case MARCH:
                return "MARCH";
            case APRIL:
                return "APRIL";
            case MAY:
                return "MAY";
            case JUNE:
                return "JUNE";
            case JULY:
                return "JULY";
            case AUGUST:
                return "AUGUST";
            case SEPTEMBER:
                return "SEPTEMBER";
            case OCTOBER:
                return "OCTOBER";
            case NOVEMBER:
                return "NOVEMBER";
            case DECEMBER:
                return "DECEMBER";

            case MONDAY:
                return "MO";
            case TUESDAY:
                return "TU";
            case WEDNESDAY:
                return "WE";
            case THURSDAY:
                return "TH";
            case FRIDAY:
                return "FR";
            case SATURDAY:
                return "SA";
            case SUNDAY:
                return "SU";

            case HOURLY:
                return "HOURLY";
            case DAILY:
                return "DAILY";
            case WEEKLY:
                return "WEEKLY";
            case MONTHLY:
                return "MONTHLY";
            case YEARLY:
                return "YEARLY";
        }
        return null;
    }

    public static int constant(String value) {
        switch (value) {
            case "HOURLY":
                return GRecurrence.HOURLY;
            case "DAILY":
                return GRecurrence.DAILY;
            case "WEEKLY":
                return GRecurrence.WEEKLY;
            case "MONTHLY":
                return GRecurrence.MONTHLY;
            case "YEARLY":
                return GRecurrence.YEARLY;

            case "MO":
                return GRecurrence.MONDAY;
            case "TU":
                return GRecurrence.TUESDAY;
            case "WE":
                return GRecurrence.WEDNESDAY;
            case "TH":
                return GRecurrence.THURSDAY;
            case "FR":
                return GRecurrence.FRIDAY;
            case "SA":
                return GRecurrence.SATURDAY;
            case "SU":
                return GRecurrence.SUNDAY;

            default:
                return -1;
        }
    }

    public static String constantFull(int value) {
        switch (value) {
            case MONDAY:
                return "MONDAY";
            case TUESDAY:
                return "TUESDAY";
            case WEDNESDAY:
                return "WEDNESDAY";
            case THURSDAY:
                return "THURSDAY";
            case FRIDAY:
                return "FRIDAY";
            case SATURDAY:
                return "SATURDAY";
            case SUNDAY:
                return "SUNDAY";
            default:
                return constant(value);
        }
    }


    private int frequency;
    private int interval;
    private int byDay;
    private Set<Integer> byMonth;
    private Set<Integer> byMonthDay;
    private int frequencyCount;

    private Date startDate;
    private Date endDate;


    public GRecurrence setFrequency(int frequency) throws GCalendarException {
        switch (frequency) {
            case DAILY:
            case WEEKLY:
            case MONTHLY:
            case YEARLY: {
                this.frequency = frequency;
                break;
            }
            default:
                throw new GCalendarException("Wrong frequency: " + frequency);
        }
        return this;
    }

    public GRecurrence setInterval(int interval) throws GCalendarException {
        if (interval <= 0) {
            throw new GCalendarException("Wrong interval: " + interval);
        }
        this.interval = interval;
        return this;
    }

    private Set<Integer> createNumberSet(int low, int up, String messageError, int... args) throws GCalendarException {
        Set<Integer> localSet = new TreeSet<>();
        for (int arg : args) {
            if (arg < low || arg > up) throw new GCalendarException(messageError + ": " + arg);
            localSet.add(arg);
        }
        return localSet;
    }

    public GRecurrence setByMonth(int... months) throws GCalendarException {
        byMonth = createNumberSet(0, 12, "Wrong month number", months);
        return this;
    }

    public GRecurrence setByMonthDay(int... monthDays) throws GCalendarException {
        byMonthDay = createNumberSet(0, 31, "Wrong number day in month", monthDays);
        return this;
    }

    public static boolean isDayConstant(int value) {
        switch (value) {
            case SUNDAY:
            case MONDAY:
            case TUESDAY:
            case WEDNESDAY:
            case THURSDAY:
            case FRIDAY:
            case SATURDAY:
                return true;
            default:
                return false;
        }
    }

    public GRecurrence setByDay(int... days) throws GCalendarException {
        int byDayLocal = 0;
        for (int day : days) {
            if (isDayConstant(day)) {
                byDayLocal += day;
            } else {
                throw new GCalendarException("Wrong day constant");
            }
        }
        byDay = byDayLocal;
        return this;
    }

    public GRecurrence setFrequencyCount(int frequencyCount) {
        this.frequencyCount = frequencyCount;
        return this;
    }

    public GRecurrence setStartDate(Date startDate) {
        this.startDate = startDate;
        return this;
    }

    public GRecurrence setEndDate(Date endDate) {
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
        if (isDayConstant(day)) {
            return (day & byDay) > 0;
        } else {
            return false;
        }
    }


    public Set<Integer> getDays() {
        Set<Integer> daySet = new TreeSet<>();
        int[] allDays = {MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY};
        for (int day : allDays) {
            if (checkDays(day)) {
                daySet.add(day);
            }
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
