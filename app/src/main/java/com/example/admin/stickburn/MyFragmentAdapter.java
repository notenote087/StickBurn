package com.example.admin.stickburn;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by timqossible on 11/22/2017.
 */

public class MyFragmentAdapter extends FragmentStatePagerAdapter {
    public MyFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new FragmentDatail();
            case 1: return new FragmentHistory();
            default: return new FragmentDatail();
        }
    }

    @Override
    public int getCount() {return 2;}

    public CharSequence getPageTitle(int position){
        String[] tabs = {"Datail", "History"};
        return tabs[position];
    }
}
