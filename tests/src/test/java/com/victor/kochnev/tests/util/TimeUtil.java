package com.victor.kochnev.tests.util;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TimeUtil {
    private TimeUtil() {
    }

    public static void compareZonedDateTime(ZonedDateTime expected, ZonedDateTime actual) {
        ZonedDateTime actualWithSameZoneId = actual.withZoneSameInstant(expected.getZone());
        assertEquals(expected.truncatedTo(ChronoUnit.SECONDS), actualWithSameZoneId.truncatedTo(ChronoUnit.SECONDS));
    }
}
