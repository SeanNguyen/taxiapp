package com.taxi123.taxi123android.model;

import android.location.Location;

import com.taxi123.taxi123android.LocationService;
import com.taxi123.taxi123android.collector.LocationListCollector;
import com.taxi123.taxi123android.collector.LocationStatusCollector;

import java.util.HashMap;
import java.util.Vector;

public class LocationListModel extends Model {
    private Vector<Integer> locationIds;
    private HashMap<Integer, LocationModel> locationModels;
    private LocationService locationAdapter;

    public LocationListModel (LocationService locationAdapter) {
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
        this.locationIds = sortByDistance(this.locationIds, location);
        this.locationIds = sortByStatus(this.locationIds);
        locationAdapter.notifyDataSetChanged();
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

    private Vector <Integer> sortByDistance (Vector <Integer> original, Location originalLocation) {
        Location destLocation = new Location("destination");

        for (int i = 0; i < this.locationIds.size(); i++) {
            for (int j = 0; j < this.locationIds.size() - 1; j++) {
                int id = this.locationIds.get(j);
                int nextId = this.locationIds.get(j + 1);

                float distanceToCurrent = this.locationModels.get(id).calculateDistance(originalLocation);
                float distanceToNext = this.locationModels.get(nextId).calculateDistance(originalLocation);
                if (distanceToCurrent > distanceToNext) {
                    locationIds.set(j, nextId);
                    locationIds.set(j + 1, id);
                }
            }
        }
        return null;
    }
}