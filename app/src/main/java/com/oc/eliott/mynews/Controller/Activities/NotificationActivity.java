package com.oc.eliott.mynews.Controller.Activities;

import android.os.Bundle;

import com.oc.eliott.mynews.Controller.Fragments.NotificationFragment;
import com.oc.eliott.mynews.R;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class NotificationActivity extends AppCompatActivity {
    NotificationFragment notificationFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        this.configureToolbar();
        this.configureAndShowDefaultFragment();
    }

    // Setup Toolbar
    private void configureToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if(ab != null) ab.setDisplayHomeAsUpEnabled(true);
    }

    // Display default fragment
    private void configureAndShowDefaultFragment(){
        notificationFragment = (NotificationFragment) getSupportFragmentManager().findFragmentById(R.id.notification_activity_linear_layout);
        if(notificationFragment == null){
            notificationFragment = new NotificationFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.notification_activity_linear_layout, notificationFragment).commit();
        }
    }
}
