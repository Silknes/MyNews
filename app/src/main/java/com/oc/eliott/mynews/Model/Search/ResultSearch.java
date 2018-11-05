package com.oc.eliott.mynews.Model.Search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultSearch {
    @SerializedName("response")
    @Expose
    private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }
}
