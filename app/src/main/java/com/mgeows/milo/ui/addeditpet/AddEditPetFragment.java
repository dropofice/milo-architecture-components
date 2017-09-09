package com.mgeows.milo.ui.addeditpet;

import android.app.DatePickerDialog;
import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.mgeows.milo.PetApplication;
import com.mgeows.milo.R;
import com.mgeows.milo.db.entity.Pet;
import com.mgeows.milo.vm.PetViewModel;
import com.mgeows.milo.vm.PetViewModelFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Listener} interface
 * to handle interaction events.
 * Use the {@link AddEditPetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddEditPetFragment extends LifecycleFragment implements DatePickerDialog.OnDateSetListener {

    // Key for the petId for editing
    private static final String ID_KEY = "id.addedit";
    // Spinner selection
    private static final int GENDER_MALE = 0;
    private static final int GENDER_FEMALE = 1;

    @BindView(R.id.et_name)
    EditText mEtName;
    @BindView(R.id.et_breed)
    EditText mEtBreed;
    @BindView(R.id.spn_gender)
    Spinner mSpinner;
    @BindView(R.id.et_birth_date)
    EditText mEtBirthDate;
    @BindView(R.id.img_date_picker)
    ImageView imgDatePicker;
    @BindView(R.id.et_weight)
    EditText mEtWeight;
    @BindView(R.id.et_owner)
    EditText mEtOwner;
    @BindView(R.id.et_address)
    EditText mEtAddress;
    @BindView(R.id.et_contact_no)
    EditText mEtContactNo;

    Unbinder unbinder;

    private String mId;
    private String mName;
    private String mBreed;
    private int mGender;
    private Date mBirthDate;
    private String mWeight;
    private String mOwner;
    private String mAddress;
    private String mContactNo;
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
        setupGenderSpinner();
        setupFieldsListener();
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
                    mBirthDate = pet.birthDate;
                    setUi(pet);
                }
            }
        });
    }

    private void setUi(@NonNull Pet pet) {
        mEtName.setText(pet.name);
        mEtBreed.setText(pet.breed);
        mSpinner.setSelection(pet.gender);
        mEtWeight.setText(pet.weight);
        mEtOwner.setText(pet.owner);
        mEtAddress.setText(pet.address);
        mEtContactNo.setText(pet.contactNo);
        setEtBirthDateUi(pet, null);
    }

    private void setEtBirthDateUi(Pet pet, Date selectedDate) {
        Date nonFormattedDate = null;
        if (pet != null) {
            nonFormattedDate = pet.birthDate;
        }
        else {
            nonFormattedDate = selectedDate;
        }
        SimpleDateFormat dateFormat =
                new SimpleDateFormat("MMM-dd-yyyy", Locale.getDefault());
        String formattedDate = dateFormat.format(nonFormattedDate);
        mEtBirthDate.setText(formattedDate);
    }

    private void setupGenderSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter spinnerAdapter =
                ArrayAdapter.createFromResource(getContext(), R.array.array_gender_options,
                                                android.R.layout.simple_spinner_item);
        // Specify dropdown layout style - simple list view with 1 item per line
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        // Apply the adapter to the spinner
        mSpinner.setAdapter(spinnerAdapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        mGender = GENDER_MALE;
                        break;
                    case 1:
                        mGender = GENDER_FEMALE;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mGender = GENDER_MALE;
            }
        });

    }

    private void setupFieldsListener() {

    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        // If this is a new pet, hide the update menu
        if (mId == null || mId.isEmpty()) {
            MenuItem menuItem = menu.findItem(R.id.action_update);
            menuItem.setVisible(false);
        }
        else {
            MenuItem menuItem = menu.findItem(R.id.action_save);
            menuItem.setVisible(false);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.pet_addedit_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                savePet();
                return true;
            case R.id.action_update:
                updatePet();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void savePet() {
        mName = mEtName.getEditableText().toString().trim();
        if (!TextUtils.isEmpty(mName) && mBirthDate != null) {
            mBreed = mEtBreed.getEditableText().toString().trim();
            mWeight = mEtWeight.getEditableText().toString().trim();
            mOwner = mEtOwner.getEditableText().toString().trim();
            mAddress = mEtAddress.getEditableText().toString().trim();
            mContactNo = mEtContactNo.getEditableText().toString().trim();
            Pet pet = new Pet(mName, mBreed, mGender, mBirthDate, mWeight, mOwner, mAddress,
                              mContactNo);
            mViewModel.addPet(pet);
            mListener.onPetSaved();
        }
        else {
            showEmptyNameMsg();
        }
    }

    private void updatePet() {
        mName = mEtName.getEditableText().toString().trim();
        if (!TextUtils.isEmpty(mName) && mBirthDate != null) {
            mBreed = mEtBreed.getEditableText().toString().trim();
            mWeight = mEtWeight.getEditableText().toString().trim();
            mOwner = mEtOwner.getEditableText().toString().trim();
            mAddress = mEtAddress.getEditableText().toString().trim();
            mContactNo = mEtContactNo.getEditableText().toString().trim();
            Pet pet = new Pet(mId, mName, mBreed, mGender, mBirthDate, mWeight, mOwner, mAddress,
                              mContactNo);
            mViewModel.updatePet(pet);
            mListener.onPetUpdated();
        }
        else {
            showEmptyNameMsg();
        }
    }

    private void showEmptyNameMsg() {
        Snackbar.make(mEtName, R.string.empty_required_field, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
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

    @OnClick(R.id.img_date_picker)
    public void onViewClicked() {
        // Use the current date as the default date in the picker
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePicker = new DatePickerDialog(getContext(), this, year, month, day);
        datePicker.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        // Do something with the date chosen by the user
        final Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        mBirthDate = calendar.getTime();
        setEtBirthDateUi(null, mBirthDate);

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
