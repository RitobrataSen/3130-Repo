package com.example.rito.groupapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

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
    EditText user;
    EditText password;
    EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        databaseRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://group-10-9598f.firebaseio.com").child("STUDENT");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration__form);
        user = (EditText)findViewById(R.id.user_name);
        password = (EditText)findViewById(R.id.password);
        email = (EditText)findViewById(R.id.email);
        submit = findViewById(R.id.submit_button);

        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                String name_info = user.getText().toString();
                String password_info = password.getText().toString();
                String email_info = email.getText().toString();

                User newUser = new User();
                newUser.setEmail(email_info);
                newUser.setPassword(password_info);
                newUser.setUsername(name_info);

                String x = databaseRef.push().getKey();
                databaseRef.child(x).setValue(newUser);
            }
        });

    }
}

