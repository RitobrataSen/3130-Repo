package com.example.rito.groupapp;

public class User {

    private String email;
    private int enrollment;
    private String password;
    private String username;

    public User(String eMail, int enroll, String pw, String user){
        email = eMail;
        enrollment = enroll;
        password = pw;
        username = user;
    }

    public User() {
    }

    public String getEmail() {
        return email;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public int getEnrollment() {
        return enrollment;
    }

    public void setEmail(String eMail) {
        email = eMail;
    }
    public void setUsername(String user) {
        username = user;
    }
    public void setPassword(String pw) {
        password = pw;
    }
    public void setEnrollment(int enrol) {
        enrollment = enrol;
    }

}
