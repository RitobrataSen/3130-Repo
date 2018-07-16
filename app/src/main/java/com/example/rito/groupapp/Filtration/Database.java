package com.example.rito.groupapp.Filtration;

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
    public void setEnrollment(int term_code, String subject_code, String course_code, int crn, int user_id){
    }
    //in TERMS > term_code > SUBJECTS > subject_code > COURSES > course_code > CRN > crn > enrollment > student_id
    public void setRegistration(int term_code, String subject_code, String course_code, int crn,
                                int user_id){
    }
    //save to the student database
    //new student
    //edit student
    //delete students


    //delete methods---------------------------------------------------------------------------------------------------------------
    public void deleteEnrollment(int term_code, String subject_code, String course_code, int crn, int user_id){
    }
    public void deleteRegistration(int term_code, String subject_code, String course_code, int crn,
                                   int user_id){
    }

    //search methods---------------------------------------------------------------------------------------------------------------
    public void findCRN(ArrayList<Integer> crn){
    }
    public void findCRN(int crn){
    }

    public void findCourse(ArrayList<String> course_code){
    }
    public void findCourse(String course_code){
    }

    public void findSubjects(ArrayList<String> subject_code){
    }
    public void findSubjects(String subject_code){
    }

    public void findTerms(ArrayList<String> term_code){
    }
    public void findTerms(String term_code){
    }


    public void readData(){

        //this.db.setValue("Hello, World!");

        System.out.println("READDATA METHOD");
        System.out.println(this.dbRef.toString());//.child("TERMS").toString());
        System.out.println("READDATA SUCCESS");
    }

}