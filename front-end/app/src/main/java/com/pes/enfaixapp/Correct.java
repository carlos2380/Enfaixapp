package com.pes.enfaixapp;

/**
 * Created by Àlex on 25/10/2016.
 */


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Correct extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //comprovar al server si s'ha creat correctament
        setContentView(R.layout.correct);
        /*String[] s= getIntent().getStringArrayExtra("info");
        String user = s[0];
        String password = s[1];*/
    }
}
