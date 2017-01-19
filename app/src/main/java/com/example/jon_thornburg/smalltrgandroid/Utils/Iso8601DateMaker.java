package com.example.jon_thornburg.smalltrgandroid.Utils;

import android.util.Log;

import com.example.jon_thornburg.smalltrgandroid.Constants;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by jon_thornburg on 1/16/17.
 */

public class Iso8601DateMaker {

    public static String getISODate() {

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));

        StringBuffer buffer = new StringBuffer();
        buffer.append(calendar.get(Calendar.YEAR));
        buffer.append("-");
        buffer.append(twoDigit(calendar.get(Calendar.MONTH) + 1));
        buffer.append("-");
        buffer.append(twoDigit(calendar.get(Calendar.DAY_OF_MONTH)));
        buffer.append("T");
        buffer.append(twoDigit(calendar.get(Calendar.HOUR_OF_DAY)));
        buffer.append(":");
        buffer.append(twoDigit(calendar.get(Calendar.MINUTE)));
        buffer.append(":");
        buffer.append(twoDigit(calendar.get(Calendar.SECOND)));
        buffer.append("GMT");
        return buffer.toString();
    }

    private static String twoDigit(int i) {
        if (i >= 0 && i < 10) {
            return "0" + String.valueOf(i);
        }
        return String.valueOf(i);
    }

    public static String robinFromFilter() {
        Calendar cal = Calendar.getInstance();
        if (cal.get(Calendar.MINUTE) > 30) {
            cal.set(Calendar.MINUTE, 30);
        } else if (cal.get(Calendar.MINUTE) < 30) {
            cal.set(Calendar.MINUTE, 0);
        }

        cal.set(Calendar.SECOND, 0);
        //cal.add(Calendar.MONTH, 1);
        SimpleDateFormat formatter = new SimpleDateFormat(Constants.ISO_DATE_FORMAT);
        String outFilter = formatter.format(cal.getTime());
        Log.e("RobinBusy", "robinFromFilter" + outFilter);
        return outFilter;
    }

    public static String robinToFilter() {

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.add(Calendar.HOUR, 2);
        SimpleDateFormat formatter = new SimpleDateFormat(Constants.ISO_DATE_FORMAT);
        String outFilter = formatter.format(cal.getTime());
        Log.e("RobinBusy", "robinToFilter" + outFilter);
        return outFilter;
    }

    public static String iso8601FromFilter() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));

        StringBuffer buffer = new StringBuffer();
        buffer.append(calendar.get(Calendar.YEAR));
        buffer.append("-");
        buffer.append(twoDigit(calendar.get(Calendar.MONTH) + 1));
        buffer.append("-");
        buffer.append(twoDigit(calendar.get(Calendar.DAY_OF_MONTH)));
        buffer.append("T");
        buffer.append(twoDigit(calendar.get(Calendar.HOUR_OF_DAY)));
        buffer.append(":");
        if (Calendar.MINUTE == 30) {
            buffer.append(twoDigit(calendar.get(Calendar.MINUTE)));
        } else {
            buffer.append(twoDigit(0));
        }
        buffer.append(":");
        buffer.append(twoDigit(0));
        buffer.append("GMT");
        return buffer.toString();
    }

    public static String iso8601ToFilter() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));

        StringBuffer buffer = new StringBuffer();
        buffer.append(calendar.get(Calendar.YEAR));
        buffer.append("-");
        buffer.append(twoDigit(calendar.get(Calendar.MONTH) + 1));
        buffer.append("-");
        buffer.append(twoDigit(calendar.get(Calendar.DAY_OF_MONTH)));
        buffer.append("T");
        buffer.append(twoDigit(calendar.get(Calendar.HOUR_OF_DAY) + 4));
        buffer.append(":");
        if (Calendar.MINUTE == 30) {
            buffer.append(twoDigit(calendar.get(Calendar.MINUTE)));
        } else {
            buffer.append(twoDigit(0));
        }
        buffer.append(":");
        buffer.append(twoDigit(0));
        buffer.append("GMT");
        return buffer.toString();
    }
}
