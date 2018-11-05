package com.oc.eliott.mynews.Model.TopStories;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.oc.eliott.mynews.Model.Article;

import java.util.List;

public class ResultTopStories {
    @SerializedName("results")
    @Expose
    private List<ArticleTopStories> results = null;

    public List<? extends Article> getResults() {
        return results;
    }

    public void setResults(List<ArticleTopStories> results) {
        this.results = results;
    }
}
