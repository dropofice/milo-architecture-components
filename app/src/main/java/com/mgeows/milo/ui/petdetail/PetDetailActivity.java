package com.mgeows.milo.ui.petdetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mgeows.milo.R;

public class PetDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pet_detail_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detailToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String name = getIntent().getStringExtra(Intent.EXTRA_TEXT);

         show(name);
    }

    /** Shows the pet detail fragment */
    private void show(String name) {

        PetDetailFragment fragment = PetDetailFragment.forProduct(name);

        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("")
                .add(R.id.detailFragmentContainer, fragment, null)
                .commit();
    }

}
