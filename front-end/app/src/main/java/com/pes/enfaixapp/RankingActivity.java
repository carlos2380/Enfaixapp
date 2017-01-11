package com.pes.enfaixapp;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.pes.enfaixapp.Adapters.AdaptadorRanking;
import com.pes.enfaixapp.Controllers.AsyncResult;
import com.pes.enfaixapp.Controllers.HTTPHandler;
import com.pes.enfaixapp.Controllers.JSONConverter;
import com.pes.enfaixapp.Models.RankingItem;

import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class RankingActivity extends Fragment {

    private View view;
    private ListView list;
    private ProgressBar loading;

    public RankingActivity() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.activity_ranking, container, false);
        list = (ListView) view.findViewById(R.id.listRanking);
        loading = (ProgressBar) view.findViewById(R.id.loadingRanking);
        RankingActivity.MyAsync async = new RankingActivity.MyAsync(view.getContext());
        async.callRanking(view.getContext());
        return view;
    }


    public void insertarRanking (ArrayList<RankingItem> rankingItem) {

        ArrayList<RankingItem> ran = new ArrayList<RankingItem>();
        for (int i = 0; i < rankingItem.size(); ++i) {
            RankingItem r = new RankingItem(rankingItem.get(i).getPunctuation(), rankingItem.get(i).getName(), rankingItem.get(i).getPos());
            ran.add(r);
        }
        AdaptadorRanking customAdapter = new AdaptadorRanking(view.getContext(),  ran);
        list.setAdapter(customAdapter);
        loading.setVisibility(View.GONE);
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
                ArrayList<RankingItem> rankingItem = new ArrayList<RankingItem>();
                rankingItem = (ArrayList<RankingItem>) JSONConverter.toRankingList(output);
                insertarRanking(rankingItem);
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
