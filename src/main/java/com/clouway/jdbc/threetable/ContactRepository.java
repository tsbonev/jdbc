package com.clouway.jdbc.threetable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContactRepository {

    private static ContactRepository instance;

    private static Connection conn;

    public static ContactRepository getInstance(Connection conn) {

        if (instance == null) instance = new ContactRepository(conn);

        instance.conn = conn;

        return instance;
    }

    public static void clearInstance() {
        instance = null;
    }

    private ContactRepository(Connection conn) {

        this.conn = conn;

    }

    public List<Contact> getAll() {

        try {

            PreparedStatement select = conn.prepareStatement("SELECT * FROM contact");
            ResultSet result = select.executeQuery();

            List<Contact> list = new ArrayList<>();

            while (result.next()) {

                Contact contact = new Contact();
                contact.setId(result.getInt("id"));
                contact.setPhone(result.getString("phone"));
                contact.setUserId(result.getInt("userId"));
                list.add(contact);
            }

            return list;


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

    public void createContactTable() {

        try {
            PreparedStatement create = conn.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS contact(" +
                            "id int NOT NULL AUTO_INCREMENT," +
                            "phone varchar(255) NOT NULL," +
                            "userId int NOT NULL," +
                            "PRIMARY KEY(id)," +
                            "FOREIGN KEY (userId) REFERENCES users(id) ON DELETE CASCADE)");
            create.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
