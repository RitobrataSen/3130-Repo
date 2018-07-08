package com.example.rito.groupapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    public static User currentUser;

    Button loginButton;
    Button registerButton;
    Button calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = findViewById(R.id.login_button);
        registerButton = findViewById(R.id.register_button);
        calendarView = findViewById(R.id.view_calendar);

        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MainContentLogin.class));
                //  startActivity(new Intent(MainActivity.this, ReadCourses.class));
            }
        });
        calendarView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CalendarView.class));
                //  startActivity(new Intent(MainActivity.this, ReadCourses.class));
            }
        });

    }
}
