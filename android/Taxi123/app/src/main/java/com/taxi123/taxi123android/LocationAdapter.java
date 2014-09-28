package com.taxi123.taxi123android;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by Sean Nguyen on 15/9/2014.
 */

public class LocationAdapter extends BaseAdapter {

    //private attributes
    private LocationListModel locations;
    private Context context;
    private LayoutInflater inflater;

    public LocationAdapter() {
        super();
    }

    public LocationAdapter(Context c) {
        super();
        this.locations = new LocationListModel(this);
        this.context = context;
        this.inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

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
            view = inflater.inflate(android.R.layout.simple_list_item_1, viewGroup, false);
        }
        LocationModel location = this.locations.getLocationByIndex(i);
        TextView locationName = (TextView) view.findViewById(android.R.id.text1);
        locationName.setText(location.getName());
        if (location.getStatus() == 1) {
            locationName.setTextColor(Color.parseColor(Configurations.COLOR_INDEMAND));
        } else if (location.getStatus() == 0) {
            locationName.setTextColor(Color.parseColor(Configurations.COLOR_NOTINDEMAND));
        }
        return view;
    }

    public void refreshLocationList() {
        this.locations.refreshLocationList();
    }
}
