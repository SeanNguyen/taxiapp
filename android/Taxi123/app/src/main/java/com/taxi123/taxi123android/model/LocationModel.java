package com.taxi123.taxi123android.model;

import android.location.Location;

import com.taxi123.taxi123android.collector.PositionCollector;

public class LocationModel extends  Model {
    private int id;
    private String name;
    private String address;
    private String postalCode;
    private int status;
    private double latitude;
    private double longitude;
    private double distance;

    public LocationModel(int id, String name, String address, String postalCode, int status) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.status = status;
        this.latitude = -1;
        this.longitude = -1;
        this.distance = -1;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getAddress() {
        return this.address;
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    public int getStatus() {
        return this.status;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public double getDistance() {
        return this.distance;
    }

    public void setId (int id) {
        this.id = id;
    }

    public void setName (String name) {
        this.name = name;
    }

    public void setAddress (String address) {
        this.address = address;
    }

    public void setPostalCode (int id) {
        this.postalCode = postalCode;
    }

    public void setStatus (int status) {
        this.status = status;
    }

    public void setLatitude(double lat) {
        this.latitude = lat;
    }

    public void setLongitude(double lng) {
        this.longitude = lng;
    }

    public void updatePosition() {
        PositionCollector collector = new PositionCollector(this);
        collector.execute();
    }

    public double calculateDistance (Location location) {
        Location destLocation = new Location("destLocation");
        destLocation.setLatitude(this.latitude);
        destLocation.setLongitude(this.longitude);
        this.distance = destLocation.distanceTo(location);
        return this.distance;
    }
}
