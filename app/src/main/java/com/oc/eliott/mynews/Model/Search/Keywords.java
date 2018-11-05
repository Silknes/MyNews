package com.oc.eliott.mynews.Model.Search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Keywords {
    @SerializedName("rank")
    @Expose
    private String rank;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("value")
    @Expose
    private String value;

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
