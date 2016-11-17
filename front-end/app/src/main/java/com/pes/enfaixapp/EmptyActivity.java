package com.pes.enfaixapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.pes.enfaixapp.Models.Usuari;

public class EmptyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hola);

        Usuari usuari = (Usuari) this.getIntent().getSerializableExtra("User");

        TextView id = (TextView) findViewById(R.id.id1);
        id.setText(String.valueOf(usuari.getId()));
        TextView name = (TextView) findViewById(R.id.name1);
        name.setText(usuari.getNom());
        TextView surname = (TextView) findViewById(R.id.surname1);
        surname.setText(usuari.getCognoms());
        TextView email = (TextView) findViewById(R.id.email1);
        email.setText(usuari.getCorreu());

    }
}
