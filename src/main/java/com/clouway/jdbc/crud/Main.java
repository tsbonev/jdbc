package com.clouway.jdbc.crud;

import java.sql.*;

public class Main {

    public static void main(String[] args) {

        Connection conn = getConnection();
        createTable(conn);
        insertIntoTable(conn, "John", "john@doe.com", 25, "9605123456");
        insertIntoTable(conn, "Steve", "steve@joe.com", 50, "6705123456");
        selectWhereNameIs(conn,"Steve");
        selectAllFromTable(conn);
        alterTable(conn);
        deleteFromTable(conn, "6705123456");
        addLastName(conn, "Joe", "6705123456");
        dropTable(conn);


    }

    public static void dropTable(Connection conn){

        try{

            PreparedStatement drop = conn.prepareStatement("DROP TABLE person");
            drop.execute();

        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public static void addLastName(Connection conn, String lname, String egn){

        try{

            PreparedStatement add = conn.prepareStatement("UPDATE person" +
                    " SET lname = ?" +
                    " WHERE egn LIKE ?");

            add.setString(1, lname);
            add.setString(2, egn);
            add.execute();

            PreparedStatement select = conn.prepareStatement("SELECT lname FROM person" +
                    " WHERE egn LIKE ?");
            select.setString(1, egn);

            ResultSet result = select.executeQuery();
            while (result.next()){
                System.out.println("Last name: " + result.getString("lname"));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public static void alterTable(Connection conn){

        try {
            PreparedStatement alter = conn.prepareStatement("ALTER TABLE person ADD lname varchar(50)");
            alter.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void deleteFromTable(Connection conn, String egn) {

        try {
            PreparedStatement delete = conn.prepareStatement("DELETE FROM person" +
                    " WHERE egn LIKE ?");

            delete.setString(1, egn);
            delete.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void selectWhereNameIs(Connection conn, String name) {

        try {
            PreparedStatement select = conn.prepareStatement("SELECT * FROM person" +
                    " WHERE fname LIKE ?");

            select.setString(1, name);

            ResultSet result = select.executeQuery();

            while (result.next()) {
                System.out.println("Age: " + result.getString("age"));
                System.out.println("Email: " + result.getString("email"));
                System.out.println("Egn: " + result.getString("egn"));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public static void selectAllFromTable(Connection conn) {

        try {
            PreparedStatement select = conn.prepareStatement("SELECT * FROM person");

            ResultSet result = select.executeQuery();

            while (result.next()) {
                System.out.println("Name: " + result.getString("fname"));
                System.out.println("Age: " + result.getString("age"));
                System.out.println("Email: " + result.getString("email"));
                System.out.println("Egn: " + result.getString("egn"));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void insertIntoTable(Connection conn, String name, String email, int age, String egn) {

        try {

            PreparedStatement insert = conn.prepareStatement(
                    "INSERT INTO person(fname, egn, age, email)" +
                            "VALUES (?, ?, ?, ?)"
            );

            insert.setString(1, name);
            insert.setString(2, egn);
            insert.setInt(3, age);
            insert.setString(4, email);

            insert.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void createTable(Connection conn) {

        try {
            PreparedStatement create = conn.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS person(" +
                            "fname varchar(255) NOT NULL," +
                            "egn varchar(10) NOT NULL," +
                            "age int NOT NULL," +
                            "email varchar(255) NOT NULL," +
                            "PRIMARY KEY(egn))");
            create.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static Connection getConnection() {

        try {
            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/people?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=Europe/Sofia";
            String username = "user";
            String password = "password";
            Class.forName(driver);

            Connection conn = DriverManager.getConnection(url, username, password);
            System.out.println("Connection established");
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        return null;

    }
}
