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
import com.google.firebase.database.ValueEventListener;

/**
 * MainContentLogin handles user authentication to ensure that the user with
 * the specified email exists, and that the given password matches. When this is
 * found true, the currentUser in MainActivity is updated, and the logged-in
 * intent is initialized.
 *
 * Dryden added the currentUser Global object in a refactor following the main creation of main activity login.
 *
 * @author  Shane, Divanno, Dryden
 * @since   07-06-18
 */
public class MainContentLogin extends AppCompatActivity {
    Button forgotPasswordButton;
    Button loginButton;
    EditText userEmail;
    EditText userPassword;
    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        Log.d("debug.print","onCreate MainContentLogin");

        loginButton = findViewById(R.id.login_submit_button);
        loginButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
				userEmail = findViewById(R.id.user_email);
                userPassword = findViewById(R.id.user_pw);
                final String email = userEmail.getText().toString();
                final String pw = userPassword.getText().toString();
				Database db = new Database("STUDENT");
				final DatabaseReference ref = db.getDbRef();

                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
						boolean emailExists = false;

                        if (dataSnapshot.exists()) {
                            for (DataSnapshot student : dataSnapshot.getChildren()) {

                                User currentUser = (User) student.getValue(User.class);
                                if (!currentUser.getEmail().equals(email)) {
                                    continue;
                                }

                                emailExists = true;
                                if (currentUser.getPassword().equals(pw)) {
                                    Toast.makeText(getApplicationContext(), "User Authenticated! Welcome " + currentUser.getUsername(),
                                            Toast.LENGTH_LONG).show();
                                    ref.removeEventListener(this);
                                    MainActivity.currentUser = currentUser;
									startActivity(new Intent(MainContentLogin.this, CourseFilterActivity.class));
									return;
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
        forgotPasswordButton = findViewById(R.id.forgot_password);
        forgotPasswordButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                startActivity(new Intent(MainContentLogin.this,RecoveryEmailActivity.class));
            }
        });
    }
}
