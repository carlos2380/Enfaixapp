package com.pes.enfaixapp;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pes.enfaixapp.Models.Esdeveniment;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class EsdevenimentActivity extends AppCompatActivity {


    public EsdevenimentActivity() {
        // Required empty public constructor
    }

    private ImageView foto;
    private TextView titolEsdv;
    private TextView descripcioEsdv;
    private TextView direccioEsdv;


    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esdeveniment);

        foto = (ImageView) findViewById(R.id.imatgeCrearEsdeveniment);
        titolEsdv = (TextView) findViewById(R.id.titolEsdvMostrar);
        descripcioEsdv = (TextView) findViewById(R.id.descripcioEsdvMostrar);
        direccioEsdv = (TextView) findViewById(R.id.direccioEsdvMostrar);

        //      TODO: AGAFAR LA FOTO DE LA GALERIA I POSAR BOTÓ QUE ENLLAÇI A MODIFICAR

        // AGAFAR DADES DE L'ESDEVENIMENT
        Esdeveniment esdv = new Esdeveniment();


        //SETEJAR LES DADES DE L'ESDEVENIMENT

        //Bitmap image = StringToBitMap();

        descripcioEsdv.setText(esdv.getDescripcio());
        titolEsdv.setText(esdv.getTitol());
        direccioEsdv.setText(esdv.getDireccio());
       // foto.setImageBitmap(image);
    }
}
