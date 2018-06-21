package com.clouway.jdbc.foreignkey;

import org.jmock.Expectations;
import org.jmock.Sequence;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class repositoryUnitTest {

    Person person = new Person("John", "9454256423", 20, "John@doe.com");

    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();

    Connection mockConn;
    PreparedStatement prst;
    PersonRepository repo;
    ResultSet resultSet;

    @Before
    public void setUp(){
        mockConn = context.mock(Connection.class);
        prst = context.mock(PreparedStatement.class);
        resultSet = context.mock(ResultSet.class);
        repo = PersonRepository.instanceOf(mockConn);
    }

    @After
    public void cleanUp(){
        PersonRepository.clearInstance();
    }

    @Test
    public void addPerson() throws SQLException {

        context.checking(new Expectations(){{
            oneOf(mockConn).prepareStatement(with(any(String.class))); will(returnValue(prst));
            oneOf(prst).setString(1, person.getFname());
            oneOf(prst).setString(2, person.getEmail());
            oneOf(prst).setString(3, person.getEgn());
            oneOf(prst).setInt(4, person.getAge());
            oneOf(prst).execute();

        }});

        repo.addPerson(person);

    }

    @Test
    public void updatePerson() throws SQLException {

        context.checking(new Expectations(){{
            oneOf(mockConn).prepareStatement(with(any(String.class))); will(returnValue(prst));
            oneOf(prst).setString(1, person.getFname());
            oneOf(prst).setString(2, person.getEgn());
            oneOf(prst).setInt(3, person.getAge());
            oneOf(prst).setString(4, person.getEmail());
            oneOf(prst).setInt(5, person.getId());
            oneOf(prst).execute();
        }});


        repo.updatePerson(person);

    }

    @Test
    public void deletePersonById() throws SQLException{

        context.checking(new Expectations(){{
            oneOf(mockConn).prepareStatement(with(any(String.class))); will(returnValue(prst));
            oneOf(prst).setInt(1, person.getId());
            oneOf(prst).execute();

        }});

        repo.deletePersonById(person.getId());

    }

    @Test
    public void dropTable() throws SQLException{

        context.checking(new Expectations(){{
            oneOf(mockConn).prepareStatement(with(any(String.class))); will(returnValue(prst));
            oneOf(prst).execute();

        }});

        repo.dropTable();

    }

    @Test
    public void dropContent() throws SQLException{

        context.checking(new Expectations(){{
            oneOf(mockConn).prepareStatement(with(any(String.class))); will(returnValue(prst));
            oneOf(prst).execute();

        }});

        repo.dropTableContent();

    }

    @Test
    public void getAll() throws SQLException{

        List<Person> personList = new ArrayList<>();
        personList.add(person);

        context.checking(new Expectations(){{

            oneOf(mockConn).prepareStatement(with(any(String.class))); will(returnValue(prst));
            oneOf(prst).executeQuery(); will(returnValue(resultSet));
            oneOf(resultSet).next(); will(returnValue(true));
            oneOf(resultSet).getInt("id"); will(returnValue(person.getId()));
            oneOf(resultSet).getInt("age"); will(returnValue(person.getAge()));
            oneOf(resultSet).getString("fname"); will(returnValue(person.getFname()));
            oneOf(resultSet).getString("email"); will(returnValue(person.getEmail()));
            oneOf(resultSet).getString("egn"); will(returnValue(person.getEgn()));
            oneOf(resultSet).next(); will(returnValue(false));

        }});

        List<Person> resultList = repo.getAll();

        assertThat(resultList.get(0).getFname(), is(personList.get(0).getFname()));

    }

    @Test
    public void getById() throws SQLException {


        context.checking(new Expectations(){{

                oneOf(mockConn).prepareStatement(with(any(String.class))); will(returnValue(prst));
                oneOf(prst).setInt(1, person.getId());
                oneOf(prst).executeQuery(); will(returnValue(resultSet));
                oneOf(resultSet).next(); will(returnValue(true));
                oneOf(resultSet).getInt("id"); will(returnValue(person.getId()));
                oneOf(resultSet).getInt("age"); will(returnValue(person.getAge()));
                oneOf(resultSet).getString("fname"); will(returnValue(person.getFname()));
                oneOf(resultSet).getString("email"); will(returnValue(person.getEmail()));
                oneOf(resultSet).getString("egn"); will(returnValue(person.getEgn()));
                oneOf(resultSet).next(); will(returnValue(false));

            }});

        Person foundPerson = repo.getPersonById(0);
        assertThat(foundPerson.getFname().equals(person.getFname()), is(true));

    }


    @Test
    public void createTable() throws SQLException{

        context.checking(new Expectations(){{

            oneOf(mockConn).prepareStatement(with(any(String.class)));
            will(returnValue(prst));
            oneOf(prst).execute();

        }});

        repo.createPersonTable();

    }



}
