package com.pes.enfaixapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.vision.text.Line;
import com.pes.enfaixapp.Controllers.AsyncResult;
import com.pes.enfaixapp.Controllers.BitmapUtilities;
import com.pes.enfaixapp.Controllers.ContextUser;
import com.pes.enfaixapp.Controllers.HTTPHandler;
import com.pes.enfaixapp.Controllers.JSONConverter;
import com.pes.enfaixapp.Models.Colla;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CollaInfoActivity extends AppCompatActivity implements OnMapReadyCallback {

    private TextView tvNomre;
    private TextView tvDireccio;
    private TextView tvTel;
    private TextView tvDescripcio;
    private TextView tvUrl;
    private TextView tvEmail;
    private ImageView btFoto;
    private Button btFollowColla;
    private Button btUnete;
    private Colla actual;

    private Integer idColla;
    private GoogleMap mGoogleMap;
    SupportMapFragment mapFragment;
    Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colla_info);

        tvNomre = (TextView) findViewById(R.id.nombre_info_colla);
        tvDireccio = (TextView) findViewById(R.id.direccio_info_colla);
        tvTel = (TextView) findViewById(R.id.phone_info_colla);
        tvUrl = (TextView) findViewById(R.id.url_info_colla);
        tvDescripcio = (TextView) findViewById(R.id.descripcio_info_colla);
        tvEmail = (TextView) findViewById(R.id.email_info_colla);
        btFoto = (ImageView) findViewById(R.id.foto_info_colla);
        btFollowColla = (Button) findViewById(R.id.follow_info_colla);
        btUnete = (Button) findViewById(R.id.join_info_colla);

        tvNomre.setText(getIntent().getExtras().getString("name"));

        idColla = getIntent().getExtras().getInt("id");

        CollaInfoActivity.MyAsyncColla async = new CollaInfoActivity.MyAsyncColla(getApplicationContext());
        async.callColla(getApplicationContext());

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor((getIntent().getExtras().getString("color")))));



        btFollowColla.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(btFollowColla.getText().equals("Segueix")) {
                    CollaInfoActivity.MyAsync async = new CollaInfoActivity.MyAsync(getApplicationContext());
                    try {
                        btFollowColla.setText("Unfollow");
                        ArrayList<Colla> collasSeguides = ContextUser.getInstance().getCollesSegueix();
                        Colla cc = new Colla();
                        cc.setId(idColla);
                        collasSeguides.add(cc);
                        async.followColla(getApplicationContext());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else {
                    CollaInfoActivity.MyAsync async = new CollaInfoActivity.MyAsync(getApplicationContext());
                    try {
                        btFollowColla.setText("Segueix");
                        ArrayList<Colla> collasSeguides = ContextUser.getInstance().getCollesSegueix();
                        for (int i = 0; i < collasSeguides.size(); ++i) {
                            if ( collasSeguides.get(i).getId() == idColla)collasSeguides.remove(i);
                        }
                        async.unfollowColla(getApplicationContext());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        btUnete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(btUnete.getText().equals("Uneix-te")) {
                    CollaInfoActivity.MyAsync async = new CollaInfoActivity.MyAsync(getApplicationContext());
                    try {
                        btUnete.setText("Sortir");
                        ArrayList<Colla> collasSeguides = ContextUser.getInstance().getCollesSegueix();
                        Colla cc = new Colla();
                        cc.setId(idColla);
                        collasSeguides.add(cc);
                        async.followColla(getApplicationContext());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else {
                    CollaInfoActivity.MyAsync async = new CollaInfoActivity.MyAsync(getApplicationContext());
                    try {
                        btUnete.setText("Uneix-te");
                        ArrayList<Colla> collasSeguides = ContextUser.getInstance().getCollesSegueix();
                        for (int i = 0; i < collasSeguides.size(); ++i) {
                            if ( collasSeguides.get(i).getId() == idColla)collasSeguides.remove(i);
                        }
                        async.unfollowColla(getApplicationContext());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        btUnete.setVisibility(View.GONE);

    }

    public void insertarInfoColla(Colla c) {
        actual = c;
        if(!c.getDireccio().equals("null")) {
            tvDireccio.setText(c.getDireccio());
        }else {
            tvDireccio.setText("Catalunya");
            actual.setDireccio("Catalunya");
        }

        /*if(!c.getColor().equals("null")) {
            RelativeLayout rl = (RelativeLayout) findViewById(R.id.activity_colla_info);
            rl.setBackgroundColor(Color.parseColor(c.getColor()));
        }*/

        if(!c.getDescripcio().equals("null")) {
            tvDescripcio.setText(c.getDescripcio());
        }else {
            LinearLayout ll = (LinearLayout) findViewById(R.id.layDesc);
            ll.setVisibility(View.GONE);
        }

        if(!c.getTelefono().equals("null")) {
            tvTel.setText(c.getTelefono());
        }else {
            LinearLayout ll = (LinearLayout) findViewById(R.id.layTel);
            ll.setVisibility(View.GONE);
        }

        if(!c.getWeb().equals("null")) {
            tvUrl.setText(c.getWeb());
        }else {
            LinearLayout ll = (LinearLayout) findViewById(R.id.layurl);
            ll.setVisibility(View.GONE);
        }

        if(!c.getEmail().equals("null")) {
            tvEmail.setText(c.getEmail());
        }else {
            LinearLayout ll = (LinearLayout) findViewById(R.id.layurl);
            ll.setVisibility(View.GONE);
        }

        if(!c.getImage().equals("null")) {
            btFoto.setImageBitmap(BitmapUtilities.stringToBitMap(c.getImage()));
        }

        ArrayList<Colla> collasSeguides = ContextUser.getInstance().getCollesSegueix();
        for (int i = 0; i < collasSeguides.size(); ++i) {
            if ( collasSeguides.get(i).getId() == c.getId())btFollowColla.setText("Unfollow");
        }


        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapaInfoColla);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> direccions;
        try {
            direccions = geocoder.getFromLocationName(tvDireccio.getText().toString(), 1);
            if(direccions.size() > 0) {
                LatLng latlong = new LatLng(direccions.get(0).getLatitude(), direccions.get(0).getLongitude());
                if(marker != null) marker.remove();
                //mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                marker = mGoogleMap.addMarker(new MarkerOptions().position(latlong).title(tvNomre.getText().toString()));
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlong, 7.0f));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        // Add a marker in Sydney and move the camera

    }

    public void llamadaTelefono(View view) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + actual.getTelefono())));
    }

    public void verWeb(View view) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(actual.getWeb())));
    }

    private void showError(String err) {
        Toast.makeText(getApplicationContext(), err, Toast.LENGTH_LONG).show();
    }

    class MyAsync implements AsyncResult {
            Context context;
            public MyAsync(Context context) {
                this.context = context;
            }

            public void followColla(Context context) throws JSONException {
                JSONObject jsonFollowColla = new JSONObject();
                jsonFollowColla.accumulate("colla_id", getIntent().getExtras().getInt("id"));
                HTTPHandler httphandler = new HTTPHandler();
                httphandler.setAsyncResult(this);
                httphandler.execute("POST", "http://10.4.41.165:5000/users/" + ContextUser.getInstance().getId() +"/follows?colla_id="+ idColla , jsonFollowColla.toString());
            }

        public void unfollowColla(Context context) throws JSONException {
            JSONObject jsonFollowColla = new JSONObject();
            jsonFollowColla.accumulate("colla_id", getIntent().getExtras().getInt("id"));
            HTTPHandler httphandler = new HTTPHandler();
            httphandler.setAsyncResult(this);
            httphandler.execute("DELETE", "http://10.4.41.165:5000/users/" + ContextUser.getInstance().getId() +"/follows?colla_id="+ idColla , jsonFollowColla.toString());
        }

            @Override
            public void processFinish(JSONObject output) {

            }

    }

    public


    class MyAsyncColla implements AsyncResult {
        Context context;
        public MyAsyncColla(Context context) {
            this.context = context;
        }

        public void callColla(Context context) {
            HTTPHandler httphandler = new HTTPHandler();
            httphandler.setAsyncResult(this);
            httphandler.execute("GET", "http://10.4.41.165:5000/colles/" + idColla.toString(), null);
        }
        @Override
        public void processFinish(JSONObject output) {
            try {
                insertarInfoColla(JSONConverter.toColla(output));
            } catch (Exception e) {
                e.printStackTrace();
                showError(e.getMessage().toString());
            }
        }
    }
}
