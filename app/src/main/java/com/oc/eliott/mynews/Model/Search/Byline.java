package com.oc.eliott.mynews.Model.Search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Byline {
    @SerializedName("organization")
    @Expose
    private String organization;
    @SerializedName("original")
    @Expose
    private String original;
    @SerializedName("person")
    @Expose
    private List<Person> person = null;

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public List<Person> getPerson() {
        return person;
    }

    public void setPerson(List<Person> person) {
        this.person = person;
    }
}
