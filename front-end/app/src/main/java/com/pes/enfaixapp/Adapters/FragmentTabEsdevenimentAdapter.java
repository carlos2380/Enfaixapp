package com.pes.enfaixapp.Adapters;

/**
 * Created by carlos on 15/11/2016.
 */
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.pes.enfaixapp.ListadoEsdevenimentsFragment;
import com.pes.enfaixapp.ListadoEsdevenimentsFragmentSeguides;


/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class FragmentTabEsdevenimentAdapter extends FragmentStatePagerAdapter {

    public FragmentTabEsdevenimentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        if(position == 0) return ListadoEsdevenimentsFragment.newInstance(position + 1);
        else return ListadoEsdevenimentsFragmentSeguides.newInstance(position + 1);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "TOTS";
            case 1:
                return "PREFERITS";
        }
        return null;
    }
}