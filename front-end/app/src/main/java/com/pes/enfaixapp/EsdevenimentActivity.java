package com.pes.enfaixapp;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pes.enfaixapp.Models.Esdeveniment;


/**
 * A simple {@link Fragment} subclass.
 */
public class EsdevenimentActivity extends Activity {


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

        //      TODO: AGAFAR LA FOTO DE LA GALERIA

        // AGAFAR DADES DE L'ESDEVENIMENT
        Esdeveniment esdv = new Esdeveniment();
        esdv.setTitol("Títol de prova");
        esdv.setDescripcio("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
        esdv.setDireccio("C/Jordi Girona 2342353");

        //SETEJAR LES DADES DE L'ESDEVENIMENT


        descripcioEsdv.setText(esdv.getDescripcio());
        titolEsdv.setText(esdv.getTitol());
        direccioEsdv.setText(esdv.getDireccio());

    }

}
