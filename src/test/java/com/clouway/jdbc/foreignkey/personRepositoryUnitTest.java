package com.clouway.jdbc.foreignkey;

import org.jmock.Expectations;
import org.jmock.Sequence;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class personRepositoryUnitTest {

    Person person = new Person("John", "9454256423", 20, "John@doe.com");

    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();

    Connection mockConn;
    PreparedStatement prst;
    PersonRepository repo;

    @Before
    public void setUp(){
        mockConn = context.mock(Connection.class);
        prst = context.mock(PreparedStatement.class);
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


}
