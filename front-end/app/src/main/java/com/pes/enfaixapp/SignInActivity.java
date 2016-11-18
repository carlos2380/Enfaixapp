package com.pes.enfaixapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.pes.enfaixapp.Adapters.AdaptadorColla;
import com.pes.enfaixapp.Adapters.AdaptadorCollesSeguides;
import com.pes.enfaixapp.Models.Colla;
import com.pes.enfaixapp.Models.Usuari;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;

/**
 * Created by Àlex on 17/10/2016.
 */

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
    private ListView listViewCollesConvencionals;
    private ListView listViewCollesUniversitaries;
    private ListView listViewTotes;
    private ArrayList<Colla> CollesSeguides;

    public void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
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
        signInButton = (Button) findViewById(R.id.reg);

        nom = (TextView) findViewById(R.id.nameLabel);
        cognom = (TextView) findViewById(R.id.surnameLabel);
        correu = (TextView) findViewById(R.id.textCorreu);
        contrasenya = (TextView) findViewById(R.id.textContrasenya);
        contrasenya2 = (TextView) findViewById(R.id.textContrasenya2);


        inputName = (EditText) findViewById(R.id.nameInput);
        inputSurname = (EditText) findViewById(R.id.surnameInput);
        inputCorreu = (EditText) findViewById(R.id.correuInput);
        inputPasswd = (EditText) findViewById(R.id.contrasenyaInput);
        inputPasswd2 = (EditText) findViewById(R.id.contrasenyaInput2);


        userInfoLayout = findViewById(R.id.userInfoLayout);
        collesConvencionalsLayout = findViewById(R.id.collesConvencionalsLayout);
        collesUniversitariesLayout = findViewById(R.id.collesUniversitariesLayout);
        allCollesLayout = findViewById(R.id.allCollesLayout);

        listViewCollesConvencionals = (ListView) findViewById(R.id.conventionalList);
        listViewCollesUniversitaries = (ListView) findViewById(R.id.universitariesList);
        listViewTotes = (ListView) findViewById(R.id.allList);


        cb = (CheckBox) findViewById(R.id.checkBox);

        CollesSeguides = new ArrayList<Colla>();



        Colla AZU = new Colla("Arreplegats de la Zona Universitaria", "AZU", R.drawable.logo_azu);
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

        user = new Usuari();


        aboutYouButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                hideSoftKeyboard();
                if (userInfoLayout.getVisibility() != View.VISIBLE) {
                    userInfoLayout.setVisibility(View.VISIBLE);
                    collesConvencionalsLayout.setVisibility(View.GONE);
                    collesUniversitariesLayout.setVisibility(View.GONE);
                    allCollesLayout.setVisibility(View.GONE);

                    if (inputName.callOnClick()) {
                        nom.setTextColor(65536);
                    } else if (inputSurname.callOnClick()) {
                        cognom.setTextColor(65536);

                    } else if (inputCorreu.callOnClick()) {
                        correu.setTextColor(65536);

                    } else if (inputPasswd.callOnClick()) {
                        contrasenya.setTextColor(65536);
                    } else if (inputPasswd2.callOnClick()) {
                        contrasenya2.setTextColor(65536);
                    }
                } else {
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

                    AdaptadorColla adaptadorCollesConv = new AdaptadorColla(getApplicationContext(), collesConv);
                    listViewCollesConvencionals.setAdapter(adaptadorCollesConv);
                } else {
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
                    listViewCollesUniversitaries.setAdapter(adaptadorCollesUni);

                } else {
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
                    listViewTotes.setAdapter(adaptadorCollesTotes);

                    listViewTotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            if (cb.isClickable()) {
                                cb.setChecked(false);
                                CollesSeguides.remove((Colla) listViewTotes.getSelectedItem());
                            }
                            //posar el checkbox de la colla corresponent amb el tick
                            CollesSeguides.add((Colla) listViewTotes.getSelectedItem());

                        }
                    });
                } else {
                    allCollesLayout.setVisibility(View.GONE);
                }
            }
        });


        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean checked = true;
                if (inputName.getText().toString().isEmpty()) {
                    Toast.makeText(context, "Nom buit!", Toast.LENGTH_LONG).show();
                    checked = false;
                }

                if (inputSurname.getText().toString().isEmpty()) {
                    Toast.makeText(context, "Cognom buit!", Toast.LENGTH_LONG).show();
                    checked = false;
                }

                if (inputCorreu.getText().toString().isEmpty()) {
                    Toast.makeText(context, "Correu buit!", Toast.LENGTH_LONG).show();
                    checked = false;
                }

                if (inputPasswd.getText().toString().isEmpty()) {
                    Toast.makeText(context, "Password buit!", Toast.LENGTH_LONG).show();
                    checked = false;
                }

                if (inputPasswd2.getText().toString().isEmpty() && checked == true) {
                    Toast.makeText(context, "Repeat Password buit!", Toast.LENGTH_LONG).show();
                }
                    if (inputPasswd.getText().toString().equals(inputPasswd2.getText().toString())) {
                    JSONObject jsonUser = new JSONObject();
                    try {
                        user.setNom(inputName.getText().toString());
                        user.setCognoms(inputSurname.getText().toString());
                        user.setCorreu(inputCorreu.getText().toString());
                        user.setPsswd(inputPasswd.getText().toString());


                        Colla CollaConvEscollida = (Colla) listViewCollesConvencionals.getSelectedItem();
                        if (CollaConvEscollida != null) {
                            user.addCollaQuePertany(CollaConvEscollida);
                        }

                        Colla CollaUniEscollida = (Colla) listViewCollesUniversitaries.getSelectedItem();
                        if (CollaUniEscollida != null) {
                            user.addCollaQuePertany(CollaUniEscollida);
                        }

                        user.setCollesSeguides(CollesSeguides);

                        jsonUser.accumulate("email", user.getCorreu());
                        jsonUser.accumulate("password", user.getPsswd());
                        jsonUser.accumulate("name", user.getNom());
                        jsonUser.accumulate("surname", user.getCognoms());
                        jsonUser.accumulate("belongs", new JSONArray(user.getCollesALesQuePertany()));
                        jsonUser.accumulate("follows", new JSONArray(user.getCollesSeguides()));
                        //////////////////////////////////////////////////////////
                        //jsonUser.accumulate("totesColles",user.getCollesALesQuePertany() ); //DEFINIR EL ATRIBUT QUE ES FA SERVIR A BACKEND PER TOTES
                        //////////////////////////////////////////////////////////
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    HTTPHandler httphandler = new HTTPHandler();
                    httphandler.setAsyncResult(context);
                    httphandler.execute("POST", "http://10.4.41.165:5000/signin", jsonUser.toString());
                } else {
                    Toast toast = Toast.makeText(context, "Les contrasenyes no coincideixer", Toast.LENGTH_LONG);
                    toast.show();
                }

            }
        });

    }

    @Override
    public void processFinish(JSONObject output) {
        if (output != null) {
            try {
                int response = output.getInt("response");
                if (response == HttpURLConnection.HTTP_OK || response == HttpURLConnection.HTTP_CREATED) {
                    Intent intent = new Intent(SignInActivity.this, DrawerActivity.class);
                    Usuari u = JSONConverter.toUser(output);
                    intent.putExtra("User", u);
                    startActivity(intent);
                }
                else {      //cas error 500
                    Toast toast = Toast.makeText(context, "ERROR 500: INTERNAL SERVER ERROR", Toast.LENGTH_LONG);
                    toast.show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Toast toast = Toast.makeText(context, "El server no funciona", Toast.LENGTH_LONG);
            toast.show();
        }
    }
}