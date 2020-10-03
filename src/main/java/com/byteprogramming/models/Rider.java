package com.byteprogramming.models;

import java.util.UUID;

public class Rider {

    private UUID id;
    private String name;
    private double totalKms;
    private double avgSpeed;

    public Rider() {

    }

    public Rider(String name, double totalKms, double avgSpeed) {
        this.id = id;
        this.name = name;
        this.totalKms = totalKms;
        this.avgSpeed = avgSpeed;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTotalKms() {
        return totalKms;
    }

    public void setTotalKms(double totalKms) {
        this.totalKms = totalKms;
    }

    public double getAvgSpeed() {
        return avgSpeed;
    }

    public void setAvgSpeed(double avgSpeed) {
        this.avgSpeed = avgSpeed;
    }
}
