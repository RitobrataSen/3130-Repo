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
    private int courseListSize = 4;
    public TextView monday[] = new TextView[courseListSize];
    public TextView tuesday[] = new TextView[courseListSize];
    public TextView wednesday[] = new TextView[courseListSize];
    public TextView thursday[] = new TextView[courseListSize];
    public TextView friday[] = new TextView[courseListSize];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_view);

        populateTextViewLists();

        Courses c = new Courses("CSCI3110", "Computer Science Class", "", "", "", "");
        c.SetStime("3:30");
        c.SetEtime("4:30");

        displayCourse(monday[1],c);




//        courseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://group-10-9598f.firebaseio.com").child("TERMS").child("201910").child("SUBJECTS").child("CSCI").child("COURSES");
//        courseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//
//                if (snapshot.exists()) {
//                    for (DataSnapshot csciCourseSnapshot : snapshot.getChildren()) {
//                        if (csciCourseSnapshot.child("10677").child("course_code").getValue().toString().equals("CSCI1105")) {
//                            //(String num, String name, String Sd, String Ed, String Ins, String max)
//                            //currentCourse = new Courses(csciCourseSnapshot.child("10677").getValue().toString(), csciCourseSnapshot.child("course_name").getValue().toString(),
//                            //                            csciCourseSnapshot.child("start_date").getValue().toString(), csciCourseSnapshot.child("end_date").getValue().toString(),
//                            //                            csciCourseSnapshot.child("instructor").getValue().toString(), csciCourseSnapshot.child("max").getValue().toString());
//                            currentCourse = new Courses();
//                            currentCourse.SetNam(csciCourseSnapshot.child("10677").child("course_name").getValue().toString());
//
//
//
//                        }
//                    }
//               }
//           }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.e("The read failed: ", databaseError.getMessage());
//            }
//        });

    }

    public void displayCourse(TextView selected, Courses course){
        selected.setText(course.GetCname()+"\n"+course.GetCode()+"\n"+course.GetSt()+"-"+course.GetEt());
    }

    public void populateTextViewLists(){
        monday[0] = findViewById(R.id.m_body);
        monday[1] = findViewById(R.id.m1_body);
        monday[2] = findViewById(R.id.m2_body);
        monday[3] = findViewById(R.id.m3_body);

        tuesday[0] = findViewById(R.id.t_body);
        tuesday[1] = findViewById(R.id.t1_body);
        tuesday[2] = findViewById(R.id.t2_body);
        tuesday[3] = findViewById(R.id.t3_body);

        wednesday[0] = findViewById(R.id.w_body);
        wednesday[1] = findViewById(R.id.w1_body);
        wednesday[2] = findViewById(R.id.w2_body);
        wednesday[3] = findViewById(R.id.w3_body);

        thursday[0] = findViewById(R.id.r_body);
        thursday[1] = findViewById(R.id.r1_body);
        thursday[2] = findViewById(R.id.r2_body);
        thursday[3] = findViewById(R.id.r3_body);

        friday[0] = findViewById(R.id.f_body);
        friday[1] = findViewById(R.id.f1_body);
        friday[2] = findViewById(R.id.f2_body);
        friday[3] = findViewById(R.id.f3_body);
    }
}
