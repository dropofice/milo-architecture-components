package com.mgeows.milo.ui.petdetail;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;


public class PetDetailPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<String> mIds;

    public PetDetailPagerAdapter(FragmentManager fm, ArrayList<String> ids) {
        super(fm);
        this.mIds = ids;
    }

    @Override
    public Fragment getItem(int position) {
        return PetDetailFragment.forPet(mIds.get(position));
    }

    @Override
    public int getCount() {
        return mIds.size();
    }
}
