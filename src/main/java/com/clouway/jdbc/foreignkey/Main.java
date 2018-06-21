package com.clouway.jdbc.foreignkey;

import java.sql.*;

@SuppressWarnings("Duplicates")
public class Main {

    public static void main(String[] args){

        Connection conn = getConnection();
        createPersonTable(conn);
        createTripTable(conn);

        Person john = new Person("John", "9454256423", 20, "John@doe.com");
        Trip sofiaTrip = new Trip(john.getEgn(),
                "Sofia",
                Date.valueOf("2018-01-01"),
                Date.valueOf("2018-01-25"));

        addPerson(conn, john);
        addTrip(conn, sofiaTrip);



    }

    public static void addTrip(Connection conn, Trip trip){

        try {
            PreparedStatement add = conn.prepareStatement("INSERT INTO trip(" +
                    "city, personEgn, arrival, departure)" +
                    " VALUES (?, ?, ?, ?)");

            add.setString(1, trip.getCity());
            add.setString(2, trip.getEgn());
            add.setDate(3, trip.getArrival());
            add.setDate(4, trip.getArrival());

            add.execute();

        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public static void addPerson(Connection conn, Person person){

        try {
            PreparedStatement add = conn.prepareStatement("INSERT INTO person(" +
                    "fname, email, egn, age)" +
                    " VALUES (?, ?, ?, ?)");

            add.setString(1, person.getFname());
            add.setString(2, person.getEmail());
            add.setString(3, person.getEgn());
            add.setInt(4, person.getAge());

            add.execute();

        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public static void createTripTable(Connection conn){

        try {

            PreparedStatement create = conn.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS trip(" +
                            "id int NOT NULL AUTO_INCREMENT," +
                            "city varchar(255) NOT NULL," +
                            "arrival date NOT NULL," +
                            "departure date NOT NULL," +
                            "personEgn varchar(10) NOT NULL," +
                            "PRIMARY KEY(id)," +
                            "FOREIGN KEY (personEgn) REFERENCES person(egn))"
            );

            create.execute();

        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public static void createPersonTable(Connection conn) {

        try {
            PreparedStatement create = conn.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS person(" +
                            "id int NOT NULL AUTO_INCREMENT," +
                            "fname varchar(255) NOT NULL," +
                            "egn varchar(10) NOT NULL UNIQUE," +
                            "age int NOT NULL," +
                            "email varchar(255) NOT NULL," +
                            "PRIMARY KEY(id))");
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
