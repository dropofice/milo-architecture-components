package com.mgeows.milo.ui.addeditpet;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mgeows.milo.R;
import com.mgeows.milo.util.ActivityUtils;

public class AddEditPetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit_pet_activity);

        setupToolbar();
        fireAddEditFragment();
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.mAddEditToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void fireAddEditFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        AddEditPetFragment fragment = AddEditPetFragment.newInstance();
        ActivityUtils.addFragmentToActivity(fragmentManager, fragment, R.id.mAddEditContainer);
    }
}
