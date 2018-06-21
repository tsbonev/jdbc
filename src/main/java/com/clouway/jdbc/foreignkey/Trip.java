package com.clouway.jdbc.foreignkey;

import java.sql.Date;

public class Trip {

    private int id;
    private String egn;
    private String city;
    private Date arrival;
    private Date departure;

    public Trip(){}

    public Trip(String egn, String city, Date arrival, Date departure) {
        this.egn = egn;
        this.city = city;
        this.arrival = arrival;
        this.departure = departure;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getEgn() {
        return egn;
    }

    public void setEgn(String egn) {
        this.egn = egn;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Date getArrival() {
        return arrival;
    }

    public void setArrival(Date arrival) {
        this.arrival = arrival;
    }

    public Date getDeparture() {
        return departure;
    }

    public void setDeparture(Date departure) {
        this.departure = departure;
    }
}
