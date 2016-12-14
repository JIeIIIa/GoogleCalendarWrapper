package com.google.gcalendar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by JIeIIIa on 14.12.2016.
 */
public class RecurrenceEventUtils {
    static public final SimpleDateFormat FORMAT_DATE = new SimpleDateFormat("yyyyMMdd'T'HHmmss");

    public static String frequencyText(int frequency) {
        switch (frequency) {
            case RecurrenceEvent.HOURLY:
                return "HOURLY";
            case RecurrenceEvent.DAILY:
                return "DAILY";
            case RecurrenceEvent.WEEKLY:
                return "WEEKLY";
            case RecurrenceEvent.MONTHLY:
                return "MONTHLY";
            case RecurrenceEvent.YEARLY:
                return "YEARLY";

        }
        return "";
    }

    public static String byDayText(List<Integer> byDay) {
        if (byDay.size() == 0) {
            return "";
        }
        String[] days = new String[byDay.size()];
        for (int i = 0; i < byDay.size(); i++) {
            switch (byDay.get(i)) {
                case RecurrenceEvent.MONDAY:
                    days[i] = "MO";
                    break;
                case RecurrenceEvent.TUESDAY:
                    days[i] = "TU";
                    break;
                case RecurrenceEvent.WEDNESDAY:
                    days[i] = "WE";
                    break;
                case RecurrenceEvent.THURSDAY:
                    days[i] = "TH";
                    break;
                case RecurrenceEvent.FRIDAY:
                    days[i] = "FR";
                    break;
                case RecurrenceEvent.SATURDAY:
                    days[i] = "SA";
                    break;
                case RecurrenceEvent.SUNDAY:
                    days[i] = "SU";
                    break;
            }

        }
        StringBuilder sb = new StringBuilder(days[0]);
        for (int i = 1; i < days.length; i++) {
            sb.append(",").append(days[i]);
        }
        return sb.toString();
    }

    private static String ListToString(List<Integer> list) {
        if (list == null && list.size() == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder().append(list.get(0));
        for (int i = 1; i < list.size(); i++) {
            sb.append(",").append(list.get(i));
        }

        return sb.toString();
    }

    static List<String> recurrenceToList(RecurrenceEvent recurrenceEvent) throws GCalendarException {
        if (recurrenceEvent.getStartDate() == null) {
            throw new GCalendarException("Converting error: null startDate");
        }
        List<String> list = new ArrayList<>();
        list.add("DTSTART:" + FORMAT_DATE.format(recurrenceEvent.getStartDate()));

        StringBuilder sb = new StringBuilder("RRULE:");

        sb.append("FREQ=")
                .append(frequencyText(recurrenceEvent.getFrequency()));

        if (recurrenceEvent.getInterval() > 0) {
            sb.append(";INTERVAL=")
                    .append(recurrenceEvent.getInterval());
        }
        if (recurrenceEvent.getFrequencyCount() > 0) {
            sb.append(";COUNT=")
                    .append(recurrenceEvent.getFrequencyCount());
        }

        if (recurrenceEvent.getDays() != null) {
            sb.append(";BYDAY=")
                    .append(byDayText(new ArrayList<>(recurrenceEvent.getDays())));
        }

        if (recurrenceEvent.getByMonth() != null) {
            //TODO: add method byMonthToString
            sb.append(";BYMONTH=").append(ListToString(new ArrayList<>(recurrenceEvent.getByMonth())));
        }

        if (recurrenceEvent.getByMonthDay() != null) {
            //TODO: add method byMonthDayToString
            sb.append(";BYMONTHDAY=").append(ListToString(new ArrayList<>(recurrenceEvent.getByMonthDay())));
        }

        if (recurrenceEvent.getEndDate() != null) {
            sb.append(";UNTIL=")
                    .append(FORMAT_DATE.format(recurrenceEvent.getEndDate()))
                    .append("Z");
        }

        list.add(sb.toString());
        return list;
    }

    public static RecurrenceEvent parseRecurrence(List<String> list) throws GCalendarException {
        if (list == null || list.size() < 2) {
            throw new GCalendarException("Parse recurrence error: wrong input format");
        }
        RecurrenceEvent re = new RecurrenceEvent();


        return re;
    }

}
