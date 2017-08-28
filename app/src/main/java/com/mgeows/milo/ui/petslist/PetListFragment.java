package com.mgeows.milo.ui.petslist;

import android.arch.lifecycle.LifecycleFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mgeows.milo.R;
import com.mgeows.milo.ui.db.entity.Pet;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by JC on 08/28/2017.
 */

public class PetListFragment extends LifecycleFragment {

    @BindView(R.id.rv_pets_list)
    RecyclerView rvPetsList;
    Unbinder unbinder;
    private PetListAdapter adapter;

    public PetListFragment() {
    }

    public static PetListFragment newInstance() {
        return new PetListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new PetListAdapter(null);
        initData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.pet_list_fragment, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        rvPetsList.setAdapter(adapter);
        rvPetsList.setHasFixedSize(true);
        rvPetsList.setLayoutManager(new LinearLayoutManager(getContext()));
        return rootView;
    }

    private void initData() {
        Pet petA = new Pet("Milo", "Beagle");
        Pet petB = new Pet("Pogi", "Aspin");
        Pet petC = new Pet("Tipo", "Mix");
        Pet petD = new Pet("Shadow", "Aspin");
        Pet petE = new Pet("Chelly", "Siberian");
        Pet petF = new Pet("MuyMuy", "German Sheperd");
        List<Pet> pets = new ArrayList<>();
        pets.add(petA);
        pets.add(petB);
        pets.add(petC);
        pets.add(petD);
        pets.add(petE);
        pets.add(petF);
        adapter.setData(pets);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
