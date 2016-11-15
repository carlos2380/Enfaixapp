package com.pes.enfaixapp.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pes.enfaixapp.Models.Noticia;
import com.pes.enfaixapp.R;


import java.util.List;
/**
 * Created by carlos on 15/11/2016.
 */

public class NoticiasAdapter extends RecyclerView.Adapter<NoticiasAdapter.NoticiasViewHolder> {
    private List<Noticia> items;

    public static class NoticiasViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public ImageView imagen;
        public TextView nombre;
        public TextView visitas;

        public NoticiasViewHolder(View v) {

            super(v);
            imagen = (ImageView) v.findViewById(R.id.imagen);
            nombre = (TextView) v.findViewById(R.id.titulo);
            visitas = (TextView) v.findViewById(R.id.contentido);
        }
    }

    public NoticiasAdapter(List<Noticia> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public NoticiasViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_noticia, viewGroup, false);
        return new NoticiasViewHolder(v);
    }

    @Override
    public void onBindViewHolder(NoticiasViewHolder viewHolder, int i) {
        viewHolder.imagen.setImageResource(items.get(i).getPhotoId());
        viewHolder.nombre.setText(items.get(i).getTitulo());
        viewHolder.visitas.setText(items.get(i).getContenido());
    }
}