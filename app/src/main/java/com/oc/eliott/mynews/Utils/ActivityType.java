package com.oc.eliott.mynews.Utils;

public enum ActivityType {
    SEARCH("Search"),
    NOTIFICATION("Notification");

    private String stringActivityType;

    private ActivityType(String stringActivityTypes){
        this.stringActivityType = stringActivityTypes;
    }

    public String toString(){
        return stringActivityType;
    }
}
