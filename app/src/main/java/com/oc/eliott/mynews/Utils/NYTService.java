package com.oc.eliott.mynews.Utils;

import com.oc.eliott.mynews.Model.MostPopular.ResultMostPopular;
import com.oc.eliott.mynews.Model.Search.ResultSearch;
import com.oc.eliott.mynews.Model.TopStories.ResultTopStories;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

// Class where we describe the structure of each url need by the API
public interface NYTService {
    @GET("topstories/v2/home.json")
    Call<ResultTopStories> getFollowingTSArticle(@Query("api-key") String apiKey);

    @GET("mostpopular/v2/mostviewed/{section}/{time-period}.json")
    Call<ResultMostPopular> getFollowingMPArticle(@Path("section") String section,
                                                  @Path("time-period") String timePeriod,
                                                  @Query("api-key") String apiKey);

    @GET("search/v2/articlesearch.json")
    Call<ResultSearch> getFollowingSearchArticle(@Query("api-key") String apiKey,
                                                 @Query("fq") String newsDesk,
                                                 @Query("q") String queryTerm,
                                                 @Query("begin_date") String beginDate,
                                                 @Query("end_date") String endDate,
                                                 @Query("sort") String sort);

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/svc/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
