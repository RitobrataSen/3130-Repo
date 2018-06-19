/**
 * MainContentLogin.java:
 * This is the main script for the login activity. The entered
 * login details are verified with the firebase data and feedback is provided
 * back to the user.
 *
 * @author Shane Mitravitz
 * @param savedInstanceState passed in to the onCreate method
 * @param view passed in to the onClick method
 * @param dataSnapshot passed in to the onDataChange method
 * @param onCancelled passed in to the onCancelled method
 */

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

public class MainContentLogin extends AppCompatActivity {

    Button loginButton;
    EditText userEmail;
    EditText userPassword;
    User currentUser;
    DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://group-10-9598f.firebaseio.com");

    /*
        @Override
        public void onStart() {
            super.onStart();
            // Check if user is signed in (non-null) and update UI accordingly.
            FirebaseUser currentUser = mAuth.getCurrentUser();
            updateUI(currentUser);
        }
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        loginButton = findViewById(R.id.login_submit_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                userEmail = findViewById(R.id.user_number);
                userPassword = findViewById(R.id.user_pw);
                final String email = userEmail.getText().toString();
                final String pw = userPassword.getText().toString();

                //full credit for snapshot query access to user Upendrah Shah @
                //https://stackoverflow.com/questions/45136779/login-using-email-stored-in-firebase-realtime-database
                Query student_exists_query = databaseRef.child("STUDENT");
                student_exists_query.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                    /*
                    * this verifies login info after pressing the submit button
                    * */

                        boolean emailExists = false;
                        System.out.println(dataSnapshot.getChildrenCount());
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot student : dataSnapshot.getChildren()) {
                                currentUser = new User();
                                currentUser.setPassword(student.child("password").getValue().toString());
                                currentUser.setEmail(student.child("email").getValue().toString());
                                //currentUser.setEnrollment(Integer.parseInt(student.child("enrollment").getValue().toString()));
                                currentUser.setUsername(student.child("username").getValue().toString());

                                /*
                                these blocks perform validation on the enter details vs details
                                found in firebase
                                 */
                                if (!currentUser.getEmail().equals(email)) {
                                    continue;
                                }
                                emailExists = true;
                                if (currentUser.getPassword().equals(pw))
                                    Toast.makeText(getApplicationContext(), "User Authenticated! Welcome " + currentUser.getUsername(),
                                            Toast.LENGTH_LONG).show();
                                else {
                                    Toast.makeText(getApplicationContext(), "Incorrect password, please try again!", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                        else
                            Toast.makeText(getApplicationContext(), "There are no registered users yet", Toast.LENGTH_LONG).show();
                        if (!emailExists)
                            Toast.makeText(getApplicationContext(), "User not found", Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
                //

            }
        });
    }
}
