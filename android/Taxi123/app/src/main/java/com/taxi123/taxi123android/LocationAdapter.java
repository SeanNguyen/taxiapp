package com.taxi123.taxi123android;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.taxi123.taxi123android.model.LocationListModel;
import com.taxi123.taxi123android.model.LocationModel;

public class LocationAdapter extends BaseAdapter {

    //private attributes
    private LocationListModel locations;
    private LayoutInflater inflater;

    public LocationAdapter(Context c) {
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
        View statusBar = (View) view.findViewById(R.id.statusBar);

        locationName.setText(location.getName());
        locationAddr.setText(location.getAddress());
        postalCode.setText(String.valueOf(location.getPostalCode()));
        distance.setText(Utilities.convertDistanceToString(location.getDistance()));

        if (location.getStatus() == 1) {
            locationName.setTextColor(Color.parseColor(Configurations.COLOR_BLACK));
            locationAddr.setTextColor(Color.parseColor(Configurations.COLOR_BLACK));
            postalCode.setTextColor(Color.parseColor(Configurations.COLOR_BLACK));
            distance.setTextColor(Color.parseColor(Configurations.COLOR_BLACK));
            statusBar.setBackgroundColor(Color.parseColor(Configurations.COLOR_INDEMAND));
        } else if (location.getStatus() == 0) {
            locationName.setTextColor(Color.parseColor(Configurations.COLOR_NOTINDEMAND));
            locationAddr.setTextColor(Color.parseColor(Configurations.COLOR_NOTINDEMAND));
            postalCode.setTextColor(Color.parseColor(Configurations.COLOR_NOTINDEMAND));
            distance.setTextColor(Color.parseColor(Configurations.COLOR_NOTINDEMAND));
            statusBar.setBackgroundColor(Color.parseColor(Configurations.COLOR_NOTINDEMAND));
        }
        return view;
    }

    //Other public methods
    public void refreshLocationList() {
        this.locations.refreshLocationList();
    }

    public void refreshStatus(Location location) {
        this.locations.refreshLocationStatus();
        this.locations.refreshListOrder(location);
    }
}
