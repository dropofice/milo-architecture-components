package com.mgeows.milo.ui.petslist;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.mgeows.milo.R;
import com.mgeows.milo.db.entity.Pet;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class PetListFragment extends LifecycleFragment {

    @BindView(R.id.rv_pets_list)
    RecyclerView rvPetsList;
    Unbinder unbinder;
    private PetListAdapter adapter;

    PetListViewModel viewModel;

    public PetListFragment() {
    }

    public static PetListFragment newInstance() {
        return new PetListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        adapter = new PetListAdapter(null, itemClickListener);
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(PetListViewModel.class);

        subscribeUi(viewModel);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.pet_list_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_dummy:
                Pet pet = new Pet("Milo", "Beagle");
                viewModel.insertPet(pet);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void subscribeUi(PetListViewModel viewModel) {
        // Update the list when the data changes
        viewModel.getPets().observe(this, new Observer<List<Pet>>() {
            @Override
            public void onChanged(@Nullable List<Pet> pets) {
                if (pets != null) {
                    adapter.setData(pets);
                }
            }
        });
    }

    private final  PetItemClickListener itemClickListener = new PetItemClickListener() {
        @Override
        public void onItemClick(String name, int position) {
            if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                ((PetListActivity) getActivity()).showPetDetail(name, position);
            }
        }
    };
}
