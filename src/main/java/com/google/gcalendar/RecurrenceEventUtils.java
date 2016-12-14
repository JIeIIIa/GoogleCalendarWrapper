package com.google.gcalendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        list.add("DTSTART;" + FORMAT_DATE.format(recurrenceEvent.getStartDate()));

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
        String line;

        line = list.get(0).replaceAll(" " , "");
        parseFirstLine(re, line);

        line = list.get(1).replaceAll(" ", "");
        parseSecondLine(re, line);

        return re;
    }

    private static void parseFirstLine(RecurrenceEvent re, String line) throws GCalendarException {
        Pattern pattern;
        Matcher matcher;
        if (!line.startsWith("DTSTART;")) {
            throw new GCalendarException("Parse error: DTSTART not found");
        }

        try {
            pattern = Pattern.compile("\\d{8}T\\d{6}");
            matcher = pattern.matcher(line);
            String date = "";
            if(matcher.find()) {
                date = matcher.group();
            }
            re.setStartDate(FORMAT_DATE.parse(date));
        } catch (Exception e) {
            throw new GCalendarException("Parse error: wrong StartDate format", e);
        }
    }

    private static void parseSecondLine(RecurrenceEvent re, String line) throws GCalendarException {
        if (!line.startsWith("RRULE:")) {
            throw new GCalendarException("Parse error: RRULE not found");
        }
        parseFrequency(re, line);
        parseFrequencyCount(re, line);
        parseEndDate(re, line);
        parseInterval(re, line);
        parseByMonth(re, line);
        parseByMonthDay(re, line);
        parseByDay(re, line);

    }

    private static void parseFrequency(RecurrenceEvent re, String line) throws GCalendarException {
        Pattern pattern = Pattern.compile("FREQ=\\w*");
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            String frequencyText = matcher.group()
                    .replaceAll("FREQ=", "")
                    .toUpperCase();
            int frequency;

            switch (frequencyText) {
                case "HOURLY":
                    frequency = RecurrenceEvent.HOURLY;
                    break;
                case "DAILY":
                    frequency = RecurrenceEvent.DAILY;
                    break;
                case "WEEKLY":
                    frequency = RecurrenceEvent.WEEKLY;
                    break;
                case "MONTHLY":
                    frequency = RecurrenceEvent.MONTHLY;
                    break;
                case "YEARLY":
                    frequency = RecurrenceEvent.YEARLY;
                    break;
                default:
                    frequency = -1;
            }
            re.setFrequency(frequency);
        }

    }

    private static int parseIntParameter(String line, String param, String errorMsg) throws GCalendarException {
        Pattern pattern = Pattern.compile(param+"\\d*");
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            int result;
            String frequencyCountText = matcher.group()
                    .replaceAll(param, "");
            try {
                result = Integer.parseInt(frequencyCountText);
            } catch (NumberFormatException e) {
                throw new GCalendarException(errorMsg, e);
            }
            return result;
        }
        return -1;
    }

    private static void parseFrequencyCount(RecurrenceEvent re, String line) throws GCalendarException {
        int parse = parseIntParameter(line, "COUNT=", "Parse error: wrong frequencyCount");
        if (parse != -1) {
            re.setFrequencyCount(parse);
        }
    }

    private static void parseInterval(RecurrenceEvent re, String line) throws GCalendarException {
        int parse = parseIntParameter(line, "INTERVAL=", "Parse error: wrong interval");
        if (parse != -1) {
            re.setInterval(parse);
        }
    }

    private static void parseEndDate(RecurrenceEvent re, String line) throws GCalendarException {
        Pattern pattern = Pattern.compile("UNTIL=\\d{8}T\\d{6}Z");
        Matcher matcher = pattern.matcher(line);

        if (matcher.find()) {
            pattern = Pattern.compile("\\d{8}T\\d{6}");
            matcher = pattern.matcher(line);
            if (matcher.find()) {
                try {
                    re.setEndDate(FORMAT_DATE.parse(matcher.group()));
                } catch (ParseException e) {
                    throw new GCalendarException("Parse error: wrong endDate format", e);
                }
            } else {
                throw new GCalendarException("Parse error: wrong endDate not found");
            }

        }
    }

    private static int[] parseIntArrayParameter(String line, String param, String errorMsg) throws GCalendarException {
        Pattern pattern = Pattern.compile(param+"(\\d(,\\d+)*)*");
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {

            String elementLine = matcher.group()
                    .replaceAll(param, "");
            String[] element = elementLine.split(",");
            int[] result = new int[element.length];
            try {
                for (int i = 0; i < element.length; i++) {
                    result[i] = Integer.parseInt(element[i]);
                }
            } catch (NumberFormatException e) {
                throw new GCalendarException(errorMsg, e);
            }
            return result;
        }
        return null;
    }

    private static void parseByMonth(RecurrenceEvent re, String line) throws GCalendarException {
        int[] parse = parseIntArrayParameter(line, "BYMONTH=", "Parse error: wrong BYMONTH parameters");
        if (parse != null) {
            re.setByMonth(parse);
        }
    }

    private static void parseByMonthDay(RecurrenceEvent re, String line) throws GCalendarException {
        int[] parse = parseIntArrayParameter(line, "BYMONTHDAY=", "Parse error: wrong BYMONTHDAY parameters");
        if (parse != null) {
            re.setByMonthDay(parse);
        }
    }

    private static void parseByDay(RecurrenceEvent re, String line) throws GCalendarException {
        Pattern pattern = Pattern.compile("BYDAY=(\\w\\w(,\\w\\w)*)*");
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            String elementLine = matcher.group()
                    .replaceAll("BYDAY=", "");
            String[] elements = elementLine.split(",");
            Set<Integer> days = new TreeSet<>();

            for (String s : elements) {
                switch (s) {
                    case"MO":
                        days.add(RecurrenceEvent.MONDAY);
                        break;
                    case"TU":
                        days.add(RecurrenceEvent.TUESDAY);
                        break;
                    case"WE":
                        days.add(RecurrenceEvent.WEDNESDAY);
                        break;
                    case"TH":
                        days.add(RecurrenceEvent.THURSDAY);
                        break;
                    case"FR":
                        days.add(RecurrenceEvent.FRIDAY);
                        break;
                    case"SA":
                        days.add(RecurrenceEvent.SATURDAY);
                        break;
                    case"SU":
                        days.add(RecurrenceEvent.SUNDAY);
                        break;
                    default:
                        throw new GCalendarException("Parse error: wrong BYDAY parameters '" + s + "'");
                }
            }
            int results[] = new int[days.size()];
            int i = 0;
            for (Integer day : days) {
                results[i++] = day;
            }
            re.setByDay(results);

        }

    }
}
