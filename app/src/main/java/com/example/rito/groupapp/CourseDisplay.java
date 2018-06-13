package com.example.rito.groupapp;

import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.*;

import static android.content.ContentValues.TAG;

public class CourseDisplay {

    private DatabaseReference mDatabase;
    private ValueEventListener mCourseListener;
    private Courses mCourse;

    public void onClick () {
        super.onClick();

        ValueEventListener CourseListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mCourse = dataSnapshot.getValue(Courses.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPostL:OnCancelled", databaseError.toException());
                Toast.makeText(CourseDisplay.this, "Failed to load courses.");
            }
        };
        mDatabase.addValueEventListener(CourseListener);
        mCourseListener = CourseListener;
    }
}
