package com.example.laba17.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Purpose: Formats recent order time in minutes and older orders as a date.
 * Created: 2026-06-15.
 * Author: Codex.
 * Main methods: format returns relative minutes for the first hour or a calendar date.
 */
public final class OrderDateFormatter {

    private static final long MINUTE_MILLIS = 60_000L;
    private static final long HOUR_MILLIS = 60 * MINUTE_MILLIS;

    private OrderDateFormatter() {
    }

    public static String format(long createdAtMillis, long nowMillis, Locale locale) {
        long difference = Math.max(0, nowMillis - createdAtMillis);
        if (difference < HOUR_MILLIS) {
            long minutes = Math.max(1, difference / MINUTE_MILLIS);
            return minutes + " min ago";
        }
        return new SimpleDateFormat("dd.MM.yyyy", locale).format(new Date(createdAtMillis));
    }
}
