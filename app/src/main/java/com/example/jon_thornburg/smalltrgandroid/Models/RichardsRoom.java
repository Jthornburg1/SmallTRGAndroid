package com.example.jon_thornburg.smalltrgandroid.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jon_thornburg on 1/11/17.
 */

public class RichardsRoom implements Serializable {

    @SerializedName("name")
    private String name;
    @SerializedName("image")
    private String imageUrl;
    @SerializedName("description")
    private String description;
    @SerializedName("id")
    private int id;
    @SerializedName("type")
    private String type;
    private List<RobinBusyTime> busyTimes;

    public RichardsRoom(String name, String imageUrl, String description, int id, String type) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.description = description;
        this.id = id;
        this.type = type;
    }

    // Getters
    public String getName() { return name; }
    public String getImageUrl() { return imageUrl; }
    public String getDescription() { return description; }
    public int getId() { return id; }
    public List<RobinBusyTime> getBusyTimes() { return busyTimes; }
    public String getType() { return type; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public void setDescription(String description) { this.description = description; }
    public void setId(int id) { this.id = id; }
    public void setBusyTimes(List<RobinBusyTime> busyTimes) { this.busyTimes = busyTimes; }
    public void setType(String type) { this.type = type; }
}
