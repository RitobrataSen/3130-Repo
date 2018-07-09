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
    public User currentUser;
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
        currentUser = MainContentLogin.getCurrentUser();
        uid = currentUser.getUsername();

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
                                    Toast.makeText(getApplicationContext(), "Succeeded! " + input_crn + " is added", Toast.LENGTH_LONG).show();
                                    cur++;
                                    mDatabase.child("STUDENT").child(uid).child("registration").setValue(input_crn);
                                    mDatabase.child("STUDENT").child(uid).child("registration").child(input_crn).setValue("true");
                                    mDatabase.child("COURSE_ENROLLEMENT").child(input_crn).child("cur").setValue(cur);
                                }
                                else{
                                    Toast.makeText(getApplicationContext(), input_crn + " is full now, please contact the instructor to add you into the waiting list", Toast.LENGTH_LONG).show();
                                }
                            }
                            //case for the inputted CRN is not exists
                            else {
                                //alert the users that the CRN is not exists
                                Toast.makeText(getApplicationContext(), input_crn + " is not exist, please try again!", Toast.LENGTH_LONG).show();
                            }
                        }
                        else
                            //alert the users that term is not selected
                            Toast.makeText(getApplicationContext(), "Please select a term", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        drop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        input_crn = crn.getText().toString();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
