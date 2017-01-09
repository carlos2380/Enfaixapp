package com.pes.enfaixapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.vision.text.Line;
import com.pes.enfaixapp.Controllers.AsyncResult;
import com.pes.enfaixapp.Controllers.ContextUser;
import com.pes.enfaixapp.Controllers.HTTPHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class CollaInfoActivity extends AppCompatActivity implements OnMapReadyCallback {

    private TextView tvNomre;
    private TextView tvDireccio;
    private TextView tvTel;
    private TextView tvDescripcio;
    private TextView tvUrl;
    private ImageView btFoto;
    private Button btFollowColla;

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
        tvDescripcio = (TextView) findViewById(R.id.descripcio_info_colla);
        tvDescripcio = (TextView) findViewById(R.id.url_info_colla);
        btFoto = (ImageView) findViewById(R.id.foto_info_colla);
        btFollowColla = (Button) findViewById(R.id.follow_info_colla);

        tvNomre.setText(getIntent().getExtras().getString("name"));

        if(!getIntent().getExtras().getString("address").equals("null")) {
            tvDireccio.setText(getIntent().getExtras().getString("address"));
        }else {
            tvDireccio.setText("Catalunya");
        }

        if(!getIntent().getExtras().getString("color").equals("null")) {
            RelativeLayout rl = (RelativeLayout) findViewById(R.id.activity_colla_info);
            rl.setBackgroundColor(Color.parseColor(getIntent().getExtras().getString("color")));
        }

        if(!getIntent().getExtras().getString("description").equals("null")) {
            tvDescripcio.setText(getIntent().getExtras().getString("description"));
        }else {
            LinearLayout ll = (LinearLayout) findViewById(R.id.layDesc);
            ll.setVisibility(View.GONE);
        }

        if(!getIntent().getExtras().getString("email").equals("null")) {
            tvUrl.setText(getIntent().getExtras().getString("email"));
        }else {
            LinearLayout ll = (LinearLayout) findViewById(R.id.layurl);
            ll.setVisibility(View.GONE);
        }

        if(!getIntent().getExtras().getString("phoneNumber").equals("null")) {
            tvUrl.setText(getIntent().getExtras().getString("phoneNumber"));
        }else {
            LinearLayout ll = (LinearLayout) findViewById(R.id.layTel);
            ll.setVisibility(View.GONE);
        }

        btFollowColla.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                CollaInfoActivity.MyAsync async = new CollaInfoActivity.MyAsync(getApplicationContext());
                try {
                    async.followColla(getApplicationContext());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

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
            httphandler.execute("POST", "http://10.4.41.165:5000/users/" + ContextUser.getInstance().getId() +"/follows", jsonFollowColla.toString());
        }

        @Override
        public void processFinish(JSONObject output) {
            
        }
    }
}
