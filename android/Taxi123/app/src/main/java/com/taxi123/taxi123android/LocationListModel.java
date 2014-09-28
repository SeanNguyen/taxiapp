package com.taxi123.taxi123android;

import java.util.HashMap;
import java.util.Vector;

public class LocationListModel extends Model {
    private Vector<Integer> locationIds;
    private HashMap<Integer, LocationModel> locationModels;
    private  LocationAdapter locationAdapter;

    public LocationListModel (LocationAdapter locationAdapter) {
        this.locationModels = new HashMap<Integer, LocationModel>();
        this.locationAdapter = locationAdapter;
        this.locationIds = new Vector<Integer>();
    }

    public void addLocation (LocationModel location) {
        int id = location.getId();
        this.locationModels.put(id, location);
        this.locationIds.add(id);
    }

    public void setLocationStatus (int id, int status) {
        this.locationModels.get(id).setStatus(status);
    }

    public LocationModel getLocationByIndex (int i) {
        int id = this.locationIds.get(i);
        return this.locationModels.get(id);
    }

    public LocationModel getLocationById (int id) {
        return this.locationModels.get(id);
    }

    public int getLength() {
        return this.locationModels.size();
    }

    public void refreshLocationList() {
        this.locationModels.clear();
        this.locationIds.clear();
        LocationListCollector dataCollector = new LocationListCollector(this);
        dataCollector.execute(Configurations.DATACOLLECTOR_FULL);
    }

    public void refreshLocationStatus() {
        LocationStatusCollector dataCollector = new LocationStatusCollector(this);
        dataCollector.execute(Configurations.DATACOLLECTOR_MIN);
    }

    public void notifyDataSetChange() {
        this.locationIds = sortByStatus(this.locationIds);
        locationAdapter.notifyDataSetChanged();
    }

    private Vector <Integer> sortByStatus (Vector <Integer> original) {
        Vector <Integer> greenStatus = new Vector<Integer>();
        Vector <Integer> redStatus = new Vector<Integer>();
        for (Integer id : original) {
            if (this.locationModels.get(id).getStatus() == 1) {
                greenStatus.add(id);
            } else {
                redStatus.add(id);
            }
        }
        Vector<Integer> result = new Vector<Integer>();
        result.addAll(greenStatus);
        result.addAll(redStatus);
        return result;
    }
}