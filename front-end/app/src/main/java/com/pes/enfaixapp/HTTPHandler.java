package com.pes.enfaixapp;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

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
                int port = url.getPort();
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod(httpMethod);
                connection.setRequestProperty("Content-Type", "application/json");
                if (params[2] != null) {
                    // Send POST output.
                    DataOutputStream printout = new DataOutputStream(connection.getOutputStream());
                    printout.writeBytes(params[2]);
                    printout.flush();
                    printout.close();
                }
                connection.setConnectTimeout(15000 /* milliseconds */);
                connection.connect();

                int responseCode = connection.getResponseCode();
                String response;
                if (responseCode >= HttpURLConnection.HTTP_OK && responseCode < HttpURLConnection.HTTP_MULT_CHOICE) {
                    response = parseResponse(connection.getInputStream());
                } else {
                    response = parseResponse(connection.getErrorStream());
                }
                JSONObject result = new JSONObject();
                Object json = new JSONTokener(response).nextValue();
                if (json instanceof JSONArray) {
                    JSONArray array = (JSONArray) json;
                    result.accumulate("array", array);
                } else if (json instanceof JSONObject) {
                    result = (JSONObject) json;
                }
                result.accumulate("connection", connection.getResponseMessage());
                result.accumulate("response", responseCode);
                return result;
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }
            return null;
    }

    private String parseResponse(InputStream inputStream) {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String output;
        try {
            while ((output = br.readLine()) != null) {
                sb.append(output);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
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
