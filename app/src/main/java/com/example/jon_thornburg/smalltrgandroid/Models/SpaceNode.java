package com.example.jon_thornburg.smalltrgandroid.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jon_thornburg on 1/16/17.
 */

public class SpaceNode implements Serializable {

    @SerializedName("space")
    private RichardsRoom richardsRoom;
    @SerializedName("busy")
    private List<RobinBusyTime> busy;

    public SpaceNode(RichardsRoom richardsRoom) {
        this.richardsRoom = richardsRoom;
    }

    public RichardsRoom getRichardsRoom() { return richardsRoom; }
    public void setRichardsRoom(RichardsRoom richardsRoom) { this.richardsRoom = richardsRoom; }

    public List<RobinBusyTime> getBusy() {
        return busy;
    }
    public void setBusy(List<RobinBusyTime> busy) { this.busy = busy; }
}
