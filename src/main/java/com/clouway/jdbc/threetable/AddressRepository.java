package com.clouway.jdbc.threetable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddressRepository {

    private static AddressRepository instance;

    private static Connection conn;

    public static AddressRepository getInstance(Connection conn) {

        if (instance == null) instance = new AddressRepository(conn);

        instance.conn = conn;

        return instance;
    }

    public static void clearInstance() {
        instance = null;
    }

    private AddressRepository(Connection conn) {

        this.conn = conn;

    }

    public List<Address> getAll() {

        try {

            PreparedStatement select = conn.prepareStatement("SELECT * FROM address");
            ResultSet result = select.executeQuery();

            List<Address> list = new ArrayList<>();

            while (result.next()) {

                Address address = new Address();
                address.setId(result.getInt("id"));
                address.setCity(result.getString("city"));
                address.setStreet(result.getString("street"));
                list.add(address);
            }

            return list;


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

    public void createAddressTable() {

        try {
            PreparedStatement create = conn.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS address(" +
                            "id int NOT NULL AUTO_INCREMENT," +
                            "city varchar(255) NOT NULL," +
                            "street varchar(255) NOT NULL," +
                            "PRIMARY KEY(id))");
            create.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
