package com.google.gcalendar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by JIeIIIa on 14.12.2016.
 */
@RunWith(value = Parameterized.class)
public class RecurrenceEventUtilsTest {
    public static final String FILE_NAME = "D:\\Java\\GoogleAPI\\GoogleCalendarWrapper\\src\\test\\java\\com\\google\\gcalendar\\RecurrenceEvent.json";
    private RecurrenceEventTestValue testValue;

    public RecurrenceEventUtilsTest(RecurrenceEventTestValue testValue) {
        this.testValue = testValue;
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Parameterized.Parameters
    public static Collection<RecurrenceEventTestValue> data() throws FileNotFoundException {
        JsonRecurrenceEventTestValue jsonTestValue;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        File file = new File(FILE_NAME);

        jsonTestValue = gson.fromJson(new FileReader(file), JsonRecurrenceEventTestValue.class);
        return jsonTestValue.getList();
    }

    @Before
    public void setUp() throws Exception {


    }

    @After
    public void tearDown() throws Exception {

    }

    public void compareRecurrenceStrings(List<String> expected, List<String> target) {
        if (expected != null) {
            assertNotNull(target);
        }
        assertEquals(expected.size(), target.size());
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i).replaceAll(" ", ""), target.get(i));
        }
    }

    public void compereSet(Set<Integer> expected, Set<Integer> target) {
        if (expected == null) {
            assertNull(target);
        } else {
            assertNotNull(target);
            assertTrue(expected.containsAll(target));
            assertTrue(target.containsAll(expected));
        }
    }

    @Test
    public void convertingTest() throws Exception {
        System.out.print("-= " + testValue.getTestName() + "_converting =-");

        List<String> expectedRecurrence = testValue.getCorrectResult();
        List<String> targetRecurrence = RecurrenceEventUtils.recurrenceToList(testValue.getRecurrenceEvent());
        compareRecurrenceStrings(expectedRecurrence, targetRecurrence);
        System.out.println("\t\t\t\t\t\t\t\tOk!");

    }

    @Test
    public void parseTest() throws Exception {
        System.out.print("-= " + testValue.getTestName() + "_parse =-");

        List<String> expectedRecurrence = testValue.getCorrectResult();

        RecurrenceEvent expectedRecurenceEvent = testValue.getRecurrenceEvent();
        RecurrenceEvent targetRecurrenceEvent = RecurrenceEventUtils.parseRecurrence(expectedRecurrence);
        assertEquals(expectedRecurenceEvent.getStartDate(), targetRecurrenceEvent.getStartDate());
        assertEquals(expectedRecurenceEvent.getFrequencyCount(), targetRecurrenceEvent.getFrequencyCount());
        assertEquals(expectedRecurenceEvent.getFrequency(), targetRecurrenceEvent.getFrequency());
        assertEquals(expectedRecurenceEvent.getInterval(), targetRecurrenceEvent.getInterval());
        assertEquals(expectedRecurenceEvent.getEndDate(), targetRecurrenceEvent.getEndDate());

        Set<Integer> expected;
        Set<Integer> target;
        expected = expectedRecurenceEvent.getDays();
        target = targetRecurrenceEvent.getDays();
        compereSet(expected, target);


        expected = expectedRecurenceEvent.getByMonth();
        target = targetRecurrenceEvent.getByMonth();
        compereSet(expected, target);

        expected = expectedRecurenceEvent.getByMonthDay();
        target = targetRecurrenceEvent.getByMonthDay();
        compereSet(expected, target);

        System.out.println("\t\t\t\t\t\t\t\t\t\tOk!");

    }

}