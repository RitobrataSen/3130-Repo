package com.example.rito.groupapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * CalendarView Activity displays all courses that a student is registered for sorted by
 * day. Plans exist for expanding this to short all activities based on start time.
 *
 * @author   Dryden and Shane
 * @since    2018-07-08
 */
public class CalendarView extends AppCompatActivity {

    private int courseListSize = 4;
    private final ArrayList<String> codeList = new ArrayList<>();
    private String selectedCourse;
    public TextView monday[] = new TextView[courseListSize];
    public TextView tuesday[] = new TextView[courseListSize];
    public TextView wednesday[] = new TextView[courseListSize];
    public TextView thursday[] = new TextView[courseListSize];
    public TextView friday[] = new TextView[courseListSize];
    public Course courseList[];
    public Button course_button;
    public Button detail;
    public static String selectedCRN;
    DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://group-10-9598f.firebaseio.com");


    private Toolbar hdrToolBar;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.go_to_course:
                startActivity(new Intent(CalendarView.this, CourseFilterActivity.class));
                return true;

            case R.id.go_to_calender:
                startActivity(new Intent(CalendarView.this, CalendarView.class));
                return true;

            case R.id.go_to_add_crn:
                startActivity(new Intent(CalendarView.this, CourseRegistration.class));
                return true;

            case R.id.go_to_view_remove_registered:
                startActivity(new Intent(CalendarView.this, ViewRemoveCourseRegistrationActivity.class));
                return true;

            case R.id.log_out:
                startActivity(new Intent(CalendarView.this, Logout_Activity.class));
                return true;
        }

		return super.onOptionsItemSelected(item);

	}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_view);

        detail = (Button) findViewById(R.id.Detail);
        hdrToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(hdrToolBar);

        codeList.add("Select the course you want view the detail");

        populateTextViewLists();

        if(MainActivity.currentUser != null) {
            courseList = new Course[MainActivity.currentUser.getRegistration().keySet().toArray().length];
            for(int i=0; i < MainActivity.currentUser.getRegistration().keySet().toArray().length; i++) {

                //Recall, this wont work unless a user is signed in.
                String crn = MainActivity.currentUser.getRegistration().keySet().toArray()[i].toString();
                Query courseSchedule = databaseRef.child("COURSE_SCHEDULE").child(crn);
                courseSchedule.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            dataSnapshot.getChildren();
                            Course_Schedule toAdd = new Course_Schedule();
                            toAdd.setCourse_code(dataSnapshot.child("course_code").getValue().toString());
                            toAdd.setCourse_name(dataSnapshot.child("course_name").getValue().toString());
                            toAdd.setEndTime(dataSnapshot.child("end_time").getValue().toString());
                            toAdd.setStartTime(dataSnapshot.child("start_time").getValue().toString());
                            codeList.add(dataSnapshot.child("course_code").getValue().toString());

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
            Spinner codeSpinner = (Spinner) findViewById(R.id.codeSpinner);
            ArrayAdapter<String> codeAdapter = new ArrayAdapter<>(CalendarView.this, android.R.layout.simple_spinner_item, codeList);
            codeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            codeSpinner.setAdapter(codeAdapter);

            selectedCourse = codeSpinner.getSelectedItem().toString();
            int index = codeList.indexOf(selectedCourse) - 1;
            if (index >= 0) {
                selectedCRN = MainActivity.currentUser.getRegistration().keySet().toArray()[index].toString();
            }
            detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("debug.print", "main_activity: Submit clicked");
                    startActivity(new Intent(CalendarView.this, Detail_Page.class));
                }
            });
        }
        else{
            Course_Schedule c = new Course_Schedule("Error", "User Failed to Login",
                    "", "", "", "", "",
                    "", "", "", "", "", "");
            c.setStartTime("0:00");
            c.setEndTime("0:00");
            displayCourse(monday, c);
        }
    }


    public void displayCourse(TextView[] selected, Course_Schedule course){
        for(int i = 0; i < courseListSize; i++) {
            if(selected[i].getText().length() == 0) {
                selected[i].setText(course.getCourse_name() + "\n" + course.getCourse_code() + "\nTime:" + course
                        .getStartTime() + "-" + course.getEndTime());
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
