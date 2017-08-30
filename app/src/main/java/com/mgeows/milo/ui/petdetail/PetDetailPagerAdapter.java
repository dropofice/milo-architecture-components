package com.mgeows.milo.ui.petdetail;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by JC on 08/30/2017.
 */

public class PetDetailPagerAdapter extends FragmentStatePagerAdapter {

    private String name;

    public PetDetailPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return PetDetailFragment.forPet(name);
    }

    @Override
    public int getCount() {
        return 6;
    }

    public void setName(String name) {
        this.name = name;
    }
}
