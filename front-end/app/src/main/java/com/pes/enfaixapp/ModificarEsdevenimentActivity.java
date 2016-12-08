package com.pes.enfaixapp;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.SharedPreferencesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.pes.enfaixapp.Models.Esdeveniment;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.pes.enfaixapp.R.id.imageView;
import static com.pes.enfaixapp.R.id.titolEsdvModify;


/**
 * A simple {@link Fragment} subclass.
 */
public class ModificarEsdevenimentActivity extends Activity {



    public ModificarEsdevenimentActivity() {
        // Required empty public constructor
    }

    private ImageView imageView;
    private Uri uriFoto;
    private Button afegirFotoViaDisp;
    private Button afegirFotoViaCam;
    private Button eliminarFoto;
    private ImageButton modificarEsdv;
    final static int RESULTADO_FOTO = 0;
    final static int RESULTADO_GALERIA = 1;
    final static int RESULTADO_BORRAR_FOTO = 2;
    private Esdeveniment esdv = new Esdeveniment();
    private EditText titolEsdvModify;
    private EditText etdireccioModify;
    private EditText etdescriptModify;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_esdeveniment);

        imageView = (ImageView) findViewById(R.id.imatgeModificarEsdeveniment);
        //afegirFotoViaCam = (Button) viewCrearEsdv.findViewById(R.id.afegirViaCamara);
        afegirFotoViaDisp = (Button) findViewById(R.id.afegirViaDispositiu);
        eliminarFoto = (Button) findViewById(R.id.eliminarFoto);
        modificarEsdv = (ImageButton) findViewById(R.id.modificarEsv);
        titolEsdvModify = (EditText) findViewById(R.id.titolEsdvModify);
        etdireccioModify = (EditText) findViewById(R.id.localitzacioEsdvModify);
        etdescriptModify = (EditText) findViewById(R.id.descrEsdvModify);


        afegirFotoViaDisp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, RESULTADO_GALERIA);            }
        });

        eliminarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, RESULTADO_BORRAR_FOTO);
            }
        });

        modificarEsdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModificarEsdevenimentActivity.MyAsync async = new ModificarEsdevenimentActivity.MyAsync(getApplicationContext());
                try {
                    async.modifyEsdeveniment(getApplicationContext());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //Recoger la vuelta a la actividad
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*if (requestCode == RESULTADO_EDITAR) {
            actualizarVistas();

            //Forzar a acutalizar la vista
            findViewById(R.id.scrollView1).invalidate();
        }*/
        if (/* Si no a cancelado la accion y esta correcto*/ requestCode == RESULTADO_GALERIA
                ) {
            esdv.setFoto(data.getDataString());
            ponerFoto(imageView, esdv.getFoto());

        }
        /*if(requestCode == RESULTADO_FOTO) {           //PER LA POSAR FOTO A TRAVES DE CAM => DE MOMENT HO DEIXEM
            esdv.setFoto(uriFoto.toString());
            ponerFoto(imageView, esdv.getFoto());
        }*/

        else if(requestCode == RESULTADO_BORRAR_FOTO) {
            esdv.setFoto(null);
            ponerFoto(imageView, esdv.getFoto());
        }
    }

    public void ponerFoto(ImageView img, String foto) {
        //Compruba que es una foto y la pone
        imageView.setImageURI(uriFoto);
        if (foto != null) {
        } else {
            imageView.setImageBitmap(null);
        }
    }

    //Abrir Galeria o similar
    public void galeria(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, RESULTADO_GALERIA);
    }


    private class MyAsync implements AsyncResult {
        Context context;
        public MyAsync(Context context) {
            this.context = context;
        }

        public void modifyEsdeveniment(Context context) throws JSONException {
            JSONObject jsonUser = new JSONObject();
            jsonUser.accumulate("title", titolEsdvModify.getText().toString());
            jsonUser.accumulate("description", etdescriptModify.getText().toString());
            jsonUser.accumulate("path", uriFoto);

            Calendar c = Calendar.getInstance();
            int cDay = c.get(Calendar.DAY_OF_MONTH);
            int cMonth = c.get(Calendar.MONTH) + 1;
            int cYear = c.get(Calendar.YEAR);
            String selectedMonth = "" + cMonth;
            String selectedYear = "" + cYear;
            Object cHour = c.get(Calendar.HOUR);
            Object cMinute = c.get(Calendar.MINUTE);
            Object cSecond = c.get(Calendar.SECOND);

            SimpleDateFormat sdf = new SimpleDateFormat("dd,MMMM,YYYY");
            String dateStr = sdf.format(c.getTime());
            Date date = null;
            try {
                date = sdf.parse(dateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            jsonUser.accumulate("date", date);

            jsonUser.accumulate("address", etdireccioModify.getText().toString());
            //***********************************//
            //      PONER USUARIO                //
            //***********************************//

            jsonUser.accumulate("id_user", "1");
            //***********************************//
            //      PONER COLLA                  //
            //***********************************//
            jsonUser.accumulate("id_colla", "1");
            HTTPHandler httphandler = new HTTPHandler();
            httphandler.setAsyncResult(this);
            httphandler.execute("PUT", "http://10.4.41.165:5000/events/", null);
        }

        @Override
        public void processFinish(JSONObject output) {

            guardaryvolver();
        }

        public void guardaryvolver() {
            //***********************************//
            //     TOAST DE GUARDADO OK
            //     VOLVER
            //***********************************//


        }
    }

}
