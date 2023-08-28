package com.project.hairtologyowner.components.utils;

import android.annotation.SuppressLint;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class StringFormat {

    public static String date(Date date) {
        return new SimpleDateFormat("MMMM dd yy").format(date);
    }

    public static String time(Time time) {
        return new SimpleDateFormat("h:mm a").format(time);
    }

}
