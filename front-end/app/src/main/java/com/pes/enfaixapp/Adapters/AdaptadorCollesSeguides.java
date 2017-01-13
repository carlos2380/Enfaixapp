package com.pes.enfaixapp.Adapters;

/**
 * Created by Marc on 25/10/2016.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pes.enfaixapp.Models.Colla;
import com.pes.enfaixapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Àlex on 24/10/2016.
 */

public class AdaptadorCollesSeguides extends ArrayAdapter<Colla>implements
        View.OnClickListener {

    private LayoutInflater layoutInflater;

    public AdaptadorCollesSeguides(Context context, List<Colla> objects) {
        super(context, 0, objects);
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // holder pattern
        Holder holder = null;
        if (convertView == null) {
            holder = new Holder();

            convertView = layoutInflater.inflate(R.layout.listview_colla, parent, false);
            holder.setTextViewTitle((TextView) convertView.findViewById(R.id.listText));
            holder.setCheckBox((CheckBox) convertView.findViewById(R.id.checkBox));
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        final Colla row = getItem(position);
        holder.getTextViewTitle().setText(row.getName());
        holder.getCheckBox().setTag(position);
        holder.getCheckBox().setChecked(row.isSeleccionadaSeguida());
        holder.getCheckBox().setOnClickListener(this);

        return convertView;
    }

    @Override
    public void onClick(View v) {

        CheckBox checkBox = (CheckBox) v;
        int position = (Integer) v.getTag();
        getItem(position).setSeleccionadaSeguida(checkBox.isChecked());

        String msg = "Posicion " +String.valueOf(position);
        //Toast.makeText(this.getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    static class Holder
    {
        TextView textViewTitle;
        TextView textViewSubtitle;
        CheckBox checkBox;

        public TextView getTextViewTitle()
        {
            return textViewTitle;
        }

        public void setTextViewTitle(TextView textViewTitle)
        {
            this.textViewTitle = textViewTitle;
        }

        public TextView getTextViewSubtitle()
        {
            return textViewSubtitle;
        }

        public void setTextViewSubtitle(TextView textViewSubtitle)
        {
            this.textViewSubtitle = textViewSubtitle;
        }
        public CheckBox getCheckBox()
        {
            return checkBox;
        }
        public void setCheckBox(CheckBox checkBox)
        {
            this.checkBox = checkBox;
        }

    }
}
