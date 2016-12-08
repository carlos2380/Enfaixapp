package com.pes.enfaixapp;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.io.File;


/**
 * A simple {@link Fragment} subclass.
 */
public class EsdevenimentListActivity extends Fragment {


    private static View view;
    private ImageButton createEsdv;

    public EsdevenimentListActivity() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.activity_esdeveniment_list, container, false);
        createEsdv = (ImageButton) view.findViewById(R.id.createEsdvActivity);

        createEsdv.setOnClickListener(new View.OnClickListener() {
            private FragmentManager supportFragmentManager;

            public FragmentManager getSupportFragmentManager() {
                return supportFragmentManager;
            }


            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("CLAVE", "VALOR");
                //CrearEsdevenimentActivity fragment = new CrearEsdevenimentActivity();
                /*FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.commit();*/

                Intent intent = new Intent(getActivity(), MapsActivity.class);
                //intent.putExtra(MediaStore.EXTRA_OUTPUT, uriFoto);
                ((AppCompatActivity)getActivity()).startActivity(intent);
            }
        });
        return view;
    }

}
