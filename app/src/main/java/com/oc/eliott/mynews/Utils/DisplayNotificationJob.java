package com.oc.eliott.mynews.Utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
import static com.oc.eliott.mynews.Controller.Fragments.NotificationFragment.KEY_NEWS_DESK;
import static com.oc.eliott.mynews.Controller.Fragments.NotificationFragment.KEY_PREFERENCES_QUERY_TERM;
import static com.oc.eliott.mynews.Controller.Fragments.NotificationFragment.KEY_SHARED_PREFERENCES;

public class DisplayNotificationJob extends Job implements NYTCalls.CallbacksSearch{
    private List<? extends Article> articles, listArticlesinPref;
    public static final String TAG = "show_notification_job_tag";
    private static final String KEY_PREFERENCES_ARTICLES = "KEY_PREFERENCES_ARTICLES";
    private static final String CHANNEL_ID = "HIGH_NOTIFICATION";
    private static final int NOTIFICATION_ID = 001;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private int nbOfDifferentArticle;

    // This job verify is there new article published since the last call
    // And if there new article send a notification with the number of new article
    @NonNull
    @Override
    protected Result onRunJob(Params params) {
        preferences = getContext().getSharedPreferences(KEY_SHARED_PREFERENCES, MODE_PRIVATE);
        editor = preferences.edit();
        String queryTerm = preferences.getString(KEY_PREFERENCES_QUERY_TERM, "");
        String newsDesk = preferences.getString(KEY_NEWS_DESK, "");
        fetchArticles(queryTerm, newsDesk);

        return Result.SUCCESS;
    }

    // Schedule the job every day if there is a network connection
    public static void schedulePeriodic() {
        new JobRequest.Builder(DisplayNotificationJob.TAG)
                .setPeriodic(TimeUnit.MINUTES.toMillis(1440), TimeUnit.MINUTES.toMillis(5))
                .setUpdateCurrent(true)
                .setPersisted(true)
                .setRequiredNetworkType(JobRequest.NetworkType.CONNECTED)
                .build()
                .schedule();
    }

    // Method that configure the call to the API
    private void fetchArticles(String queryTerm, String newsDesk) {
        String beginDate = "19820101";
        Calendar cal = Calendar.getInstance();
        String endDate = "" + cal.get(Calendar.YEAR) + (cal.get(Calendar.MONTH) + 1) + cal.get(Calendar.DAY_OF_MONTH);
        NYTCalls.fetchSearchArticles(this, "7a0743e89dda4664b7925d78d94f9ea2", newsDesk, queryTerm, beginDate, endDate, "newest");
    }

    // When the API answer we checked if we have new article and update the string display by the notif according to it
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

    // Method that get the old list of article and get the new list
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

    // Method that compare the 2 list and return true if at least one article are different
    // More over count the number of different article
    private void isThereNewArticle(){
        int nbOfSameArticle = 0;
        for(Article article : articles){
            for(Article article1 : listArticlesinPref){
                if(Objects.equals(article.getArticleTitle(), article1.getArticleTitle())) {
                    nbOfSameArticle = nbOfSameArticle + 1;
                }
            }
        }
        nbOfDifferentArticle = 10 - nbOfSameArticle;
    }

    // Method that configure the notification send to the user
    private void myNotif(String contentText){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getContext(), "NOTIFICATION_CHANNEL_NAME");
        mBuilder.setSmallIcon(R.drawable.ic_info_white_24dp)
                .setContentTitle(getContext().getResources().getString(R.string.app_name))
                .setContentText(contentText)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        NotificationManager mNotificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
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
