package com.example.pc.doantotnghiep.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.pc.doantotnghiep.View.Fragments.AnGiFragment;
import com.example.pc.doantotnghiep.View.Fragments.OdauFragment;

/**
 * Created by PC on 12/03/2018.
 */

public class AdapterViewPageTrangChu extends FragmentStatePagerAdapter {
    AnGiFragment angiFragment;
    OdauFragment odauFragment;

    public AdapterViewPageTrangChu(FragmentManager fm) {
        super(fm);
        odauFragment = new OdauFragment();
        angiFragment = new AnGiFragment();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return odauFragment;
            case 1:
                return angiFragment ;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return 2;
    }
}
