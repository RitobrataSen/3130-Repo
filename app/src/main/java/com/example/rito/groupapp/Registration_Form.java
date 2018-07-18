
/*
Java class for Registration activity.

It links with firebase, create custom user object and update it to the daatbase.

This is a child activity of main activity.

Layout xml: activity_registration_form.xml
additional drawable: background_color_bry.xml

Authour: Yuze Divannno
Last Modified: July 6th, 2018
 */


package com.example.rito.groupapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The Registration_Form activity accepts new-user input which is used to
 * populate a User object, and then is pushed to the database.
 *
 * @author  Qu and Divanno
 * @since   07-09-18
 */

public class Registration_Form extends AppCompatActivity {
    public DatabaseReference databaseRef;
    Button submit;
    EditText username;
    EditText password, password2;
    EditText email;
    int result;
    String un;
    String pw;
    String em ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //databaseRef = FirebaseDatabase.getInstance().getReferenceFromUrl
         //       ("https://group-10-9598f.firebaseio.com").child("STUDENT");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration__form);
        username = (EditText)findViewById(R.id.user_name);
        password = (EditText)findViewById(R.id.password);
        password2 = (EditText)findViewById(R.id.password_validate);
        email = (EditText)findViewById(R.id.email);
        submit = findViewById(R.id.submit_button);
        result = -1;

        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (password == password2) {


                    final Context context = getApplicationContext();
                    final int duration = Toast.LENGTH_SHORT;
                    DatabaseReference ref;

                    final Database db = new Database();
                    un = username.getText().toString();
                    pw = password.getText().toString();
                    em = email.getText().toString();

                    ref = db.getDb().getReference("STUDENT");
                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            //check if username is a child of STUDENT
                            CharSequence text;
                            boolean exists = false;

                            if (dataSnapshot.child(un).exists()) {
                                text = "Sorry, username already exists.";
                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();
                                exists = true;
                            } else {
                                //verify if email exists
                                for (DataSnapshot data : dataSnapshot.getChildren()) {
                                    User u = data.getValue(User.class);

                                    if (u.getEmail().equalsIgnoreCase(em)) {
                                        text = "Sorry, email already exists.";
                                        Toast toast = Toast.makeText(context, text, duration);
                                        toast.show();
                                        exists = true;
                                        break;
                                    }
                                }
                            }
                            if (!(exists)) {
                                db.addUser(em, un, pw);
                                text = "Success! Added new user!";
                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();
                                startActivity(new Intent(Registration_Form.this, MainActivity.class));
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
                if(password != password2){
                    Toast.makeText(getBaseContext(), "passwords not the same", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}

