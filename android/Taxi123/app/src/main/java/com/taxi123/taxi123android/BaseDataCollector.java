package com.taxi123.taxi123android;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class BaseDataCollector extends AsyncTask<String,Void,String> {

    protected   LocationListModel model;
    protected ProgressDialog dialog;


    public BaseDataCollector(LocationListModel model) {
        this.model = model;
        dialog = new ProgressDialog(Configurations.MainActivity);
    }

    @Override
    protected String doInBackground(String... args) {
        return "";
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        this.model.notifyDataSetChange();
    }


    protected String convertResponseMessageToString(HttpResponse response) {
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

}
