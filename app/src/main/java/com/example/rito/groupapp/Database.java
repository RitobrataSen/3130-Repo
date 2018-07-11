/* HOW TO USE DATABASE CLASS

PART 1: modifications to build.gradle (module: app)
1) add the following to dependencies
    implementation 'com.android.support:appcompat-v7:28.0.0-alpha3'
    implementation 'com.google.firebase:firebase-core:16.0.1'
    implementation 'com.google.firebase:firebase-database:16.0.1'
    implementation 'com.google.firebase:firebase-crash:16.0.1'
    implementation 'com.google.firebase:firebase-auth:16.0.2'
    implementation 'com.google.firebase:firebase-messaging:11.8.0'

2) add the following to the end of the document
	apply plugin: 'com.google.gms.google-services'

PART 2: modifications to build.gradle (Project: ProjectName)
1) add the following to buildscript > repositories
	google() // Gradle 4.0+
	maven { url "https://maven.google.com" } // Gradle < 4.0

2) add the following to buildscript > dependencies (replace older versions)
	classpath 'com.android.tools.build:gradle:3.1.2'
    classpath 'com.google.gms:google-services:4.0.1'

3) add the following to allprojects > repositories
	google() // Gradle 4.0+
	maven { url "https://maven.google.com" } // Gradle < 4.0

PART 3: Sync new build.gradle scripts (icon in top-right)

PART 4: Firebase initialization and use
1) add the following import to an activity
	import com.google.firebase.FirebaseApp;

2) add the following code to an activity's onCreate method
	FirebaseApp.initializeApp(this);

3) Initialize Database class
	Database db = new Database();

4) Call Methods as needed
	db.methodName();
*/

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


	public void readData(){

		//this.db.setValue("Hello, World!");

		System.out.println("READDATA METHOD");
		System.out.println(this.dbRef.toString());//.child("TERMS").toString());
		System.out.println("READDATA SUCCESS");
	}

}