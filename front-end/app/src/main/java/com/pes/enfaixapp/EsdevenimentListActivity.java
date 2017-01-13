package com.pes.enfaixapp;


import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.pes.enfaixapp.Adapters.FragmentTabEsdevenimentAdapter;
import com.pes.enfaixapp.Adapters.FragmentTabNoticiaAdapter;
import com.pes.enfaixapp.Controllers.ContextUser;

import java.io.File;


/**
 * A simple {@link Fragment} subclass.
 */
public class EsdevenimentListActivity extends Fragment {


    private static View view;
    private ViewPager mViewPager;
    private TabLayout tabLayout;

    private android.support.design.widget.AppBarLayout appbar;
    private FragmentTabEsdevenimentAdapter mFragmentTabEsdevenimentAdapter;
    public EsdevenimentListActivity() {
        // Required empty public constructor
        if (appbar != null) {
            if (ContextUser.getInstance().getCollesPertany().size() > 0) {
                if ((ContextUser.getInstance().getCollesPertany().get(0).getId()) == Integer.valueOf(ContextUser.getInstance().getId_collaSwitch())) {
                    appbar.setBackgroundColor(Color.parseColor(ContextUser.getInstance().getCollesPertany().get(0).getColor()));
                } else {
                    if (ContextUser.getInstance().getCollesPertany().size() > 1) {
                        if ((ContextUser.getInstance().getCollesPertany().get(1).getId()) == Integer.valueOf(ContextUser.getInstance().getId_collaSwitch())) {
                            appbar.setBackgroundColor(Color.parseColor(ContextUser.getInstance().getCollesPertany().get(1).getColor()));
                        }
                    }
                }
            }
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.activity_esdeveniment_list, container, false);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
         mFragmentTabEsdevenimentAdapter = new FragmentTabEsdevenimentAdapter(((AppCompatActivity)getActivity()).getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) view.findViewById(R.id.containerEsdv);
        mViewPager.setAdapter( mFragmentTabEsdevenimentAdapter);

        appbar = (AppBarLayout) view.findViewById(R.id.appbarEsdev);
        tabLayout = (TabLayout) view.findViewById(R.id.tabsEsdv);
        tabLayout.setupWithViewPager(mViewPager);

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(mViewPager);
            }
        });

        if (appbar != null) {
            if (ContextUser.getInstance().getCollesPertany().size() > 0) {
                if ((ContextUser.getInstance().getCollesPertany().get(0).getId()) == Integer.valueOf(ContextUser.getInstance().getId_collaSwitch())) {
                    appbar.setBackgroundColor(Color.parseColor(ContextUser.getInstance().getCollesPertany().get(0).getColor()));
                } else {
                    if (ContextUser.getInstance().getCollesPertany().size() > 1) {
                        if ((ContextUser.getInstance().getCollesPertany().get(1).getId()) == Integer.valueOf(ContextUser.getInstance().getId_collaSwitch())) {
                            appbar.setBackgroundColor(Color.parseColor(ContextUser.getInstance().getCollesPertany().get(1).getColor()));
                        }
                    }
                }
            }
        }
        return view;
    }

}
