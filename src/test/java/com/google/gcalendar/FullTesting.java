package com.google.gcalendar;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by JIeIIIa on 14.12.2016.
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({GCalendarAuthorizationTest.class,
        GEventCheckerTest.class,
        GCalendarTest.class,
        GRecurrenceUtilsTest.class,
        GCalendarGRecurrenceTest.class
})
public class FullTesting {

}
