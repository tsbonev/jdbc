package com.clouway;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Main {

    static String userName = "springuser";
    static String password = "password";
    static String serverName = "localhost";
    static int portNumber = 4444;

    static Connection getConnection() throws SQLException {

        Connection conn = null;
        Properties connectionProps = new Properties();
        connectionProps.put("springuser", userName);
        connectionProps.put("password",password);

        conn = DriverManager.getConnection(
                "jdbc:mysql://" +
                        serverName +
                        ":" + portNumber + "/",
                connectionProps);
        System.out.println("Connected to database");
        return conn;
    }

    public static void main(String[] args) throws SQLException {

        Connection conn = getConnection();
    }
}
