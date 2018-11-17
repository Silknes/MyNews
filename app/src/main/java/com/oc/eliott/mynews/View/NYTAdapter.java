package com.oc.eliott.mynews.View;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.oc.eliott.mynews.Model.Article;
import com.oc.eliott.mynews.R;

import java.util.List;

public class NYTAdapter extends RecyclerView.Adapter<NYTViewHolder>{
    private List<Article> articleList;
    private RequestManager glide;

    public NYTAdapter(List<Article> articleList, RequestManager glide){
        this.articleList = articleList;

        this.glide = glide;
    }

    @NonNull
    @Override
    public NYTViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.fragment_recycler_view_item, parent, false);

        return new NYTViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NYTViewHolder viewHolder, int position) {
        viewHolder.updateWithArticles(this.articleList.get(position), this.glide);
    }

    @Override
    public int getItemCount() {
        if(this.articleList.size() >= 0 && this.articleList.size() < 10) return this.articleList.size();
        else return 10;
    }

    public String getUrl(int position){
        return this.articleList.get(position).getArticleURL();
    }
}
