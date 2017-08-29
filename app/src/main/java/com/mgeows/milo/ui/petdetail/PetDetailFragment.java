package com.mgeows.milo.ui.petdetail;

import android.arch.lifecycle.LifecycleFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mgeows.milo.R;

/**
 * Created by JC on 08/29/2017.
 */

public class PetDetailFragment extends LifecycleFragment {

    public PetDetailFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.pet_detail_fragment, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String name = null;
        name = getArguments().getString("NAME_KEY", "NO_NAME");
        TextView textView = (TextView) getView().findViewById(R.id.textPet);
        textView.setText(name);
    }

    /**
     * Creates PetDetailFragment for specific product ID
     */
    public static PetDetailFragment forPet(String name) {

        PetDetailFragment fragment = new PetDetailFragment();
        Bundle args = new Bundle();
        args.putString("NAME_KEY", name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
