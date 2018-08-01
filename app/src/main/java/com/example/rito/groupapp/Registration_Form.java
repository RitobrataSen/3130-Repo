package com.example.rito.groupapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    private ProgressDialog msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration__form);

		msg = new ProgressDialog(Registration_Form.this);
		msg.setTitle("Loading");
		msg.setMessage("Loading, please wait...");
		msg.setCancelable(false);
		msg.show();

        submit = findViewById(R.id.submit_button);
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
				msg.setTitle("Account Registration");
				msg.setMessage("Validating entries, please wait...");
				msg.show();

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
                    msg.hide();
                	Toast.makeText(getApplicationContext(),
                            "Enter your email first!", Toast.LENGTH_LONG).show();
                    return;
                }
                if (un.isEmpty()) {
                    msg.hide();
                    Toast.makeText(getApplicationContext(),
                            "Enter your username!", Toast.LENGTH_LONG).show();
                    return;
                }
                if (pw.isEmpty()) {
                    msg.hide();
                    Toast.makeText(getApplicationContext(),
                            "Enter your password!", Toast.LENGTH_LONG).show();
                    return;
                }
                if (pw2.isEmpty()) {
                    msg.hide();
                    Toast.makeText(getApplicationContext(),
                            "Confirm your password!", Toast.LENGTH_LONG).show();
                    return;
                }

                //matching pw check. db will not be opened until pws match
                if (!pw.equals(pw2)) {
                    msg.hide();
                    Toast.makeText(getApplicationContext(), "Enter matching passwords to " +
							"proceed!", Toast.LENGTH_LONG).show();
                    return;
                }

                final Database db = new Database();
                DatabaseReference ref = db.getDb().getReference("STUDENT");
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
						msg.setMessage("Validating entries, please wait...");
						msg.show();
                        boolean exists = false;
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot student : dataSnapshot.getChildren()) {
                                User currentUser = (User) student.getValue(User.class);
                                if (currentUser != null) {
                                    if (currentUser.getEmail().equals(em)) {
										exists = true;
										msg.hide();
                                        Toast.makeText(getApplicationContext(), "This " +
												"email" +
												" is already in use!", Toast.LENGTH_LONG).show();

                                        break;
                                    }
                                    else if (currentUser.getUsername().equals(un)) {
										exists = true;
										msg.hide();
										Toast.makeText(getApplicationContext(), "This username is already in use!", Toast.LENGTH_LONG).show();
                                        break;
                                    }
                                }
                                else {
									msg.hide();
									Toast.makeText(getApplicationContext(), "Database error, null student detected!", Toast.LENGTH_LONG).show();
                                    return;
                                }
                            }
                        }
                        if (!exists) {
                            db.addUser(em, un, pw);
							msg.hide();
							Toast.makeText(getApplicationContext(), "Success! Added new user " + un, Toast.LENGTH_LONG).show();
                            startActivity(new Intent(Registration_Form.this, MainActivity.class));
                        }
                        msg.hide();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
						msg.hide();
                    }
                });
            }

        });
		msg.show();
    }
}

