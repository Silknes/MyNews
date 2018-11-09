package com.oc.eliott.mynews.Utils;

import android.support.annotation.Nullable;

import com.oc.eliott.mynews.Model.MostPopular.ResultMostPopular;
import com.oc.eliott.mynews.Model.Search.ResultSearch;
import com.oc.eliott.mynews.Model.TopStories.ResultTopStories;

import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NYTCalls {
    public interface CallbacksTopStories {
        void onResponseTopStories(@Nullable ResultTopStories resultTS);
        void onFailureTopStories();
    }

    public interface CallbacksMostPopular{
        void onResponseMostPopular(@Nullable ResultMostPopular resultMP);
        void onFailureMostPopular();
    }

    public interface CallbacksSearch{
        void onResponseSearch(@Nullable ResultSearch resultSearch);
        void onFailureSearch();
    }

    public static void fetchTopStoriesArticles(CallbacksTopStories callbacks, String apiKey){
        final WeakReference<CallbacksTopStories> callbacksWeakReference = new WeakReference<CallbacksTopStories>(callbacks);
        NYTService nytService = NYTService.retrofit.create(NYTService.class);
        Call<ResultTopStories> call = nytService.getFollowingTSArticle(apiKey);
        call.enqueue(new Callback<ResultTopStories>() {
            @Override
            public void onResponse(Call<ResultTopStories> call, Response<ResultTopStories> response) {
                if(callbacksWeakReference.get() != null) callbacksWeakReference.get().onResponseTopStories(response.body());
            }
            @Override
            public void onFailure(Call<ResultTopStories> call, Throwable t) {
                if(callbacksWeakReference.get() != null) callbacksWeakReference.get().onFailureTopStories();
            }
        });
    }

    public static void fetchMostPopularArticles(CallbacksMostPopular callbacks, String section, String timePeriod, String apiKey){
        final WeakReference<CallbacksMostPopular> callbacksWeakReference = new WeakReference<CallbacksMostPopular>(callbacks);
        NYTService nytService = NYTService.retrofit.create(NYTService.class);
        Call<ResultMostPopular> call = nytService.getFollowingMPArticle(section, timePeriod, apiKey);
        call.enqueue(new Callback<ResultMostPopular>() {
            @Override
            public void onResponse(Call<ResultMostPopular> call, Response<ResultMostPopular> response) {
                if(callbacksWeakReference.get() != null) callbacksWeakReference.get().onResponseMostPopular(response.body());

            }
            @Override
            public void onFailure(Call<ResultMostPopular> call, Throwable t) {
                if(callbacksWeakReference.get() != null) callbacksWeakReference.get().onFailureMostPopular();
            }
        });
    }

    public static void fetchSearchArticles
            (CallbacksSearch callbacks, String apiKey, String newsDesk, String queryTerm, String beginDate, String endDate, String sort){
        final WeakReference<CallbacksSearch> callbacksWeakReference = new WeakReference<CallbacksSearch>(callbacks);
        NYTService nytService = NYTService.retrofit.create(NYTService.class);
        Call<ResultSearch> call = nytService.getFollowingSearchArticle(apiKey, newsDesk, queryTerm, beginDate, endDate, sort);
        call.enqueue(new Callback<ResultSearch>() {
            @Override
            public void onResponse(Call<ResultSearch> call, Response<ResultSearch> response) {
                if(callbacksWeakReference.get() != null) callbacksWeakReference.get().onResponseSearch(response.body());
            }
            @Override
            public void onFailure(Call<ResultSearch> call, Throwable t) {
                if(callbacksWeakReference.get() != null) callbacksWeakReference.get().onFailureSearch();
            }
        });
    }
}
