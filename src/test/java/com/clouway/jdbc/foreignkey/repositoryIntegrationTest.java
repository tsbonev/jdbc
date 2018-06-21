package com.clouway.jdbc.foreignkey;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class repositoryIntegrationTest {

    Connection conn;
    TripRepository tripRepo;
    PersonRepository personRepo;

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

    Trip varnaTripWithSteve = new Trip(bill.getEgn(),
            "Varna",
            Date.valueOf("2020-03-02"),
            Date.valueOf("2020-05-23"));

    @Before
    public void setUp(){

        conn = Demo.getConnection();
        tripRepo = TripRepository.instanceOf(conn);
        personRepo = PersonRepository.instanceOf(conn);

        personRepo.createPersonTable();
        tripRepo.createTripTable();
        personRepo.addPerson(bill);
        personRepo.addPerson(john);
        personRepo.addPerson(steve);
    }

    @After
    public void cleanUp() throws SQLException {
        tripRepo.dropTable();
        personRepo.dropTable();
        conn.close();
        PersonRepository.clearInstance();
        TripRepository.clearInstance();
    }

    @Test
    public void addTripAndGetById(){

        tripRepo.addTrip(sofiaTripWithBill);

        assertThat(tripRepo.getTripById(1).getEgn(), is(sofiaTripWithBill.getEgn()));

    }

    @Test
    public void deleteTripById(){

        tripRepo.addTrip(sofiaTripWithBill);

        assertThat(tripRepo.getTripById(1).getEgn(), is(sofiaTripWithBill.getEgn()));

        tripRepo.deleteTripById(1);

        assertThat(tripRepo.getTripById(1).getEgn(), is(nullValue()));

    }

    @Test
    public void getMostVisitedCities(){

        tripRepo.addTrip(sofiaTripWithBill);
        tripRepo.addTrip(sofiaTripWithJohn);
        tripRepo.addTrip(sofiaTripWithSteve);
        tripRepo.addTrip(varnaTripWithSteve);

        List<String> citiesList = Arrays.asList("Sofia", "Varna");

        List<String> resultList = tripRepo.getMostVisitedDescending();

        assertThat(resultList.get(0), is(citiesList.get(0)));
        assertThat(resultList.get(1), is(citiesList.get(1)));

    }

    @Test
    public void getVisitorsAtTheSameTime(){

        tripRepo.addTrip(sofiaTripWithBill);
        tripRepo.addTrip(sofiaTripWithJohn);
        tripRepo.addTrip(sofiaTripWithSteve);
        tripRepo.addTrip(varnaTripWithSteve);

        List<Person> resultList = tripRepo.getPeopleInCity("Sofia");

        assertThat(resultList.size(), is(2));
        assertThat(resultList.stream()
                .filter(p -> p.getFname().equalsIgnoreCase("John")).count(), is(1L));
        assertThat(resultList.stream()
                .filter(p -> p.getFname().equalsIgnoreCase("Bill")).count(), is(1L));

    }

    @Test
    public void updateTrip(){

        tripRepo.addTrip(sofiaTripWithBill);

        Trip retrievedTrip = tripRepo.getTripById(1);

        assertThat(retrievedTrip.getCity(), is("Sofia"));

        retrievedTrip.setCity("Varna");

        tripRepo.updateTrip(retrievedTrip);

        assertThat(tripRepo.getTripById(1).getCity(), is("Varna"));

    }

    @Test
    public void dropContent() throws SQLException {

        DatabaseMetaData dbm = conn.getMetaData();

        tripRepo.dropTableContent();

        ResultSet tables = dbm.getTables(null,null, "trip", null);

        assertThat(tables.next(), is(true));
        assertThat(tripRepo.getAll().size(), is(0));

    }

}
