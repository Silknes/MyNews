package com.oc.eliott.mynews.Controller.Activities;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.oc.eliott.mynews.R;
import com.oc.eliott.mynews.Utils.PageAdapter;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private TabLayout tabs;
    private ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.configureToolbar();
        this.configureDrawerLayout();
        this.configureNavigationView();
        this.configureViewPager();
    }

    // Handle click on Toolbar's items
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

    // Add the menu to the toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    // Setup Toolbar
    private void configureToolbar(){
        this.toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    // Manage NavigationDrawer behavior
    @Override
    public void onBackPressed() {
        if(this.drawerLayout.isDrawerOpen(GravityCompat.START)){
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    // Manage click on NavigationDrawer's items
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

    // Setup NavigationDrawer
    private void configureDrawerLayout(){
        this.drawerLayout = findViewById(R.id.activity_main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    // Setup NavigationView (Used with NavigationDrawer)
    private void configureNavigationView(){
        NavigationView navigationView = findViewById(R.id.activity_main_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    // Setup ViewPager & TabLayout
    private void configureViewPager(){
        pager = findViewById(R.id.activity_main_viewpager);
        pager.setAdapter(new PageAdapter(getSupportFragmentManager()){});

        tabs = findViewById(R.id.activity_main_tab_layout);
        tabs.setupWithViewPager(pager);
    }

    // Change the current page display by the PageAdapter
    private void selectPage(int position){
        tabs.setScrollPosition(position, 0f, true);
        pager.setCurrentItem(position);
    }
}
