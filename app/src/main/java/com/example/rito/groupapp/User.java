package com.example.rito.groupapp;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import java.util.HashMap;
import java.util.ArrayList;

@IgnoreExtraProperties
public class User {

    //fields
    private String uid;
    private String email;
    private String username;
    private String password;
    private HashMap<String, Boolean> registration;

    //constructors
    public User() {
    }

    public User(String u, String e, String un, String p, HashMap<String, Boolean> re) {
        this.uid = u;
        this.email = e;
        this.username = un;
        this.password = p;
        this.registration = new HashMap<>();
        this.registration.putAll(re);
    }

    //methods

    public String getUid() {return this.uid;}
    public String getEmail() {return this.email;}
    public String getUsername() {return this.username;}
    public String getPassword() {return this.password;}
    public HashMap<String, Boolean> getRegistration(){return this.registration;}

    public void setUid(String u) {this.uid = u;}
    public void setEmail(String e) {this.email = e;}
    public void setUsername(String un) {this.username = un;}
    public void setPassword(String p) {this.password = p;}
    public void setRegistration(HashMap<String, Boolean> re){
        //to add/ remove individual pairs get entire hash map first then call
        //hash map functions: hashMap.put(k, v) or hashMap.remove(k)
        this.registration = new HashMap<>();
        this.registration.putAll(re);
    }

    @Exclude
    public HashMap<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", this.uid);
        result.put("email", this.email);
        result.put("username", this.username);
        result.put("password", this.password);
        result.put("registration", this.registration);
        return result;
    }
}