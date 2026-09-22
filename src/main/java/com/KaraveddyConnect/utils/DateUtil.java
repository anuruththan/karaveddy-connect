package com.KaraveddyConnect.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author Anuruththan-local
 * @org Smartzi
 * @since 2026-09-22 18:01 PM
 **/
@Slf4j
public class DateUtil {
    private static final String UTC_TIME_FORMAT = "dd-MM-yyyy HH:mm:ss";
    private static final String UTC_DATE_FORMAT = "dd-MM-yyyy";

    public static String getCurrentUTCTime() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(UTC_TIME_FORMAT);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat.format(date);
    }

    public static String getCurrentUTCDate() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(UTC_DATE_FORMAT);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat.format(date);
    }

    public static String unixToUTC(String unixTime) {
        long timestamp = Long.parseLong(unixTime);
        Date date = new Date(timestamp);

        SimpleDateFormat sdf = new SimpleDateFormat(UTC_TIME_FORMAT);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        return sdf.format(date);
    }
}