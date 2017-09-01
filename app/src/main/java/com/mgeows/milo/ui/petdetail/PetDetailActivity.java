package com.mgeows.milo.ui.petdetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mgeows.milo.R;
import com.mgeows.milo.ui.addeditpet.AddEditPetActivity;

import butterknife.ButterKnife;

import static android.R.attr.name;

public class PetDetailActivity extends AppCompatActivity {

    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pet_detail_activity);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detailToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String name = getIntent().getStringExtra(Intent.EXTRA_TEXT);
        position = getIntent().getIntExtra("POSITION_KEY", 0);
        // showPetDetailFragment(name);

        PetDetailPagerAdapter adapter = new PetDetailPagerAdapter(getSupportFragmentManager());
        adapter.setName(name);
        ViewPager pager = (ViewPager) findViewById(R.id.detailPager);
        pager.setAdapter(adapter);
        pager.setCurrentItem(position);
    }

    public void fireAddEditActivity() {
        Intent intent = new Intent(this, AddEditPetActivity.class);
        intent.putExtra("POSITION_KEY", position);
        intent.putExtra(Intent.EXTRA_TEXT, name);
        startActivity(intent);
    }

    /**
     * Shows the pet detail fragment
     */
//    private void showPetDetailFragment(String name) {
//        PetDetailFragment fragment = PetDetailFragment.forPet(name);
//        getSupportFragmentManager()
//                .beginTransaction()
//                .add(detailPager, fragment, null)
//                .commit();
//    }

}
