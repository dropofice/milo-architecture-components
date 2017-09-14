package com.mgeows.milo.ui.petdetail;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mgeows.milo.PetApplication;
import com.mgeows.milo.R;
import com.mgeows.milo.db.entity.Pet;
import com.mgeows.milo.di.UiComponent;
import com.mgeows.milo.libs.ImageLoader;
import com.mgeows.milo.vm.PetViewModel;
import com.mgeows.milo.vm.PetViewModelFactory;

import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by JC on 08/29/2017.
 */

public class PetDetailFragment extends LifecycleFragment {

    private static final String ID_KEY = "id.key.adapter";

    @BindView(R.id.detail_image)
    ImageView mImage;
    @BindView(R.id.tv_detail_name)
    TextView mName;
    @BindView(R.id.tv_detail_breed)
    TextView mBreed;
    @BindView(R.id.tv_detail_gender)
    TextView mGender;
    @BindView(R.id.tv_detail_birth_date)
    TextView mBirthDate;
    @BindView(R.id.tv_detail_weight)
    TextView mWeight;
    @BindView(R.id.tv_detail_owner)
    TextView mOwner;
    @BindView(R.id.tv_detail_address)
    TextView mAddress;
    @BindView(R.id.tv_detail_contact_no)
    TextView mContactNo;

    Unbinder unbinder;

    private ImageLoader imageLoader;
    private SharedPreferences sharedPreference;
    private String mId;
    private String mUnit;
    private Listener mListener;
    private PetViewModel mViewModel;

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
        setupInjection();
        setHasOptionsMenu(true);
    }

    private void setupInjection() {
        PetApplication application = (PetApplication) getActivity().getApplication();
        UiComponent uiComponent = application.getUiComponent(this, null);
        imageLoader = uiComponent.getImageLoader();
        sharedPreference = uiComponent.getSharedPreferences();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.pet_detail_fragment, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupUnit();
        mId = getArguments().getString(ID_KEY);
        mViewModel = getViewModel();
        subscribeUi(mViewModel, mId);
    }

    private void setupUnit() {
        mUnit = sharedPreference.getString(
                getString(R.string.units_key), getString(R.string.unit_kg));
    }

    private PetViewModel getViewModel() {
        PetApplication application = (PetApplication) getActivity().getApplication();
        PetViewModelFactory factory = new PetViewModelFactory(application);
        return ViewModelProviders.of(getActivity(), factory).get(PetViewModel.class);
    }

    private void subscribeUi(PetViewModel viewModel, String id) {
        viewModel.getPet(id).observe(this, new Observer<Pet>() {
            @Override
            public void onChanged(@Nullable Pet pet) {
                setUi(pet);
            }
        });
    }

    private void setUi(Pet pet) {
        if (pet != null) {
            setPetDetailImage(pet);
            mName.setText(pet.name);
            mBreed.setText(pet.breed);
            mGender.setText(setGender(pet.gender));
            SimpleDateFormat dateFormat =
                    new SimpleDateFormat("MMM-dd-yyyy", Locale.getDefault());
            String date = dateFormat.format(pet.birthDate);
            mBirthDate.setText(date);
            mWeight.setText(String.format("%s%s", pet.weight, mUnit));
            mOwner.setText(pet.owner);
            mAddress.setText(pet.address);
            mContactNo.setText(pet.contactNo);
        }
    }

    private void setPetDetailImage(Pet pet) {
        int orientation = getContext().getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            imageLoader.load(mImage, pet.imagePath);
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            imageLoader.loadCircleCrop(mImage, pet.imagePath);
        }
    }

    private String setGender(int gender) {
        if (gender == 0) return getString(R.string.gender_male);
        if (gender == 1) return getString(R.string.gender_female);
        return "Unknown Gender";
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
        mListener.fireAddEditActivity(mId);
    }

    private void deletePet(String id) {
        mViewModel.deletePetById(id);
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

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    interface Listener {
        void fireAddEditActivity(String id);
        void finishPetDetailActivity();
    }
}
