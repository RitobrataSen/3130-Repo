package com.example.rito.groupapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CourseRegistration extends AppCompatActivity{
    private DatabaseReference mDatabase;
    private Button add,drop;
    private EditText crn;
    private String input_crn, term, selectTerm;
    private int cur;
    private Spinner termSpinner;
    private String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_register);

        //set the reference of the database to the specific location
        mDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl("https://group-10-9598f.firebaseio.com/");
        add = findViewById(R.id.add);
        drop = findViewById(R.id.drop);
        crn = findViewById(R.id.crn);
        User user = MainActivity.currentUser;
        uid = user.getUsername();

        termSpinner = findViewById(R.id.term);
        ArrayAdapter<CharSequence> adapterTerm = ArrayAdapter.createFromResource(this,
                R.array.terms_array, android.R.layout.simple_spinner_item);
        adapterTerm.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        termSpinner.setAdapter(adapterTerm);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Read the inputed string from users
                        input_crn = crn.getText().toString();
                        int max = Integer.parseInt(dataSnapshot.child("COURSE_ENROLLEMENT").child(input_crn).child("max").getValue().toString());
                        cur = Integer.parseInt(dataSnapshot.child("COURSE_ENROLLEMENT").child(input_crn).child("cur").getValue().toString());
                        term = termSpinner.getSelectedItem().toString();
                        if (term.equals("2018 Summer"))
                            selectTerm = "201830";
                        else if(term.equals("2018 Medicine"))
                            selectTerm = "201900";
                        else if(term.equals("2019 Fall"))
                            selectTerm = "201910";
                        else if(term.equals("2019 Winter"))
                            selectTerm= "201920";


                        if (!term.equals("Select the term you want to register")){
                            //Checking the user's inputted crn is exists or not
                            if (dataSnapshot.child("COURSE_ENROLLEMENT").child(input_crn).exists()) {
                                //Checking the number of student that enrolled in this course is full or not
                                if (max > cur) {
                                    //Checking if the current student is enrolled the entered course or not
                                    if (!dataSnapshot.child("STUDENT").child(uid).child("registration").child(input_crn).exists()) {
                                        //Checking if the number of courses that current student are taking right now is not more than 5
                                        if(dataSnapshot.child("STUDENT").child(uid).child("registration").getChildrenCount() < 5) {
                                            Toast.makeText(getApplicationContext(), "Succeeded! " + input_crn + " is added", Toast.LENGTH_LONG).show();
                                            cur++;
                                            mDatabase.child("STUDENT").child(uid).child("registration").child(input_crn).setValue("true");
                                            mDatabase.child("COURSE_ENROLLEMENT").child(input_crn).child("cur").setValue(cur);
                                        }
                                        //case for student enroll more than 5 courses
                                        else
                                            Toast.makeText(getApplicationContext(),"You are not allowed to take more than 5 courses",
                                                    Toast.LENGTH_LONG).show();
                                    }
                                    //case for duplicate enrollment
                                    else
                                        Toast.makeText(getApplicationContext(),input_crn + " is already enrolled in your list, please do not enroll same course",
                                                Toast.LENGTH_LONG).show();
                                }
                                //case for course is full
                                else
                                    Toast.makeText(getApplicationContext(), input_crn + " is full now, please contact the instructor to add you into the waiting list",
                                            Toast.LENGTH_LONG).show();
                            }
                            //case for the inputted CRN is not exists
                            else
                                Toast.makeText(getApplicationContext(), input_crn + " is not exist, please try again!",
                                        Toast.LENGTH_LONG).show();
                        }
                        //case for term is not selected
                        else
                            Toast.makeText(getApplicationContext(), "Please select a term",
                                    Toast.LENGTH_LONG).show();
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
                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        cur = Integer.parseInt(dataSnapshot.child("COURSE_ENROLLEMENT").child(input_crn).child("cur").getValue().toString()) - 1;
                        input_crn = crn.getText().toString();
                        //Checking if the inputted course is enrolled by the student or not
                        if(dataSnapshot.child("STUDENT").child(uid).child("registration").child(input_crn).exists()){
                            //remove the course from student's enroll list and reset the current enroll student of the course
                            mDatabase.child("STUDENT").child(uid).child("registration").child(input_crn).removeValue();
                            mDatabase.child("COURSE_ENROLLEMENT").child(input_crn).child("cur").setValue(cur);
                            Toast.makeText(getApplicationContext(),input_crn + " is removed", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"You are not enrolled in "+ input_crn+" please try again", Toast.LENGTH_LONG).show();
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
