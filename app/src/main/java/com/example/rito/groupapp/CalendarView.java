package com.example.rito.groupapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CalendarView extends AppCompatActivity {

    public DatabaseReference courseReference;
    ArrayList<String> temp = new ArrayList<>();
    Courses currentCourse;
    TextView monday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_view);

        monday = findViewById(R.id.monday);

        courseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://group-10-9598f.firebaseio.com").child("TERMS").child("201910").child("SUBJECTS").child("CSCI").child("COURSES");
        courseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot csciCourseSnapshot : snapshot.getChildren()) {
                        if (csciCourseSnapshot.child("10677").child("course_code").getValue().toString().equals("CSCI1105")) {
                            //(String num, String name, String Sd, String Ed, String Ins, String max)
                            //currentCourse = new Courses(csciCourseSnapshot.child("10677").getValue().toString(), csciCourseSnapshot.child("course_name").getValue().toString(),
                            //                            csciCourseSnapshot.child("start_date").getValue().toString(), csciCourseSnapshot.child("end_date").getValue().toString(),
                            //                            csciCourseSnapshot.child("instructor").getValue().toString(), csciCourseSnapshot.child("max").getValue().toString());
                            currentCourse = new Courses();
                            currentCourse.SetNam(csciCourseSnapshot.child("10677").child("course_name").getValue().toString());
                            monday.setText(currentCourse.GetCname());
                            //System.out.println(currentCourse.GetCname());
                        }
                        /*
                        if (csciCourseSnapshot.getValue().toString().equals("")) {

                        }
                        if (csciCourseSnapshot.getValue().toString().equals("")) {

                        }
                        if (csciCourseSnapshot.getValue().toString().equals("")) {

                        }
                        if (csciCourseSnapshot.getValue().toString().equals("")) {

                        }
                        */
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("The read failed: ", databaseError.getMessage());
            }
        });

    }
}
