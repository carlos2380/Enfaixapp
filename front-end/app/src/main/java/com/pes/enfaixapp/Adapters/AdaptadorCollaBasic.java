package com.pes.enfaixapp.Adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pes.enfaixapp.Controllers.BitmapUtilities;
import com.pes.enfaixapp.Models.Colla;
import com.pes.enfaixapp.R;

import java.util.List;

/**
 * Created by carlos on 15/11/2016.
 */

public class AdaptadorCollaBasic extends RecyclerView.Adapter<AdaptadorCollaBasic.NoticiasViewHolder> {
    private List<Colla> items;

    public static class NoticiasViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public TextView nombre;
        public ImageView imagen;
        public NoticiasViewHolder(View v) {

            super(v);
            imagen = (ImageView) v.findViewById(R.id.imagenCollaListBasic);
            nombre = (TextView) v.findViewById(R.id.nombreCollaCardBasic);
        }
    }

    public AdaptadorCollaBasic(List<Colla> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public NoticiasViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_colla, viewGroup, false);
        return new NoticiasViewHolder(v);
    }

    @Override
    public void onBindViewHolder(NoticiasViewHolder viewHolder, int i) {
        viewHolder.imagen.setImageBitmap(BitmapUtilities.stringToBitMap(items.get(i).getImage()));
        viewHolder.nombre.setText(items.get(i).getName());
    }
}