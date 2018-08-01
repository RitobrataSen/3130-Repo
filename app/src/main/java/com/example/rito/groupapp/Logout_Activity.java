package com.example.rito.groupapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;


/**
 * Logout_activity logs the user out by setting the object to null then sending
 * the app back to the main activity for a new user to log in or register
 *
 * @author Divanno and Yuze
 * @since 2018-07-05
 */
public class Logout_Activity extends AppCompatActivity {
    private ProgressDialog msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        msg = new ProgressDialog(Logout_Activity.this);
        msg.setTitle("Log out");
        msg.setMessage("Logging out, please wait...");
        msg.setCancelable(false);
        msg.show();

        Log.d("debug.print","onCreate Logout_Activity");

        MainActivity.currentUser = null;

        Intent intent = new Intent(Logout_Activity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        msg.hide();
        Toast.makeText(getBaseContext(),"logout was successful",Toast.LENGTH_LONG).show();
        Logout_Activity.this.startActivity(intent);
        finish();
    }

}

