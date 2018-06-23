package com.clouway.jdbc.threetable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

@SuppressWarnings("Duplicates")
public class Demo {

    public static void main(String[] args){

        AddressRepository addressRepo = AddressRepository.getInstance(getConnection());
        UserRepository userRepo = UserRepository.getInstance(getConnection());
        ContactRepository contactRepo = ContactRepository.getInstance(getConnection());

        addressRepo.createAddressTable();
        userRepo.createUserTable();
        contactRepo.createContactTable();

        List<User> userList = userRepo.getAll();
        List<Address> addresses = addressRepo.getAll();
        List<Contact> contactList = contactRepo.getAll();


    }

    public static Connection getConnection() {

        try {
            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/people?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=Europe/Sofia";
            String username = "user";
            String password = "password";
            Class.forName(driver);

            Connection conn = DriverManager.getConnection(url, username, password);
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;

    }

}
