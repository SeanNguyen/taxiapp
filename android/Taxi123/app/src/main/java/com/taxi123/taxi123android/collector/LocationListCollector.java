package com.taxi123.taxi123android.collector;

import android.app.ProgressDialog;
import android.util.Log;

import com.taxi123.taxi123android.Configurations;
import com.taxi123.taxi123android.Utilities;
import com.taxi123.taxi123android.model.LocationListModel;
import com.taxi123.taxi123android.model.LocationModel;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;

public class LocationListCollector extends BaseDataCollector {

    protected LocationListModel model;
    protected ProgressDialog dialog;

    public LocationListCollector(LocationListModel model) {
        this.model = model;
        dialog = new ProgressDialog(Configurations.MainActivity);
    }

    @Override
    protected String doInBackground(String... args) {
        HttpResponse response = sendHttpRequest();
        String result = convertResponseMessageToString(response);
        parserJsonString(result);
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.dialog.setMessage(Configurations.MESSAGE_LOADING);
        this.dialog.show();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        this.model.notifyDataSetChange();
    }

    //private helper methods
    private HttpResponse sendHttpRequest() {
        try {
            String link;
            link = Configurations.SERVER_LINKQUERY_ALL;
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI(link));
            return client.execute(request);
        } catch (Exception e) {
            Log.e("log_tag", "Error in http connection" + e.toString());
        }
        return null;
    }

    private boolean parserJsonString(String jsonString) {
        try {
            JSONObject reader = new JSONObject(jsonString);
            String isSuccess = reader.getString(Configurations.DATABASE_JSONRESPONSE_SUCCESS);
            if (isSuccess.equals(Configurations.BOOLEAN_FALSE))
                return false;

            JSONArray locations = reader.getJSONArray(Configurations.DATABASE_JSONRESPONSE_LOCATIONS);
            for (int i = 0; i < locations.length(); i++) {
                JSONObject location = locations.getJSONObject(i);
                String locationIdString = location.getString(Configurations.DATABASE_JSONRESPONSE_LOCATIONID);
                int id = Utilities.convertStringToInt(locationIdString);
                String locationName = location.getString(Configurations.DATABASE_JSONRESPONSE_LOCATIONNAME);
                String address = location.getString(Configurations.DATABASE_JSONRESPONSE_ADDRESS);
                String postalCode = location.getString(Configurations.DATABASE_JSONRESPONSE_POSTALCODE);
                String statusString = location.getString(Configurations.DATABASE_JSONRESPONSE_LOCATIONSTATUS);
                int status = Utilities.convertStringToInt(statusString);
                LocationModel locationModel = new LocationModel(id, locationName, address, postalCode, status);
                locationModel.updatePosition();
                this.model.addLocation(locationModel);
            }
        } catch (JSONException e) {
            return false;
        }
        return true;
    }
}
