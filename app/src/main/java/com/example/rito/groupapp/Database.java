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
 * @Completed    2018-07-08
 *
 * @since 2018-19-08
 * @Update : updated functionality which enables database to update the information of an user
 *
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


	//set methods------------------------------------------------------------------------------------------------------------------
	public void addRemoveCourse(String crn, String username, boolean val){
		DatabaseReference ref;

		//remove from: COURSE_ENROLLMENT/<crn>/ENROLLMENT/<username> = null
		String pathEnrollment = String.format(
				"COURSE_ENROLLMENT/%s/ENROLLMENT/%s", crn, username);

		ref = this.db.getReference(pathEnrollment);
		ref.setValue(val ? val : null);

		//remove from: STUDENT/<username>/registration/<crn> = null
		String pathRegistration = String.format(
				"STUDENT/%s/registration/%s", username, crn);

		ref = this.db.getReference(pathRegistration);
		ref.setValue(val ? val : null);


		//decrement curr by 1: COURSE_ENROLLMENT/<crn>/curr/<> = null
		//***NOTE curr field is not necessary as we can just get a
		// child count of ENROLLMENT HashMap

	}

	public void updateUser(User oldusr, User newusr){

		DatabaseReference refOld = this.db.getReference(oldusr.getPath());
		refOld.setValue(null);
		DatabaseReference refNew = this.db.getReference(newusr.getPath());
		refNew.setValue(newusr);
		MainActivity.currentUser = newusr;
	}

	public void readData(){

		//this.db.setValue("Hello, World!");

		System.out.println("READDATA METHOD");
		System.out.println(this.dbRef.toString());//.child("TERMS").toString());
		System.out.println("READDATA SUCCESS");
	}

}