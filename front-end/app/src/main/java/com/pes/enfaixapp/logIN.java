package com.pes.enfaixapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class logIN extends Activity {

    private Button b1,b2; //log in, cancel
    private EditText ed1,ed2; //user, pass
    private int intents = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        b1=(Button)findViewById(R.id.log_button);
        b2 =(Button)findViewById(R.id.reg_button);
        ed1=(EditText)findViewById(R.id.editText);
        ed2=(EditText)findViewById(R.id.editText2);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((ed1.getText().toString().equals("marc") &&
                        ed2.getText().toString().equals("marti")) || (ed1.getText().toString().equals("alex") &&
                        ed2.getText().toString().equals("correa")) ) {
                        Toast.makeText(getApplicationContext(), "CORRECTE. ACCEDINT...", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(logIN.this, Mur.class));

                }
                else{
                    if (intents == 0) {
                        b1.setBackgroundColor(Color.GRAY);
                        b1.setEnabled(false);
                        Toast.makeText(getApplicationContext(), "TORNA-HO A PROVAR EN 30 SEGONS...", Toast.LENGTH_SHORT).show();

                    }
                    else Toast.makeText(getApplicationContext(), "COMBINACIÓ INCORECCTA", Toast.LENGTH_SHORT).show();

                    intents--;
                }
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(logIN.this, RegistroActivity.class));
            }
        });
    }
}