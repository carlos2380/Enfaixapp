package com.pes.enfaixapp.Adapters;

/**
 * Created by carlos on 15/11/2016.
 */
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.pes.enfaixapp.NoticiaActivity;


/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class FragmentTabNoticiaAdapter extends FragmentStatePagerAdapter {

    public FragmentTabNoticiaAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        if(position == 0) return new ListadoNoticiasFragment();
        else return new ListadoNoticiasFragmentSeguidas();
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
                return "TOTES";
            case 1:
                return "PREFERIDES";
        }
        return null;
    }
}