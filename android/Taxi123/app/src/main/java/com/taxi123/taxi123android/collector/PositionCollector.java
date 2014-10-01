package com.taxi123.taxi123android.collector;

import android.util.Log;

import com.taxi123.taxi123android.Configurations;
import com.taxi123.taxi123android.Utilities;
import com.taxi123.taxi123android.model.LocationModel;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;

public class PositionCollector extends BaseDataCollector {
    LocationModel model;

    public PositionCollector (LocationModel model) {
        this.model = model;
    }

    @Override
    protected String doInBackground(String... args) {
//        HttpResponse response = sendHttpRequest();
//        String result = convertResponseMessageToString(response);
//        parserJsonString(result);
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        this.model.notifyDataSetChange();
    }

    //private helper methods
    private HttpResponse sendHttpRequest() {
        try {
            String link;
            String addr = model.getAddress();
            link = generateQueryLink(addr);

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
            String isSuccess = reader.getString(Configurations.GGLAPI_STATUS);
            if (!isSuccess.equals(Configurations.GGLAPI_STATUS_OK))
                return false;

            JSONObject result = reader.getJSONObject(Configurations.GGLAPI_RESULT);
            JSONObject geo = result.getJSONObject(Configurations.GGLAPI_GEOMETRY);
            JSONObject location = geo.getJSONObject(Configurations.GGLAPI_LOCATION);
            String lat = location.getString(Configurations.GGLAPI_LATITUDE);
            String lng = location.getString(Configurations.GGLAPI_LONGITUDE);
            double latitude = Utilities.convertStringToInt(lat);
            double longitude = Utilities.convertStringToInt(lng);
            this.model.setLatitude(latitude);
            this.model.setLongitude(longitude);
        } catch (JSONException e) {
            return false;
        }
        return true;
    }

    private String generateQueryLink (String address) {
        return Configurations.GGLAPI_LINKQUERY_POSITION + address.replaceAll(" ", "+");
    }
}
