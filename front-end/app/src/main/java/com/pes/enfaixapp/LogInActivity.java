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

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
                    SharedPreferences preferences = getSharedPreferences("Shared", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("session-token", token);
                    editor.putString("user_id", user_id);
                    ContextUser.getInstance().setId(user_id);
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