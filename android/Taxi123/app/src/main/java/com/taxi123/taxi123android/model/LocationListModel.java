package com.taxi123.taxi123android.model;

import android.location.Location;

import com.taxi123.taxi123android.LocationAdapter;
import com.taxi123.taxi123android.collector.LocationListCollector;
import com.taxi123.taxi123android.collector.LocationStatusCollector;

import java.util.HashMap;
import java.util.Vector;

public class LocationListModel extends Model {
    private Vector<Integer> locationIds;
    private HashMap<Integer, LocationModel> locationModels;
    private LocationAdapter locationAdapter;

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
        dataCollector.execute();
    }

    public void refreshLocationStatus() {
        LocationStatusCollector dataCollector = new LocationStatusCollector(this);
        dataCollector.execute();
    }

    public void refreshListOrder(Location location) {
        sortByDistance(location);
        sortByStatus();
        locationAdapter.notifyDataSetChanged();
    }

    public void notifyDataSetChange() {
        sortByStatus();
        locationAdapter.notifyDataSetChanged();
    }

    private void sortByStatus () {
        Vector <Integer> greenStatus = new Vector<Integer>();
        Vector <Integer> redStatus = new Vector<Integer>();
        for (Integer id : this.locationIds) {
            if (this.locationModels.get(id).getStatus() == 1) {
                greenStatus.add(id);
            } else {
                redStatus.add(id);
            }
        }
        Vector<Integer> result = new Vector<Integer>();
        result.addAll(greenStatus);
        result.addAll(redStatus);
        this.locationIds = result;
    }

    private void sortByDistance (Location originalLocation) {
        for (int i = 0; i < this.locationIds.size(); i++) {
            for (int j = 0; j < this.locationIds.size() - 1; j++) {
                int id = this.locationIds.get(j);
                int nextId = this.locationIds.get(j + 1);

                double distanceToCurrent = this.locationModels.get(id).calculateDistance(originalLocation);
                double distanceToNext = this.locationModels.get(nextId).calculateDistance(originalLocation);
                if (distanceToCurrent > distanceToNext) {
                    locationIds.set(j, nextId);
                    locationIds.set(j + 1, id);
                }
            }
        }
    }
}