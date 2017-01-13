package com.pes.enfaixapp.Adapters;

/**
 * Created by Marc on 25/10/2016.
 */

/**
 * Created by Marc on 25/10/2016.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.pes.enfaixapp.Models.Colla;
import com.pes.enfaixapp.R;

import java.util.ArrayList;

/**
 * Created by Àlex on 24/10/2016.
 */

public class AdaptadorColla extends ArrayAdapter {

    protected ArrayList<Colla> colles;
    private Context context;
    private Integer selected_position = -1;

    public AdaptadorColla(Context context, ArrayList<Colla> items) {
        super(context,0,items);
        this.colles = items;
        this.context = context;
    }

    @Override
    public int getCount() {
        return colles.size();
    }

    public void clear() {
        colles.clear();
    }

    public void addAll(ArrayList<Colla> category) {
        for (int i = 0; i < category.size(); i++) {
            colles.add(category.get(i));
        }
    }

    @Override
    public Object getItem(int arg0) {
        return colles.get(arg0);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            // Create a new view into the list.
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_colla, parent, false);
        }

        // Set data into the view.
        ImageView imatgeColla = (ImageView) convertView.findViewById(R.id.listImage);
        TextView nomColla = (TextView) convertView.findViewById(R.id.listText);
        final CheckBox chkbox=(CheckBox)convertView.findViewById(R.id.checkBox);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chkbox.setChecked(true);

            }
        });

        Colla c = (Colla) getItem(position);
        nomColla.setText(c.getName());

        chkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    selected_position =  position;
                }
                else {
                    //selected_position = -1;
                }
                notifyDataSetChanged();
            }
        });

        chkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chkbox.isChecked()) chkbox.setChecked(false);
            }
        });

        chkbox.setChecked(position==selected_position);
        c.setSeleccionada(position==selected_position);



        return convertView;
    }

}