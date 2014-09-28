package com.taxi123.taxi123android;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;

public class DataCollector extends AsyncTask<String,Void,String> {

    private  LocationListModel model;
    private  boolean isQueryFull;
    private ProgressDialog dialog;


    public DataCollector(LocationListModel model) {
        this.model = model;
        isQueryFull = true;
        dialog = new ProgressDialog(Configurations.MainActivity);
    }

    @Override
    protected String doInBackground(String... args) {
        if (args[0].equals(Configurations.DATACOLLECTOR_FULL)) {
            this.isQueryFull = true;
        } else if (args[0].equals(Configurations.DATACOLLECTOR_MIN)) {
            this.isQueryFull = false;
        }

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
    private  HttpResponse sendHttpRequest() {
        try {
            String link;
            if (isQueryFull) {
                link = Configurations.SERVER_LINKQUERY_ALL;
            } else {
                link = Configurations.SERVER_LINKQUERY_IDSTATUS;
            }

            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI(link));
            return client.execute(request);
        } catch (Exception e) {
            Log.e("log_tag", "Error in http connection" + e.toString());
        }
        return null;
    }

    private String convertResponseMessageToString (HttpResponse response) {
        StringBuilder sb;
        InputStream is;
        //convert response to string
        try {
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            sb = new StringBuilder();
            sb.append(reader.readLine()).append("\n");

            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            is.close();
            return sb.toString();
        } catch (Exception e) {
            Log.e("log_tag", "Error converting result " + e.toString());
        }
        return "";
    }

    private boolean parserJsonString (String jsonString) {
        try {
            JSONObject reader = new JSONObject(jsonString);
            String isSuccess = reader.getString(Configurations.DATABASE_JSONRESPONSE_SUCCESS);
            if (isSuccess.equals(Configurations.BOOLEAN_FALSE))
                return false;

            JSONArray locations = reader.getJSONArray(Configurations.DATABASE_JSONRESPONSE_LOCATIONS);
            for (int i = 0; i < locations.length(); i++) {
                JSONObject location = locations.getJSONObject(i);
                if (isQueryFull) {
                    String locationIdString = location.getString(Configurations.DATABASE_JSONRESPONSE_LOCATIONID);
                    int id = Utilities.convertStringToInt(locationIdString);
                    String locationName = location.getString(Configurations.DATABASE_JSONRESPONSE_LOCATIONNAME);
                    String address = location.getString(Configurations.DATABASE_JSONRESPONSE_ADDRESS);
                    String postalCodeString = location.getString(Configurations.DATABASE_JSONRESPONSE_POSTALCODE);
                    int postalCode = Utilities.convertStringToInt(postalCodeString);
                    String statusString = location.getString(Configurations.DATABASE_JSONRESPONSE_LOCATIONSTATUS);
                    int status = Utilities.convertStringToInt(statusString);
                    LocationModel locationModel = new LocationModel(id, locationName, address, postalCode, status);
                    this.model.addLocation(locationModel);
                } else {
                    String locationIdString = location.getString(Configurations.DATABASE_JSONRESPONSE_LOCATIONID);
                    int id = Utilities.convertStringToInt(locationIdString);
                    String statusString = location.getString(Configurations.DATABASE_JSONRESPONSE_LOCATIONSTATUS);
                    int status = Utilities.convertStringToInt(statusString);
                    this.model.setLocationStatus(id, status);
                }
            }
        } catch (JSONException e) {
            return false;
        }
        return true;
    }
}
