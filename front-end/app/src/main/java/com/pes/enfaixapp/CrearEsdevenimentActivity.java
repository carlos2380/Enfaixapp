package com.pes.enfaixapp;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pes.enfaixapp.Adapters.ListadoNoticiasFragmentSeguidas;
import com.pes.enfaixapp.Models.Esdeveniment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.EmptyStackException;


/**
 * A simple {@link Fragment} subclass.
 */
public class CrearEsdevenimentActivity extends Activity {


    private ImageView imageView;
    private Uri uriFoto;

    public CrearEsdevenimentActivity() {
        // Required empty public constructor
    }

    private Button afegirFotoViaDisp;
    private Button afegirFotoViaCam;
    private Button eliminarFoto;
    private ImageButton crearEsdv;
    final static int RESULTADO_FOTO = 0;
    final static int RESULTADO_GALERIA = 1;
    final static int RESULTADO_BORRAR_FOTO = 2;
    private Esdeveniment esdv = new Esdeveniment();

    private EditText titolEsdv;
    private EditText etdireccio;
    private EditText etdescript;

    private DatePicker datePicker;
    private Calendar calendar;

    private TextView dateView;
    private int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_esdeveniment);

        /*Bundle bundle = this.getArguments();

        String s = "CASA";
        if (bundle != null) {
            s = bundle.getString("CLAVE");
        }
        Toast toast = Toast.makeText(viewCrearEsdv.getContext(), s, Toast.LENGTH_LONG);
        toast.show(); */

        titolEsdv = (EditText) findViewById(R.id.titolEsdv);
        etdireccio = (EditText) findViewById(R.id.localitzacioEsdv);
        etdescript = (EditText) findViewById(R.id.descrEsdv);

        imageView = (ImageView) findViewById(R.id.imatgeCrearEsdeveniment);
        //afegirFotoViaCam = (Button) viewCrearEsdv.findViewById(R.id.afegirViaCamara);
        afegirFotoViaDisp = (Button) findViewById(R.id.afegirViaDispositiu);
        eliminarFoto = (Button) findViewById(R.id.eliminarFoto);
        crearEsdv = (ImageButton) findViewById(R.id.crearEsv);
        dateView = (TextView) findViewById(R.id.textView3);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);

        /*afegirFotoViaCam.setOnClickListener(new View.OnClickListener() {   //PER LA POSAR FOTO A TRAVES DE CAM => DE MOMENT HO DEIXEM
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                uriFoto = Uri.fromFile(new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES) + File.separator + "img_" + (System.currentTimeMillis() / 1000) + ".jpg"));
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uriFoto);
                ((AppCompatActivity)getActivity()).startActivityForResult(intent, RESULTADO_FOTO);
            }

        });*/
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

        crearEsdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {  //CRIDES HTTP PER FER UN POST SOBRE ESDEVENIMENTS
                CrearEsdevenimentActivity.MyAsync async = new CrearEsdevenimentActivity.MyAsync(getApplicationContext());
                try {
                    async.createEsdeveniment(getApplicationContext());
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

    public void guardaryvolver() {
        //***********************************//
        //     TOAST DE GUARDADO OK
        //     VOLVER
        //***********************************//

        Toast toast = Toast.makeText(getApplicationContext(), "Esdeveniment creat correctament", Toast.LENGTH_LONG);
        toast.show();
        finish();
        //falta fer intent cap enrere
    }

    //Abrir Galeria o similar
    public void galeria(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, RESULTADO_GALERIA);
    }


    class MyAsync implements AsyncResult {
        Context context;
        public MyAsync(Context context) {
            this.context = context;
        }

        public void createEsdeveniment(Context context) throws JSONException {
            JSONObject jsonEvent = new JSONObject();
            jsonEvent.accumulate("title", titolEsdv.getText().toString());
            jsonEvent.accumulate("description", etdescript.getText().toString());
            jsonEvent.accumulate("img", "1234");

            String date = dateView.getText().toString();

            jsonEvent.accumulate("date", date);

            jsonEvent.accumulate("address", etdireccio.getText().toString());
            //***********************************//
            //      PONER USUARIO                //
            //***********************************//

            jsonEvent.accumulate("user_id", "1");
            //***********************************//
            //      PONER COLLA                  //
            //***********************************//
            jsonEvent.accumulate("colla_id", "1");
            HTTPHandler httphandler = new HTTPHandler();
            httphandler.setAsyncResult(this);
            httphandler.execute("POST", "http://10.4.41.165:5000/events", jsonEvent.toString());
        }

        @Override
        public void processFinish(JSONObject output) {
            guardaryvolver();
        }
    }


    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "ca",
                Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2+1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        dateView.setText(new StringBuilder().append(year).append("/")
                .append(month).append("/").append(day));
    }




}
