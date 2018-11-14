package com.oc.eliott.mynews.Controller.Activities;

import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.oc.eliott.mynews.Controller.Fragments.NotificationFragment;
import com.oc.eliott.mynews.R;
import com.oc.eliott.mynews.Utils.ActivityType;

public class NotificationActivity extends AppCompatActivity {
    NotificationFragment notificationFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        this.configureToolbar();
        this.configureAndShowSearchNotifFragment();
    }

    // This method configure the toolbar and add a up button to go back to the MainActivity
    private void configureToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if(ab != null) ab.setDisplayHomeAsUpEnabled(true);
    }

    // This method set the fragment for this activity
    private void configureAndShowSearchNotifFragment(){
        notificationFragment = (NotificationFragment) getSupportFragmentManager().findFragmentById(R.id.notification_activity_linear_layout);
        if(notificationFragment == null){
            notificationFragment = new NotificationFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.notification_activity_linear_layout, notificationFragment).commit();
        }
    }
}
