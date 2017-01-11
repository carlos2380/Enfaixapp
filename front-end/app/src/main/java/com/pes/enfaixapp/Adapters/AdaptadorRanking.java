package com.pes.enfaixapp.Adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.pes.enfaixapp.Models.RankingItem;
import com.pes.enfaixapp.R;

import java.util.List;

public class AdaptadorRanking extends ArrayAdapter<RankingItem> {
    private LayoutInflater inflador; // Crea Layouts a partir del XML

    public AdaptadorRanking(Context context, List<RankingItem> items) {
        super(context, 0, items);
        inflador = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        RankingItem rankingItem = getItem(position);
        // Declare Variables
        TextView txtNom;
        TextView txtPos;
        TextView txtPunts;

        if (convertView == null) {
            convertView = inflador.inflate(R.layout.custom_listranking, parent, false);
        }

        // Locate the TextViews in listview_item.xml
        txtNom = (TextView) convertView.findViewById(R.id.nomRank);
        txtPos = (TextView) convertView.findViewById(R.id.posicioRank);
        txtPunts = (TextView) convertView.findViewById(R.id.puntsRank);
        txtNom.setText(rankingItem.getName());
        txtPos.setText(String.valueOf(rankingItem.getPos()));
        txtPunts.setText(String.valueOf(rankingItem.getPunctuation()));

        return convertView;
    }

}