package com.oc.eliott.mynews.Controller.Activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.oc.eliott.mynews.Controller.Fragments.SearchAndNotifFragment;
import com.oc.eliott.mynews.R;
import com.oc.eliott.mynews.Utils.ActivityType;

public class NotificationActivity extends AppCompatActivity {
    SearchAndNotifFragment searchAndNotifFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        this.configureToolbar();
        this.configureAndShowSearchNotifFragment();
    }

    public void onResume(){
        super.onResume();

        this.buildFragmentConsideringParentActivity();
    }

    // This method configure the toolbar and add a up button to go back to the MainActivity
    private void configureToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    // This method set the fragment for this activity
    private void configureAndShowSearchNotifFragment(){
        searchAndNotifFragment = (SearchAndNotifFragment) getSupportFragmentManager().findFragmentById(R.id.notification_activity_linear_layout);
        if(searchAndNotifFragment == null){
            searchAndNotifFragment = new SearchAndNotifFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.notification_activity_linear_layout, searchAndNotifFragment).commit();
        }
    }

    // Update the fragment view for NoticationActivity
    private void buildFragmentConsideringParentActivity(){
        ActivityType notificationActivity = ActivityType.NOTIFICATION;
        searchAndNotifFragment.updateViewConsideringParentActivity(notificationActivity);
        searchAndNotifFragment.addListenerOnCheckboxConsideringParentActivity(notificationActivity);
    }
}
