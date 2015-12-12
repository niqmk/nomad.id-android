package com.allega.nomad.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by imnotpro on 7/5/15.
 */
public class TimeUtil {

    public static final String DATE_FORMAT = "EEEE, d MMM yyyy ";

    public static long addCurrentTimeZone(long utc0) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(utc0 * 1000);
        calendar.add(Calendar.MILLISECOND, TimeZone.getDefault().getRawOffset() / 1000);
        return calendar.getTimeInMillis();
    }

    public static String longToDate(long time) {
        Date date = new Date(addCurrentTimeZone(time));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
        return simpleDateFormat.format(date);
    }
}
