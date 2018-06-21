package com.clouway.jdbc.foreignkey;

public class Person {

    private int id;
    private String fname;
    private String egn;
    private int age;
    private String email;

    public Person(){}

    public Person(String fname, String egn, int age, String email) {
        this.fname = fname;
        this.egn = egn;
        this.age = age;
        this.email = email;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getEgn() {
        return egn;
    }

    public void setEgn(String egn) {
        this.egn = egn;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
