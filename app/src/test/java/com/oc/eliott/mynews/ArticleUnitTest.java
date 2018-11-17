package com.oc.eliott.mynews;

import com.oc.eliott.mynews.Model.Article;
import com.oc.eliott.mynews.Model.MostPopular.ArticleMostPopular;
import com.oc.eliott.mynews.Model.Search.ArticleSearch;
import com.oc.eliott.mynews.Model.TopStories.ArticleTopStories;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ArticleUnitTest {
    private ArrayList<Article> articles;
    @Before
    public void setup(){
        articles = new ArrayList<>();
        ArticleTopStories articleTopStories = new ArticleTopStories();
        articleTopStories.setTitle("TopStories Title");
        articleTopStories.setSection("TopStories");
        articleTopStories.setUrl("http://www.fooTS.com");
        articleTopStories.setPublishedDate("1905-07-01T00:00:00Z");

        ArticleMostPopular articleMostPopular = new ArticleMostPopular();
        articleMostPopular.setTitle("MostPopular Title");
        articleMostPopular.setSection("MostPopular");
        articleMostPopular.setUrl("http://www.fooMP.com");
        articleMostPopular.setPublishedDate("1907-10-01T00:00:00Z");

        ArticleSearch articleSearch = new ArticleSearch();
        articleSearch.setSnippet("Search Title");
        articleSearch.setSectionName("Search");
        articleSearch.setWebUrl("http://www.fooSearch.com");
        articleSearch.setPubDate("1909-07-28T00:00:00Z");

        articles.add(articleTopStories);
        articles.add(articleMostPopular);
        articles.add(articleSearch);
    }

    @Test
    public void formatTheDate() {
        assertEquals("1905/07/01", articles.get(0).formatTheDate());
        assertEquals("1907/10/01", articles.get(1).formatTheDate());
        assertEquals("1909/07/28", articles.get(2).formatTheDate());
    }

    @Test
    public void getTitle() {
        assertEquals("TopStories Title", articles.get(0).getArticleTitle());
        assertEquals("MostPopular Title", articles.get(1).getArticleTitle());
        assertEquals("Search Title", articles.get(2).getArticleTitle());
    }

    @Test
    public void getSectionNamed() {
        assertEquals("TopStories", articles.get(0).getArticleSection());
        assertEquals("MostPopular", articles.get(1).getArticleSection());
        assertEquals("Search", articles.get(2).getArticleSection());
    }

    @Test
    public void getWebUrl() {
        assertEquals("http://www.fooTS.com", articles.get(0).getArticleURL());
        assertEquals("http://www.fooMP.com", articles.get(1).getArticleURL());
        assertEquals("http://www.fooSearch.com", articles.get(2).getArticleURL());
    }
}