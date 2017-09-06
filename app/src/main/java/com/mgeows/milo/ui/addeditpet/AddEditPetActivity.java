package com.mgeows.milo.ui.addeditpet;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.mgeows.milo.R;
import com.mgeows.milo.ui.petslist.PetListActivity;
import com.mgeows.milo.util.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddEditPetActivity extends AppCompatActivity implements AddEditPetFragment.Listener {

    // Keys from PetDetailActivity to get petId for editing
    private static final String BUNDLE_KEY_EDIT = "bundle.edit";
    private static final String ID_KEY_EDIT = "id.edit";

    @BindView(R.id.addedit_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.addedit_container)
    FrameLayout mContainer;
    @BindView(R.id.addedit_root_view)
    CoordinatorLayout mRootView;

    private String mId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit_pet_activity);
        ButterKnife.bind(this);
        mId = checkIntent();
        setupToolbar();
        if (null == savedInstanceState) {
            invalidateOptionsMenu();
            fireAddEditFragment(mId);
        }
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_TITLE);
        if (mId == null || mId.isEmpty()) {
            mActionBar.setTitle(R.string.addedit_activity_title_add);
        }
        else {
            mActionBar.setTitle(R.string.addedit_activity_title_edit);
        }
    }

    private String checkIntent() {
        Intent intent = getIntent();
        if (intent.hasExtra(BUNDLE_KEY_EDIT)) {
            Bundle bundle = intent.getBundleExtra(BUNDLE_KEY_EDIT);
            return bundle.getString(ID_KEY_EDIT);
        }
        return null;
    }

    private void fireAddEditFragment(String id) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        AddEditPetFragment fragment = AddEditPetFragment.newInstance(id);
        ActivityUtils.addFragmentToActivity(fragmentManager, fragment, R.id.addedit_container);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPetSaved() {
        startPetListActivity();
    }

    @Override
    public void onPetUpdated() {
        startPetListActivity();
    }

    private void startPetListActivity() {
        Intent intent = new Intent(this, PetListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                                | Intent.FLAG_ACTIVITY_CLEAR_TASK
                                | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
