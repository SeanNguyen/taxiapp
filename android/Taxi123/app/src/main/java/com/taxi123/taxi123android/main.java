package com.taxi123.taxi123android;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class main extends Activity {
    LocationAdapter locationAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Configurations.MainActivity = this;
        ListView locationListView = (ListView) findViewById(R.id.locationListView);
        this.locationAdapter = new LocationAdapter(this);
        locationListView.setAdapter(locationAdapter);
        this.locationAdapter.refreshLocationList();

        locationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView locationAddr = (TextView) view.findViewById(R.id.locationAddress);
                String address = locationAddr.getText().toString();
                openGoogleMap(address);
            }
        });
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
        this.locationAdapter.refreshLocationList();
    }

    //private helper methods
    private void openGoogleMap(String destAddress) {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
            Uri.parse("https://maps.google.com/maps?f=d&daddr=" + destAddress));
        startActivity(intent);
    }
}
