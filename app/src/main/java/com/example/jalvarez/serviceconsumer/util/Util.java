package com.example.jalvarez.serviceconsumer.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Util {

    public static final String NDATEFORMAT = "yyyy-MM-dd HH:mm:ss-05";

    public static String getDate(String format) {
        DateFormat date = new SimpleDateFormat(format, Locale.US);
        Calendar cal = Calendar.getInstance();

        return date.format(cal.getTime());
    }
}
