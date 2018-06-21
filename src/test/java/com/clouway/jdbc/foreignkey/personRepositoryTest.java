package com.clouway.jdbc.foreignkey;

import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class personRepositoryTest {

    Person john = new Person("John", "9454256423", 20, "John@doe.com");
    Person bill = new Person("Bill", "9351235432", 35, "Bill@doe.com");
    Person steve = new Person("Steve", "9252131453", 55, "Steve@aol.com");

    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();
    Connection mockConn = context.mock(Connection.class);
    PreparedStatement prst = context.mock(PreparedStatement.class);

    PersonRepository repo = PersonRepository.instanceOf(mockConn);

    @Test
    public void addPerson() throws SQLException {

        context.checking(new Expectations(){{
            oneOf(mockConn).prepareStatement(with(any(String.class))); will(returnValue(prst));
            oneOf(prst).setString(1, john.getFname());
            oneOf(prst).setString(2, john.getEmail());
            oneOf(prst).setString(3, john.getEgn());
            oneOf(prst).setInt(4, john.getAge());
            oneOf(prst).execute();

        }});

        repo.addPerson(john);

    }

    @Test
    public void updatePerson() throws SQLException {

        context.checking(new Expectations(){{
            oneOf(mockConn).prepareStatement(with(any(String.class))); will(returnValue(prst));
            oneOf(prst).setString(1, john.getFname());
            oneOf(prst).setString(2, john.getEgn());
            oneOf(prst).setInt(3, john.getAge());
            oneOf(prst).setString(4, john.getEmail());
            oneOf(prst).setInt(5, john.getId());
            oneOf(prst).execute();
        }});


        repo.updatePerson(john);

    }

    @Test
    public void deletePersonById() throws SQLException{

        context.checking(new Expectations(){{
            oneOf(mockConn).prepareStatement(with(any(String.class))); will(returnValue(prst));
            oneOf(prst).setInt(1, john.getId());
            oneOf(prst).execute();

        }});

        repo.deletePersonById(john.getId());

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
