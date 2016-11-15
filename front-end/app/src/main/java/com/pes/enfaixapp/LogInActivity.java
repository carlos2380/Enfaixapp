package com.pes.enfaixapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LogInActivity extends Activity implements AsyncResult {

    private Button loginButton, signInButton; //log in, cancel
    private EditText usernameField, passwordField; //user, pass
    private LogInActivity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        context = this;
        loginButton =(Button)findViewById(R.id.logInButton);
        signInButton =(Button)findViewById(R.id.signInButton);
        usernameField =(EditText)findViewById(R.id.usernameText);
        passwordField =(EditText)findViewById(R.id.passwordText);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject jsonLogin = new JSONObject();
                try {
                    jsonLogin.accumulate("username", usernameField.getText());
                    MessageDigest messageDigest;
                    messageDigest = MessageDigest.getInstance("MD5");
                    messageDigest.update(usernameField.getText().toString().getBytes());
                    byte[] sum = messageDigest.digest();
                    BigInteger bigInteger = new BigInteger(1,sum);
                    String hash = bigInteger.toString(64);
                    jsonLogin.accumulate("password", hash);
                } catch (JSONException | NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }

                HTTPHandler httphandler = new HTTPHandler();
                httphandler.setAsyncResult(context);
                httphandler.execute("POST", "http://10.4.41.165/login", jsonLogin.toString());
                //SOLO LOCALHOST ---------------------------------------
                startActivity(new Intent(LogInActivity.this, DrawerActivity.class));
                ///////////////////////////////////////////////////
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
    public void processFinish(Object output) {
        JSONObject response = (JSONObject)output;
        try {
            boolean status = response.getBoolean("status");
            if (status) {
                SharedPreferences preferences = getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("session-token", response.getString("session-token"));
                editor.apply();
                startActivity(new Intent(LogInActivity.this, DrawerActivity.class));
            }
            else {
                Toast toast = Toast.makeText(context, "Nom d'usuari o contrasenya incorrectes", Toast.LENGTH_LONG);
                toast.show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}