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

import com.pes.enfaixapp.Controllers.ContextUser;
import com.pes.enfaixapp.Models.Colla;

import java.util.ArrayList;

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
                String user_id = preferences.getString("user_id", null);
                String user_name = preferences.getString("user_name", null);
                String user_surname = preferences.getString("user_surname", null);
                String user_email = preferences.getString("user_email", null);
                ArrayList<Colla> collesUser = new ArrayList<>();
                if (preferences.getString("user_belongsConvencional",null) != null) {
                    Colla c = new Colla();
                    c.setName(preferences.getString("user_belongsConvencional", null));
                    c.setId(Integer.parseInt(preferences.getString("user_idCollaConv",null)));
                    collesUser.add(c);

                }
                if (preferences.getString("user_belongsUni",null) != null) {
                    Colla c = new Colla();
                    c.setName(preferences.getString("user_belongsUni", null));
                    c.setId(Integer.parseInt(preferences.getString("user_idCollaUni",null)));
                    collesUser.add(c);
                }

                ContextUser.getInstance().setId(user_id);
                ContextUser.getInstance().setEmail(user_email);
                ContextUser.getInstance().setCognoms(user_surname);
                ContextUser.getInstance().setNom(user_name);
                ContextUser.getInstance().setCollesPertany(collesUser);
                ContextUser.getInstance().setId_collaSwitch(preferences.getString("user_idCollaConv", null));

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
