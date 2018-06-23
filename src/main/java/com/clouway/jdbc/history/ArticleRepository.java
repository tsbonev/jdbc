package com.clouway.jdbc.history;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArticleRepository {

    private static ArticleRepository instance;

    private static Connection conn;

    private int perPage = 1;

    public void setPerPage(int perPage){
        this.perPage = perPage;
    }

    public static ArticleRepository getInstance(Connection conn) {

        if (instance == null) instance = new ArticleRepository(conn);

        instance.conn = conn;

        return instance;
    }

    public static void clearInstance() {
        instance = null;
    }

    private ArticleRepository(Connection conn) {

        this.conn = conn;

    }

    public void dropTable(){

        try {
            PreparedStatement drop = conn.prepareStatement("DROP TABLE article");
            PreparedStatement dropHistory = conn.prepareStatement("DROP TABLE article_history");
            drop.execute();
            dropHistory.execute();

        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public List<Article> getHistoryPage(int pageNum){

        try {
            PreparedStatement get = conn.prepareStatement("SELECT * FROM article_history" +
                    " LIMIT ? OFFSET ?");

            get.setInt(1, this.perPage);
            get.setInt(2, this.perPage * (pageNum - 1));

            ResultSet resultSet = get.executeQuery();

            List<Article> articleList = new ArrayList<>();

            while (resultSet.next()){
                Article article = new Article();
                article.setId(resultSet.getInt("id"));
                article.setContent(resultSet.getString("content"));
                articleList.add(article);
            }

            return articleList;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void addBatch(List<Article> articleList){

        try {

            PreparedStatement add = conn.prepareStatement("INSERT INTO article(" +
                    "content)" +
                    " VALUES (?)");

            PreparedStatement history = conn.prepareStatement("INSERT INTO article_history(" +
                    "content)" +
                    " VALUES (?)");

            int rowCount = 0;

            for (Article article : articleList) {

                rowCount++;

                add.setString(1, article.getContent());
                add.addBatch();
                history.setString(1, article.getContent());
                add.addBatch();

                if(rowCount % 1000 == 0 || rowCount >= articleList.size()){
                    add.executeLargeBatch();
                    history.executeLargeBatch();
                }

            }

        }catch (SQLException e){
            e.printStackTrace();
        }


    }

    public void addArticle(Article article){

        try {
            PreparedStatement add = conn.prepareStatement("INSERT INTO article(" +
                    "content)" +
                    " VALUES (?)");

            PreparedStatement history = conn.prepareStatement("INSERT INTO article_history(" +
                    "content)" +
                    " VALUES (?)");

            add.setString(1, article.getContent());
            history.setString(1, article.getContent());

            add.execute();
            history.execute();

        }catch (SQLException e){
            e.printStackTrace();
        }

    }


    public void createArticleTable() {

        try {
            PreparedStatement create = conn.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS article(" +
                            "id int NOT NULL AUTO_INCREMENT," +
                            "content text NOT NULL," +
                            "PRIMARY KEY(id))"
                            );
            PreparedStatement createHistory = conn.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS article_history(" +
                            "id int NOT NULL AUTO_INCREMENT," +
                            "content text NOT NULL," +
                            "PRIMARY KEY(id))"
            );

            create.execute();
            createHistory.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
