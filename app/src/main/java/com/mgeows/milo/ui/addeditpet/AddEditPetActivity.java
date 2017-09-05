package com.mgeows.milo.ui.addeditpet;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.mgeows.milo.R;
import com.mgeows.milo.ui.petslist.PetListActivity;
import com.mgeows.milo.util.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddEditPetActivity extends AppCompatActivity implements AddEditPetFragment.Listener {

    // Keys from PetDetailActivity
    private static final String BUNDLE_KEY_EDIT = "bundle.edit";
    private static final String ID_KEY_EDIT = "id.edit";

    @BindView(R.id.mAddEditRootView)
    CoordinatorLayout mAddEditRootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit_pet_activity);
        ButterKnife.bind(this);
        setupToolbar();

        String mId = checkIntent();
        fireAddEditFragment(mId);
    }

    private String checkIntent() {
        Intent intent = getIntent();
        if (intent.hasExtra(BUNDLE_KEY_EDIT)) {
            Bundle bundle = intent.getBundleExtra(BUNDLE_KEY_EDIT);
            return bundle.getString(ID_KEY_EDIT);
        }
        return null;
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

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.mAddEditToolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP);
        //        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void fireAddEditFragment(String id) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        AddEditPetFragment fragment = AddEditPetFragment.newInstance(id);
        ActivityUtils.addFragmentToActivity(fragmentManager, fragment, R.id.mAddEditContainer);
    }

    @Override
    public void onEntitySaved() {
        Snackbar.make(mAddEditRootView, "Pet Saved", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onEntityDeleted() {
        Snackbar.make(mAddEditRootView, "Pet Deleted", Snackbar.LENGTH_SHORT).show();
    }

    private void startPetListActivity() {
        Intent intent = new Intent(this, PetListActivity.class);
        startActivity(intent);
    }
}
