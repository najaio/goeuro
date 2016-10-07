package com.goeuro.devtest;

import com.google.gson.annotations.SerializedName;

public class City {

    @SerializedName("_id")
    private String id;
    private String name;
    @SerializedName("geo_position")
    private Geolocation location;

    public City() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Geolocation getLocation() {
        return location;
    }

    public void setLocation(Geolocation location) {
        this.location = location;
    }

}
