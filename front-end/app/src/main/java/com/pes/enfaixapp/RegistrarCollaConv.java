package com.pes.enfaixapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by Àlex on 17/10/2016.
 */

import android.support.v7.app.AppCompatActivity;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
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
    Button button5;

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

    CheckBox cb;

    final ArrayList<Colla> collesConv = new ArrayList<Colla>();
    final ArrayList<Colla> collesUni = new ArrayList<Colla>();
    final ArrayList<Colla> collesTotes = new ArrayList<Colla>();

    boolean but, but2, but3, but4;

    Usuari user;
    String psswd, psswdCheck = "buit";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registre_escollir_collaconv);

        button = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button5 = (Button)findViewById(R.id.reg);

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

        cb = (CheckBox) findViewById(R.id.checkbox);




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
        collesTotes.add(CVXV);
        collesTotes.add(CDS);

        but = but2 = but3 = but4 = false;


        button.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view){
                    if (but == false) {
                        lay1.setVisibility(View.VISIBLE);
                        lay2.setVisibility(View.GONE);
                        lay3.setVisibility(View.GONE);
                        lay4.setVisibility(View.GONE);

                        if (inputName.callOnClick()){
                            nom.setTextColor(65536);
                            user.setNom(inputName.getText().toString());
                        }
                        else if (inputSurname.callOnClick()){
                            cognom.setTextColor(65536);
                            user.setCognoms(inputSurname.getText().toString());

                        }
                        else if (inputCorreu.callOnClick()) {
                            correu.setTextColor(65536);
                            user.setCorreu(inputCorreu.getText().toString());

                        }
                        else if (inputPasswd.callOnClick()) {
                            contrasenya.setTextColor(65536);
                            psswd = inputPasswd.getText().toString();
                            user.setPsswd(inputPasswd.getText().toString());
                        }
                        else if (inputPasswd2.callOnClick()){
                            contrasenya2.setTextColor(65536);
                            psswdCheck = inputPasswd2.getText().toString();

                        }

                      /*  Log.d("PASSWORD", psswd);
                        Log.d("PASSWORD2", psswdCheck);
                        if (psswdCheck.equals(psswd)) {
                            user.setPsswd(psswd);
                        /*} else {
                            Toast.makeText(getApplicationContext(), "LA CONTRASENYA NO COINCIDEIX", Toast.LENGTH_SHORT).show();

                        }*/

                        but = true;

                    }
                    else {
                        lay1.setVisibility(View.GONE);
                        but = false;
                    }
                }

            
        });


        button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (but2 == false) {
                    lay1.setVisibility(View.GONE);
                    lay2.setVisibility(View.VISIBLE);
                    lay3.setVisibility(View.GONE);
                    lay4.setVisibility(View.GONE);


                    /*Llistat amb les colles que apareixeran*/

                    AdaptadorColla adaptadorCollesConv = new AdaptadorColla(getApplicationContext(), collesConv);
                    final ListView lv = (ListView) findViewById(R.id.lv);
                    lv.setAdapter(adaptadorCollesConv);

                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            final int pos = position;
                            //posar el checkbox de la colla corresponent amb el tick
                            Colla CollaConvEscollida = new Colla();
                            CollaConvEscollida = (Colla)lv.getSelectedItem();
                            if (user.getCollaConv() == null)    //només setejem si no en té cap de posada ja
                                user.setCollaConv(CollaConvEscollida);

                        }
                    });
                    but2 = true;
                }
                else {
                    lay2.setVisibility(View.GONE);
                    but2 = false;
                }


            }
        });

        button3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (but3 == false) {
                    lay1.setVisibility(View.GONE);
                    lay2.setVisibility(View.GONE);
                    lay3.setVisibility(View.VISIBLE);
                    lay4.setVisibility(View.GONE);

                    AdaptadorColla adaptadorCollesUni = new AdaptadorColla(getApplicationContext(), collesUni);
                    final ListView lvu = (ListView) findViewById(R.id.lvUni);

                    lvu.setAdapter(adaptadorCollesUni);

                    lvu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            final int pos = position;
                            //posar el checkbox de la colla corresponent amb el tick
                            Colla CollaUniEscollida = new Colla();
                            CollaUniEscollida = (Colla)lvu.getSelectedItem();
                            if (user.getCollaUni() == null) //només setejem si no en té cap ja
                                user.setCollaUni(CollaUniEscollida);
                        }
                    });
                    but3 = true;
                }
                else {
                    lay3.setVisibility(View.GONE);
                    but3 = false;
                }

            }
        });
        button4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (but4 == false) {
                    lay1.setVisibility(View.GONE);
                    lay2.setVisibility(View.GONE);
                    lay3.setVisibility(View.GONE);
                    lay4.setVisibility(View.VISIBLE);
                    AdaptadorColla adaptadorCollesTotes = new AdaptadorColla(getApplicationContext(), collesTotes);
                    final ListView lvTotes = (ListView) findViewById(R.id.lvTotes);

                    lvTotes.setAdapter(adaptadorCollesTotes);

                    lvTotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            final int pos = position;
                            //posar el checkbox de la colla corresponent amb el tick
                            ArrayList<Colla> CollesSeguides = new ArrayList<Colla>();
                            CollesSeguides.add((Colla) lvTotes.getSelectedItem());

                        }
                    });
                    but4 = true;
                }
                else {
                    lay4.setVisibility(View.GONE);
                    but4 = false;
                }

            }
        });


        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent myintent=new Intent(RegistrarCollaConv.this, Correct.class).putExtra("info", a);
                //crida al server per guardar les dades (nom, colles, etc)

                startActivity(new Intent(RegistrarCollaConv.this, Correct.class));


            }
        });

    }
}