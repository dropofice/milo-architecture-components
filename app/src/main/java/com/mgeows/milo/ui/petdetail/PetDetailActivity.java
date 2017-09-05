package com.mgeows.milo.ui.petdetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mgeows.milo.R;
import com.mgeows.milo.ui.addeditpet.AddEditPetActivity;

import java.util.ArrayList;

public class PetDetailActivity extends AppCompatActivity implements PetDetailFragment.Listener{

    // Keys from PetListActivity
    private static final String POSITION_KEY_DETAIL = "position.detail";
    private static final String IDS_KEY_DETAIL = "ids.detail";
    private static final String BUNDLE_KEY_DETAIL = "bundle.detail";

    // Keys for AddEditActivity
    private static final String BUNDLE_KEY_EDIT = "bundle.edit";
    private static final String ID_KEY_EDIT = "id.edit";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pet_detail_activity);

        setupToolbar();
        setupViewPager();
    }

    private void setupViewPager() {

        Bundle bundle = getIntent().getBundleExtra(BUNDLE_KEY_DETAIL);
        int position = bundle.getInt(POSITION_KEY_DETAIL);
        ArrayList<String> ids = bundle.getStringArrayList(IDS_KEY_DETAIL);

        PetDetailPagerAdapter adapter = new PetDetailPagerAdapter(getSupportFragmentManager(), ids);
        ViewPager pager = (ViewPager) findViewById(R.id.detailPager);
        pager.setAdapter(adapter);
        pager.setCurrentItem(position);
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.detailToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
