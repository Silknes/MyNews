package com.oc.eliott.mynews.Controller.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.oc.eliott.mynews.Model.Article;
import com.oc.eliott.mynews.Model.MostPopular.ResultMostPopular;
import com.oc.eliott.mynews.Model.Search.ResultSearch;
import com.oc.eliott.mynews.Model.TopStories.ResultTopStories;
import com.oc.eliott.mynews.R;
import com.oc.eliott.mynews.Utils.NYTCalls;
import com.oc.eliott.mynews.View.NYTAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecyclerViewFragment extends Fragment implements NYTCalls.CallbacksTopStories, NYTCalls.CallbacksMostPopular, NYTCalls.CallbacksSearch{

    private static final String KEY_POSITION = "KEY_POSITION";

    @BindView(R.id.fragment_main_recycler_view) RecyclerView recyclerView;
    private List<Article> articles;
    private NYTAdapter NYTAdapter;

    public RecyclerViewFragment() {}

    public static RecyclerViewFragment newInstance(int position){
        RecyclerViewFragment frag = new RecyclerViewFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_POSITION, position);
        frag.setArguments(args);
        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        ButterKnife.bind(this, view);

        int position = getArguments().getInt(KEY_POSITION, -1);

        this.configureRecyclerView();
        this.fetchArticles(position);

        return view;
    }

    // Configure RecyclerView
    private void configureRecyclerView(){
        this.articles = new ArrayList<Article>();
        this.NYTAdapter = new NYTAdapter(articles, Glide.with(this));
        this.recyclerView.setAdapter(this.NYTAdapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void fetchArticles(int position) {
        String news_desk;
        switch(position){
            case 0 :
                NYTCalls.fetchTopStoriesArticles(this, "7a0743e89dda4664b7925d78d94f9ea2");
                break;
            case 1 :
                NYTCalls.fetchMostPopularArticles(this, "all-sections", "1", "7a0743e89dda4664b7925d78d94f9ea2");
                break;
            case 2 :
                news_desk = "news_desk:(\"Business\")";
                NYTCalls.fetchSearchArticles(this, "7a0743e89dda4664b7925d78d94f9ea2", news_desk, "newest");
                break;
            case 3 :
                news_desk = "news_desk:(\"Food\")";
                NYTCalls.fetchSearchArticles(this, "7a0743e89dda4664b7925d78d94f9ea2", news_desk, "newest");
                break;
            case 4 :
                news_desk = "news_desk:(\"Sports\")";
                NYTCalls.fetchSearchArticles(this, "7a0743e89dda4664b7925d78d94f9ea2", news_desk, "newest");
                break;
            default :
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                break;
        }
    }

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

    private void updateUI(List<? extends Article> article){
        List<? extends Article> articleList = article;
        articles.addAll(articleList);
        NYTAdapter.notifyDataSetChanged();
    }

}
