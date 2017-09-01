package com.mgeows.milo.ui.addeditpet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.mgeows.milo.R;
import com.mgeows.milo.util.ActivityUtils;

public class AddEditPetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit_pet_activity);

        Intent intent = getIntent();

        int position = intent.getIntExtra("POSITION KEY", 0);

        setupToolbar();
        fireAddEditFragment(position);
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
        ActionBar ab =  getSupportActionBar();
        ab.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP);
//        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void fireAddEditFragment(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        AddEditPetFragment fragment = AddEditPetFragment.newInstance(position);
        ActivityUtils.addFragmentToActivity(fragmentManager, fragment, R.id.mAddEditContainer);
    }
}
