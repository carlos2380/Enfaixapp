package com.pes.enfaixapp;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.pes.enfaixapp.Adapters.AdaptadorCollaBasic;
import com.pes.enfaixapp.Adapters.AdaptadorRanking;
import com.pes.enfaixapp.Adapters.RecyclerItemClickListener;
import com.pes.enfaixapp.Controllers.AsyncResult;
import com.pes.enfaixapp.Controllers.CustomIntent;
import com.pes.enfaixapp.Controllers.HTTPHandler;
import com.pes.enfaixapp.Controllers.JSONConverter;
import com.pes.enfaixapp.Models.Colla;
import com.pes.enfaixapp.Models.Ranking;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class RankingActivity extends Fragment {

    private View view;
    private ListView list;


    public RankingActivity() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.activity_ranking, container, false);
        list = (ListView) view.findViewById(R.id.listRanking);
        RankingActivity.MyAsync async = new RankingActivity.MyAsync(view.getContext());
        async.callRanking(view.getContext());
        return view;
    }


    public void insertarRanking (ArrayList<Ranking> ranking) {

        ArrayList<Ranking> ran = new ArrayList<Ranking>();
        for (int i = 0; i < ranking.size(); ++i) {
            Ranking r = new Ranking(ranking.get(i).getPunctuation(), ranking.get(i).getName(), ranking.get(i).getPos());
            ran.add(r);
        }
        AdaptadorRanking customAdapter = new AdaptadorRanking(view.getContext(),  ran);
        list.setAdapter(customAdapter);
    }

    private class MyAsync implements AsyncResult {
        Context context;
        public MyAsync(Context context) {
            this.context = context;
        }

        public void callRanking(Context context) {
            HTTPHandler httphandler = new HTTPHandler();
            httphandler.setAsyncResult(this);
            httphandler.execute("GET", "http://10.4.41.165:5000/ranking", null);
        }

        @Override
        public void processFinish(JSONObject output) {
            try {
                ArrayList<Ranking> ranking = new ArrayList<Ranking>();
                ranking = (ArrayList<Ranking>) JSONConverter.toRankingList(output);
                insertarRanking(ranking);
            } catch (Exception e) {
                e.printStackTrace();
                showError(e.getMessage().toString());
            }
        }

        private void showError(String err) {
            Toast.makeText(view.getContext(), err, Toast.LENGTH_LONG).show();
        }
    }


}
