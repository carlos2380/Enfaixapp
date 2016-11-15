package com.pes.enfaixapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class LoadingActivity extends AppCompatActivity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(isConnectedToNetwork()) {
            SharedPreferences preferences = getPreferences(MODE_PRIVATE);
            String sessionToken = preferences.getString("session-token", null);
            if (sessionToken == null) {
                startActivity(new Intent(LoadingActivity.this, LogInActivity.class));
            } else {
                startActivity(new Intent(LoadingActivity.this, DrawerActivity.class));
            }
        }
        else {
            Toast toast = Toast.makeText(this,"No estàs connectat", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    private boolean isConnectedToNetwork() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
