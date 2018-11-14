package com.oc.eliott.mynews.Utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.oc.eliott.mynews.Controller.Activities.NotificationActivity;
import com.oc.eliott.mynews.Controller.Fragments.NotificationFragment;
import com.oc.eliott.mynews.Model.Article;
import com.oc.eliott.mynews.Model.Search.ArticleSearch;
import com.oc.eliott.mynews.Model.Search.ResultSearch;
import com.oc.eliott.mynews.R;

import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static android.content.Context.MODE_PRIVATE;
import static com.oc.eliott.mynews.Controller.Activities.MainActivity.CHANNEL_ID;
import static com.oc.eliott.mynews.Controller.Activities.MainActivity.NOTIFICATION_ID;

public class DisplayNotificationJob extends Job implements NYTCalls.CallbacksSearch{
    private List<? extends Article> articles, listArticlesinPref;
    private NotificationCompat.Builder mBuilder;
    private NotificationManager mNotificationManager;
    public static final String TAG = "show_notification_job_tag";
    public static final String KEY_PREFERENCES_ARTICLES = "KEY_PREFERENCES_ARTICLES";
    private String newsDesk, queryTerm;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private int nbOfSameArticle = 0;
    private int nbOfDifferentArticle;

    @NonNull
    @Override
    protected Result onRunJob(Params params) {
        preferences = getContext().getSharedPreferences("DISPLAY_NOTIFICATION_JOB", MODE_PRIVATE);
        editor = preferences.edit();
        newsDesk = NotificationFragment.newsDesk;
        queryTerm = NotificationFragment.queryTerm;
        fetchArticles(queryTerm, newsDesk);

        return Result.SUCCESS;
    }

    public static void schedulePeriodic() {
        new JobRequest.Builder(DisplayNotificationJob.TAG)
                .setPeriodic(TimeUnit.HOURS.toMillis(24))
                .setUpdateCurrent(true)
                .setPersisted(true)
                .build()
                .schedule();
    }

    private void fetchArticles(String queryTerm, String newsDesk) {
        String beginDate = "19820101";
        Calendar cal = Calendar.getInstance();
        String endDate = "" + cal.get(Calendar.YEAR) + (cal.get(Calendar.MONTH) + 1) + cal.get(Calendar.DAY_OF_MONTH);
        NYTCalls.fetchSearchArticles(this, "7a0743e89dda4664b7925d78d94f9ea2", newsDesk, queryTerm, beginDate, endDate, "newest");
    }

    @Override
    public void onResponseSearch(@Nullable ResultSearch result) {
        if(result != null) articles = result.getResponse().getDocs();
        getOrPutArticleListInPref(articles);

        isThereNewArticle();

        if(nbOfDifferentArticle != 0) {
            String contentText = nbOfDifferentArticle + " articles has been published";
            myNotif(contentText);
        }
        else {
            String contentText = "No article has been published";
            myNotif(contentText);
        }

        Gson gson = new Gson();
        String json = gson.toJson(articles);
        editor.putString(KEY_PREFERENCES_ARTICLES, json).apply();
    }

    @Override
    public void onFailureSearch() {}

    private void getOrPutArticleListInPref(List<? extends Article> articles){
        Gson gson = new Gson();
        String json = preferences.getString(KEY_PREFERENCES_ARTICLES, null);
        if(json == null){
            json = gson.toJson(articles);
            editor.putString(KEY_PREFERENCES_ARTICLES, json).apply();
        }
        Type type = new TypeToken<List<ArticleSearch>>(){}.getType();
        listArticlesinPref = gson.fromJson(json, type);
    }

    private boolean isThereNewArticle(){
        nbOfSameArticle = 0;
        for(Article article : articles){
            for(Article article1 : listArticlesinPref){
                if(Objects.equals(article.getArticleTitle(), article1.getArticleTitle())) {
                    nbOfSameArticle = nbOfSameArticle + 1;
                }
            }
        }
        nbOfDifferentArticle = 10 - nbOfSameArticle;
        return nbOfSameArticle < 10;
    }

    private void myNotif(String contentText){
        mBuilder = new NotificationCompat.Builder(getContext(), "NOTIFICATION_CHANNEL_NAME");
        mBuilder.setSmallIcon(R.drawable.ic_info_white_24dp)
                .setContentTitle(getContext().getResources().getString(R.string.app_name))
                .setContentText(contentText)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        mNotificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
            assert mNotificationManager != null;
            mBuilder.setChannelId(CHANNEL_ID);
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
        assert mNotificationManager != null;
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}
