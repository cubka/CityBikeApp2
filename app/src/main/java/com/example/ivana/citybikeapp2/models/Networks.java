package com.example.ivana.citybikeapp2.models;

import java.io.Serializable;

public class Networks implements Serializable{

    public String name;
    public Location location;

    public Networks() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getCity(){
        return location.getCity();
    }
    public String getCountry(){
        return location.getCountry();
    }

    public float getLan() { return location.getLatitude();}
    public float getLong() { return location.getLongitude();}
}

