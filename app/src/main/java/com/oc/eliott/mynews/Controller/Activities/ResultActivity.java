package com.oc.eliott.mynews.Controller.Activities;

import android.os.Bundle;

import com.oc.eliott.mynews.Controller.Fragments.ResultFragment;
import com.oc.eliott.mynews.R;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

/*
 Implémenter un fragment qui aura pour rôle de faire la requête et d'afficher le résultat
 Tout ce qui est en commentaire doit être mis dans le fragment
 Passer les arguments de la requête dans un objet "Request" plutôt que chaque valeur indépendament
 */

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

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
        ResultFragment resultFragment = (ResultFragment) getSupportFragmentManager().findFragmentById(R.id.notification_activity_linear_layout);
        if(resultFragment == null){
            resultFragment = ResultFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.linearLayout_fragment_container_result_activity, resultFragment)
                    .commit();
        }
    }
}
