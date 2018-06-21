package com.clouway.jdbc.foreignkey;

import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class connectionTest {

    @Test
    public void testConnection() throws SQLException {

        Connection conn = Demo.getConnection();

        assertThat(conn, is(notNullValue()));
        conn.close();

    }

}
