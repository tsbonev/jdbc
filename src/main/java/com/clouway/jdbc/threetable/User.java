package com.clouway.jdbc.threetable;

public class User {

    private int id;
    private String fname;
    private int addressId;

    public User(){}

    public User(String fname, int addressId) {
        this.id = id;
        this.fname = fname;
        this.addressId = addressId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }
}
