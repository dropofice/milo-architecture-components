package com.mgeows.milo.ui.petdetail;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mgeows.milo.PetApplication;
import com.mgeows.milo.R;
import com.mgeows.milo.db.entity.Pet;
import com.mgeows.milo.vm.PetViewModel;
import com.mgeows.milo.vm.PetViewModelFactory;

/**
 * Created by JC on 08/29/2017.
 */

public class PetDetailFragment extends LifecycleFragment {

    private static final String ID_KEY = "id.key.adapter";

    TextView tv;
    private String mId;
    private PetViewModel mViewModel;
    private Listener mListener;

    public PetDetailFragment() {
    }

    /**
     * Creates PetDetailFragment for specific PET ID
     */
    public static PetDetailFragment forPet(String id) {
        PetDetailFragment fragment = new PetDetailFragment();
        Bundle args = new Bundle();
        args.putString(ID_KEY, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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
        mId = getArguments().getString(ID_KEY);
        tv = (TextView) getView().findViewById(R.id.textPetDetail);

        mViewModel = getViewModel();
        subscribeUi(mViewModel, mId);
    }

    private PetViewModel getViewModel() {
        PetApplication application = (PetApplication) getActivity().getApplication();
        PetViewModelFactory factory = new PetViewModelFactory(application);
        return ViewModelProviders.of(this, factory).get(PetViewModel.class);
    }

    private void subscribeUi(PetViewModel viewModel, String id) {
        viewModel.getPet(id).observe(this, new Observer<Pet>() {
            @Override
            public void onChanged(@Nullable Pet pet) {
                if (pet != null) {
                    tv.setText(pet.petName);
                }
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.pet_detail_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                editPet();
                return true;
            case R.id.action_delete:
                deletePet(mId);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void editPet() {
        Toast.makeText(getContext(), "Edit", Toast.LENGTH_SHORT).show();
        mListener.fireAddEditActivity(mId);
    }

    private void deletePet(String id) {
        mViewModel.deletePetById(id);
        Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();
        mListener.finishPetDetailActivity();

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

    interface Listener {
        void fireAddEditActivity(String id);
        void finishPetDetailActivity();
    }
}
