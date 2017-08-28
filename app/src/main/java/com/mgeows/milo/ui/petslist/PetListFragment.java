package com.mgeows.milo.ui.petslist;

import android.arch.lifecycle.LifecycleFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mgeows.milo.R;

/**
 * Created by JC on 08/28/2017.
 */

public class PetListFragment extends LifecycleFragment {

    public PetListFragment() {
    }

    public static PetListFragment newInstance() {
        return new PetListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.pet_list_fragment, container, false);
        return rootView;
    }
}
