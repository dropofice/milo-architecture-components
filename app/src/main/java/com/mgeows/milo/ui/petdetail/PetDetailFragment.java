package com.mgeows.milo.ui.petdetail;

import android.arch.lifecycle.LifecycleFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mgeows.milo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by JC on 08/29/2017.
 */

public class PetDetailFragment extends LifecycleFragment {

    @BindView(R.id.textPet)
    TextView textPet;
    Unbinder unbinder;

    public PetDetailFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        String name = null;
        if (savedInstanceState != null) {
            name = savedInstanceState.getString("NAME_KEY", "No name");
        }
        View rootView = inflater.inflate(R.layout.pet_detail_fragment, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        textPet.setText(name);
        return rootView;
    }

    /**
     * Creates PetDetailFragment for specific product ID
     */
    public static PetDetailFragment forProduct(String name) {

        PetDetailFragment fragment = new PetDetailFragment();
        Bundle args = new Bundle();
        args.putString("NAME_KEY", name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
