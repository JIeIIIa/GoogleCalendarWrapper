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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by JIeIIIa on 14.12.2016.
 */
@RunWith(value = Parameterized.class)
public class GRecurrenceUtilsTest {

    private GRecurrenceTestValue testValue;

    public GRecurrenceUtilsTest(GRecurrenceTestValue testValue) {
        this.testValue = testValue;
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Parameterized.Parameters
    public static Collection<GRecurrenceTestValue> data() throws FileNotFoundException {
        JsonGRecurrenceTestValue jsonTestValue;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        File file = new File(Configuration.RECURRENCE_EVENT_TEST_JSON);

        jsonTestValue = gson.fromJson(new FileReader(file), JsonGRecurrenceTestValue.class);
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

        List<String> expectedGRecurrence = testValue.getCorrectResult();
        List<String> targetRecurrence = GRecurrenceUtils.toList(testValue.getgRecurrence());
        compareRecurrenceStrings(expectedGRecurrence, targetRecurrence);
        System.out.println("\t\t\t\t\t\t\t\tOk!");

    }

    @Test
    public void parseTest() throws Exception {
        System.out.print("-= " + testValue.getTestName() + "_parse =-");

        List<String> expectedRecurrence = testValue.getCorrectResult();

        GRecurrence expectedGRecurrence = testValue.getgRecurrence();
        GRecurrence targetGRecurrence = GRecurrenceUtils.parseRecurrence(expectedRecurrence);
        assertEquals(expectedGRecurrence.getStartDate(), targetGRecurrence.getStartDate());
        assertEquals(expectedGRecurrence.getFrequencyCount(), targetGRecurrence.getFrequencyCount());
        assertEquals(expectedGRecurrence.getFrequency(), targetGRecurrence.getFrequency());
        assertEquals(expectedGRecurrence.getInterval(), targetGRecurrence.getInterval());
        assertEquals(expectedGRecurrence.getEndDate(), targetGRecurrence.getEndDate());

        Set<Integer> expected;
        Set<Integer> target;
        expected = expectedGRecurrence.getDays();
        target = targetGRecurrence.getDays();
        compereSet(expected, target);


        expected = expectedGRecurrence.getByMonth();
        target = targetGRecurrence.getByMonth();
        compereSet(expected, target);

        expected = expectedGRecurrence.getByMonthDay();
        target = targetGRecurrence.getByMonthDay();
        compereSet(expected, target);

        System.out.println("\t\t\t\t\t\t\t\t\t\tOk!");

    }

}