package com.taxi123.taxi123android;

/**
 * Created by Storm on 9/28/2014.
 */
public class LocationModel extends  Model {
    private int id;
    private String name;
    private String address;
    private int postalCode;
    private int status;

    public LocationModel(int id, String name, String address, int postalCode, int status) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.status = status;
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

    public int getPostalCode() {
        return this.postalCode;
    }

    public int getStatus() {
        return this.status;
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
}
