package com.pes.enfaixapp;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.pes.enfaixapp.Controllers.AsyncResult;
import com.pes.enfaixapp.Controllers.BitmapUtilities;
import com.pes.enfaixapp.Controllers.ContextUser;
import com.pes.enfaixapp.Controllers.HTTPHandler;
import com.pes.enfaixapp.Models.Esdeveniment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class ModificarEsdevenimentActivity extends AppCompatActivity implements OnMapReadyCallback {



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
    private String image;
    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;
    private int year, month, day;
    private String colla_id;

    private GoogleMap mGoogleMap;
    SupportMapFragment mapFragment;
    Marker marker;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_esdeveniment);


        imageView = (ImageView) findViewById(R.id.imatgeCrearEsdeveniment);
        //afegirFotoViaCam = (Button) viewCrearEsdv.findViewById(R.id.afegirViaCamara);
        afegirFotoViaDisp = (Button) findViewById(R.id.afegirViaDispositiu);
        eliminarFoto = (Button) findViewById(R.id.eliminarFoto);


        modificarEsdv = (ImageButton) findViewById(R.id.crearEsv);  //canviem la imatge

        final int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            modificarEsdv.setBackgroundDrawable( getResources().getDrawable(R.drawable.ic_edit_black_24dp) );
        } else {
            modificarEsdv.setBackground( getResources().getDrawable(R.drawable.ic_edit_black_24dp));
        }
        modificarEsdv.setImageResource(R.drawable.ic_edit_black_24dp);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapaCrearEsdv);

        titolEsdvModify = (EditText) findViewById(R.id.titolEsdv);
        etdireccioModify = (EditText) findViewById(R.id.localitzacioEsdv);
        etdescriptModify = (EditText) findViewById(R.id.descrEsdv);
        dateView = (TextView) findViewById(R.id.textView3);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);

        titolEsdvModify.setText(getIntent().getExtras().getString("titol"));
        etdireccioModify.setText(getIntent().getExtras().getString("direccio"));
        etdescriptModify.setText(getIntent().getExtras().getString("descripcio"));
        imageView.setImageBitmap(BitmapUtilities.stringToBitMap(getIntent().getExtras().getString("foto")));
        colla_id = getIntent().getExtras().getString("colla_id");

        if (colla_id.equals(String.valueOf(ContextUser.getInstance().getCollesPertany().get(0).getId())))
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(ContextUser.getInstance().getCollesPertany().get(0).getColor())));
        else if (colla_id.equals(String.valueOf(ContextUser.getInstance().getCollesPertany().get(1).getId()))) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(ContextUser.getInstance().getCollesPertany().get(1).getColor())));
        }

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

        etdireccioModify.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                syncMap();
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
            Uri contentURI = Uri.parse(data.getDataString());
            ContentResolver cr = getContentResolver();
            InputStream in = null;
            try {
                in = cr.openInputStream(contentURI);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize=8;
            Bitmap thumb = BitmapFactory.decodeStream(in,null,options);
            imageView.setImageBitmap(thumb);
            imageView.buildDrawingCache();
            image = BitmapUtilities.bitMapToString(thumb);

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


    class MyAsync implements AsyncResult {
        Context context;
        public MyAsync(Context context) {
            this.context = context;
        }

        public void modifyEsdeveniment(Context context) throws JSONException {
            JSONObject jsonEvent = new JSONObject();
            jsonEvent.accumulate("title", titolEsdvModify.getText().toString());
            jsonEvent.accumulate("description", etdescriptModify.getText().toString());
            jsonEvent.accumulate("img", image);

            String date = dateView.getText().toString();

            jsonEvent.accumulate("date", date);
            jsonEvent.accumulate("address", etdireccioModify.getText().toString());
            jsonEvent.accumulate("user_id", "1");
            jsonEvent.accumulate("colla_id", "1");
            HTTPHandler httphandler = new HTTPHandler();
            httphandler.setAsyncResult(this);
            httphandler.execute("PUT", "http://10.4.41.165:5000/events/4", jsonEvent.toString());
        }

        @Override
        public void processFinish(JSONObject output) {
            guardaryvolver();
        }
    }

        public void guardaryvolver() {
            //***********************************//
            //     TOAST DE GUARDADO OK
            //     VOLVER
            //***********************************//
            Toast toast = Toast.makeText(getApplicationContext(), "Esdeveniment modificat correctament", Toast.LENGTH_LONG);
            toast.show();
            finish();

        }

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

    //MAPA GOOGLE
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> address;
        try {
            address = geocoder.getFromLocationName(etdireccioModify.getText().toString(), 1);
            if(address.size() > 0) {
                LatLng latlong = new LatLng(address.get(0).getLatitude(), address.get(0).getLongitude());
                if(marker != null) marker.remove();
                //mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                marker = mGoogleMap.addMarker(new MarkerOptions().position(latlong).title(titolEsdvModify.getText().toString()));
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlong, 14.0f));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        // Add a marker in Sydney and move the camera

    }

    //Para poder llamarlo dentro de OnChange, no es necesario la funcion sino
    private void syncMap(){
        mapFragment.getMapAsync(this);
    }
}


