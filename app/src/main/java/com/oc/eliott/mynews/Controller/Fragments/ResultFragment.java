package com.oc.eliott.mynews.Controller.Fragments;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.oc.eliott.mynews.Controller.Activities.WebViewActivity;
import com.oc.eliott.mynews.Model.Article;
import com.oc.eliott.mynews.R;
import com.oc.eliott.mynews.View.ItemClickSupport;
import com.oc.eliott.mynews.View.NYTAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class ResultFragment extends Fragment {
    @BindView(R.id.fragment_result_recycler_view)
    RecyclerView recyclerView;
    private List<Article> articleList; // Contain articles retrieved by the API
    private NYTAdapter nytAdapter;

    public ResultFragment() {}

    public static ResultFragment newInstance(){
        return new ResultFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result, container, false);

        this.configureRecyclerView();
        this.configureOnClickRecyclerView();

        return view;
    }

    // Setup RecyclerView
    public void configureRecyclerView(){
        this.articleList = new ArrayList<>();
        if(getContext() != null) this.nytAdapter = new NYTAdapter(articleList, Glide.with(getContext()));
        this.recyclerView.setAdapter(nytAdapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    // Handle click on RecyclerView's items
    private void configureOnClickRecyclerView(){
        ItemClickSupport.addTo(recyclerView, R.layout.fragment_recycler_view_item)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        String url = nytAdapter.getUrl(position);
                        Intent intent = new Intent(getContext(), WebViewActivity.class);
                        intent.putExtra("url", url);
                        startActivity(intent);
                    }
                });
    }
}
