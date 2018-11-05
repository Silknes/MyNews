package com.oc.eliott.mynews.Model.MostPopular;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.oc.eliott.mynews.Model.Article;

import java.util.List;

public class ResultMostPopular {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("copyright")
    @Expose
    private String copyright;
    @SerializedName("num_results")
    @Expose
    private Integer numResults;
    @SerializedName("results")
    @Expose
    private List<ArticleMostPopular> results = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public Integer getNumResults() {
        return numResults;
    }

    public void setNumResults(Integer numResults) {
        this.numResults = numResults;
    }

    public List<? extends Article> getResults() {
        return results;
    }

    public void setResults(List<ArticleMostPopular> results) {
        this.results = results;
    }
}
