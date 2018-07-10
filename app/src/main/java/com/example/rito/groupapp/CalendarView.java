package com.example.rito.groupapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
/**
 * CalendarView Activity displays all courses that a student is registered for sorted by
 * day. Plans exist for expanding this to short all activities based on start time.
 *
 * @author   Dryden and Shane
 * @since    2018-07-08
 */
public class CalendarView extends AppCompatActivity {

    private int courseListSize = 4;
    public TextView monday[] = new TextView[courseListSize];
    public TextView tuesday[] = new TextView[courseListSize];
    public TextView wednesday[] = new TextView[courseListSize];
    public TextView thursday[] = new TextView[courseListSize];
    public TextView friday[] = new TextView[courseListSize];
    public Courses courseList[];
    public Button course_button;
    DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://group-10-9598f.firebaseio.com");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_view);


        course_button = findViewById(R.id.course_button);

        course_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(CalendarView.this, ReadCourses.class));
            }
        });
        populateTextViewLists();

        if(MainActivity.currentUser != null) {
            courseList = new Courses[MainActivity.currentUser.getRegistration().keySet().toArray().length];
            for(int i=0; i < MainActivity.currentUser.getRegistration().keySet().toArray().length; i++) {

                //Recall, this wont work unless a user is signed in.
                String crn = MainActivity.currentUser.getRegistration().keySet().toArray()[i].toString();
                Query courseSchedule = databaseRef.child("COURSE_SCHEDULE").child(crn);
                courseSchedule.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            dataSnapshot.getChildren();
                            Courses toAdd = new Courses();
                            toAdd.SetNo(dataSnapshot.child("course_code").getValue().toString());
                            toAdd.SetNam(dataSnapshot.child("course_name").getValue().toString());
                            toAdd.SetEtime(dataSnapshot.child("end_time").getValue().toString());
                            toAdd.SetStime(dataSnapshot.child("start_time").getValue().toString());

                            if (dataSnapshot.child("mon").getValue().toString().equals("1")) {
                                displayCourse(monday, toAdd);
                            }
                            if (dataSnapshot.child("tue").getValue().toString().equals("1")) {
                                displayCourse(tuesday, toAdd);
                            }
                            if (dataSnapshot.child("wed").getValue().toString().equals("1")) {
                                displayCourse(wednesday, toAdd);
                            }
                            if (dataSnapshot.child("thu").getValue().toString().equals("1")) {
                                displayCourse(thursday, toAdd);
                            }
                            if (dataSnapshot.child("fri").getValue().toString().equals("1")) {
                                displayCourse(friday, toAdd);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        }
        else{
            Courses c = new Courses("Error", "User Failed to Login", "", "", "", "");
            c.SetStime("0:00");
            c.SetEtime("0:00");
            displayCourse(monday, c);
        }
    }


    public void displayCourse(TextView[] selected, Courses course){
        for(int i = 0; i < courseListSize; i++) {
            if(selected[i].getText().length() == 0) {
                selected[i].setText(course.GetCname() + "\n" + course.GetCode() + "\nTime:" + course.GetSt() + "-" + course.GetEt());
                break;
            }
        }

    }

    // Method fills list of TextViews and clears all old courses
    public void populateTextViewLists(){
        monday[0] = findViewById(R.id.m_body);
        monday[1] = findViewById(R.id.m1_body);
        monday[2] = findViewById(R.id.m2_body);
        monday[3] = findViewById(R.id.m3_body);

        monday[0].setText("");
        monday[1].setText("");
        monday[2].setText("");
        monday[3].setText("");

        tuesday[0] = findViewById(R.id.t_body);
        tuesday[1] = findViewById(R.id.t1_body);
        tuesday[2] = findViewById(R.id.t2_body);
        tuesday[3] = findViewById(R.id.t3_body);

        tuesday[0].setText("");
        tuesday[1].setText("");
        tuesday[2].setText("");
        tuesday[3].setText("");

        wednesday[0] = findViewById(R.id.w_body);
        wednesday[1] = findViewById(R.id.w1_body);
        wednesday[2] = findViewById(R.id.w2_body);
        wednesday[3] = findViewById(R.id.w3_body);

        wednesday[0].setText("");
        wednesday[1].setText("");
        wednesday[2].setText("");
        wednesday[3].setText("");

        thursday[0] = findViewById(R.id.r_body);
        thursday[1] = findViewById(R.id.r1_body);
        thursday[2] = findViewById(R.id.r2_body);
        thursday[3] = findViewById(R.id.r3_body);

        thursday[0].setText("");
        thursday[1].setText("");
        thursday[2].setText("");
        thursday[3].setText("");

        friday[0] = findViewById(R.id.f_body);
        friday[1] = findViewById(R.id.f1_body);
        friday[2] = findViewById(R.id.f2_body);
        friday[3] = findViewById(R.id.f3_body);

        friday[0].setText("");
        friday[1].setText("");
        friday[2].setText("");
        friday[3].setText("");
    }



    public int getCourseListSize(){
        return courseListSize;
    }
}
