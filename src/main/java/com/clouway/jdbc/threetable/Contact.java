package com.clouway.jdbc.threetable;

public class Contact {

    private int id;
    private String phone;
    private int userId;

    public Contact(){}

    public Contact(String phone, int userId) {
        this.phone = phone;
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
