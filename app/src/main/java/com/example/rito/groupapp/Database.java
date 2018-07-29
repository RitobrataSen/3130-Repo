package com.example.rito.groupapp;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 This class is used to create a firebase reference.
 This class contains the standard attributes, getters and setters.
 This class also contains a method that allows for easy add/ delete
 of courses.
 *
 * @author   Gobii, Rito, Yuhao
 * @Completed   2018-07-08
 *
 * @Since 2018-07-17
 * Added a method that allowed the database to update any user information
 */

public class Database extends Application {
	//fields
	public FirebaseDatabase db;
	public DatabaseReference dbRef;

	//constructors
	public Database (){

		FirebaseDatabase fb;
		fb = FirebaseDatabase.getInstance();
		this.db = fb;
		DatabaseReference ref;
		ref = fb.getReference();
		this.dbRef = ref;

	}

	public Database(String refPath){

		FirebaseDatabase fb;
		fb = FirebaseDatabase.getInstance();
		this.db = fb;
		DatabaseReference ref;
		ref = fb.getReference(refPath);
		this.dbRef = ref;

	}

	public FirebaseDatabase getDb() {
		return this.db;
	}

	public DatabaseReference getDbRef() {
		return this.dbRef;
	}

	public void setDb(FirebaseDatabase db) {
		this.db = db;
	}

	public void setDbRef(DatabaseReference dbRef) {
		this.dbRef = dbRef;
	}


	public boolean equals (Database db){

		return (
				this.db.getReference().toString().equals(db.getDb().getReference().toString())
				&& this.dbRef.toString().equals(db.getDbRef().toString())
				);
	}


	public void addRemoveCourse(String crn, String username, boolean val){
		DatabaseReference ref;

		//remove from: COURSE_ENROLLMENT/<crn>/ENROLLMENT/<username> = null
		String pathEnrollment = String.format(
				"CRN_DATA/%s/enrollment/%s", crn, username);

		ref = this.db.getReference(pathEnrollment);
		ref.setValue(val ? val : null);

		//remove from: STUDENT/<username>/registration/<crn> = null
		String pathRegistration = String.format(
				"STUDENT/%s/registration/%s", username, crn);

		ref = this.db.getReference(pathRegistration);
		ref.setValue(val ? val : null);

		//refresh the currentUser variable
		updateCurrentUser(username);
	}

	public void addUser(String email, String username, String password){
		int result = -1;
		/*
		0 = success
		1 = username exists
		2 = email exists
		 */
		DatabaseReference ref;

		//check if username exists
		String pathUsername = String.format(
				"STUDENT/%s", username);

		Log.d("debug.print", "UPE: " +
				username + "," + password + "," + email);

		HashMap<String, Boolean> hm = new HashMap<>();
		User user = new User(email, username, password, hm);
		ref = this.db.getReference(pathUsername);
		ref.setValue(user);

		updateCurrentUser(username);
	}

	public void updateCurrentUser(String username){
		//refresh the currentUser variable
		DatabaseReference ref = this.db.getReference("STUDENT/" + username);
		ref.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				User user = (User) dataSnapshot.getValue(User.class);
				MainActivity.currentUser = user;
			}

			@Override
			public void onCancelled(DatabaseError databaseError) {

			}
		});
	}
	public void updateUser(User oldusr, User newusr){
		DatabaseReference refOld = this.db.getReference(oldusr.getPath());
		refOld.setValue(null);
		DatabaseReference refNew = this.db.getReference(newusr.getPath());
		refNew.setValue(newusr);
		MainActivity.currentUser = newusr;
	}

}