package com.taxi123.taxi123android;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LocationListModel extends Model {
    private HashMap<Integer, LocationModel> locationModels;
    private  LocationAdapter locationAdapter;

    public LocationListModel (LocationAdapter locationAdapter) {
        this.locationModels = new HashMap<Integer, LocationModel>();
        this.locationAdapter = locationAdapter;
    }

    public void addLocation (LocationModel location) {
        int id = location.getId();
        this.locationModels.put(id, location);
    }

    public void setLocationStatus (int id, int status) {
        this.locationModels.get(id).setStatus(status);
    }

    public LocationModel getLocationByIndex (int i) {
        if (i >= this.locationModels.size())
            return null;
        List<Integer> keys = new ArrayList<Integer>(this.locationModels.keySet());
        int key = keys.get(i);
        return this.locationModels.get(key);
    }

    public LocationModel getLocationById (int id) {
        return this.locationModels.get(id);
    }

    public int getLength() {
        return this.locationModels.size();
    }

    public void refreshLocationList() {
        this.locationModels.clear();
        DataCollector dataCollector = new DataCollector(this);
        dataCollector.execute(Configurations.DATACOLLECTOR_FULL);
    }

    public void refreshLocationStatus() {
        DataCollector dataCollector = new DataCollector(this);
        dataCollector.execute(Configurations.DATACOLLECTOR_MIN);
    }

    public void notifyDataSetChange() {
        locationAdapter.notifyDataSetChanged();
    }
}