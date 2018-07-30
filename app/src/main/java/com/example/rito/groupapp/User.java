package com.example.rito.groupapp;

import android.util.Log;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import java.util.HashMap;
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
    private String email;
    private String username;
    private String password;
    private HashMap<String, Boolean> registration;

    //constructors
    public User() {
        this.registration = new HashMap<>();
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

    public String toString(){
        return String.format("%s %s %s", this.email, this.username, this.password);
    }

    public String createPath() {
		return String.format(String.format("STUDENT/%s", this.username));
	}

    public boolean compareRegistration(User u){
		//we cant use equals unless we check if it is null first
    	if (this.registration  == u.getRegistration()){
			// checks if 2 hashmaps are both null or both store the same address
  			return true;
		} else {
			//use the equals method to check if both hashmaps are the same
			return (this.registration.equals(u.getRegistration()));
		}
	}

    public boolean equals(User u){
		if (this.email.equalsIgnoreCase(u.getEmail()) &&
				this.username.equalsIgnoreCase(u.getUsername()) &&
				this.password.equals(u.getPassword()) &&
				compareRegistration(u)
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
        result.put("email", this.email);
        result.put("username", this.username);
        result.put("password", this.password);
        result.put("registration", this.registration);
        return result;
    }
}