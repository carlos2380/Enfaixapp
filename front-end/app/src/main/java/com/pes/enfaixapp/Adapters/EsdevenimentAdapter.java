package com.pes.enfaixapp.Adapters;

import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.pes.enfaixapp.Controllers.BitmapUtilities;
import com.pes.enfaixapp.Models.Esdeveniment;
import com.pes.enfaixapp.Models.Noticia;
import com.pes.enfaixapp.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by carlos on 15/11/2016.
 */

public class EsdevenimentAdapter extends RecyclerView.Adapter<EsdevenimentAdapter.EsdevenimentViewHolder> {
    private List<Esdeveniment> items;

    public static class EsdevenimentViewHolder extends RecyclerView.ViewHolder  implements OnMapReadyCallback{
        // Campos respectivos de un item
        public ImageView imagen;
        public TextView nombre;
        public TextView descripcio;
        public TextView localitzacio;
        public TextView data;
        public GoogleMap mGoogleMap;
        public Marker marker;
        public MapView map;
        private View viw;

        public EsdevenimentViewHolder(View v) {

            super(v);
            viw = v;
            imagen = (ImageView) v.findViewById(R.id.fotoEsdvCard);
            nombre = (TextView) v.findViewById(R.id.tituloEsdvCard);
            descripcio = (TextView) v.findViewById(R.id.contentidoEsdvCard);
            localitzacio = (TextView) v.findViewById(R.id.localitzacioEsdvCard);
            data = (TextView) v.findViewById(R.id.dateEsdvCard);
            map = (MapView) v.findViewById(R.id.mapEsdvCard);

            if (map != null)
            {
                map.onCreate(null);
                map.onResume();
                map.getMapAsync(this);
            }
        }

        @Override
        public void onMapReady(GoogleMap googleMap) {
            mGoogleMap = googleMap;
            Geocoder geocoder = new Geocoder(viw.getContext(), Locale.getDefault());
            List<Address> address;
            try {
                address = geocoder.getFromLocationName(localitzacio.getText().toString().substring(6), 1);
                if(address.size() > 0) {
                    LatLng latlong = new LatLng(address.get(0).getLatitude(), address.get(0).getLongitude());
                    if(marker != null) marker.remove();
                    //mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                    marker = mGoogleMap.addMarker(new MarkerOptions().position(latlong).title(nombre.getText().toString()));
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlong, 10.0f));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            // Add a marker in Sydney and move the camera

        }
    }

    public EsdevenimentAdapter(List<Esdeveniment> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    View v;
    @Override
    public EsdevenimentViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_esdeveniment, viewGroup, false);
        return new EsdevenimentViewHolder(v);
    }

    @Override
    public void onBindViewHolder(EsdevenimentViewHolder viewHolder, int i) {
        viewHolder.imagen.setImageBitmap(BitmapUtilities.stringToBitMap(items.get(i).getFoto()));
        viewHolder.nombre.setText(items.get(i).getTitol());
        viewHolder.descripcio.setText(items.get(i).getDescripcio());
        viewHolder.localitzacio.setText("Lloc: " + items.get(i).getDireccio());
        viewHolder.data.setText(items.get(i).getDate());
        GoogleMap thisMap = viewHolder.mGoogleMap;
        if(thisMap != null) {
            Geocoder geocoder = new Geocoder(v.getContext(), Locale.getDefault());
            List<Address> address;
            try {
                address = geocoder.getFromLocationName(items.get(i).getDireccio().substring(6), 1);
                if(address.size() > 0) {
                    LatLng latlong = new LatLng(address.get(0).getLatitude(), address.get(0).getLongitude());
                    thisMap.addMarker(new MarkerOptions().position(latlong).title(items.get(i).getTitol()));
                    thisMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlong, 10.0f));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //MAPA GOOGLE

}