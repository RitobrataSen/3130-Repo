package com.example.rito.groupapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * This activity is used to retrieve a password for a given user email. In a more secure
 * implementation, the recovery email would be sent to the provided email directly, but since
 * our database is populated by emails that are not real, the dummy 3130.group10@gmail.com address is used.
 *
 * After the email has been confirmed to exist, an asynctask doInBackground process is invoked so that
 * the UI thread is not disturbed by the sending process.
 *
 * @author  Shane, Gobii
 * @since   07-27-2018
 */


public class RecoveryEmailActivity extends AppCompatActivity {

    Button sendEmail;
    Button returnToMCL;
    EditText accountEmail;
    private User recoveringUser;
    //this variable is to be used only for testing
    static String tempPassword;
    Database dbRefStudent = new Database();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.recovery_email_activity);

        Log.d("debug.print","onCreate RecoveryEmailActivity");

        sendEmail = findViewById(R.id.send_email);
        returnToMCL = findViewById(R.id.return_to_MCL);

        sendEmail.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("StaticFieldLeak")
            public void onClick(View view) {
                accountEmail = findViewById(R.id.user_email_recovery);
                if(accountEmail.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Enter an email first!", Toast.LENGTH_LONG).show();
                    return;
                }
                final String emailToRecover = accountEmail.getText().toString();
                Query valid_email_query = dbRefStudent.getDbRef().child("STUDENT");
                valid_email_query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        boolean emailExists = false;
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot student : dataSnapshot.getChildren()) {
                                User currentUser = (User) student.getValue(User.class);
                                if (currentUser.getEmail().equals(emailToRecover)) {
                                    emailExists = true;
                                    recoveringUser = currentUser;
                                    break;
                                }
                            }
                        }
                        if(emailExists) {
                            final String tempPasswordToSend = GMailSender.generateRandPassword();
                            tempPassword = tempPasswordToSend;
                            final GMailSender sender = new GMailSender();
                            //AsyncTask implementation credit to dokkaebi (2013)
                            //https://stackoverflow.com/questions/14374578/using-asynctask-to-send-android-email
                            new AsyncTask<Void, Void, Void>() {
                                @Override
                                public Void doInBackground(Void... arg) {
                                    try {
                                        User oldUser = recoveringUser;
                                        recoveringUser.setPassword(tempPasswordToSend);
                                        dbRefStudent.updateUser(oldUser,recoveringUser);
                                        String recoveryMessage = "CSCI3130 Group 10\nA password reset has been requested for your account.\n\n" +
                                                "Email: " + emailToRecover + "\nPassword: " + tempPasswordToSend;
                                        sender.sendMail("Account Recovery", recoveryMessage);
                                    } catch (Exception e) {
                                        Log.e("SendMail", e.getMessage(), e);
                                    }
                                    return null;
                                }
                            }.execute();
                            Toast.makeText(getApplicationContext(), "Your password has been reset!\nPlease check your email for "+
                                    "your temporary password!", Toast.LENGTH_LONG).show();
                            //
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Enter a registered email address!", Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        });
        returnToMCL.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(RecoveryEmailActivity.this, MainActivity.class));
            }
        });
    }
}
