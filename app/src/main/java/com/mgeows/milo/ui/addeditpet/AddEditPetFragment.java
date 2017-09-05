package com.mgeows.milo.ui.addeditpet;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
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

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddEditPetFragment.Listener} interface
 * to handle interaction events.
 * Use the {@link AddEditPetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddEditPetFragment extends LifecycleFragment {
    TextView textView;

    private static final String ID_KEY = "id.addedit";

    private String mId;
    private Pet mPet;
    private PetViewModel mViewModel;
    private Listener mListener;

    public AddEditPetFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AddEditPetFragment.
     */
    public static AddEditPetFragment newInstance(String id) {
        AddEditPetFragment fragment = new AddEditPetFragment();
        if (!TextUtils.isEmpty(id)) {
            Bundle args = new Bundle();
            args.putString(ID_KEY, id);
            fragment.setArguments(args);
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_edit_pet_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        textView = (TextView) getView().findViewById(R.id.tvAddEdit);
        mViewModel = getViewModel();
        checkArguments();
    }

    private PetViewModel getViewModel() {
        PetApplication application = (PetApplication) getActivity().getApplication();
        PetViewModelFactory factory = new PetViewModelFactory(application);
        return ViewModelProviders.of(this, factory).get(PetViewModel.class);

    }

    // If there is a petId populate UI for editing
    private void checkArguments() {
        if (getArguments() != null) {
            mId = getArguments().getString(ID_KEY);
            populateUi(mId);
        }
    }

    private void populateUi(String mId) {
        mViewModel.getPet(mId).observe(this, new Observer<Pet>() {
            @Override
            public void onChanged(@Nullable Pet pet) {
                textView.setText(checkNotNull(pet).petId);
            }
        });

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.pet_addedit_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                savePet();
                break;
            case R.id.action_update:
                updatePet();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void savePet() {
        mViewModel.insertPet(mPet);
        Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
        mListener.onEntitySaved();
    }

    private void updatePet() {
        mViewModel.updatePet(mPet);
        Toast.makeText(getContext(), "Updated", Toast.LENGTH_SHORT).show();
        mListener.onEntitySaved();
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
        super.onDetach();
         mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    interface Listener {
        void onEntitySaved();
    }
}
