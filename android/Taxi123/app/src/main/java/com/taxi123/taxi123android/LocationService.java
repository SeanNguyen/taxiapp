package com.taxi123.taxi123android;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.taxi123.taxi123android.model.LocationListModel;
import com.taxi123.taxi123android.model.LocationModel;

public class LocationService extends BaseAdapter implements LocationListener{

    //private attributes
    private LocationListModel locations;
    private LayoutInflater inflater;

    public LocationService(Context c) {
        super();
        this.locations = new LocationListModel(this);
        this.inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    //Adapter
    @Override
    public int getCount() {
        return this.locations.getLength();
    }

    @Override
    public Object getItem(int i) {
        return this.locations.getLocationById(i).getName();
    }

    @Override
    public long getItemId(int i) {
        if (i < this.locations.getLength())
            return i;
        else
            return -1;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(R.layout.listview_item_custom, null);
        }
        LocationModel location = this.locations.getLocationByIndex(i);
        TextView locationName = (TextView) view.findViewById(R.id.locationName);
        TextView locationAddr = (TextView) view.findViewById(R.id.locationAddress);
        TextView postalCode = (TextView) view.findViewById(R.id.postalCode);
        TextView distance = (TextView) view.findViewById(R.id.distance);

        locationName.setText(location.getName());
        locationAddr.setText(location.getAddress());
        postalCode.setText("(" + location.getPostalCode() + ")");
        float d = location.getDistance();
        if (d > -1)
            distance.setText("" + d);

        if (location.getStatus() == 1) {
            locationName.setTextColor(Color.parseColor(Configurations.COLOR_INDEMAND));
            locationAddr.setTextColor(Color.parseColor(Configurations.COLOR_INDEMAND));
            postalCode.setTextColor(Color.parseColor(Configurations.COLOR_INDEMAND));
            distance.setTextColor(Color.parseColor(Configurations.COLOR_INDEMAND));
        } else if (location.getStatus() == 0) {
            locationName.setTextColor(Color.parseColor(Configurations.COLOR_NOTINDEMAND));
            locationAddr.setTextColor(Color.parseColor(Configurations.COLOR_NOTINDEMAND));
            postalCode.setTextColor(Color.parseColor(Configurations.COLOR_NOTINDEMAND));
            distance.setTextColor(Color.parseColor(Configurations.COLOR_NOTINDEMAND));
        }
        return view;
    }

    //LocationListener
    @Override
    public void onLocationChanged(Location location) {
        locations.refreshListOrder(location);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    //Other public methods
    public void refreshLocationList() {
        this.locations.refreshLocationList();
    }

    public void refreshStatus() {
        this.locations.refreshLocationStatus();
    }
}
