package com.clouway.jdbc.threetable;

import com.clouway.jdbc.foreignkey.Trip;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    private static UserRepository instance;

    private static Connection conn;

    public static UserRepository getInstance(Connection conn) {

        if (instance == null) instance = new UserRepository(conn);

        instance.conn = conn;

        return instance;
    }

    public static void clearInstance() {
        instance = null;
    }

    private UserRepository(Connection conn) {

        this.conn = conn;

    }

    public List<User> getAll() {

        try {

            PreparedStatement select = conn.prepareStatement("SELECT * FROM users");
            ResultSet result = select.executeQuery();

            List<User> list = new ArrayList<>();

            while (result.next()) {

                User user = new User();
                user.setId(result.getInt("id"));
                user.setFname(result.getString("fname"));
                user.setAddressId(result.getInt("address"));
                list.add(user);
            }

            return list;


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

    public void createUserTable() {

        try {
            PreparedStatement create = conn.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS users(" +
                            "id int NOT NULL AUTO_INCREMENT," +
                            "address int NOT NULL," +
                            "fname varchar(255) NOT NULL," +
                            "PRIMARY KEY(id)," +
                            "FOREIGN KEY(address) REFERENCES address(id) ON DELETE CASCADE)");
            create.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
