package com.google.gcalendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by JIeIIIa on 14.12.2016.
 */
public class RecurrenceEventUtilsTest {
    public SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    public void compareRecurresceString(List<String> result, String first, String second) {
        assertNotNull(result);
        assertEquals(2, result.size());

        String line = result.get(0);
        assertEquals(first, line);

        line = result.get(1);
        assertEquals(second, line);
    }

    @Test
    public void recurrenceToList01() throws Exception {
        RecurrenceEvent recurrence = new RecurrenceEvent();
        recurrence.setStartDate(sdf.parse("02.09.1997 09:00:00"));
        recurrence.setFrequency(RecurrenceEvent.DAILY);
        recurrence.setFrequencyCount(10);

        compareRecurresceString(RecurrenceEventUtils.recurrenceToList(recurrence),
                "DTSTART:19970902T090000",
                "RRULE:FREQ=DAILY;COUNT=10");
    }

    @Test
    public void recurrenceToList02() throws Exception {
        RecurrenceEvent recurrence = new RecurrenceEvent();
        recurrence.setStartDate(sdf.parse("02.09.1997 09:00:00"));
        recurrence.setFrequency(RecurrenceEvent.DAILY);
        recurrence.setEndDate(sdf.parse("24.12.1997 00:00:00"));

        compareRecurresceString(RecurrenceEventUtils.recurrenceToList(recurrence),
                "DTSTART:19970902T090000",
                "RRULE:FREQ=DAILY;UNTIL=19971224T000000Z");

    }

    @Test
    public void recurrenceToList03() throws Exception {
        RecurrenceEvent recurrence = new RecurrenceEvent();
        recurrence.setStartDate(sdf.parse("02.09.1997 09:00:00"));
        recurrence.setFrequency(RecurrenceEvent.DAILY);
        recurrence.setInterval(2);

        compareRecurresceString(RecurrenceEventUtils.recurrenceToList(recurrence),
                "DTSTART:19970902T090000",
                "RRULE:FREQ=DAILY;INTERVAL=2");
    }

    @Test
    public void recurrenceToList04() throws Exception {
        RecurrenceEvent recurrence = new RecurrenceEvent();
        recurrence.setStartDate(sdf.parse("02.09.1997 09:00:00"));
        recurrence.setFrequency(RecurrenceEvent.DAILY);
        recurrence.setInterval(10);
        recurrence.setFrequencyCount(5);

        compareRecurresceString(RecurrenceEventUtils.recurrenceToList(recurrence),
                "DTSTART:19970902T090000",
                "RRULE:FREQ=DAILY;INTERVAL=10;COUNT=5");
    }

    @Test
    public void recurrenceToList05() throws Exception {
        RecurrenceEvent recurrence = new RecurrenceEvent();
        recurrence.setStartDate(sdf.parse("01.01.1998 09:00:00"));
        recurrence.setFrequency(RecurrenceEvent.DAILY);
        recurrence.setByMont(RecurrenceEvent.JANUARY);
        recurrence.setEndDate(sdf.parse("31.01.2000 14:00:00"));

        compareRecurresceString(RecurrenceEventUtils.recurrenceToList(recurrence),
                "DTSTART:19980101T090000",
                "RRULE:FREQ=DAILY;BYMONTH=1;UNTIL=20000131T140000Z");
    }

    @Test
    public void recurrenceToList06() throws Exception {
        RecurrenceEvent recurrence = new RecurrenceEvent();
        recurrence.setStartDate(sdf.parse("02.09.1997 09:00:00"));
        recurrence.setFrequency(RecurrenceEvent.WEEKLY);
        recurrence.setFrequencyCount(10);

        compareRecurresceString(RecurrenceEventUtils.recurrenceToList(recurrence),
                "DTSTART:19970902T090000",
                "RRULE:FREQ=WEEKLY;COUNT=10");
    }

    @Test
    public void recurrenceToList07() throws Exception {
        RecurrenceEvent recurrence = new RecurrenceEvent();
        recurrence.setStartDate(sdf.parse("02.09.1997 09:00:00"));
        recurrence.setFrequency(RecurrenceEvent.WEEKLY);
        recurrence.setEndDate(sdf.parse("24.12.1997 00:00:00"));

        compareRecurresceString(RecurrenceEventUtils.recurrenceToList(recurrence),
                "DTSTART:19970902T090000",
                "RRULE:FREQ=WEEKLY;UNTIL=19971224T000000Z");
    }

    @Test
    public void recurrenceToList08() throws Exception {
        RecurrenceEvent recurrence = new RecurrenceEvent();
        recurrence.setStartDate(sdf.parse("02.09.1997 09:00:00"));
        recurrence.setFrequency(RecurrenceEvent.WEEKLY);
        recurrence.setByDay(RecurrenceEvent.TUESDAY, RecurrenceEvent.THURSDAY);
        recurrence.setEndDate(sdf.parse("07.10.1997 00:00:00"));

        compareRecurresceString(RecurrenceEventUtils.recurrenceToList(recurrence),
                "DTSTART:19970902T090000",
                "RRULE:FREQ=WEEKLY;BYDAY=TU,TH;UNTIL=19971007T000000Z");
    }

    @Test
    public void recurrenceToList09() throws Exception {
        RecurrenceEvent recurrence = new RecurrenceEvent();
        recurrence.setStartDate(sdf.parse("02.09.1997 09:00:00"));
        recurrence.setFrequency(RecurrenceEvent.WEEKLY);
        recurrence.setByDay(RecurrenceEvent.TUESDAY, RecurrenceEvent.THURSDAY);
        recurrence.setFrequencyCount(10);

        compareRecurresceString(RecurrenceEventUtils.recurrenceToList(recurrence),
                "DTSTART:19970902T090000",
                "RRULE:FREQ=WEEKLY;COUNT=10;BYDAY=TU,TH");
    }

    @Test
    public void recurrenceToList13() throws Exception {
        RecurrenceEvent recurrence = new RecurrenceEvent();
        recurrence.setStartDate(sdf.parse("02.09.1997 09:00:00"));
        recurrence.setFrequency(RecurrenceEvent.WEEKLY);
        recurrence.setByDay(RecurrenceEvent.MONDAY, RecurrenceEvent.SATURDAY, RecurrenceEvent.WEDNESDAY);
        recurrence.setInterval(2);
        recurrence.setEndDate(sdf.parse("14.12.2016 08:32:14"));

        compareRecurresceString(RecurrenceEventUtils.recurrenceToList(recurrence),
                "DTSTART:19970902T090000",
                "RRULE:FREQ=WEEKLY;INTERVAL=2;BYDAY=MO,WE,SA;UNTIL=20161214T083214Z");
    }

    @Test
    public void recurrenceToList14() throws Exception {
        RecurrenceEvent recurrence = new RecurrenceEvent();
        recurrence.setStartDate(sdf.parse("02.01.2016 09:00:00"));
        recurrence.setFrequency(RecurrenceEvent.MONTHLY);
        recurrence.setByMonthDay(1, 5, 10, 11, 15);
        recurrence.setEndDate(sdf.parse("14.12.2016 08:32:14"));

        compareRecurresceString(RecurrenceEventUtils.recurrenceToList(recurrence),
                "DTSTART:20160102T090000",
                "RRULE:FREQ=MONTHLY;BYMONTHDAY=1,5,10,11,15;UNTIL=20161214T083214Z");
    }


    @Test
    public void recurrenceToList15() throws Exception {
        RecurrenceEvent recurrence = new RecurrenceEvent();
        recurrence.setStartDate(sdf.parse("02.09.1997 09:00:00"));
        recurrence.setFrequency(RecurrenceEvent.MONTHLY);
        recurrence.setByMonthDay(10, 11, 13, 9, 8, 7, 12);
        recurrence.setByDay(RecurrenceEvent.SATURDAY);

        compareRecurresceString(RecurrenceEventUtils.recurrenceToList(recurrence),
                "DTSTART:19970902T090000",
                "RRULE:FREQ=MONTHLY;BYDAY=SA;BYMONTHDAY=7,8,9,10,11,12,13");
    }

    @Test
    public void recurrenceToList16() throws Exception {
        RecurrenceEvent recurrence = new RecurrenceEvent();
        recurrence.setStartDate(sdf.parse("02.09.1997 09:00:00"));
        recurrence.setFrequency(RecurrenceEvent.YEARLY);
        recurrence.setByMont(RecurrenceEvent.NOVEMBER);
        recurrence.setByMonthDay(6, 7, 5, 4, 3, 2, 8);
        recurrence.setByDay(RecurrenceEvent.TUESDAY);
        recurrence.setInterval(4);

        compareRecurresceString(RecurrenceEventUtils.recurrenceToList(recurrence),
                "DTSTART:19970902T090000",
                "RRULE:FREQ=YEARLY;INTERVAL=4;BYDAY=TU;BYMONTH=11;BYMONTHDAY=2,3,4,5,6,7,8");
    }

    @Test
    public void toListException() throws Exception{
        RecurrenceEvent recurrence = new RecurrenceEvent();
        recurrence.setFrequency(RecurrenceEvent.YEARLY);
        recurrence.setByMont(RecurrenceEvent.NOVEMBER);
        recurrence.setByMonthDay(6, 7, 5, 4, 3, 2, 8);
        recurrence.setByDay(RecurrenceEvent.TUESDAY);
        recurrence.setInterval(4);

        exception.expect(GCalendarException.class);
        exception.expectMessage("Converting error: null startDate");
        RecurrenceEventUtils.recurrenceToList(recurrence);
    }

}