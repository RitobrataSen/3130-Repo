package com.example.rito.groupapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;


/**
 *
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

