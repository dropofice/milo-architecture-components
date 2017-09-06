package com.mgeows.milo.ui.petdetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mgeows.milo.R;
import com.mgeows.milo.ui.addeditpet.AddEditPetActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PetDetailActivity extends AppCompatActivity implements PetDetailFragment.Listener {

    // Keys from PetListActivity to setup viewpager and adapter
    private static final String POSITION_KEY_DETAIL = "position.detail";
    private static final String IDS_KEY_DETAIL = "ids.detail";
    private static final String BUNDLE_KEY_DETAIL = "bundle.detail";

    // Keys for AddEditActivity
    private static final String BUNDLE_KEY_EDIT = "bundle.edit";
    private static final String ID_KEY_EDIT = "id.edit";

    @BindView(R.id.detail_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.detail_pager)
    ViewPager mPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pet_detail_activity);
        ButterKnife.bind(this);
        setupToolbar();
        setupViewPager();
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void setupViewPager() {
        Bundle bundle = getIntent().getBundleExtra(BUNDLE_KEY_DETAIL);
        int position = bundle.getInt(POSITION_KEY_DETAIL);
        ArrayList<String> ids = bundle.getStringArrayList(IDS_KEY_DETAIL);

        PetDetailPagerAdapter adapter = new PetDetailPagerAdapter(getSupportFragmentManager(), ids);
        mPager.setAdapter(adapter);
        mPager.setCurrentItem(position);
    }

    private void startAddEditActivity(String id) {
        Bundle bundle = new Bundle();
        bundle.putString(ID_KEY_EDIT, id);
        Intent intent = new Intent(this, AddEditPetActivity.class);
        intent.putExtra(BUNDLE_KEY_EDIT, bundle);
        startActivity(intent);
    }

    @Override
    public void fireAddEditActivity(String id) {
        startAddEditActivity(id);
    }

    @Override
    public void finishPetDetailActivity() {
        finish();
    }
}
