package com.oc.eliott.mynews.Controller.Activities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.oc.eliott.mynews.Controller.Fragments.RecyclerViewFragment;
import com.oc.eliott.mynews.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private Toolbar toolbar;            // Object Toolbar use to configure a new Toolbar (used in method configureToolbar())
    private DrawerLayout drawerLayout;  // Object DrawerLayout use to configure a new DrawerLayout (used in method configureDrawerLayout())

    private static String CHANNEL_ID = "HIGH_NOTIFICATION";
    private static int NOTIFICATION_ID = 001;

    private RecyclerViewFragment recyclerViewFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.configureToolbar();
        this.configureDrawerLayout();
        this.configureNavigationView();
        this.configureAndShowRecyclerViewFragment();
    }

    // This method make clickable the items of the Toolbar and on click start a new activity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.item_toolbar_search:
                Intent intentSearch = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intentSearch);
                return true;
            case R.id.item_toolbar_notifications:
                Intent intentNotification = new Intent(MainActivity.this, NotificationActivity.class);
                startActivity(intentNotification);
                return true;
            case R.id.item_toolbar_help:
                Toast.makeText(this, R.string.btn_help, Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item_toolbar_about:
                Toast.makeText(this, R.string.btn_about, Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // This method called the file that configure the toolbar menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    // This method configure the new toolbar
    private void configureToolbar(){
        this.toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    // This method closed the NavigationDrawer when user click on back button
    @Override
    public void onBackPressed() {
        if(this.drawerLayout.isDrawerOpen(GravityCompat.START)){
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    // This method make clickable the items of the NavigationDrawer and update the view
    // of the MainActivity with the good article of each category
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.drawer_top_stories:
                Toast.makeText(this, "Top Stories", Toast.LENGTH_SHORT).show();
                break;
            case R.id.drawer_most_popular:
                Toast.makeText(this, "Most Popular", Toast.LENGTH_SHORT).show();
                break;
            case R.id.drawer_business:
                Toast.makeText(this, "Business", Toast.LENGTH_SHORT).show();
                break;
            case R.id.drawer_it:
                Toast.makeText(this, "IT", Toast.LENGTH_SHORT).show();
                break;
            case R.id.drawer_sport:
                Toast.makeText(this, "Sport", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        this.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    // This method set a new icon on the toolbar which on click open the NavigationDrawer
    private void configureDrawerLayout(){
        this.drawerLayout = findViewById(R.id.activity_main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    // This method configure the NavigationView and add a listener on each item
    private void configureNavigationView(){
        NavigationView navigationView = findViewById(R.id.activity_main_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void configureAndShowRecyclerViewFragment(){
        recyclerViewFragment = (RecyclerViewFragment) getSupportFragmentManager().findFragmentById(R.id.activity_main_frame_layout);
        if (recyclerViewFragment == null) {
            recyclerViewFragment = new RecyclerViewFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_main_frame_layout, recyclerViewFragment)
                    .commit();
        }
    }
}
