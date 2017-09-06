package com.mgeows.milo.ui.addeditpet;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Listener} interface
 * to handle interaction events.
 * Use the {@link AddEditPetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddEditPetFragment extends LifecycleFragment {

    // Key for the petId for editing
    private static final String ID_KEY = "id.addedit";

    @BindView(R.id.et_name)
    TextInputEditText mEtName;
    @BindView(R.id.et_breed)
    TextInputEditText mEtBreed;
    Unbinder unbinder;

    private String mId;
    private String mName;
    private String mBreed;
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
        View view = inflater.inflate(R.layout.add_edit_pet_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
                if (pet != null) {
                    mEtName.setText(pet.petName);
                    mEtBreed.setText(pet.petBreed);
                }
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
        mName = mEtName.getEditableText().toString().trim();
        mBreed = mEtBreed.getEditableText().toString().trim();
        mPet = new Pet(mName, mBreed);
        mViewModel.insertPet(mPet);
        mListener.onPetSaved();
    }

    private void updatePet() {
        mName = mEtName.getEditableText().toString().trim();
        mBreed = mEtBreed.getEditableText().toString().trim();
        Pet pet = new Pet(mId, mName, mBreed);
        mViewModel.updatePet(pet);
        mListener.onPetUpdated();
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

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
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
        void onPetSaved();
        void onPetUpdated();
    }
}
