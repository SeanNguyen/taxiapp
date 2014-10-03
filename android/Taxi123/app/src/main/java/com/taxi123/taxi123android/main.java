package com.taxi123.taxi123android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class main extends Activity{
    private LocationAdapter locationAdapter;
    private boolean isRefreshing = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Configurations.MainActivity = this;
        ListView locationListView = (ListView) findViewById(R.id.locationListView);
        this.locationAdapter = new LocationAdapter(this);
        locationListView.setAdapter(locationAdapter);
        this.locationAdapter.refreshLocationList();


        //link to google map app when click
        locationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView locationAddr = (TextView) view.findViewById(R.id.locationAddress);
                String address = locationAddr.getText().toString();
                openGoogleMap(address);
            }
        });

        //Timer for refresh data status
        final Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                ((main) Configurations.MainActivity).refresh();
                h.postDelayed(this, 2000);
            }
        }, 2000); // 5 second delay (takes millis)
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    public void refreshButton_onClick(View view) {
        if (this.isRefreshing)
            return;
        this.isRefreshing = true;
        this.locationAdapter.refreshLocationList();
    }

    public void refresh() {
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        this.locationAdapter.refreshStatus(location);
    }

    public boolean isRefreshing() {
        return this.isRefreshing;
    }

    public void setRefreshStatus(boolean status) {
        this.isRefreshing = status;
    }

    //private helper methods
    private void openGoogleMap(String destAddress) {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
            Uri.parse("https://maps.google.com/maps?f=d&daddr=" + destAddress));
        startActivity(intent);
    }
}
