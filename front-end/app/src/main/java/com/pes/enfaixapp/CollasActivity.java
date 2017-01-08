package com.pes.enfaixapp;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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

import com.pes.enfaixapp.Adapters.AdaptadorCollaBasic;
import com.pes.enfaixapp.Adapters.EsdevenimentAdapter;
import com.pes.enfaixapp.Adapters.RecyclerItemClickListener;
import com.pes.enfaixapp.Controllers.AsyncResult;
import com.pes.enfaixapp.Controllers.CustomIntent;
import com.pes.enfaixapp.Controllers.HTTPHandler;
import com.pes.enfaixapp.Controllers.JSONConverter;
import com.pes.enfaixapp.Models.Colla;
import com.pes.enfaixapp.Models.Esdeveniment;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CollasActivity extends Fragment {

    private View view;
    private static RecyclerView recycler;
    private static RecyclerView.Adapter adapter;
    private static RecyclerView.LayoutManager lManager;
    private static ProgressBar loading;
    private static FloatingActionButton flaotingButton;

    public CollasActivity() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.activity_collas, container, false);
        loading = (ProgressBar) view.findViewById(R.id.loadingCollas);
        CollasActivity.MyAsync async = new CollasActivity.MyAsync(view.getContext());
        async.callCollas(view.getContext());
        return view;
    }

    public void insertarCollas(final List<Colla> collas) {
        // Obtener el Recycler
        recycler = (RecyclerView) view.findViewById(R.id.recicladorCollas);
        recycler.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(view.getContext());
        recycler.setLayoutManager(lManager);

        // Crear un nuevo adaptador
        adapter = new AdaptadorCollaBasic((List<Colla>) collas);
        recycler.setAdapter(adapter);

        recycler.addOnItemTouchListener(
                new RecyclerItemClickListener(view.getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {

                        Intent intent = new Intent(getActivity(), CollaInfoActivity.class);
                        intent.putExtra("address", collas.get(position).getDireccio());
                        intent.putExtra("color", collas.get(position).getColor());
                        intent.putExtra("description", collas.get(position).getDescripcio());
                        intent.putExtra("email", collas.get(position).getEmail());
                        intent.putExtra("id", collas.get(position).getId());
                        CustomIntent.getInstance().setFoto(collas.get(position).getImage());
                        intent.putExtra("name", collas.get(position).getName());
                        intent.putExtra("phoneNumber", collas.get(position).getTelefono());
                        intent.putExtra("uni", collas.get(position).isUniversitaria());
                        ((AppCompatActivity)getActivity()).startActivity(intent);
                    }
                })
        );
        loading.setVisibility(View.GONE);
    }

    private void showError(String err) {
        Toast.makeText(view.getContext(), err, Toast.LENGTH_LONG).show();
    }


    private class MyAsync implements AsyncResult {
        Context context;
        public MyAsync(Context context) {
            this.context = context;
        }

        public void callCollas(Context context) {
            HTTPHandler httphandler = new HTTPHandler();
            httphandler.setAsyncResult(this);
            httphandler.execute("GET", "http://10.4.41.165:5000/colles", null);
        }

        @Override
        public void processFinish(JSONObject output) {
            try {
                ArrayList<Colla> collas = new ArrayList<Colla>();
                collas = (ArrayList<Colla>) JSONConverter.toCollaList(output);
                insertarCollas(collas);
            } catch (Exception e) {
                e.printStackTrace();
                showError(e.getMessage().toString());
            }
        }
    }

}
