package com.pes.enfaixapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

public class LoadingActivity extends AppCompatActivity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(isConnectedToNetwork()) {
            SharedPreferences preferences = getSharedPreferences("Shared", MODE_PRIVATE);
            String sessionToken = preferences.getString("session-token", null);
            if (sessionToken == null) {
                startActivity(new Intent(LoadingActivity.this, LogInActivity.class));
                finish();
            } else {
                startActivity(new Intent(LoadingActivity.this, DrawerActivity.class));
                finish();
            }
        }
        else {
            new AlertDialog.Builder(this)
                    .setTitle("No esteu connectats")
                    .setMessage("Si us plau, connecteu-vos a la xarxa")
                    .setPositiveButton("Acceptar",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    finish();
                                }
                            }).show();
        }
    }

    private boolean isConnectedToNetwork() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
