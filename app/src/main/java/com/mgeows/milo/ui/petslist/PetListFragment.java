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
import com.mgeows.milo.db.util.DatabaseInitializer;
import com.mgeows.milo.di.UiComponent;
import com.mgeows.milo.vm.PetViewModel;
import com.mgeows.milo.vm.PetViewModelFactory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class PetListFragment extends LifecycleFragment implements PetItemClickListener {

    @BindView(R.id.rv_pets_list)
    RecyclerView mRvPetsList;
    Unbinder unbinder;

    private PetListAdapter mAdapter;
    private Listener mListener;
    private PetViewModel mViewModel;
    private UiComponent component;

    public PetListFragment() {
    }

    public static PetListFragment newInstance() {
        return new PetListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupInjection();
        setHasOptionsMenu(true);
    }

    private void setupInjection() {
        PetApplication application = (PetApplication) getActivity().getApplication();
        component = application.getUiComponent(this, this);
        mAdapter = getListAdapter();
    }

    // For testing
    public PetListAdapter getListAdapter() {
        return component.getListAdapter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.pet_list_fragment, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        mRvPetsList.setAdapter(mAdapter);
        mRvPetsList.setHasFixedSize(true);
        mRvPetsList.setLayoutManager(new LinearLayoutManager(getContext()));
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
            case R.id.action_delete_dummies:
                deleteDummies();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addDummy() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2015, 5, 24);
        Date date = calendar.getTime();
        Pet pet = new Pet("Pogi", "Aspin", 1, date, "15", "Bob", "145 Caloocan City", "0917-1234567", null);
        mViewModel.addPet(pet);
    }

    private void addDummies() {
        List<Pet> pets = DatabaseInitializer.initData();
        mViewModel.addAllPets(pets);
    }

    private void deleteDummies() {
        mViewModel.deleteAllPets();
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
                     mAdapter.setData(pets);
                }
            }
        });
    }

    @Override
    public void onItemClick(int position, ArrayList<String> ids) {
        if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            mListener.firePetDetailActivity(position, ids);
        }
    }

    interface Listener {
        void fireAddEditPetActivity();
        void firePetDetailActivity(int position, ArrayList<String> ids);
    }

}
