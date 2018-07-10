
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

                databaseRef.child(name_info).setValue(newUser);
            }
        });

    }
}

