package com.mgeows.milo.ui.addeditpet;

import android.app.DatePickerDialog;
import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.mgeows.milo.BuildConfig;
import com.mgeows.milo.PetApplication;
import com.mgeows.milo.R;
import com.mgeows.milo.db.entity.Pet;
import com.mgeows.milo.libs.ImageLoader;
import com.mgeows.milo.vm.PetViewModel;
import com.mgeows.milo.vm.PetViewModelFactory;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Listener} interface
 * to handle interaction events.
 * Use the {@link AddEditPetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddEditPetFragment extends LifecycleFragment implements
                                                          DatePickerDialog.OnDateSetListener,
                                                          ImageChooserFragment.Listener {
    // Key for the petId for editing
    private static final String ID_KEY = "id.addedit";
    private static final String AUTHORITY = BuildConfig.APPLICATION_ID + ".fileprovider";
    // Spinner selection
    private static final int GENDER_MALE = 0;
    private static final int GENDER_FEMALE = 1;
    static final int REQUEST_TAKE_PHOTO = 1024;

    @BindView(R.id.edit_iv_photo)
    ImageView mIvPhoto;
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

    private ImageLoader imageLoader;
    private String mId;
    private String mImagePath;
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

    // use this to keep a reference to the file you create so
    // that it can be access from onActivityResult()
    private File mImageFile;


    private ImageChooserFragment imageChooserFragment;

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
        setupInjection();
        setHasOptionsMenu(true);
    }

    private void setupInjection() {
        PetApplication application = (PetApplication) getActivity().getApplication();
        imageLoader= application.getUiComponent(this, null).getImageLoader();
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
        imageLoader.load(mIvPhoto, pet.imagePath);
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
                                                android.R.layout.simple_spinner_dropdown_item);
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
        super.onCreateOptionsMenu(menu, inflater);
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
                              mContactNo, mImagePath);
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
                              mContactNo, mImagePath);
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
    public void onDatePickerClick() {
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

    @OnClick(R.id.et_birth_date)
    public void onEtBirthDateClick(View view) {
        onDatePickerClick();
        hideSoftKeyboard(view);
    }

    private void hideSoftKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @OnClick(R.id.edit_iv_photo)
    public void onImgVuClick() {
        imageChooserFragment = new ImageChooserFragment();
        imageChooserFragment.setListener(this);
        imageChooserFragment.show(getFragmentManager(), "image_chooser");
    }

    @Override
    public void onTakePhoto() {
        imageChooserFragment.dismiss();
        startCameraIntent();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            mImagePath = mImageFile.getAbsolutePath();
            Glide.with(this).asBitmap().load(mImagePath).into(mIvPhoto);
        }
    }

    private void startCameraIntent() {
        Intent photoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (photoIntent.resolveActivity(getContext().getPackageManager()) != null) {

            try {
                mImageFile = createImageFile();
            } catch (IOException e) {
                // file wasn't created
            }
            if (mImageFile != null) {
                Uri imageUri = FileProvider.getUriForFile(getContext(), AUTHORITY, mImageFile);
                photoIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                // This is important. Without it, you may get Security Exceptions.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    photoIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                }
                else
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        ClipData clip = ClipData.newUri(getContext().getContentResolver(), "A photo", imageUri);
                        photoIntent.setClipData(clip);
                        photoIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    }
                    else {
                        List<ResolveInfo> resInfoList =
                                getContext().getPackageManager().queryIntentActivities(photoIntent,
                                                                          PackageManager.MATCH_DEFAULT_ONLY);
                        for (ResolveInfo resolveInfo : resInfoList) {
                            String packageName = resolveInfo.activityInfo.packageName;
                            getContext().grantUriPermission(packageName, imageUri,
                                               Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                        }
                    }
            }
            startActivityForResult(photoIntent, REQUEST_TAKE_PHOTO);
        }
        else {
            // device doesn't have camera
            Snackbar.make(mEtName, "No camera", Snackbar.LENGTH_SHORT).show();
        }
    }

    @NonNull
    private File createImageFile() throws IOException {
        String imageFileName =
                "pet_image" + System.currentTimeMillis() + "_"; // give it a unique filename
        File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        // use this if you want android to automatically save it into the device's image gallery:
        // File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(imageFileName, ".jpg", storageDir);
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
