package com.oc.eliott.mynews.Model.Search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.oc.eliott.mynews.Model.Article;

import java.util.List;

public class Response {
    @SerializedName("docs")
    @Expose
    private List<ArticleSearch> docs = null;

    public List<? extends Article> getDocs() {
        return docs;
    }

    public void setDocs(List<ArticleSearch> docs) {
        this.docs = docs;
    }

    /*@SerializedName("meta")
    @Expose
    private Meta meta;*/

    /*public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }*/
}
