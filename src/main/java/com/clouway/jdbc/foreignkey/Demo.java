package com.clouway.jdbc.foreignkey;

import java.sql.*;
import java.util.List;

@SuppressWarnings("Duplicates")
public class Demo {

    public static void main(String[] args){

        Connection conn = getConnection();

        TripRepository tripRepo = TripRepository.instanceOf(conn);
        PersonRepository personRepo = PersonRepository.instanceOf(conn);

        personRepo.createPersonTable();
        tripRepo.createTripTable();

        Person john = new Person("John", "9454256423", 20, "John@doe.com");
        Person bill = new Person("Bill", "9351235432", 35, "Bill@doe.com");
        Person steve = new Person("Steve", "9252131453", 55, "Steve@aol.com");


        Trip sofiaTripWithJohn = new Trip(john.getEgn(),
                "Sofia",
                Date.valueOf("2018-01-01"),
                Date.valueOf("2018-01-25"));

        Trip sofiaTripWithBill = new Trip(bill.getEgn(),
                "Sofia",
                Date.valueOf("2018-01-01"),
                Date.valueOf("2018-02-20"));

        Trip sofiaTripWithSteve = new Trip(bill.getEgn(),
                "Sofia",
                Date.valueOf("2019-03-02"),
                Date.valueOf("2019-05-23"));



        personRepo.addPerson(john);
        personRepo.addPerson(bill);
        personRepo.addPerson(steve);

        tripRepo.addTrip(sofiaTripWithJohn);
        tripRepo.addTrip(sofiaTripWithBill);
        tripRepo.addTrip(sofiaTripWithSteve);

        Person getJohn = personRepo.getPersonById(1);
        Trip getSofiaTrip = tripRepo.getTripById(1);

        getJohn.setEmail("newJohnDoe@gmail.com");
        getSofiaTrip.setArrival(Date.valueOf("2018-01-08"));

        personRepo.updatePerson(getJohn);
        tripRepo.updateTrip(getSofiaTrip);

        System.out.println("People total: " + personRepo.getAll().size());

        List<Person> peopleWithJNames = personRepo.getPeopleByName("J");

        System.out.println("People with J names: " + peopleWithJNames.size());

        List<Person> peopleInSameCity = tripRepo.getPeopleInCity("Sofia");

        System.out.println("People in Sofia at the same time: " + peopleInSameCity.size());



        tripRepo.dropTable();
        personRepo.dropTable();

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
