package com.google.gcalendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import static org.junit.Assert.*;

@RunWith(value = Parameterized.class)
public class GEventCheckerTest {
    private GEvent event;
    private Date startBefore;
    private Date startAfter;
    private Date finishBefore;
    private Date finishAfter;
    private boolean[] results;


    public GEventCheckerTest(GEvent event, Date startAfter, Date startBefore, Date finishAfter, Date finishBefore, boolean[] results) {
        this.event = event;
        this.startBefore = startBefore;
        this.startAfter = startAfter;
        this.finishBefore = finishBefore;
        this.finishAfter = finishAfter;
        this.results = results;
    }


    @Test
    public void check() throws Exception {
        boolean debugMode = false;
        if (debugMode) {
            System.out.println("-= BEGIN " + event.getTitle() + " =-");
        }

        GEventChecker checker = new GEventChecker().setStartAfterDate(startAfter);
        boolean res = checker.check(event);
        assertEquals(results[0], res);
        if (debugMode) {
            System.out.println("   ->setStartAfterDate");
        }

        checker = new GEventChecker().setStartBeforeDate(startBefore);
        res = checker.check(event);
        assertEquals(results[1], res);
        if (debugMode) {
            System.out.println("   ->setStartBeforeDate");
        }



        checker = new GEventChecker().setFinishBeforeDate(finishBefore);
        res = checker.check(event);
        assertEquals(results[3], res);
        if (debugMode) {
            System.out.println("   ->setFinishBeforeDate");
        }

        checker = new GEventChecker().setFinishAfterDate(finishAfter);
        res = checker.check(event);
        assertEquals(results[2], res);
        if (debugMode) {
            System.out.println("   ->setFinishAfterDate");
        }

        checker = new GEventChecker().setStartAfterDate(startAfter).setStartBeforeDate(startBefore);
        res = checker.check(event);
        assertEquals(results[1] && results[0], res);
        if (debugMode) {
            System.out.println("   ->setStartAfterDate+setStartBeforeDate");
        }

        checker = new GEventChecker().setFinishAfterDate(finishAfter).setFinishBeforeDate(finishBefore);
        res = checker.check(event);
        assertEquals(results[2] && results[3], res);
        if (debugMode) {
            System.out.println("   ->setFinishAfterDate+setFinishBeforeDate");
        }

        checker = new GEventChecker()
                .setStartAfterDate(startAfter)
                .setStartBeforeDate(startBefore)
                .setFinishAfterDate(finishAfter)
                .setFinishBeforeDate(finishBefore);
        res = checker.check(event);
        assertEquals(results[0] && results[1] && results[2] && results[3], res);
        if (debugMode) {
            System.out.println("   ->combine");
        }
        System.out.println("-= FINISH " + event.getTitle() + " =-\n");
    }


    @Parameterized.Parameters
    public static Collection<Object[]> getTestData() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        return Arrays.asList(new Object[][]{
                {new GEvent("Test 1", sdf.parse("01.02.2016"), sdf.parse("01.12.2016")),
                        sdf.parse("01.01.2016"),
                        sdf.parse("01.03.2016"),
                        sdf.parse("01.10.2016"),
                        sdf.parse("25.12.2016"),
                        new boolean[]{true, true, true, true}
                },
                {new GEvent("Test 2", sdf.parse("01.02.2016"), sdf.parse("01.12.2016")),
                        sdf.parse("01.03.2016"),
                        sdf.parse("01.03.2016"),
                        sdf.parse("01.10.2016"),
                        sdf.parse("25.12.2016"),
                        new boolean[]{false, true, true, true}
                },
                {new GEvent("Test 3", sdf.parse("01.02.2016"), sdf.parse("01.12.2016")),
                        sdf.parse("01.01.2015"),
                        sdf.parse("02.01.2015"),
                        sdf.parse("01.10.2016"),
                        sdf.parse("25.12.2016"),
                        new boolean[]{true, false, true, true}
                },
                {new GEvent("Test 4", sdf.parse("01.02.2016"), sdf.parse("01.12.2016")),
                        sdf.parse("01.01.2016"),
                        sdf.parse("01.03.2016"),
                        sdf.parse("10.12.2016"),
                        sdf.parse("25.12.2016"),
                        new boolean[]{true, true, false, true}
                },
                {new GEvent("Test 5", sdf.parse("01.02.2016"), sdf.parse("01.12.2016")),
                        sdf.parse("01.01.2016"),
                        sdf.parse("01.03.2016"),
                        sdf.parse("02.10.2016"),
                        sdf.parse("25.11.2016"),
                        new boolean[]{true, true, true, false}
                }
        });
    }

}