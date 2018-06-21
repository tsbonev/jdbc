package com.clouway.jdbc.foreignkey;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("Duplicates")
public class PersonRepository {

    private static Connection conn;

    private static PersonRepository instance;

    public static PersonRepository instanceOf(Connection conn){
        if(instance == null) instance = new PersonRepository(conn);
        return instance;
    }

    public static void clearInstance(){
        instance = null;
    }

    public PersonRepository(Connection conn){
        this.conn = conn;
    }

    public List<Person> getAll(){

        try {

            PreparedStatement select = conn.prepareStatement("SELECT * FROM person");
            ResultSet result = select.executeQuery();

            List<Person> list = new ArrayList<Person>();

            while (result.next()){

                Person person = new Person();
                person.setId(result.getInt("id"));
                person.setAge(result.getInt("age"));
                person.setFname(result.getString("fname"));
                person.setEmail(result.getString("email"));
                person.setEgn(result.getString("egn"));
                list.add(person);
            }

            return list;


        }catch (SQLException e){
            e.printStackTrace();
        }

        return null;

    }

    public void dropTable(){

        try {
            PreparedStatement drop = conn.prepareStatement("DROP TABLE person");
            drop.execute();

        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public void dropTableContent(){

        try {
            PreparedStatement delete = conn.prepareStatement("DELETE FROM person");
            delete.execute();

        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public List<Person> getPeopleByName(String name){

        return this.getAll()
                .stream()
                .filter(p -> p.getFname().startsWith(name))
                .collect(Collectors.toList());

    }

    public void updatePerson(Person person){

        try {

            PreparedStatement update = conn.prepareStatement("UPDATE person " +
                    "SET fname = ?, egn = ?, age = ?, email = ?" +
                    " WHERE id = ?");

            update.setString(1, person.getFname());
            update.setString(2, person.getEgn());
            update.setInt(3, person.getAge());
            update.setString(4, person.getEmail());
            update.setInt(5, person.getId());

            update.execute();

        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public void deletePersonById(int id){

        try {
            PreparedStatement delete = conn.prepareStatement("DELETE * FROM person" +
                    " WHERE id = ?");
            delete.setInt(1, id);
            delete.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }


    }

    public Person getPersonById(int id){

        try {

            PreparedStatement select = conn.prepareStatement("SELECT * FROM person" +
                    " WHERE id = ?");
            select.setInt(1, id);

            ResultSet result = select.executeQuery();

            Person person = new Person();

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

    public void addPerson(Person person){

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

    public void createPersonTable() {

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

}
