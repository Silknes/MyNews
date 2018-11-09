package com.oc.eliott.mynews.Controller.Activities;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.oc.eliott.mynews.Model.Article;
import com.oc.eliott.mynews.Model.Search.ResultSearch;
import com.oc.eliott.mynews.R;
import com.oc.eliott.mynews.Utils.NYTCalls;
import com.oc.eliott.mynews.View.ItemClickSupport;
import com.oc.eliott.mynews.View.NYTAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResultSearchActivity extends AppCompatActivity implements NYTCalls.CallbacksSearch{

    // These values are the value wich contain the value pass by SearchAndNotifFragment
    private String queryTerm, newsDesk, longBeginDate, longEndDate;

    @BindView(R.id.activity_result_search_recycler_view)
    RecyclerView recyclerView; // Use to configure the RecyclerView
    private List<Article> articles; // This value contain the List of Article get from the API
    private NYTAdapter NYTAdapter; // Use to configure the NYTAdapter that plug it with the recyclerview

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_search);
        ButterKnife.bind(this);

        // Set default values for the 2 dates
        Calendar cal = Calendar.getInstance();
        long defaultEndDate = Long.parseLong("" + cal.get(Calendar.YEAR) + (cal.get(Calendar.MONTH) + 1) + cal.get(Calendar.DAY_OF_MONTH));

        // Get all the value pass from SearchAndNotifFragment
        longBeginDate = "" + getIntent().getLongExtra("BEGIN_DATE", 19820101);
        longEndDate = "" + getIntent().getLongExtra("END_DATE", defaultEndDate);
        queryTerm = getIntent().getStringExtra("QUERY_TERM");
        newsDesk = getIntent().getStringExtra("NEWS_DESK");

        // We configure the toolbar, the recyclerview, make the call with the good parameter and set the click for each item
        this.configureToolbar();
        this.configureRecyclerView();
        this.fetchArticles(longBeginDate, longEndDate, queryTerm, newsDesk);
        this.configureOnClickRecyclerView();
    }

    // This method configure the toolbar and add a up button to go back to the MainActivity
    private void configureToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    // Set onClick on RecyclerView item
    private void configureOnClickRecyclerView(){
        ItemClickSupport.addTo(recyclerView, R.layout.fragment_recycler_view_item)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        String url = NYTAdapter.getUrl(position);
                        Intent intent = new Intent(ResultSearchActivity.this, WebViewActivity.class);
                        intent.putExtra("url", url);
                        startActivity(intent);
                    }
                });
    }

    // Configure RecyclerView
    public void configureRecyclerView(){
        this.articles = new ArrayList<Article>();
        this.NYTAdapter = new NYTAdapter(articles, Glide.with(this));
        this.recyclerView.setAdapter(this.NYTAdapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void fetchArticles(String beginDate, String endDate, String queryTerm, String newsDesk) {
        NYTCalls.fetchSearchArticles(this, "7a0743e89dda4664b7925d78d94f9ea2", newsDesk, queryTerm, beginDate, endDate, "newest");
    }

    // Override onResponse and onFailure for SearchAPI and get the List of Articles contains in the result to update the view
    // Moreover if the list return is empty we close this Activity and display a Toast
    @Override
    public void onResponseSearch(@Nullable ResultSearch result) {
        if(result != null) {
            List<? extends Article> articles = result.getResponse().getDocs();
            if(articles.size() == 0){
                Toast.makeText(this, "No result found for your search", Toast.LENGTH_LONG).show();
                this.finish();
            }
            else this.updateUI(articles);
        }
    }

    @Override
    public void onFailureSearch() { }

    // Method that take a List and use it to update the view (NYTAdapter then RecyclerView) with the good information
    private void updateUI(List<? extends Article> article){
        List<? extends Article> articleList = article;
        articles.addAll(articleList);
        NYTAdapter.notifyDataSetChanged();
    }
}
