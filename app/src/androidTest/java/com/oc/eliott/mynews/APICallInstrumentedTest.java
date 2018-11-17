package com.oc.eliott.mynews;

import android.support.annotation.Nullable;
import android.support.test.runner.AndroidJUnit4;

import com.oc.eliott.mynews.Model.Article;
import com.oc.eliott.mynews.Model.MostPopular.ResultMostPopular;
import com.oc.eliott.mynews.Model.Search.ResultSearch;
import com.oc.eliott.mynews.Model.TopStories.ArticleTopStories;
import com.oc.eliott.mynews.Model.TopStories.ResultTopStories;
import com.oc.eliott.mynews.Utils.NYTCalls;
import com.oc.eliott.mynews.Utils.NYTService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class APICallInstrumentedTest {
    @Test
    public void getTopStoriesArticle() throws Exception{
        NYTService nytService = NYTService.retrofit.create(NYTService.class);
        Call<ResultTopStories> articlesTopStories = nytService.getFollowingTSArticle("7a0743e89dda4664b7925d78d94f9ea2");
        Response<ResultTopStories> articleTSResponse = articlesTopStories.execute();

        assertEquals(articleTSResponse.code(), 200);
        assertEquals(true, articleTSResponse.isSuccessful());
    }

    @Test
    public void getMostPopularArticle() throws Exception{
        NYTService nytService = NYTService.retrofit.create(NYTService.class);
        Call<ResultMostPopular> articlesMostPopular = nytService.getFollowingMPArticle("all-sections", "1", "7a0743e89dda4664b7925d78d94f9ea2");
        Response<ResultMostPopular> articleMPResponse = articlesMostPopular.execute();

        assertEquals(articleMPResponse.code(), 200);
        assertEquals(true, articleMPResponse.isSuccessful());
    }

    @Test
    public void getSearchArticle() throws Exception{
        NYTService nytService = NYTService.retrofit.create(NYTService.class);
        Call<ResultSearch> articlesSearch = nytService
                .getFollowingSearchArticle("7a0743e89dda4664b7925d78d94f9ea2", "news_desk=(\"Business\")", "Macron", "19000101", "20181116", "newest");
        Response<ResultSearch> articleSearchResponse = articlesSearch.execute();

        assertEquals(articleSearchResponse.code(), 200);
        assertEquals(true, articleSearchResponse.isSuccessful());
    }
}
