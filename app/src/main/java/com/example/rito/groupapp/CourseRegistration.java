package com.example.rito.groupapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
//import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * CourseRegistration for adding course into student's registration list
 * or remove course from student's registration list
 * It is able to check if the inputted crn is exists or not,
 * the course is full or not, the student is taking more than 5 courses or not,
 * and the student is already taking it or not.
 * @author Yuhao, Gobii, Ritobrata Sen
 * @completed 2018-7-10
 *
 * @since 2018-07-19
 *
 * @author Ritobrata Sen, Qu Yuze
 * @updated: The an added functionality to the menu was added so that the user can now
 * navigate and view their information.
 */

//uses User and Database object

public class CourseRegistration extends AppCompatActivity{
    private DatabaseReference mDatabase;
    private Button add,drop;
    private EditText crn;
    private String input_crn, term, selectTerm;
    //private int cur;
    //private Spinner termSpinner;
    private String uid;
    private Toolbar hdrToolBar;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //main navigation menu
        switch (item.getItemId()) {
            case R.id.go_to_course:
                startActivity(new Intent(CourseRegistration.this, CourseFilterActivity.class));
                return true;

            case R.id.go_to_calender:
                startActivity(new Intent(CourseRegistration.this, CalendarView.class));
                return true;

            case R.id.go_to_add_crn:
                startActivity(new Intent(CourseRegistration.this, CourseRegistration.class));
                return true;

            case R.id.go_to_view_remove_registered:
                startActivity(new Intent(CourseRegistration.this, MyCoursesActivity.class));
                return true;
            case R.id.view_user_information:
                startActivity(new Intent(CourseRegistration.this, View_UserInformation.class));
                return true;
            case R.id.log_out:
                startActivity(new Intent(CourseRegistration.this, Logout_Activity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_register);

        Log.d("debug.print","onCreate CourseRegistration");
        hdrToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(hdrToolBar);

        //set the reference of the database to the specific location
        mDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl("https://group-10-9598f.firebaseio.com/");
        add = findViewById(R.id.add);
        drop = findViewById(R.id.drop);
        crn = findViewById(R.id.crn);
        User user = MainActivity.currentUser;
        uid = user.getUsername();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add.setClickable(false);
                Database db_ce = new Database();
                input_crn = crn.getText().toString();
                DatabaseReference ref_ce = db_ce.getDb().getReference("CRN_DATA/" +
                        input_crn);
                ref_ce.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Read the inputed string from users

                        //Checking if crn exists
                        if (dataSnapshot.exists() && crn.getText().length()>0) {
                            long max = Integer.parseInt(dataSnapshot.child("max").getValue()
                                    .toString());
                            long cur = dataSnapshot.child("enrollment").exists() ?
                                    dataSnapshot.child("enrollment").getChildrenCount() : 0;

                            //Checking the number of student that enrolled in this course is full or not
                            if (max > cur) {
                                final Database db_st = new Database("STUDENT/" + uid);
                                DatabaseReference ref_st = db_st.getDbRef();

                                //Checking if the current student is enrolled the entered course or not
                                ref_st.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if (!(dataSnapshot.hasChild("registration")) ||
                                                !(dataSnapshot.child("registration").hasChild(input_crn))) {

                                            //save data
                                            db_st.addRemoveCourse(input_crn, uid, true);
                                            Toast.makeText(
                                                    getApplicationContext(),
                                                    "Success! " + input_crn + " has been added.",
                                                    Toast.LENGTH_LONG).show();
                                            add.setClickable(true);
                                        } else {
                                            //case for duplicate enrollment
                                            Toast.makeText(
                                                    getApplicationContext(),
                                                    "You are already enrolled for " +
                                                            input_crn + "!",
                                                    Toast.LENGTH_LONG).show();
                                            String str = crn.getText().toString();
                                            if(str!=crn.getText().toString())
                                                add.setClickable(true);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                    }
                                });
                            } else {
                                //case for course is full
                                Toast.makeText(
                                        getApplicationContext(),
                                        input_crn +" is full now, please contact the " +
                                                "instructor.",
                                        Toast.LENGTH_LONG).show();

                            }
                        } else {
                            //case for the inputted CRN is not exists
                            Toast.makeText(
                                    getApplicationContext(),
                                    input_crn + " does not exist, please try again!",
                                    Toast.LENGTH_LONG).show();
                                add.setClickable(true);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        //drop button for dropping the course from user's enrollment list
        drop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Database db_st = new Database("STUDENT/" + uid);
                DatabaseReference ref_st =  db_st.getDbRef();
                ref_st.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        input_crn = crn.getText().toString();

                        //Checking if the inputted course is enrolled by the student or not
                        if(dataSnapshot.hasChild("registration") ||
                                dataSnapshot.child("registration").hasChild(input_crn)){

                            //remove the course from student's enroll list
                            db_st.addRemoveCourse(input_crn, uid, false);
                            Toast.makeText(
                                    getApplicationContext(),
                                    input_crn + " is removed!",
                                    Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(
                                    getApplicationContext(),
                                    "You are not enrolled in "+
                                            input_crn +
                                            ", please try again.",
                                    Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
