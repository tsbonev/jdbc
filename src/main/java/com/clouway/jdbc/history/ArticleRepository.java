package com.clouway.jdbc.history;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ArticleRepository {

    private static ArticleRepository instance;

    private static Connection conn;

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
