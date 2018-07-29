package com.example.rito.groupapp;

import android.util.Log;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.ArrayList;
import java.io.Serializable;

/**
 * User class is used to store information about a user.
 * In MainActivity, currentUser is of type User, and is used to populate
 * the application views.
 *
 * @author  Ritobrata, Yuhao Hu, Gobii
 * @completed   07-09-18
 *
 * @since
 * added a method which returns a string for the path to which the object is situated.
 */

@IgnoreExtraProperties
public class User implements Serializable {

    //fields
    //private String uid;
    private String email;
    private String username;
    private String password;
    private HashMap<String, Boolean> registration;

    //constructors
    public User() {
        this.registration = new HashMap<String, Boolean>();
    }

    public User(String email, String username,
                   String password, HashMap<String, Boolean> registration) {

        this.email = email;
        this.username = username;
        this.password = password;
        this.registration = new HashMap<>();
        this.registration.putAll(registration);
    }

    //methods
    public String getEmail(){
        return this.email;
    }
    public String getUsername(){
        return this.username;
    }
    public String getPassword(){
        return this.password;
    }
    public HashMap<String, Boolean> getRegistration(){
        return this.registration;
    }

    public void setEmail(String email){
        this.email = email;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public void setPassword(String password){
        this.password = password;
    }

    public void setRegistration(HashMap<String, Boolean> registration){
        //to add/ remove individual pairs get entire hash map first then call
        //hash map functions: hashMap.put(k, v) or hashMap.remove(k)
        this.registration = new HashMap<>();
        this.registration.putAll(registration);
    }

    public void updateCurrentUser(User u){
        MainActivity.currentUser = u;
    }

    public String toString(){
        return String.format("%s %s %s", this.email, this.username, this.password);
    }
    public String createPath(){
        return String.format(String.format("STUDENT/%s", this.username));
    }


    public boolean equals(User u){
		boolean flag = true;

		for (String x : this.registration.keySet()){
			if (!(u.getRegistration().containsKey(x))){
				Log.d("debug.print", x + " is in U but not in CURRENTUSER");
				return false;
			}
		}

		for (String x : u.getRegistration().keySet()){
			if (!((this.registration.containsKey(x)))){
				Log.d("debug.print", x + " is in CURRENTUSER but not in U");
				return false;
			}
		}

		if (this.email.equalsIgnoreCase(u.getEmail()) &&
				this.username.equalsIgnoreCase(u.getUsername()) &&
				this.password.equalsIgnoreCase(u.getPassword())
		){
			Log.d("debug.print", "U = CURRENTUSER");
			return true;
		}

		Log.d("debug.print", "U != CURRENTUSER");
		return false;

    }

    @Exclude
    public HashMap<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        //result.put("uid", this.uid);
        result.put("email", this.email);
        result.put("username", this.username);
        result.put("password", this.password);
        result.put("registration", this.registration);
        return result;
    }
}