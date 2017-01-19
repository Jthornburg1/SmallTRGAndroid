package com.example.jon_thornburg.smalltrgandroid.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by jon_thornburg on 1/13/17.
 */

public class RobinObject {
    @SerializedName("data")
    private List<SpaceNode> spaceNodes;

    public RobinObject(List<SpaceNode> spaceNodes) {
        this.spaceNodes = spaceNodes;
    }

    public List<SpaceNode> getSpaceNodes() { return spaceNodes; }
    public void setSpaceNodes(List<SpaceNode> spaceNodes) { this.spaceNodes = spaceNodes; }
}
