package com.clouway.jdbc.foreignkey;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collector;

@SuppressWarnings("Duplicates")
public class TripRepository {

    private Connection conn;

    private static TripRepository instance;

    public static TripRepository instanceOf(Connection conn){
        if(instance == null) instance = new TripRepository(conn);
        return instance;
    }

    private TripRepository(Connection conn){
        this.conn = conn;
    }

    public List<Person> getPeopleInCity(String city){

        List<Person> personList = new ArrayList<>();

        this.getAll().stream()
                .filter(t -> t.getCity().equalsIgnoreCase(city)).collect(SameTime.collector())
               .forEach(t -> personList.add(this.getPersonInTrip(t)));

        return personList;
    }

    private static final class SameTime {

        private Set<Trip> set = new HashSet<>();

        private Trip first, second;

        public void accept(Trip trip){

            first = second;
            second = trip;
            if(first != null && first.getArrival().before(second.getDeparture())
                    && first.getDeparture().after(second.getArrival())){
                set.add(first);
                set.add(second);
            }
        }


        public SameTime combine(SameTime other) {
            throw new UnsupportedOperationException("Parallel Stream not supported");
        }

        public List<Trip> finish() {
            List<Trip> tripList = new ArrayList<>();
            tripList.addAll(set);
            return tripList;
        }

        public static Collector<Trip, ?, List<Trip>> collector() {
            return Collector.of(SameTime::new, SameTime::accept, SameTime::combine, SameTime::finish);
        }

    }

    private Person getPersonInTrip(Trip trip){

        Person person = new Person();

        try{

            PreparedStatement select = conn.prepareStatement("SELECT * FROM person" +
                    " WHERE egn LIKE ?");

            select.setString(1, trip.getEgn());

            ResultSet result = select.executeQuery();

            while (result.next()){

                person.setId(result.getInt("id"));
                person.setAge(result.getInt("age"));
                person.setFname(result.getString("fname"));
                person.setEmail(result.getString("email"));
                person.setEgn(result.getString("egn"));
            }

            return person;

        }catch (SQLException e){
            e.printStackTrace();
        }

        return null;

    }

    public List<Trip> getAll(){

        try {

            PreparedStatement select = conn.prepareStatement("SELECT * FROM trip");
            ResultSet result = select.executeQuery();

            List<Trip> list = new ArrayList<Trip>();

            while (result.next()){

                Trip trip = new Trip();
                trip.setId(result.getInt("id"));
                trip.setCity(result.getString("city"));
                trip.setEgn(result.getString("personEgn"));
                trip.setArrival(result.getDate("arrival"));
                trip.setDeparture(result.getDate("departure"));
                list.add(trip);
            }

            return list;


        }catch (SQLException e){
            e.printStackTrace();
        }

        return null;

    }

    public void dropTable(){

        try {
            PreparedStatement drop = conn.prepareStatement("DROP TABLE trip");
            drop.execute();

        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public void updateTrip(Trip trip){

        try {

            PreparedStatement update = conn.prepareStatement("UPDATE trip " +
                    "SET city = ?, arrival = ?, departure = ?,  personEgn = ?" +
                    " WHERE id = ?");

            update.setString(1, trip.getCity());
            update.setDate(2, trip.getArrival());
            update.setDate(3, trip.getDeparture());
            update.setString(4, trip.getEgn());
            update.setInt(5, trip.getId());

            update.execute();

        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public void deleteTripById(int id){

        try {
            PreparedStatement delete = conn.prepareStatement("DELETE * FROM trip" +
                    " WHERE id = ?");
            delete.setInt(1, id);
            delete.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }


    }

    public Trip getTripById(int id){

        try {

            PreparedStatement select = conn.prepareStatement("SELECT * FROM trip" +
                    " WHERE id = ?");
            select.setInt(1, id);

            ResultSet result = select.executeQuery();

            Trip trip = new Trip();

            while (result.next()){

                trip.setId(result.getInt("id"));
                trip.setCity(result.getString("city"));
                trip.setEgn(result.getString("personEgn"));
                trip.setArrival(result.getDate("arrival"));
                trip.setDeparture(result.getDate("departure"));

            }

            return trip;

        }catch (SQLException e){
            e.printStackTrace();
        }

        return null;

    }

    public void createTripTable(){

        try {

            PreparedStatement create = conn.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS trip(" +
                            "id int NOT NULL AUTO_INCREMENT," +
                            "city varchar(255) NOT NULL," +
                            "arrival date NOT NULL," +
                            "departure date NOT NULL," +
                            "personEgn varchar(10) NOT NULL," +
                            "PRIMARY KEY(id)," +
                            "FOREIGN KEY (personEgn) REFERENCES person(egn) ON DELETE CASCADE ON UPDATE CASCADE)"
            );

            create.execute();

        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public void addTrip(Trip trip){

        try {
            PreparedStatement add = conn.prepareStatement("INSERT INTO trip(" +
                    "city, personEgn, arrival, departure)" +
                    " VALUES (?, ?, ?, ?)");

            add.setString(1, trip.getCity());
            add.setString(2, trip.getEgn());
            add.setDate(3, trip.getArrival());
            add.setDate(4, trip.getDeparture());

            add.execute();

        }catch (SQLException e){
            e.printStackTrace();
        }

    }

}
