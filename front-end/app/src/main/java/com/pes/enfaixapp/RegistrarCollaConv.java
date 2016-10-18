package com.pes.enfaixapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Àlex on 17/10/2016.
 */

public class RegistrarCollaConv extends Activity {

    Button button;
    Button button2;
    Button button3;
    Button button4;

    View lay1;
    View lay2;
    View lay3;
    View lay4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registre_escollir_collaconv);

        button = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);

        lay1 =  findViewById(R.id.uno);
        lay2 =  findViewById(R.id.dos);
        lay3 = findViewById(R.id.tres);
        lay4 = findViewById(R.id.cuatro);

        lay1.setVisibility(View.GONE);
        lay2.setVisibility(View.VISIBLE);
        lay3.setVisibility(View.GONE);
        lay4.setVisibility(View.GONE);

    }


}
