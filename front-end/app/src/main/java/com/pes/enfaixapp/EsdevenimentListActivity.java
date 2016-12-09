package com.pes.enfaixapp;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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

import java.io.File;


/**
 * A simple {@link Fragment} subclass.
 */
public class EsdevenimentListActivity extends Fragment {


    private static View view;
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private FragmentTabEsdevenimentAdapter mFragmentTabEsdevenimentAdapter;
    public EsdevenimentListActivity() {
        // Required empty public constructor
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

        tabLayout = (TabLayout) view.findViewById(R.id.tabsEsdv);
        tabLayout.setupWithViewPager(mViewPager);

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(mViewPager);
            }
        });
        return view;
    }

}
