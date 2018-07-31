
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
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;


/**
 * The Registration_Form activity accepts new-user input which is used to
 * populate a User object, and then is pushed to the database.
 *
 * Since tag update with structuring refactor/ bug fix.
 *
 * @author  Qu, Divanno, Shane
 * @completed   07-09-18
 *
 * @since   07-31-18
 */

public class Registration_Form extends AppCompatActivity {
    private Button submit;
    private EditText username;
    private EditText password, password2;
    private EditText email;

    private String em;
    private String un;
    private String pw;
    private String pw2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration__form);

        submit = findViewById(R.id.submit_button);
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                email = (EditText) findViewById(R.id.email);
                username = (EditText) findViewById(R.id.user_name);
                password = (EditText) findViewById(R.id.password);
                password2 = (EditText) findViewById(R.id.password_validate);

                em = email.getText().toString();
                un = username.getText().toString();
                pw = password.getText().toString();
                pw2 = password2.getText().toString();

                //empty field checks
                if (em.isEmpty()) {
                    Toast.makeText(getApplicationContext(),
                            "Enter your email first!", Toast.LENGTH_LONG).show();
                    return;
                }
                if (un.isEmpty()) {
                    Toast.makeText(getApplicationContext(),
                            "Enter your username!", Toast.LENGTH_LONG).show();
                    return;
                }
                if (pw.isEmpty()) {
                    Toast.makeText(getApplicationContext(),
                            "Enter your password!", Toast.LENGTH_LONG).show();
                    return;
                }
                if (pw2.isEmpty()) {
                    Toast.makeText(getApplicationContext(),
                            "Confirm your password!", Toast.LENGTH_LONG).show();
                    return;
                }

                //matching pw check. db will not be opened until pws match
                if (!pw.equals(pw2)) {
                    Toast.makeText(getApplicationContext(), "Enter matching passwords to proceed!", Toast.LENGTH_LONG).show();
                    return;
                }

                final Database db = new Database();
                DatabaseReference ref = db.getDb().getReference("STUDENT");
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        boolean exists = false;
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot student : dataSnapshot.getChildren()) {
                                User currentUser = (User) student.getValue(User.class);
                                if (currentUser != null) {
                                    if (currentUser.getEmail().equals(em)) {
                                        Toast.makeText(getApplicationContext(), "This email is already in use!", Toast.LENGTH_LONG).show();
                                        exists = true;
                                        break;
                                    }
                                    else if (currentUser.getUsername().equals(un)) {
                                        Toast.makeText(getApplicationContext(), "This username is already in use!", Toast.LENGTH_LONG).show();
                                        exists = true;
                                        break;
                                    }
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "Database error, null student detected!", Toast.LENGTH_LONG).show();
                                    return;
                                }
                            }
                        }
                        if (!exists) {
                            db.addUser(em, un, pw);
                            Toast.makeText(getApplicationContext(), "Success! Added new user " + un, Toast.LENGTH_LONG).show();
                            startActivity(new Intent(Registration_Form.this, MainActivity.class));
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

