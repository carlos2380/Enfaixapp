package com.pes.enfaixapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Àlex on 17/10/2016.
 */

import android.support.v7.app.AppCompatActivity;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

public class RegistrarCollaConv extends AppCompatActivity {

    Button button;
    Button button2;
    Button button3;
    Button button4;

    TextView nom;
    TextView cognom;
    TextView correu;
    TextView contrasenya;
    TextView contrasenya2;

    View lay1;
    View lay2;
    View lay3;
    View lay4;

    EditText inputName;
    EditText inputSurname;
    EditText inputCorreu;
    EditText inputPasswd;
    EditText inputPasswd2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registre_escollir_collaconv);

        button = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);

        nom = (TextView) findViewById(R.id.textNom);
        cognom =(TextView) findViewById(R.id.textCognom);
        correu = (TextView) findViewById(R.id.textCorreu);
        contrasenya = (TextView) findViewById(R.id.textContrasenya);
        contrasenya2 = (TextView) findViewById(R.id.textContrasenya2);


        inputName = (EditText) findViewById(R.id.nomInput);
        inputSurname = (EditText) findViewById(R.id.cognomInput);
        inputCorreu = (EditText) findViewById(R.id.correuInput);
        inputPasswd = (EditText) findViewById(R.id.contrasenyaInput);
        inputPasswd2 = (EditText) findViewById(R.id.contrasenyaInput2);

        lay1 =  findViewById(R.id.lnom);
        lay2 =  findViewById(R.id.dos);
        lay3 = findViewById(R.id.tres);
        lay4 = findViewById(R.id.cuatro);


        final ArrayList<Colla> collesConv = new ArrayList<Colla>();
        final ArrayList<Colla> collesUni = new ArrayList<Colla>();
        final ArrayList<Colla> collesTotes = new ArrayList<Colla>();

        Colla AZU = new Colla("Arreplegats de la Zona Universitaria", "AZU",R.drawable.logo_azu);
        Colla CDV = new Colla("Castellers de Vilafranca", "Els jefes", R.drawable.logo_cdv);
        Colla CVXV = new Colla("Colla Vella dels Xiquets de Valls", "Puta Vella!", R.drawable.logo_cvxv);
        Colla CJXT = new Colla("Colla Jove dels Xiquets de Tarragona", "Puta Jove!", R.drawable.logo_cjxt);
        Colla CDS = new Colla("Castellers de Sants", "CDS", R.drawable.logo_sants);
        Colla CJXV = new Colla("Colla Joves dels Xiquets de Valls", "I jove, i jove, i jove jove jove!!", R.drawable.logo_cjxv);
        Colla GUAB = new Colla("Ganàpies de la UAB", "Puta Ganàpies!", R.drawable.logo_ganapies);
        Colla PTCM = new Colla("Passerells del Tecnoampus de Mataró", "Passerells", R.drawable.logo_passarells);


        collesUni.add(AZU);
        collesUni.add(GUAB);
        collesUni.add(PTCM);

        collesConv.add(CDV);
        collesConv.add(CVXV);
        collesConv.add(CJXT);
        collesConv.add(CDS);
        collesConv.add(CJXV);

        collesTotes.add(CJXT);
        collesTotes.add(AZU);
        collesTotes.add(GUAB);
        collesTotes.add(PTCM);
        collesTotes.add(CDV);
        collesConv.add(CJXV);
        collesTotes.add(CVXV);
        collesTotes.add(CDS);



        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                lay1.setVisibility(View.VISIBLE);
                lay2.setVisibility(View.GONE);
                lay3.setVisibility(View.GONE);
                lay4.setVisibility(View.GONE);
                if (inputName.callOnClick()) nom.setTextColor(65536);
                else if (inputSurname.callOnClick()) cognom.setTextColor(65536);
                else if (inputCorreu.callOnClick()) correu.setTextColor(65536);
                else if (inputPasswd.callOnClick()) contrasenya.setTextColor(65536);
                else if (inputPasswd2.callOnClick()) contrasenya2.setTextColor(65536);



            }
        });


        button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                lay1.setVisibility(View.GONE);
                lay2.setVisibility(View.VISIBLE);
                lay3.setVisibility(View.GONE);
                lay4.setVisibility(View.GONE);
                if(!(inputPasswd.getText().toString().equals(inputPasswd2.getText().toString()))) {
                    Toast.makeText(getApplicationContext(), "LA CONTRASENYA NO COINCIDEIX", Toast.LENGTH_SHORT).show();
                }

                /*Llistat amb les colles que apareixeran*/

                AdaptadorColla adaptadorCollesConv = new AdaptadorColla(getApplicationContext(),collesConv);
                ListView lv = (ListView) findViewById(R.id.lv);
                lv.setAdapter(adaptadorCollesConv);

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        final int pos = position;
                        //posar el checkbox de la colla corresponent amb el tick

                    }
                });

            }
        });
        button3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                lay1.setVisibility(View.GONE);
                lay2.setVisibility(View.GONE);
                lay3.setVisibility(View.VISIBLE);
                lay4.setVisibility(View.GONE);

                AdaptadorColla adaptadorCollesUni = new AdaptadorColla(getApplicationContext(),collesUni);
                ListView lvu = (ListView) findViewById(R.id.lvUni);

                lvu.setAdapter(adaptadorCollesUni);

                lvu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        final int pos = position;
                        //posar el checkbox de la colla corresponent amb el tick

                    }
                });
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                lay1.setVisibility(View.GONE);
                lay2.setVisibility(View.GONE);
                lay3.setVisibility(View.GONE);
                lay4.setVisibility(View.VISIBLE);
                AdaptadorColla adaptadorCollesTotes = new AdaptadorColla(getApplicationContext(),collesTotes);
                ListView lvTotes = (ListView) findViewById(R.id.lvTotes);

                lvTotes.setAdapter(adaptadorCollesTotes);

                lvTotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        final int pos = position;
                        //posar el checkbox de la colla corresponent amb el tick

                    }
                });

            }
        });

    }
}