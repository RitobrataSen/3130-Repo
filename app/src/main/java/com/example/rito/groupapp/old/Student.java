package com.example.rito.groupapp.old;
//package com.example.rito.groupapp;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Student class is used to unload firebase data using firebase ui adapter
 * to process. Holds the same structure as firebase nodes under STUDENT.
 *
 * @author   Gobii, Rito, Yuhao
 * @since    2018-07-08
 */


@IgnoreExtraProperties
public class Student implements Serializable {

	//fields
	//private String uid;
	private String email;
	private String username;
	private String password;
	private HashMap<String, Boolean> registration;

	//constructors
	public Student() {
	}

	public Student(/*String uid,*/ String email, String username,
				   String password, HashMap<String, Boolean> registration) {
		//this.uid = uid;
		this.email = email;
		this.username = username;
		this.password = password;
		this.registration = new HashMap<>();
		this.registration.putAll(registration);
	}

	//methods
	/*
	public String getUid(){
		return this.uid;
	}
	*/
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

	/*
	public void setUid(String uid){
		this.uid = uid;
	}
	*/
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

	@Override
	public String toString(){
		//return String.format("(TermCode: %s, TermDescription: %s)", term_code, term_description);
		return String.format("%s\n%s\n%s", this.username, this.email, this.registration);
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