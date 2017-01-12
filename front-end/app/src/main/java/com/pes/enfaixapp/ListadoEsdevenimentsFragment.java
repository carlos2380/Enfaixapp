package com.pes.enfaixapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.pes.enfaixapp.Adapters.EsdevenimentAdapter;
import com.pes.enfaixapp.Adapters.RecyclerItemClickListener;
import com.pes.enfaixapp.Controllers.AsyncResult;
import com.pes.enfaixapp.Controllers.ContextUser;
import com.pes.enfaixapp.Controllers.CustomIntent;
import com.pes.enfaixapp.Controllers.HTTPHandler;
import com.pes.enfaixapp.Controllers.JSONConverter;
import com.pes.enfaixapp.Models.Colla;
import com.pes.enfaixapp.Models.Esdeveniment;
import com.pes.enfaixapp.Models.Noticia;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by carlos on 15/11/2016.
 */


/**
 * A ListadoNoticiasFragment fragment containing a simple view.
 */
public class ListadoEsdevenimentsFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static RecyclerView recycler;
    private static RecyclerView.Adapter adapter;
    private static RecyclerView.LayoutManager lManager;
    private static View rootView;
    private static SwipeRefreshLayout mSwipeRefreshLayout;
    private static ProgressBar loading;
    private static FloatingActionButton flaotingButton;

    public ListadoEsdevenimentsFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ListadoEsdevenimentsFragment newInstance(int sectionNumber) {

        ListadoEsdevenimentsFragment fragment = new ListadoEsdevenimentsFragment();
        Bundle args = new Bundle();

        List<Noticia> noticias;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_esdeveniment, container, false);

        flaotingButton = (FloatingActionButton)  rootView.findViewById(R.id.crearEsvList);
        flaotingButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), CrearEsdevenimentActivity.class);
                    ((AppCompatActivity)getActivity()).startActivityForResult(intent, 1);

                }
        });

        flaotingButton.setVisibility(View.GONE);
        ArrayList<Colla> collesAdmin = ContextUser.getInstance().getCollesAdmin();
        for (int i= 0; i < collesAdmin.size(); ++i){
            if (collesAdmin.get(i).getId() == Integer.valueOf(ContextUser.getInstance().getId_collaSwitch())){
                flaotingButton.setVisibility(View.VISIBLE);
            }
        }


        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayoutEsdv);
        loading = (ProgressBar) rootView.findViewById(R.id.loadingEsdeveniment);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                myAsync();
            }
        });

        myAsync();
        return rootView;
    }

    private void myAsync() {
        MyAsync async = new MyAsync(rootView.getContext());
        async.callEsdeveniment(rootView.getContext());
    }

    public void insertarEsdeveniments(final List<Esdeveniment> esdeveniments) {
        // Obtener el Recycler
        loading.setVisibility(View.GONE);
        recycler = (RecyclerView) rootView.findViewById(R.id.recicladorEsdev);
        recycler.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(rootView.getContext());
        recycler.setLayoutManager(lManager);

        // Crear un nuevo adaptador
        adapter = new EsdevenimentAdapter((List<Esdeveniment>) esdeveniments);
        recycler.setAdapter(adapter);

        recycler.addOnItemTouchListener(
                new RecyclerItemClickListener(rootView.getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                       /* Uri uri = Uri.parse(esdeveniments.get(position).getUrl());
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);*/

                        Intent intent = new Intent(getActivity(), EsdevenimentActivity.class);
                        intent.putExtra("address", esdeveniments.get(position).getDireccio());
                        intent.putExtra("colla_id", String.valueOf(esdeveniments.get(position).getColla()));
                        intent.putExtra("date", esdeveniments.get(position).getDate());
                        intent.putExtra("description", esdeveniments.get(position).getDescripcio());
                        intent.putExtra("id", String.valueOf(esdeveniments.get(position).getId()));
                        CustomIntent.getInstance().setFoto(esdeveniments.get(position).getFoto());
                        intent.putExtra("title", esdeveniments.get(position).getTitol());
                        intent.putExtra("user_id", esdeveniments.get(position).getUsuari());
                        ((AppCompatActivity)getActivity()).startActivity(intent);
                    }
                })
        );

        mSwipeRefreshLayout.setRefreshing(false);
    }



    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }

    private void showError(String err) {
        Toast.makeText(rootView.getContext(), err, Toast.LENGTH_LONG).show();
    }

    private class MyAsync implements AsyncResult {
        Context context;
        public MyAsync(Context context) {
            this.context = context;
        }

        public void callEsdeveniment(Context context) {
            HTTPHandler httphandler = new HTTPHandler();
            httphandler.setAsyncResult(this);
            httphandler.execute("GET", "http://10.4.41.165:5000/events", null);
        }

        @Override
        public void processFinish(JSONObject output) {
            try {
                insertarEsdeveniments(JSONConverter.toEsdeveniments(output));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                showError("Internal Error: Can't push the events");
            } catch (Exception e) {
                e.printStackTrace();
                showError(e.getMessage().toString());
            }
        }
    }
}