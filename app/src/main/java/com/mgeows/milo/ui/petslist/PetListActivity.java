package com.mgeows.milo.ui.petslist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.mgeows.milo.R;
import com.mgeows.milo.ui.addeditpet.AddEditPetActivity;
import com.mgeows.milo.ui.petdetail.PetDetailActivity;
import com.mgeows.milo.ui.settings.SettingsActivity;
import com.mgeows.milo.util.ActivityUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PetListActivity extends AppCompatActivity implements PetListFragment.Listener{

    // Keys for PetDetailActivity
    private static final String POSITION_KEY_DETAIL = "position.detail";
    private static final String IDS_KEY_DETAIL = "ids.detail";
    private static final String BUNDLE_KEY_DETAIL = "bundle.detail";

    @BindView(R.id.list_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.list_nav_view)
    NavigationView mNavView;
    @BindView(R.id.list_drawer)
    DrawerLayout mDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pet_list_activity);
        ButterKnife.bind(this);
        setupToolbar();
        setupDrawer();
        if (null == savedInstanceState) initFragment();
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_TITLE);
        ab.setTitle(R.string.list_activity_title);
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
    }
    private void setupDrawer() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();
        mDrawer.setStatusBarBackground(R.color.colorPrimaryDark);
        if (mNavView != null) {
            setupDrawerContent(mNavView);
        }
    }

    private void initFragment() {
        // Add the PetListFragment to the layout
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = PetListFragment.newInstance();
        ActivityUtils.addFragmentToActivity(fragmentManager, fragment, R.id.list_container);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Open the navigation drawer when the home icon is selected from the toolbar.
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.empty_action:
                                startActivity(
                                        new Intent(PetListActivity.this, SettingsActivity.class));
                                break;
                            default:
                                break;
                        }
                        // Close the navigation drawer when an item is selected.
                        menuItem.setChecked(true);
                        mDrawer.closeDrawers();
                        return true;
                    }
                });
    }

    private void startAddEditPetActivity() {
        Intent intent = new Intent(this, AddEditPetActivity.class);
        startActivity(intent);
    }

    private void startPetDetailActivity(int position, ArrayList<String> ids) {
        Bundle bundle = new Bundle();
        bundle.putInt(POSITION_KEY_DETAIL, position);
        bundle.putStringArrayList(IDS_KEY_DETAIL, ids);
        Intent intent = new Intent(this, PetDetailActivity.class);
        intent.putExtra(BUNDLE_KEY_DETAIL, bundle);
        startActivity(intent);
    }

    @Override
    public void fireAddEditPetActivity() {
        startAddEditPetActivity();
    }

    @Override
    public void firePetDetailActivity(int position, ArrayList<String> ids) {
        startPetDetailActivity(position, ids);
    }
}
