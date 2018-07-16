package com.example.rito.groupapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

/**
 * MainContentLogin handles user authentication to ensure that the user with
 * the specified email exists, and that the given password matches. When this is
 * found true, the currentUser in MainActivity is updated, and the logged-in
 * intent is initialized.
 *
 * @author  Shane, Divanno, Dryden
 * @since   07-06-18
 */
public class MainContentLogin extends AppCompatActivity {

    Button loginButton;
    EditText userEmail;
    EditText userPassword;
    Button Submit;
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
        Log.d("debug.print", "mainContentLogin: onCreate");

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
                        boolean emailExists = false;

                        //System.out.println(dataSnapshot.getChildrenCount());
                        //System.out.println(dataSnapshot.toString());
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot student : dataSnapshot.getChildren()) {
                                //System.out.println("for loop");
                                //System.out.println(student.toString());
                                User currentUser = (User) student.getValue(User.class);
                                //System.out.println("User: " + currentUser);

                                if (!currentUser.getEmail().equals(email)) {
                                    continue;
                                }
                                emailExists = true;
                                if (currentUser.getPassword().equals(pw)) {
                                    Toast.makeText(getApplicationContext(), "User Authenticated! Welcome " + currentUser.getUsername(),
                                            Toast.LENGTH_LONG).show();

									MainActivity.currentUser = currentUser;
									startActivity(new Intent(MainContentLogin.this, CourseFilterActivity.class));

                                }

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
            }
        });
    }
}
