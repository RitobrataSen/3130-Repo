package com.example.rito.groupapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;


/**
 *Logout_activity logs the user out by setting the object to null then sending
 * the app back to the main activity for a new user to log in or register
 * @author Divanno and Yuze
 * @since 2018-07-05
 */
public class Logout_Activity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainActivity.currentUser = null;
        Toast.makeText(getBaseContext(),"logout was successful",Toast.LENGTH_LONG).show();

        }

    }

