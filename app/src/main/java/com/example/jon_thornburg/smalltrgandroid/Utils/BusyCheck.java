package com.example.jon_thornburg.smalltrgandroid.Utils;

import android.util.Log;

import com.example.jon_thornburg.smalltrgandroid.Adapters.RoomsLoaderAdapter;
import com.example.jon_thornburg.smalltrgandroid.Constants;
import com.example.jon_thornburg.smalltrgandroid.Models.RobinBusyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by jon_thornburg on 1/18/17.
 */

public class BusyCheck {

    public static final String TAG = BusyCheck.class.getSimpleName();

    public static boolean isBusyForTimeInterval(RoomsLoaderAdapter.TimeSlot slot, RobinBusyTime event) {

        Log.d(TAG, "FromStamp: " + getFromTimestamp(event.getFrom()) + ", ToStamp: " + getToTimestamp(event.getTo()));
        Log.d(TAG, "FromSlot: " + slot.getStartTime() + ", ToSlot: " + slot.getEndTime());

        /*if (getFromTimestamp(event.getFrom()) >= slot.getStartTime() && getToTimestamp(event.getTo()) <= slot.getEndTime()) {
            return true;
        }
        if (getFromTimestamp(event.getFrom()) <= slot.getStartTime() && getToTimestamp(event.getTo()) == slot.getEndTime()) {
            return true;
        }
        if (getFromTimestamp(event.getFrom()) == slot.getStartTime() && getToTimestamp(event.getTo()) >= slot.getEndTime()) {
            return true;
        }
        if (getFromTimestamp(event.getFrom()) < slot.getStartTime() && getToTimestamp(event.getTo()) > slot.getEndTime()) {
            return true;
        }*/

        long slotOffset = slot.getStartTime() - getFromTimestamp(event.getFrom()) ;
        Log.d(TAG, "Calculation: " + event.getFrom());
        boolean lowerBoundCheck = slotOffset >= 0;

        long slotOffsetUpper = getToTimestamp(event.getTo()) - slot.getEndTime();
        boolean upperBoundCheck = slotOffsetUpper >= 0;

        if (lowerBoundCheck && upperBoundCheck) {
            return true;
        }
        /*if (lowerBoundCheck && getFromTimestamp(event.getFrom()) >= (slot.getStartTime() - 3600000) && getToTimestamp(event.getTo())
                >= slot.getEndTime()) {
            Log.d(TAG, "Found SLot: " + event.getFrom());
            return true;
        }*/
        return false;
    }

    private static long getToTimestamp(String to) {
        SimpleDateFormat formatter = new SimpleDateFormat(Constants.ISO_DATE_FORMAT);
        long outTime = Calendar.getInstance().getTimeInMillis();
        try {
            Date parsedDate = formatter.parse(to);
            outTime = parsedDate.getTime();
        } catch (ParseException e) {
            Log.e("RobinBusy", "unparsable", e);
        }

        return outTime;
    }

    private static long getFromTimestamp(String from) {
        SimpleDateFormat formatter = new SimpleDateFormat(Constants.ISO_DATE_FORMAT);
        long outTime = Calendar.getInstance().getTimeInMillis();
        try {
            Date parsedDate = formatter.parse(from);
            outTime = parsedDate.getTime();
        } catch (ParseException e) {
            Log.e("RobinBusy", "unparsable", e);
        }

        return outTime;
    }
}
