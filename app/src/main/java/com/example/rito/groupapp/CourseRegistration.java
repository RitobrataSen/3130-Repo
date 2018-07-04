package com.example.rito.groupapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class CourseRegistration extends AppCompatActivity{
    public DatabaseReference mDatabase;
    public Button add;
    public EditText crn;
    public String input_crn;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_register);

        //set the reference of the database to the specific location
        mDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl("https://group-10-9598f.firebaseio.com/").child("TERMS").child("201830").child("SUBJECTS");
        add = findViewById(R.id.add);
        crn = findViewById(R.id.crn);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Read the inputed string from users
                        input_crn = crn.getText().toString();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            //Checking if the inputted CRN is exists
                            //if statement for the inputted CRN is exists
                            if (ds.child("COURSES").child(input_crn).exists()) {
                                Toast.makeText(getApplicationContext(), "Succeeded! " + input_crn + " is added",
                                        Toast.LENGTH_LONG).show();
                                //change the reference of the database back to the root
                                mDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl("https://group-10-9598f.firebaseio.com/");
                                //change the value of the enrollment to the inputted CRN
                                mDatabase.child("STUDENT").child("Student1").child("enrollment").setValue(input_crn);
                                //end the for loop
                                break;
                            }
                            //case for the inputted CRN is not exists
                            else{
                                //alert the users that the CRN is not exists
                                Toast.makeText(getApplicationContext(), input_crn+ " is not exist, please try again!", Toast.LENGTH_LONG).show();
                                break;
                            }
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
