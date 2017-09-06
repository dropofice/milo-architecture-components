package com.mgeows.milo.ui.addeditpet;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.mgeows.milo.R;
import com.mgeows.milo.ui.petslist.PetListActivity;
import com.mgeows.milo.util.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddEditPetActivity extends AppCompatActivity implements AddEditPetFragment.Listener {

    // Keys from PetDetailActivity to get petId for editing
    private static final String BUNDLE_KEY_EDIT = "bundle.edit";
    private static final String ID_KEY_EDIT = "id.edit";

    private String mId;
    private ActionBar mActionBar;

    @BindView(R.id.mAddEditRootView)
    CoordinatorLayout mAddEditRootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit_pet_activity);
        ButterKnife.bind(this);
        setupToolbar();
        invalidateOptionsMenu();
        mId = checkIntent();
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
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        // If this is a new pet, hide the update menu
        if (mId == null || mId.isEmpty()) {
            mActionBar.setTitle(R.string.addedit_activity_title_add);
            MenuItem menuItem = menu.findItem(R.id.action_update);
            menuItem.setVisible(false);
        }
        else {
            mActionBar.setTitle(R.string.addedit_activity_title_edit);
            MenuItem menuItem = menu.findItem(R.id.action_save);
            menuItem.setVisible(false);
        }
        return true;

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
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_TITLE);

    }

    private void fireAddEditFragment(String id) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        AddEditPetFragment fragment = AddEditPetFragment.newInstance(id);
        ActivityUtils.addFragmentToActivity(fragmentManager, fragment, R.id.mAddEditContainer);
    }

    @Override
    public void onPetSaved() {
        Snackbar.make(mAddEditRootView, "Saved", Snackbar.LENGTH_SHORT).show();
        startPetListActivity();
    }

    @Override
    public void onPetUpdated() {
        Snackbar.make(mAddEditRootView, "Updated", Snackbar.LENGTH_SHORT).show();
        startPetListActivity();
    }

    private void startPetListActivity() {
        Intent intent = new Intent(this, PetListActivity.class);
        startActivity(intent);
    }
}
