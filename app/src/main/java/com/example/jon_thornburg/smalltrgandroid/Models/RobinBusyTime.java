package com.example.jon_thornburg.smalltrgandroid.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jon_thornburg on 1/16/17.
 */

public class RobinBusyTime {
    @SerializedName("from")
    private String from;
    @SerializedName("to")
    private String to;

    public String getTo() { return to; }
    public void setTo(String to) { this.to = to; }

    public String getFrom() { return from; }
    public void setFrom(String from) { this.from = from; }

    private static final String TAG = RobinBusyTime.class.getSimpleName();

}
