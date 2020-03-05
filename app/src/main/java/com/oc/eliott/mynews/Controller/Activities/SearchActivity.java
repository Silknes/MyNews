package com.oc.eliott.mynews.Controller.Activities;

import android.os.Bundle;

import com.oc.eliott.mynews.Controller.Fragments.SearchFragment;
import com.oc.eliott.mynews.R;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class SearchActivity extends AppCompatActivity {
    SearchFragment searchFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        this.configureToolbar();
        this.configureAndShowDefaultFragment();
    }

    // Setup Toolbar
    private void configureToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    // Display default fragment
    private void configureAndShowDefaultFragment(){
        searchFragment = (SearchFragment) getSupportFragmentManager().findFragmentById(R.id.search_activity_linear_layout);
        if(searchFragment == null){
            searchFragment = new SearchFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.search_activity_linear_layout, searchFragment).commit();
        }
    }
}
