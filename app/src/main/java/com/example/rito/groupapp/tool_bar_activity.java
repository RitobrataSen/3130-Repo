package com.example.rito.groupapp;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.*;

public class tool_bar_activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.toolbar_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.go_to_course) {
            setContentView(R.layout.feature_incomplete_page);
            return true;
        }
        if(id == R.id.go_to_calender){
            setContentView(R.layout.feature_incomplete_page);
            return true;
        }
        if(id == R.id.log_out){
            setContentView(R.layout.feature_incomplete_page);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}