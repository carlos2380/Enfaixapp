package com.pes.enfaixapp.Adapters;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.pes.enfaixapp.Models.Noticia;
import com.pes.enfaixapp.R;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by carlos on 15/11/2016.
 */


/**
 * A ListadoNoticiasFragment fragment containing a simple view.
 */
public class ListadoNoticiasFragmentSeguidas extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    public ListadoNoticiasFragmentSeguidas() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ListadoNoticiasFragmentSeguidas newInstance(int sectionNumber) {

        ListadoNoticiasFragmentSeguidas fragment = new ListadoNoticiasFragmentSeguidas();
        Bundle args = new Bundle();

        List<Noticia> noticias;
        if(sectionNumber == 1) {
            args.putBoolean("TODASNOTICIAS", true);;
        }else {
            noticias = getNoticiasBackend(false);
            args.putBoolean("TODASNOTICIAS", false);;
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_noticia, container, false);
        // Obtener el Recycler
        recycler = (RecyclerView) rootView.findViewById(R.id.reciclador);
        recycler.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(rootView.getContext());
        recycler.setLayoutManager(lManager);

        final List<Noticia> noticiasAdapter = getNoticiasBackend(getArguments().getBoolean("TODASNOTICIAS"));

        // Crear un nuevo adaptador
        adapter = new NoticiasAdapter((List<Noticia>) noticiasAdapter);
        recycler.setAdapter(adapter);

        recycler.addOnItemTouchListener(
                new RecyclerItemClickListener(rootView.getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Uri uri = Uri.parse(noticiasAdapter.get(position).getUrl());
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                })
        );

        return rootView;
    }

    private static List<Noticia> getNoticiasBackend(boolean todasNoticies) {
        List noticias = new ArrayList();

        if(todasNoticies) {
            noticias.add(new Noticia("Vila 1", "Atentado terrorista en", R.drawable.ic_menu_camera));
            noticias.add(new Noticia("Vila 2 VilaFranca", "25 years old", R.drawable.ic_menu_camera));
            noticias.add(new Noticia("Vila 3", "Enfaixapp guanya la competicio de apps", R.drawable.ic_menu_camera));
            noticias.add(new Noticia("Vilas 1", "Atentado terrorista en", R.drawable.ic_menu_camera));
            noticias.add(new Noticia("Vilas 2 VilaFranca", "25 years old", R.drawable.ic_menu_camera));
            noticias.add(new Noticia("Vilas 3", "Enfaixapp guanya la competicio de apps", R.drawable.ic_menu_camera));
            noticias.add(new Noticia("Vilafd 1", "Atentado terrorista en", R.drawable.ic_menu_camera));
            noticias.add(new Noticia("Vi o la 2 VilaFranca", "25 years old", R.drawable.ic_menu_camera));
            noticias.add(new Noticia("Vilau 3", "Enfaixapp guanya la competicio de apps", R.drawable.ic_menu_camera));
        }else {
            noticias.add(new Noticia("Castellers", "Atentado terrorista en", R.drawable.ic_menu_camera));
            noticias.add(new Noticia("Castellers VilaFranca", "25 years old", R.drawable.ic_menu_camera));
            noticias.add(new Noticia("Castellers vs Castellers", "Enfaixapp guanya la competicio de apps", R.drawable.ic_menu_camera));
        }

        return noticias;
    }
}