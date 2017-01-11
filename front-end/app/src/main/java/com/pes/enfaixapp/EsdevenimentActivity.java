package com.pes.enfaixapp;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.pes.enfaixapp.Controllers.BitmapUtilities;
import com.pes.enfaixapp.Controllers.ContextUser;
import com.pes.enfaixapp.Controllers.CustomIntent;
import com.pes.enfaixapp.Models.Esdeveniment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class EsdevenimentActivity extends AppCompatActivity implements OnMapReadyCallback {


    public EsdevenimentActivity() {
        // Required empty public constructor
    }
    private GoogleMap mGoogleMap;
    SupportMapFragment mapFragment;
    Marker marker;

    private ImageView fotoiv;
    private TextView titolEsdv;
    private TextView descripcioEsdv;
    private TextView direccioEsdv;
    private String address;
    private String colla_id;
    private String date;
    private String description;
    private String idEsdv;
    private String title;
    private String user_id;
    private String foto;
    private ImageButton modificarib;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esdeveniment);

        fotoiv = (ImageView) findViewById(R.id.imatgeCrearEsdeveniment);
        descripcioEsdv = (TextView) findViewById(R.id.descripcioEsdvMostrar);
        direccioEsdv = (TextView) findViewById(R.id.direccioEsdvMostrar);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapaEsdv);
        modificarib = (ImageButton) findViewById(R.id.modificarEsv);

        //      TODO: AGAFAR LA FOTO DE LA GALERIA I POSAR BOTÓ QUE ENLLAÇI A MODIFICAR

        // AGAFAR DADES DE L'ESDEVENIMENT
        Esdeveniment esdv = new Esdeveniment();


        address = getIntent().getExtras().getString("address");
        colla_id = getIntent().getExtras().getString("colla_id");
        date = getIntent().getExtras().getString("date");
        description = getIntent().getExtras().getString("description");
        idEsdv = getIntent().getExtras().getString("id");
        foto = CustomIntent.getInstance().getFoto();
        title = getIntent().getExtras().getString("title");
        user_id = getIntent().getExtras().getString("user_id");

        getSupportActionBar().setTitle(title);

        //SETEJAR LES DADES DE L'ESDEVENIMENT

        //Bitmap image = StringToBitMap();

        descripcioEsdv.setText(description);
        direccioEsdv.setText(address);
        fotoiv.setImageBitmap(BitmapUtilities.stringToBitMap(foto));
        mapFragment.getMapAsync(this);

        modificarib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {  //CRIDES HTTP PER FER UN POST SOBRE ESDEVENIMENTS
                Intent intent = new Intent(getApplicationContext(), ModificarEsdevenimentActivity.class);
                startActivity(intent);
            }


        });


    }

    //MAPA GOOGLE
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> direccions;
        try {
            direccions = geocoder.getFromLocationName(address, 1);
            if(direccions.size() > 0) {
                LatLng latlong = new LatLng(direccions.get(0).getLatitude(), direccions.get(0).getLongitude());
                if(marker != null) marker.remove();
                //mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                marker = mGoogleMap.addMarker(new MarkerOptions().position(latlong).title(title));
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlong, 14.0f));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        // Add a marker in Sydney and move the camera

    }

    @Override
    public void onBackPressed() {
        finish();
    }

}
