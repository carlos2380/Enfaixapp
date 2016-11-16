package com.pes.enfaixapp;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

/**
 * Created by Àlex on 17/10/2016.
 */

import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.pes.enfaixapp.Adapters.AdaptadorColla;
import com.pes.enfaixapp.Adapters.AdaptadorCollesSeguides;
import com.pes.enfaixapp.Models.Colla;
import com.pes.enfaixapp.Models.Usuari;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;

public class SignInActivity extends Activity implements AsyncResult {

    Button aboutYouButton;
    Button chooseCollaConvButton;
    Button chooseCollaUniButton;
    Button chooseColles;
    Button signInButton;

    TextView nom;
    TextView cognom;
    TextView correu;
    TextView contrasenya;
    TextView contrasenya2;

    View userInfoLayout;
    View collesConvencionalsLayout;
    View collesUniversitariesLayout;
    View allCollesLayout;

    EditText inputName;
    EditText inputSurname;
    EditText inputCorreu;
    EditText inputPasswd;
    EditText inputPasswd2;

    CheckBox cb;

    final ArrayList<Colla> collesConv = new ArrayList<Colla>();
    final ArrayList<Colla> collesUni = new ArrayList<Colla>();
    final ArrayList<Colla> collesTotes = new ArrayList<Colla>();

    Usuari user;
    String psswd, psswdCheck = "buit";
    private SignInActivity context;

    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    /**
     * Shows the soft keyboard
     */
    public void showSoftKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        view.requestFocus();
        inputMethodManager.showSoftInput(view, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_sign_in);
        hideSoftKeyboard();

        context = this;

        aboutYouButton = (Button) findViewById(R.id.aboutYouButton);
        chooseCollaConvButton = (Button) findViewById(R.id.button2);
        chooseCollaUniButton = (Button) findViewById(R.id.button3);
        chooseColles = (Button) findViewById(R.id.button4);
        signInButton = (Button)findViewById(R.id.reg);

        nom = (TextView) findViewById(R.id.nameLabel);
        cognom =(TextView) findViewById(R.id.surnameLabel);
        correu = (TextView) findViewById(R.id.textCorreu);
        contrasenya = (TextView) findViewById(R.id.textContrasenya);
        contrasenya2 = (TextView) findViewById(R.id.textContrasenya2);


        inputName = (EditText) findViewById(R.id.nameInput);
        inputSurname = (EditText) findViewById(R.id.surnameInput);
        inputCorreu = (EditText) findViewById(R.id.correuInput);
        inputPasswd = (EditText) findViewById(R.id.contrasenyaInput);
        inputPasswd2 = (EditText) findViewById(R.id.contrasenyaInput2);


        userInfoLayout =  findViewById(R.id.userInfoLayout);
        collesConvencionalsLayout =  findViewById(R.id.collesConvencionalsLayout);
        collesUniversitariesLayout = findViewById(R.id.collesUniversitariesLayout);
        allCollesLayout = findViewById(R.id.allCollesLayout);

        cb = (CheckBox) findViewById(R.id.checkBox);


        Colla AZU = new Colla("Arreplegats de la Zona Universitaria", "AZU",R.drawable.logo_azu);
        Colla CDV = new Colla("Castellers de Vilafranca", "Els jefes", R.drawable.logo_cdv);
        Colla CVXV = new Colla("Colla Vella dels Xiquets de Valls", "Puta Vella!", R.drawable.logo_cvxv);
        Colla CJXT = new Colla("Colla Jove dels Xiquets de Tarragona", "Puta Jove!", R.drawable.logo_cjxt);
        Colla CDS = new Colla("Castellers de Sants", "CDS", R.drawable.logo_sants);
        Colla CJXV = new Colla("Colla Joves dels Xiquets de Valls", "I jove, i jove, i jove jove jove!!", R.drawable.logo_cjxv);
        Colla GUAB = new Colla("Ganàpies de la UAB", "Puta Ganàpies!", R.drawable.logo_ganapies);
        Colla PTCM = new Colla("Passerells del Tecnoampus de Mataró", "Passerells", R.drawable.logo_passarells);
        Colla CAP = new Colla("No pertanyo a cap colla", true, R.drawable.ic_account_circle_login_24dp);

        collesUni.add(CAP);
        collesUni.add(AZU);
        collesUni.add(GUAB);
        collesUni.add(PTCM);

        collesConv.add(CAP);
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

        userInfoLayout.setVisibility(View.VISIBLE);
        collesConvencionalsLayout.setVisibility(View.GONE);
        collesUniversitariesLayout.setVisibility(View.GONE);
        allCollesLayout.setVisibility(View.GONE);

        AdaptadorCollesSeguides adaptadorCollesTotes = new AdaptadorCollesSeguides(getApplicationContext(), collesTotes);

        user = new Usuari();


        aboutYouButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view){
                    hideSoftKeyboard();
                    if (userInfoLayout.getVisibility() != View.VISIBLE) {
                        userInfoLayout.setVisibility(View.VISIBLE);
                        collesConvencionalsLayout.setVisibility(View.GONE);
                        collesUniversitariesLayout.setVisibility(View.GONE);
                        allCollesLayout.setVisibility(View.GONE);

                        if (inputName.callOnClick()){
                            nom.setTextColor(65536);
                        }
                        else if (inputSurname.callOnClick()){
                            cognom.setTextColor(65536);

                        }
                        else if (inputCorreu.callOnClick()) {
                            correu.setTextColor(65536);

                        }
                        else if (inputPasswd.callOnClick()) {
                            contrasenya.setTextColor(65536);
                            psswd = inputPasswd.getText().toString();
                        }
                        else if (inputPasswd2.callOnClick()){
                            contrasenya2.setTextColor(65536);
                            psswdCheck = inputPasswd2.getText().toString();

                        }
                    }
                    else {
                        userInfoLayout.setVisibility(View.GONE);
                    }
                }

            
        });


        chooseCollaConvButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (collesConvencionalsLayout.getVisibility() != View.VISIBLE) {
                    userInfoLayout.setVisibility(View.GONE);
                    collesConvencionalsLayout.setVisibility(View.VISIBLE);
                    collesUniversitariesLayout.setVisibility(View.GONE);
                    allCollesLayout.setVisibility(View.GONE);


                    /*Llistat amb les colles que apareixeran*/

                    AdaptadorColla adaptadorCollesConv = new AdaptadorColla(getApplicationContext(), collesConv);
                    final ListView lv = (ListView) findViewById(R.id.conventionalList);
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
                }
                else {
                    collesConvencionalsLayout.setVisibility(View.GONE);
                }

            }
        });

        chooseCollaUniButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (collesUniversitariesLayout.getVisibility() != View.VISIBLE) {
                    userInfoLayout.setVisibility(View.GONE);
                    collesConvencionalsLayout.setVisibility(View.GONE);
                    collesUniversitariesLayout.setVisibility(View.VISIBLE);
                    allCollesLayout.setVisibility(View.GONE);

                    AdaptadorColla adaptadorCollesUni = new AdaptadorColla(getApplicationContext(), collesUni);
                    final ListView lvu = (ListView) findViewById(R.id.universitariesList);

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
                }
                else {
                    collesUniversitariesLayout.setVisibility(View.GONE);
                }

            }
        });
        chooseColles.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (allCollesLayout.getVisibility() != View.VISIBLE) {
                    userInfoLayout.setVisibility(View.GONE);
                    collesConvencionalsLayout.setVisibility(View.GONE);
                    collesUniversitariesLayout.setVisibility(View.GONE);
                    allCollesLayout.setVisibility(View.VISIBLE);
                    AdaptadorCollesSeguides adaptadorCollesTotes = new AdaptadorCollesSeguides(getApplicationContext(), collesTotes);
                    final ListView lvTotes = (ListView) findViewById(R.id.allList);

                    lvTotes.setAdapter(adaptadorCollesTotes);

                    lvTotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            if (cb.isClickable()) {
                                cb.setChecked(false);
                            }
                            final int pos = position;
                            //posar el checkbox de la colla corresponent amb el tick
                            ArrayList<Colla> CollesSeguides = new ArrayList<Colla>();
                            CollesSeguides.add((Colla) lvTotes.getSelectedItem());

                        }
                    });
                }
                else {
                    allCollesLayout.setVisibility(View.GONE);
                }
            }
        });


        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject jsonUser = new JSONObject();
                try {
                    user.setNom(inputName.getText().toString());
                    user.setCognoms(inputSurname.getText().toString());
                    user.setCorreu(inputCorreu.getText().toString());
                    user.setPsswd(inputPasswd.getText().toString());
                    ArrayList<Colla> totesColles = new ArrayList<Colla>();
                    totesColles.addAll(collesConv);
                    totesColles.addAll(collesUni);
                    user.setTotesColles(totesColles);


                    jsonUser.accumulate("email", user.getCorreu() );
                    jsonUser.accumulate("password", user.getPsswd() );
                    jsonUser.accumulate("name", user.getNom() );
                    jsonUser.accumulate("surname", user.getCognoms() );
                    jsonUser.accumulate("belongs", user.getCollaConv() );
                    jsonUser.accumulate("follows", user.getCollesSeguides() );
                    //////////////////////////////////////////////////////////
                    //jsonUser.accumulate("totesColles",user.getTotesColles() ); //DEFINIR EL ATRIBUT QUE ES FA SERVIR A BACKEND PER TOTES
                    //////////////////////////////////////////////////////////
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                HTTPHandler httphandler = new HTTPHandler();
                httphandler.setAsyncResult(context);
                httphandler.execute("POST", "http://10.4.41.165:5000/signin", jsonUser.toString());


            }
        });

    }

    @Override
    public void processFinish(JSONObject output) {
        if (output != null) {
            try {
                int response = output.getInt("response");
                if (response == HttpURLConnection.HTTP_OK || response == HttpURLConnection.HTTP_CREATED)
                    startActivity(new Intent(SignInActivity.this, DrawerActivity.class));
                else {      //cas error 500
                    Toast toast = Toast.makeText(context, "ERROR 500: INTERNAL SERVER ERROR", Toast.LENGTH_LONG);
                    toast.show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else {
            Toast toast = Toast.makeText(context, "No funciona", Toast.LENGTH_LONG);
            toast.show();
        }
    }
}