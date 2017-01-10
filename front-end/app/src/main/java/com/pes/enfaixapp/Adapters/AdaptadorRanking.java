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
import com.pes.enfaixapp.Models.RankingInput;
import com.pes.enfaixapp.R;
import com.pes.enfaixapp.RankingActivity;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorRanking extends ArrayAdapter<RankingInput> {
    private LayoutInflater inflador; // Crea Layouts a partir del XML

    public AdaptadorRanking(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public AdaptadorRanking(Context context, int resource, List<RankingInput> items) {
        super(context, resource, items);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        // Declare Variables
        TextView txtNom;
        TextView txtPos;
        TextView txtPunts;

        //http://developer.android.com/intl/es/reference/android/view/LayoutInflater.html
        View itemView = inflador.inflate(R.layout.activity_ranking, parent, false);

        // Locate the TextViews in listview_item.xml
        txtNom = (TextView) itemView.findViewById(R.id.nomRank);
        txtPos = (TextView) itemView.findViewById(R.id.posicioRank);
        txtPunts = (TextView) itemView.findViewById(R.id.puntsRank);

        return itemView;
    }

}