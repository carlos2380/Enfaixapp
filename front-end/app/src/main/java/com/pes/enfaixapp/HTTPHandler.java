package com.pes.enfaixapp;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by eduard on 5/11/16.
 */

public class HTTPHandler extends AsyncTask<String, Void, JSONObject> {
    private AsyncResult asyncResult;

    public HTTPHandler() {
        asyncResult = null;
    }

    @Override
    protected JSONObject doInBackground(String... params) {
        String httpMethod = params[0].toUpperCase();
        if (isAValidHTTPMethod(httpMethod)) {
            try {
                URL url = new URL(params[1]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod(httpMethod);
                if (params[2] != null) {
                    OutputStreamWriter body = new OutputStreamWriter(connection.getOutputStream());
                    body.write(params[2]);
                }
                connection.setConnectTimeout(15000 /* milliseconds */);
                connection.connect();

                int responseCode = connection.getResponseCode();
                if ((responseCode == HttpURLConnection.HTTP_OK) || responseCode == HttpURLConnection.HTTP_CREATED) {
                    return new JSONObject(connection.getResponseMessage());
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        asyncResult.processFinish(jsonObject);
    }

    public void setAsyncResult(AsyncResult asyncResult) {
        this.asyncResult = asyncResult;
    }

    private boolean isAValidHTTPMethod(String httpMethod) {
        switch (httpMethod) {
            case "GET":
                return true;
            case "POST":
                return true;
            case "DELETE":
                return true;
            case "PUT":
                return true;
            default:
                return false;
        }
    }
}
