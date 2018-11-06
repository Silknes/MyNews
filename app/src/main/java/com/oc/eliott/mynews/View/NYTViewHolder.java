package com.oc.eliott.mynews.View;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.oc.eliott.mynews.Model.Article;
import com.oc.eliott.mynews.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class NYTViewHolder extends RecyclerView.ViewHolder{
    private TextView section, title, date;
    private ImageView icnArticle;

    public NYTViewHolder(View itemView){
        super(itemView);
        section = itemView.findViewById(R.id.fragment_main_item_section_subsection);
        title = itemView.findViewById(R.id.fragment_main_item_title);
        date = itemView.findViewById(R.id.fragment_main_item_date);
        icnArticle = itemView.findViewById(R.id.fragment_main_item_image_article);
    }

    public void updateWithArticles(Article article, RequestManager glide){
        this.section.setText(article.getArticleSection());

        this.title.setText(article.getArticleTitle());

        this.date.setText(formatTheDate(article));

        if(article.getArticleURLImage() != null) glide.load(article.getArticleURLImage()).into(icnArticle);
        else this.icnArticle.setVisibility(View.GONE);
    }

    private String formatTheDate(Article article){
        String date = article.getArticleDate();
        date = date.substring(0,4) + "/" + date.substring(5,7) + "/" + date.substring(8,10);
        return date;
    }
}
