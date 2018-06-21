package com.clouway.jdbc.foreignkey;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class tableCreationAndDeletionTest {

    Connection conn;
    TripRepository tripRepo;
    PersonRepository personRepo;

    @Before
    public void setUp(){
        conn = Demo.getConnection();
        tripRepo = TripRepository.instanceOf(conn);
        personRepo = PersonRepository.instanceOf(conn);
    }

    @After
    public void cleanUp() throws SQLException {
        conn.close();
        TripRepository.clearInstance();
        PersonRepository.clearInstance();
    }

    @Test
    public void createTables() throws SQLException {

        DatabaseMetaData dbm = conn.getMetaData();

        personRepo.createPersonTable();
        tripRepo.createTripTable();

        ResultSet tables = dbm.getTables(null,null, "person", null);

        assertThat(tables.next(), is(true));

        tables = dbm.getTables(null, null, "trip", null);

        assertThat(tables.next(), is(true));

    }

    @Test
    public void dropTables() throws SQLException {

        createTables();

        DatabaseMetaData dbm = conn.getMetaData();

        tripRepo.dropTable();
        personRepo.dropTable();

        ResultSet tables = dbm.getTables(null,null, "trip", null);
        assertThat(tables.next(), is(false));

        tables = dbm.getTables(null,null, "person", null);
        assertThat(tables.next(), is(false));

    }

}
