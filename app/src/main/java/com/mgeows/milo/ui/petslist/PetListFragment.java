package com.mgeows.milo.ui.petslist;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
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

import com.mgeows.milo.PetApplication;
import com.mgeows.milo.R;
import com.mgeows.milo.db.entity.Pet;
import com.mgeows.milo.vm.PetViewModel;
import com.mgeows.milo.vm.PetViewModelFactory;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class PetListFragment extends LifecycleFragment {

    @BindView(R.id.rv_pets_list)
    RecyclerView rvPetsList;
    Unbinder unbinder;
    private PetListAdapter adapter;
    private Listener mListener;

    private PetViewModel mViewModel;

    public PetListFragment() {
    }

    public static PetListFragment newInstance() {
        return new PetListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setupInjection();
        setHasOptionsMenu(true);
    }

    private void setupInjection() {
//        PetApplication application = (PetApplication) getActivity().getApplication();
//        application.getPetComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.pet_list_fragment, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        adapter = new PetListAdapter(null, itemClickListener);
        rvPetsList.setAdapter(adapter);
        rvPetsList.setHasFixedSize(true);
        rvPetsList.setLayoutManager(new LinearLayoutManager(getContext()));
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = getViewModel();
        subscribeUi();
    }

    private PetViewModel getViewModel() {
        PetApplication application = (PetApplication) getActivity().getApplication();
        PetViewModelFactory factory = new PetViewModelFactory(application);
        return ViewModelProviders.of(this, factory).get(PetViewModel.class);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.pet_list_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                mListener.fireAddEditPetActivity();
                return true;
            case R.id.action_add_dummy:
                addDummy();
                return true;
            case R.id.action_add_dummies:
                addDummies();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addDummies() {
    }

    private void addDummy() {
        Pet pet = new Pet("Pogi", "Aspin", 1, "15", "Bob", "145 Caloocan City", "0917-1234567");
        mViewModel.insertPet(pet);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Listener) {
            mListener = (Listener) context;
        }
        else {
            throw new RuntimeException("Must implement AddEditFragment.Listener");
        }
    }

    @Override
    public void onDetach() {
        mListener = null;
        super.onDetach();
    }

    private void subscribeUi() {
        // Update the list when the data changes
        mViewModel.getPets().observe(this, new Observer<List<Pet>>() {
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
        public void onItemClick(int position, ArrayList<String> ids) {
            if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
               mListener.firePetDetailActivity(position, ids);
            }
        }
    };

    interface Listener {
        void fireAddEditPetActivity();
        void firePetDetailActivity(int position, ArrayList<String> ids);
    }

}
