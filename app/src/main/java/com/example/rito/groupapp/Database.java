package com.example.rito.groupapp;

import android.app.Application;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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
 * @since    2018-07-08
 */

public class Database extends Application {
	//fields
	public FirebaseDatabase db;
	public DatabaseReference dbRef;

	//constructors
	public Database(){
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


	//set methods------------------------------------------------------------------------------------------------------------------
	public void addRemoveCourse(String crn, String username, boolean val){
		DatabaseReference ref;

		//set: CRN_DATA/<crn>/enrollment/<username> = null
		String pathEnrollment = String.format(
				"CRN_DATA/%s/enrollment/%s", crn, username);

		ref = this.db.getReference(pathEnrollment);
		ref.setValue(val ? val : null);

		//set: STUDENT/<username>/registration/<crn> = null
		String pathRegistration = String.format(
				"STUDENT/%s/registration/%s", username, crn);

		ref = this.db.getReference(pathRegistration);
		ref.setValue(val ? val : null);

		//refresh the currentUser variable
		updateCurrentUser(username);

	}
	public void addUser(String email, String username, String password){
		boolean verify = false;
		DatabaseReference ref;
		String pathUsername;

		HashMap<String, Boolean> hm = new HashMap<>();
		User user = new User(email, username, password, hm);

		verify = verifyUser(user);
		if (verify){
			pathUsername = user.createPath();
			ref = this.db.getReference(pathUsername);
			ref.setValue(user);
			updateCurrentUser(username);
		} else {
			Log.d("debug.print", "null email, username, and or password");
			Log.d("debug.print", "NEW USER: " + user.toString());
			Log.d("debug.print", String.format("VERIFY: %s", verify));
		}
	}

	public void updateUser(User oldusr, User newusr){

		boolean verify = verifyUser(newusr);
		if (verify && oldusr.compareRegistration(newusr)){
			DatabaseReference refOld = this.db.getReference(oldusr.createPath());
			refOld.setValue(null);
			DatabaseReference refNew = this.db.getReference(newusr.createPath());
			refNew.setValue(newusr);
			updateCurrentUser(newusr.getUsername());
		} else {
			Log.d("debug.print", "null email, username, and or password");
			Log.d("debug.print", "NEW USER: " + newusr.toString());
			Log.d("debug.print", "OLD USER: " + oldusr.toString());
			Log.d("debug.print", String.format("COMPARE REGISTRATION: %s", oldusr
					.compareRegistration(newusr)));
			Log.d("debug.print", String.format("VERIFY: %s", verify));
		}
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

	public boolean verifyUser(User user) {
		return (!((user.getEmail() == null) ||
				(user.getEmail().equals("")) ||
				(user.getUsername() == null) ||
				(user.getUsername().equals("")) ||
				(user.getPassword() == null) ||
				(user.getPassword().equals(""))
		));
	}
}