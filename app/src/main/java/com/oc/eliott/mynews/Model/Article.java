package com.oc.eliott.mynews.Model;

public abstract class Article {
    abstract public String getArticleTitle();
    abstract public String getArticleURL();
    abstract public String getArticleURLImage();
    abstract public String getArticleDate();
    abstract public String getArticleSection();

    public String formatTheDate(){
        String date = this.getArticleDate();
        date = date.substring(0,4) + "/" + date.substring(5,7) + "/" + date.substring(8,10);
        return date;
    }
}
