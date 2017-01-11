package com.pes.enfaixapp.Adapters;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.vision.text.Text;
import com.pes.enfaixapp.Models.Colla;
import com.pes.enfaixapp.Models.Ranking;
import com.pes.enfaixapp.R;
import com.pes.enfaixapp.RankingActivity;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorRanking extends ArrayAdapter<Ranking> {
    private LayoutInflater inflador; // Crea Layouts a partir del XML

    public AdaptadorRanking(Context context, List<Ranking> items) {
        super(context, 0, items);
        inflador = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Ranking ranking = getItem(position);
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
        txtNom.setText(ranking.getName());
        txtPos.setText(String.valueOf(ranking.getPos()));
        txtPunts.setText(String.valueOf(ranking.getPunctuation()));

        return convertView;
    }

}