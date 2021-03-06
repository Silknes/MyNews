package com.oc.eliott.mynews.Controller.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.oc.eliott.mynews.Controller.Activities.WebViewActivity;
import com.oc.eliott.mynews.Model.Article;
import com.oc.eliott.mynews.Model.MostPopular.ResultMostPopular;
import com.oc.eliott.mynews.Model.Search.ResultSearch;
import com.oc.eliott.mynews.Model.TopStories.ResultTopStories;
import com.oc.eliott.mynews.R;
import com.oc.eliott.mynews.Utils.NYTCalls;
import com.oc.eliott.mynews.View.ItemClickSupport;
import com.oc.eliott.mynews.View.NYTAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewFragment extends Fragment implements NYTCalls.CallbacksTopStories, NYTCalls.CallbacksMostPopular, NYTCalls.CallbacksSearch{

    private static final String KEY_POSITION = "KEY_POSITION"; // Use to save the position

    @BindView(R.id.fragment_main_recycler_view)
    RecyclerView recyclerView;
    private List<Article> articles; // Receive the object return by the API
    private NYTAdapter NYTAdapter;

    public RecyclerViewFragment() {}

    // Instanciate a new RecyclerViewFragment with the position
    public static RecyclerViewFragment newInstance(int position){
        RecyclerViewFragment frag = new RecyclerViewFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_POSITION, position);
        frag.setArguments(args);
        return frag;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        ButterKnife.bind(this, view);

        // Get the position
        int position = getArguments().getInt(KEY_POSITION, -1);

        // Configure the recyclerView, call the good API according to the position, and setup a click on each item
        this.configureRecyclerView();
        this.fetchArticles(position);
        this.configureOnClickRecyclerView();

        return view;
    }

    // Set onClick on RecyclerView item
    private void configureOnClickRecyclerView(){
        ItemClickSupport.addTo(recyclerView, R.layout.fragment_recycler_view_item)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        String url = NYTAdapter.getUrl(position);
                        Intent intent = new Intent(getActivity(), WebViewActivity.class);
                        intent.putExtra("url", url);
                        startActivity(intent);
                    }
                });
    }

    // Configure RecyclerView
    public void configureRecyclerView(){
        this.articles = new ArrayList<>();
        if(getContext() != null) this.NYTAdapter = new NYTAdapter(articles, Glide.with(getContext()));
        this.recyclerView.setAdapter(this.NYTAdapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    // Call the good API according to the page of the TabLayout
    public void fetchArticles(int position) {
        String news_desk;
        String queryTerm = null;
        String beginDate = "19820101";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String endDate = simpleDateFormat.format(new Date());
        switch(position){
            case 0 :
                NYTCalls.fetchTopStoriesArticles(this, "7a0743e89dda4664b7925d78d94f9ea2");
                break;
            case 1 :
                NYTCalls.fetchMostPopularArticles(this, "all-sections", "1", "7a0743e89dda4664b7925d78d94f9ea2");
                break;
            case 2 :
                news_desk = "news_desk:(\"Business\")";
                NYTCalls.fetchSearchArticles(this, "7a0743e89dda4664b7925d78d94f9ea2", news_desk, queryTerm, beginDate, endDate, "newest");
                break;
            case 3 :
                news_desk = "news_desk:(\"Food\")";
                NYTCalls.fetchSearchArticles(this, "7a0743e89dda4664b7925d78d94f9ea2", news_desk, queryTerm, beginDate, endDate, "newest");
                break;
            case 4 :
                news_desk = "news_desk:(\"Sports\")";
                NYTCalls.fetchSearchArticles(this, "7a0743e89dda4664b7925d78d94f9ea2", news_desk, queryTerm, beginDate, endDate, "newest");
                break;
            default :
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                break;
        }
    }


    // Override onResponse and onFailure for each API and get the List of Articles contains in the result to update the view
    @Override
    public void onResponseMostPopular(@Nullable ResultMostPopular result) {
        if(result != null) {
            List<? extends Article> articles = result.getResults();
            this.updateUI(articles);
        }
    }

    @Override
    public void onFailureMostPopular() { }

    @Override
    public void onResponseTopStories(@Nullable ResultTopStories result) {
        if (result != null) {
            List<? extends Article> articles = result.getResults();
            this.updateUI(articles);
        }
    }

    @Override
    public void onFailureTopStories() { }

    @Override
    public void onResponseSearch(@Nullable ResultSearch result) {
        if(result != null) {
            List<? extends Article> articles = result.getResponse().getDocs();
            this.updateUI(articles);
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
