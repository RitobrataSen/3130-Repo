package com.example.rito.groupapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.*;
public class ReadCourses extends AppCompatActivity {
    private static final String TAG = "ReadCourses";
    private static final String EXTRA_COURSE_KEY = "course_key";

    private String mCourseKey;

    private DatabaseReference mCourseReference;
    private ValueEventListener mCourseListener;
    private TextView mCourseCode;
    private TextView mCourseName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_load);

        mCourseKey = getIntent().getStringExtra(EXTRA_COURSE_KEY);
        if(mCourseKey == null){
            throw  new IllegalArgumentException("Must pass EXTRA_COURSE_KEY");
        }

        //Initialize Database
        mCourseReference = FirebaseDatabase.getInstance().getReference().child("courses").child(mCourseKey);

        //Initialize text view
        mCourseCode = findViewById(R.id.course_code);
        mCourseName = findViewById(R.id.course_name);
    }

    @Override
    protected void onStart() {
        super.onStart();

        //Event listener for loading data from firebase.
        ValueEventListener courseListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Get the course object and use the values to update the UI
                Courses course = dataSnapshot.getValue(Courses.class);
                //Setting the textviews from the object values
                mCourseCode.setText(course.GetCode());
                mCourseName.setText(course.GetCname());
            }
        //onCancelled method for cancel the loading process
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadCourse: onCancelled", databaseError.toException());
                Toast.makeText(ReadCourses.this, "Failed to load course.", Toast.LENGTH_SHORT).show();
            }
        };
        mCourseReference.addValueEventListener(courseListener);
        mCourseListener = courseListener;
    }

    @Override
    public void onStop(){
        super.onStop();
        if(mCourseListener != null)
            mCourseReference.removeEventListener(mCourseListener);
    }
}
