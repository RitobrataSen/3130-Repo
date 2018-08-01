package com.example.rito.groupapp;

import android.app.ProgressDialog;
import android.content.Context;
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
    private Button forgotPasswordButton;
    private Button loginButton;
    private EditText userEmail;
    private EditText userPassword;
    private ProgressDialog msg;
    private String toast_msg;
    private int duration = Toast.LENGTH_LONG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        Log.d("debug.print","onCreate MainContentLogin");

        loginButton = findViewById(R.id.login_submit_button);
        loginButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
				msg = new ProgressDialog(MainContentLogin.this);
				msg.setTitle("Login");
				msg.setMessage("Verifying credentials, please wait...");
				msg.setCancelable(false);
				msg.show();

				userEmail = findViewById(R.id.user_email);
				userPassword = findViewById(R.id.user_pw);

				final String email = userEmail.getText().toString();
				final String pw = userPassword.getText().toString();
				Database db = new Database("STUDENT");
				final DatabaseReference ref;

				toast_msg = "Invalid username and/ or password combination, " +
						"please try again!";

				if (email.equals("") || pw.equals("")){
					toast_msg= "One or more required fields were not entered. Please verify and " +
							"try again.";
					msg.hide();
					Toast.makeText(getApplicationContext(), toast_msg, duration).show();
				} else {
					ref = db.getDbRef();
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
										ref.removeEventListener(this);
										MainActivity.currentUser = currentUser;
										msg.hide();
										toast_msg = "User Authenticated! Welcome " +
												currentUser.getUsername();
										Toast.makeText(getApplicationContext(), toast_msg, duration).show();
										startActivity(new Intent(MainContentLogin.this, CourseFilterActivity.class));
										return;
									}
								}
							}
							msg.hide();
							Toast.makeText(getApplicationContext(), toast_msg, duration).show();
						}
						@Override
						public void onCancelled(DatabaseError databaseError) {
							msg.hide();
						}
					});
				}
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
