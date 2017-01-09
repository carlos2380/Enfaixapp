package com.pes.enfaixapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pes.enfaixapp.Controllers.AsyncResult;
import com.pes.enfaixapp.Controllers.ContextUser;
import com.pes.enfaixapp.Controllers.HTTPHandler;
import com.pes.enfaixapp.Controllers.JSONConverter;
import com.pes.enfaixapp.Models.Usuari;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class LogInActivity extends Activity implements AsyncResult {

    private Button loginButton, signInButton; //log in, cancel
    private EditText emailField, passwordField; //user, pass
    private LogInActivity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        context = this;
        loginButton =(Button)findViewById(R.id.logInButton);
        signInButton =(Button)findViewById(R.id.signInButton);
        emailField =(EditText)findViewById(R.id.emailText);
        passwordField =(EditText)findViewById(R.id.passwordText);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject jsonLogin = new JSONObject();
                try {
                    jsonLogin.accumulate("email", emailField.getText());
                    MessageDigest messageDigest;
                    messageDigest = MessageDigest.getInstance("MD5");
                    messageDigest.update(passwordField.getText().toString().getBytes());
                    byte[] sum = messageDigest.digest();
                    BigInteger bigInteger = new BigInteger(1,sum);
                    String hash = bigInteger.toString(64);
                    jsonLogin.accumulate("password", hash);
                } catch (JSONException | NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }


                HTTPHandler httphandler = new HTTPHandler();
                httphandler.setAsyncResult(context);
                httphandler.execute("POST", "http://10.4.41.165:5000/login", jsonLogin.toString());
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogInActivity.this, SignInActivity.class));
            }
        });
    }

    @Override
    public void processFinish(JSONObject output) {
        if (output != null) {
            try {
                int response = output.getInt("response");
                if (response == HttpURLConnection.HTTP_OK) {
                    Intent intent = new Intent(LogInActivity.this, DrawerActivity.class);
                    Usuari u = JSONConverter.toUser(output);
                    String token = output.getString("session_token");
                    String user_id = output.getString("id");
                    String user_name = (String) output.get("name");
                    String user_surname = (String) output.get("surname");
                    String user_email = (String) output.get("email");

                    ArrayList<String> user_collesPertany = new ArrayList<>();
                    JSONArray jsonArray = (JSONArray) output.get("belongs");
                    for (int i = 0; i < 2; ++i) {

                        user_collesPertany.add((String) jsonArray.getJSONObject(i).get("name"));
                    }
                    //String user_collesPertany = output.getJSONArray("belongs").getString(Integer.parseInt("name"));
                    SharedPreferences preferences = getSharedPreferences("Shared", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("session-token", token);
                    editor.putString("user_id", user_id);
                    editor.putString("user_name", user_name);
                    editor.putString("user_surname", user_surname);
                    editor.putString("user_email", user_email);
                    editor.putString("user_belongsConvencional", user_collesPertany.get(0));
                    editor.putString("user_belongsUni", user_collesPertany.get(1));

                    ContextUser.getInstance().setId(user_id);
                    ContextUser.getInstance().setCollesPertany(user_collesPertany);
                    ContextUser.getInstance().setNom(user_name);
                    ContextUser.getInstance().setCognoms(user_surname);
                    ContextUser.getInstance().setEmail(user_email);
                    editor.apply();
                    intent.putExtra("User", u);
                    startActivity(intent);
                    finish();
                }
                else if (response == HttpURLConnection.HTTP_FORBIDDEN) { // cas autentificació incorrecte
                    Toast toast = Toast.makeText(context, "El correu o la contrasenya no són correctes", Toast.LENGTH_LONG);
                    toast.show();
                }
                else {
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