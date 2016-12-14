package com.google.gcalendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by JIeIIIa on 14.12.2016.
 */
public class GRecurrenceUtils {
    static public final SimpleDateFormat FORMAT_DATE = new SimpleDateFormat("yyyyMMdd'T'HHmmss");

    public static String byDayText(List<Integer> byDay) {
        if (byDay.size() == 0) {
            return "";
        }
        String[] days = new String[byDay.size()];
        for (int i = 0; i < byDay.size(); i++) {
            days[i] = GRecurrence.constant(byDay.get(i));
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

    static List<String> toList(GRecurrence gRecurrence) throws GCalendarException {
        List<String> list = new ArrayList<>();
        if (gRecurrence.getStartDate() != null) {
//            DTSTART;TZID=Europe/Kiev:
            list.add("DTSTART;" + FORMAT_DATE.format(gRecurrence.getStartDate()));
        }

        StringBuilder sb = new StringBuilder("RRULE:");

        sb.append("FREQ=")
                .append(GRecurrence.constant(gRecurrence.getFrequency()));

        if (gRecurrence.getInterval() > 0) {
            sb.append(";INTERVAL=")
                    .append(gRecurrence.getInterval());
        }
        if (gRecurrence.getFrequencyCount() > 0) {
            sb.append(";COUNT=")
                    .append(gRecurrence.getFrequencyCount());
        }

        if (gRecurrence.getDays() != null) {
            sb.append(";BYDAY=")
                    .append(byDayText(new ArrayList<>(gRecurrence.getDays())));
        }

        if (gRecurrence.getByMonth() != null) {
            //TODO: add method byMonthToString
            sb.append(";BYMONTH=").append(ListToString(new ArrayList<>(gRecurrence.getByMonth())));
        }

        if (gRecurrence.getByMonthDay() != null) {
            //TODO: add method byMonthDayToString
            sb.append(";BYMONTHDAY=").append(ListToString(new ArrayList<>(gRecurrence.getByMonthDay())));
        }

        if (gRecurrence.getEndDate() != null) {
            sb.append(";UNTIL=")
                    .append(FORMAT_DATE.format(gRecurrence.getEndDate()))
                    .append("Z");
        }

        list.add(sb.toString());
        return list;
    }

    public static GRecurrence parseRecurrence(List<String> list) throws GCalendarException {
        if (list == null || list.size() == 0) {
            String msg = "Parse recurrence error: input data not found";
            throw new GCalendarException(msg);
        }
        GRecurrence gRecurrence = new GRecurrence();
        String line;


        if (list.size() > 1) {
            line = list.get(0).replaceAll(" ", "");
            parseFirstLine(gRecurrence, line);

            line = list.get(1).replaceAll(" ", "");
            parseSecondLine(gRecurrence, line);
        } else {
            line = list.get(0).replaceAll(" ", "");
            parseSecondLine(gRecurrence, line);
        }

        return gRecurrence;
    }

    private static void parseFirstLine(GRecurrence gRecurrence, String line) throws GCalendarException {
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
            gRecurrence.setStartDate(FORMAT_DATE.parse(date));
        } catch (Exception e) {
            throw new GCalendarException("Parse error: wrong StartDate format", e);
        }
    }

    private static void parseSecondLine(GRecurrence gRecurrence, String line) throws GCalendarException {
        if (!line.startsWith("RRULE:")) {
            throw new GCalendarException("Parse error: RRULE not found");
        }
        parseFrequency(gRecurrence, line);
        parseFrequencyCount(gRecurrence, line);
        parseEndDate(gRecurrence, line);
        parseInterval(gRecurrence, line);
        parseByMonth(gRecurrence, line);
        parseByMonthDay(gRecurrence, line);
        parseByDay(gRecurrence, line);

    }

    private static void parseFrequency(GRecurrence re, String line) throws GCalendarException {
        Pattern pattern = Pattern.compile("FREQ=\\w*");
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            String frequencyText = matcher.group()
                    .replaceAll("FREQ=", "")
                    .toUpperCase();
            int frequency = GRecurrence.constant(frequencyText);
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

    private static void parseFrequencyCount(GRecurrence gRecurrence, String line) throws GCalendarException {
        int parse = parseIntParameter(line, "COUNT=", "Parse error: wrong frequencyCount");
        if (parse != -1) {
            gRecurrence.setFrequencyCount(parse);
        }
    }

    private static void parseInterval(GRecurrence gRecurrence, String line) throws GCalendarException {
        int parse = parseIntParameter(line, "INTERVAL=", "Parse error: wrong interval");
        if (parse != -1) {
            gRecurrence.setInterval(parse);
        }
    }

    private static void parseEndDate(GRecurrence gRecurrence, String line) throws GCalendarException {
        Pattern pattern = Pattern.compile("UNTIL=\\d{8}T\\d{6}Z");
        Matcher matcher = pattern.matcher(line);

        if (matcher.find()) {
            pattern = Pattern.compile("\\d{8}T\\d{6}");
            matcher = pattern.matcher(line);
            if (matcher.find()) {
                try {
                    gRecurrence.setEndDate(FORMAT_DATE.parse(matcher.group()));
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

    private static void parseByMonth(GRecurrence gRecurrence, String line) throws GCalendarException {
        int[] parse = parseIntArrayParameter(line, "BYMONTH=", "Parse error: wrong BYMONTH parameters");
        if (parse != null) {
            gRecurrence.setByMonth(parse);
        }
    }

    private static void parseByMonthDay(GRecurrence gRecurrence, String line) throws GCalendarException {
        int[] parse = parseIntArrayParameter(line, "BYMONTHDAY=", "Parse error: wrong BYMONTHDAY parameters");
        if (parse != null) {
            gRecurrence.setByMonthDay(parse);
        }
    }

    private static void parseByDay(GRecurrence gRecurrence, String line) throws GCalendarException {
        Pattern pattern = Pattern.compile("BYDAY=(\\w\\w(,\\w\\w)*)*");
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            String elementLine = matcher.group()
                    .replaceAll("BYDAY=", "");
            String[] elements = elementLine.split(",");
            Set<Integer> days = new TreeSet<>();

            for (String s : elements) {
                int i = GRecurrence.constant(s);
                if (i != -1) {
                    days.add(i);
                } else {
                    throw new GCalendarException("Parse error: wrong BYDAY parameters '" + s + "'");
                }
            }
            int results[] = new int[days.size()];
            int i = 0;
            for (Integer day : days) {
                results[i++] = day;
            }
            gRecurrence.setByDay(results);
        }
    }
}