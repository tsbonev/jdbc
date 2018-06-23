package com.clouway.jdbc.history;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class testHistory {

    ArticleRepository repo;

    @Before
    public void setUp(){
        repo = ArticleRepository.getInstance(Demo.getConnection());
    }

    @After
    public void cleanUp(){

        repo.dropTable();
    }

    @Test
    public void paginateHistory(){

        repo.createArticleTable();
        repo.setPerPage(3);

        for(int i = 0; i < 9; i++){
            String content = "This article is numbered " + i;
            Article article = new Article(content);
            repo.addArticle(article);
        }

        List<Article> articleList = repo.getHistoryPage(2);

        assertThat(articleList.size(), is(3));
        assertThat(articleList.get(0).getContent(), is("This article is numbered 3"));


    }

    @Ignore
    @Test
    public void addOneByOne(){

        repo.createArticleTable();

        for (int i = 0; i < 100000; i++){

            String content = "This article is numbered " + i;
            Article article = new Article(content);
            repo.addArticle(article);
        }

        //21 seconds 482 ms for 10k
        //3 minutes 22 seconds 401 ms for 100k

    }

    @Ignore
    @Test
    public void addInABatch(){

        repo.createArticleTable();

        List<Article> articleList = new ArrayList<>();

        for (int i = 0; i < 100000; i++){

            String content = "This article is numbered " + i;
            Article article = new Article(content);
            articleList.add(article);
        }

        repo.addBatch(articleList);

        //13 seconds 800 ms for 10k
        //2 minutes 6 seconds 367 ms for 100k

        //at 1.17 seconds per 1000 inserts 1.000.000 would take 16 minutes

    }


}
