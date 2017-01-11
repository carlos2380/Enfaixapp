package com.pes.enfaixapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.pes.enfaixapp.Controllers.AsyncResult;
import com.pes.enfaixapp.Controllers.ContextUser;
import com.pes.enfaixapp.Controllers.HTTPHandler;
import com.pes.enfaixapp.Controllers.JSONConverter;
import com.pes.enfaixapp.Models.Colla;
import com.pes.enfaixapp.Models.Usuari;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

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
    private SignInActivity context;
    private ListView listViewCollesConvencionals;
    private ListView listViewCollesUniversitaries;
    private ListView listViewTotes;
    private ArrayList<Colla> CollesSeguides;
    private Colla choosenCollaConv;
    private Colla choosenCollaUni;
    private AdaptadorColla adaptadorCollesConv;
    private AdaptadorColla adaptadorCollesUni;
    private AdaptadorCollesSeguides adaptadorCollesTotes;

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

        HTTPHandler httpHandler = new HTTPHandler();
        httpHandler.setAsyncResult(this);
        httpHandler.execute("GET", "http://10.4.41.165:5000/colles", null);

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
        adaptadorCollesConv = new AdaptadorColla(getApplicationContext(), collesConv);
        listViewCollesConvencionals.setAdapter(adaptadorCollesConv);
        listViewCollesConvencionals.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                choosenCollaConv = (Colla) adapterView.getItemAtPosition(i);
            }
        });

        listViewCollesUniversitaries = (ListView) findViewById(R.id.universitariesList);
        adaptadorCollesUni = new AdaptadorColla(getApplicationContext(), collesUni);
        listViewCollesUniversitaries.setAdapter(adaptadorCollesUni);
        listViewCollesUniversitaries.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                choosenCollaUni = (Colla) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        listViewTotes = (ListView) findViewById(R.id.allList);

        listViewTotes.setAdapter(adaptadorCollesTotes);

        cb = (CheckBox) findViewById(R.id.checkBox);

        CollesSeguides = new ArrayList<Colla>();

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
                if ( !fieldsAreEmpty()) {
                    if (inputPasswd.getText().toString().equals(inputPasswd2.getText().toString())) {
                        JSONObject jsonUser = new JSONObject();
                        try {
                            user.setNom(inputName.getText().toString());
                            user.setCognoms(inputSurname.getText().toString());
                            user.setCorreu(inputCorreu.getText().toString());

                            MessageDigest messageDigest;
                            messageDigest = MessageDigest.getInstance("MD5");
                            messageDigest.update(inputPasswd.getText().toString().getBytes());
                            byte[] sum = messageDigest.digest();
                            BigInteger bigInteger = new BigInteger(1,sum);
                            String hash = bigInteger.toString(64);
                            user.setPsswd(hash);

                            for (int i=0; i < adaptadorCollesConv.getCount(); ++i) {
                                if ( ((Colla)adaptadorCollesConv.getItem(i)).isSeleccionada())
                                    user.addCollaQuePertany(((Colla) adaptadorCollesConv.getItem(i)).getId());

                            }

                            for (int i=0; i < adaptadorCollesUni.getCount(); ++i) {
                                if ( ((Colla)adaptadorCollesUni.getItem(i)).isSeleccionada())
                                    user.addCollaQuePertany(((Colla) adaptadorCollesUni.getItem(i)).getId());

                            }


                            /*listViewCollesConvencionals.getItemAtPosition(1);
                            if (choosenCollaConv != null) {
                                user.addCollaQuePertany(choosenCollaConv);
                            }

                            if (choosenCollaUni != null) {
                                user.addCollaQuePertany(choosenCollaUni);
                            }*/
                            for (int i=0; i < listViewTotes.getAdapter().getCount(); ++i) {
                                if ( ((Colla)listViewTotes.getAdapter().getItem(i)).isSeleccionadaSeguida())
                                    user.addCollesSeguides(((Colla) listViewTotes.getAdapter().getItem(i)).getId());
                            }


                            jsonUser.accumulate("email", user.getCorreu());
                            jsonUser.accumulate("password", user.getPsswd());
                            jsonUser.accumulate("name", user.getNom());
                            jsonUser.accumulate("surname", user.getCognoms());
                            jsonUser.accumulate("belongs", new JSONArray(user.getCollesALesQuePertany()));
                            jsonUser.accumulate("follows", new JSONArray(user.getCollesSeguides()));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (NoSuchAlgorithmException e) {
                            e.printStackTrace();
                        }

                        HTTPHandler httphandler = new HTTPHandler();
                        httphandler.setAsyncResult(context);
                        httphandler.execute("POST", "http://10.4.41.165:5000/signin", jsonUser.toString());
                    } else {
                        Toast toast = Toast.makeText(context, "Les contrasenyes no coincideixen", Toast.LENGTH_LONG);
                        toast.show();
                    }
                }

            }
        });

    }

    private Boolean fieldsAreEmpty() {
        if (inputName.getText().toString().isEmpty()) {
            Toast.makeText(context, "El camp del nom és buit", Toast.LENGTH_LONG).show();
            return true;
        }
        if (inputSurname.getText().toString().isEmpty()) {
            Toast.makeText(context, "El camp del cognom és buit", Toast.LENGTH_LONG).show();
            return true;
        }
        if (inputCorreu.getText().toString().isEmpty()) {
            Toast.makeText(context, "El camp del correu és buit", Toast.LENGTH_LONG).show();
            return true;
        }
        if (inputPasswd.getText().toString().isEmpty()) {
            Toast.makeText(context, "El camp de contrasenya és buit", Toast.LENGTH_LONG).show();
            return true;
        }
        if (inputPasswd2.getText().toString().isEmpty()) {
            Toast.makeText(context, "El camp de repeticó és buit", Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }

    @Override
    public void processFinish(JSONObject output) {
        if (output != null) {
            try {
                int response = output.getInt("response");
                if (response == HttpURLConnection.HTTP_CREATED) {
                    Intent intent = new Intent(SignInActivity.this, DrawerActivity.class);
                    Usuari u = JSONConverter.toUser(output);
                    intent.putExtra("User", u);
                    String token = output.getString("session_token");
                    String user_id = output.getString("id");
                    String user_name = (String) output.get("name");
                    String user_surname = (String) output.get("surname");
                    String user_email = (String) output.get("email");

                    ArrayList<Colla> user_collesPertany = new ArrayList<>();
                    JSONArray jsonArray = (JSONArray) output.get("belongs");
                    Colla c = new Colla();

                    for (int i = 0; i < jsonArray.length(); ++i) {
                        c.setName((String) jsonArray.getJSONObject(i).get("name"));
                        c.setId(Integer.valueOf(String.valueOf(jsonArray.getJSONObject(i).get("id"))));
                        c.setColor((String) jsonArray.getJSONObject(i).get("color"));
                        user_collesPertany.add(c);
                    }

                    ArrayList<Colla> user_collesSeg = new ArrayList<>();
                    JSONArray jsonArrayFoll = (JSONArray) output.get("follows");
                    Colla cf = new Colla();

                    for (int i = 0; i < jsonArrayFoll.length(); ++i) {
                        cf.setName((String) jsonArrayFoll.getJSONObject(i).get("name"));
                        cf.setId(Integer.valueOf(String.valueOf(jsonArray.getJSONObject(i).get("id"))));
                        user_collesSeg.add(cf);
                    }

                    SharedPreferences preferences = getSharedPreferences("Shared", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("session-token", token);
                    editor.putString("user_id", user_id);
                    editor.putString("user_name", user_name);
                    editor.putString("user_surname", user_surname);
                    editor.putString("user_email", user_email);
                    if (user_collesPertany.size() > 0) {
                        editor.putString("user_belongsConvencional", user_collesPertany.get(0).getName());
                        editor.putString("user_idCollaConv", String.valueOf(user_collesPertany.get(0).getId()));
                        editor.putString("color_Conv", user_collesPertany.get(0).getColor());

                    }
                    if (user_collesPertany.size() > 1) {
                        editor.putString("user_belongsUni", user_collesPertany.get(1).getName());
                        editor.putString("user_idCollaUni", String.valueOf(user_collesPertany.get(1).getId()));
                        editor.putString("color_Uni", user_collesPertany.get(1).getColor());

                    }

                    ContextUser.getInstance().setId(user_id);
                    ContextUser.getInstance().setCollesPertany(user_collesPertany);
                    ContextUser.getInstance().setNom(user_name);
                    ContextUser.getInstance().setCognoms(user_surname);
                    ContextUser.getInstance().setEmail(user_email);
                    ContextUser.getInstance().setCollesSegueix(user_collesSeg);
                    if (user_collesPertany.size() > 0)
                        ContextUser.getInstance().setId_collaSwitch(String.valueOf(user_collesPertany.get(0).getId()));

                    editor.apply();
                    startActivity(intent);
                    finish();
                }
                else if (response == HttpURLConnection.HTTP_OK) {
                    List<Colla> colles = JSONConverter.toCollaList(output);
                    listViewTotes.setAdapter(new AdaptadorCollesSeguides(this, colles));
                    for (Colla colla : colles) {
                        collesTotes.add(colla);
                        if (colla.isUniversitaria()) {
                            collesUni.add(colla);
                        } else {
                            collesConv.add(colla);
                        }
                    }
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