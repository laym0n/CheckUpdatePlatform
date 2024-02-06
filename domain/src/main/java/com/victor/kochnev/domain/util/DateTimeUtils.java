package com.victor.kochnev.domain.util;

import java.time.ZonedDateTime;

public class DateTimeUtils {
    private DateTimeUtils() {
    }

    public static boolean isDateInRange(ZonedDateTime when, ZonedDateTime start, ZonedDateTime end) {
        return isDateAfterOrEqual(when, start) && isDateBeforeOrEqual(when, end);
    }

    public static boolean isDateAfterOrEqual(ZonedDateTime when, ZonedDateTime start) {
        return when.isAfter(start) || when.isEqual(start);
    }

    public static boolean isDateBeforeOrEqual(ZonedDateTime when, ZonedDateTime end) {
        return when.isBefore(end) || when.isEqual(end);
    }
}
