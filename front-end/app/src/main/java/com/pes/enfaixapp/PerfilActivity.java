package com.pes.enfaixapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilActivity extends Fragment {


    public PerfilActivity() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        TextView nomUsuari = (TextView) view.findViewById(R.id.nomUsuari);
        TextView cognomsUsuari = (TextView) view.findViewById(R.id.cognomsUsuari);
        TextView emailUsuari = (TextView) view.findViewById(R.id.emailUsuari);


        return view;
    }

}
