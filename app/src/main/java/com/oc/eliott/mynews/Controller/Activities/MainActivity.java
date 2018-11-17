package com.oc.eliott.mynews.Controller.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.oc.eliott.mynews.R;
import com.oc.eliott.mynews.Utils.PageAdapter;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private Toolbar toolbar;            // Object Toolbar use to configure a new Toolbar (used in method configureToolbar())
    private DrawerLayout drawerLayout;  // Object DrawerLayout use to configure a new DrawerLayout (used in method configureDrawerLayout())

    private TabLayout tabs; // TabLayout is used to have tab for each category
    private ViewPager pager; // ViewPager is used to display the result of each call

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.configureToolbar();
        this.configureDrawerLayout();
        this.configureNavigationView();
        this.configureViewPager();
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
                selectPage(0);
                break;
            case R.id.drawer_most_popular:
                selectPage(1);
                break;
            case R.id.drawer_business:
                selectPage(2);
                break;
            case R.id.drawer_it:
                selectPage(3);
                break;
            case R.id.drawer_sport:
                selectPage(4);
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

    // Configure the PageAdapter and TabLayout to work together
    private void configureViewPager(){
        pager = findViewById(R.id.activity_main_viewpager);
        pager.setAdapter(new PageAdapter(getSupportFragmentManager()){});

        tabs = findViewById(R.id.activiy_main_tablayout);
        tabs.setupWithViewPager(pager);
    }

    // Change the current page display by the PageAdapter
    private void selectPage(int position){
        tabs.setScrollPosition(position, 0f, true);
        pager.setCurrentItem(position);
    }
}
