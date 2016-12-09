package com.pes.enfaixapp.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pes.enfaixapp.Models.Esdeveniment;
import com.pes.enfaixapp.Models.Noticia;
import com.pes.enfaixapp.R;

import java.util.List;

/**
 * Created by carlos on 15/11/2016.
 */

public class EsdevenimentAdapter extends RecyclerView.Adapter<EsdevenimentAdapter.EsdevenimentViewHolder> {
    private List<Esdeveniment> items;

    public static class EsdevenimentViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public ImageView imagen;
        public TextView nombre;
        public TextView descripcio;
        public TextView localitzacio;

        public EsdevenimentViewHolder(View v) {

            super(v);
            //imagen = (ImageView) v.findViewById(R.id.imagen);
            nombre = (TextView) v.findViewById(R.id.tituloEsdvCard);
            descripcio = (TextView) v.findViewById(R.id.contentidoEsdvCard);
            localitzacio = (TextView) v.findViewById(R.id.localitzacioEsdvCard);
        }
    }

    public EsdevenimentAdapter(List<Esdeveniment> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public EsdevenimentViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_esdeveniment, viewGroup, false);
        return new EsdevenimentViewHolder(v);
    }

    @Override
    public void onBindViewHolder(EsdevenimentViewHolder viewHolder, int i) {
        //viewHolder.imagen.setImageResource(items.get(i).getPhotoId());
        viewHolder.nombre.setText(items.get(i).getTitol());
        viewHolder.descripcio.setText(items.get(i).getDescripcio());
        viewHolder.localitzacio.setText(items.get(i).getDescripcio());
    }
}