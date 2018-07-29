package com.example.rito.groupapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * MainActivity is the first activity that the user interacts with. The user
 * chooses to either login, or register from this page. MainActivity also stores
 * the currentUser object, which holds either an initialized logged-in user, or null.
 *
 * @author  Shane, Divanno
 * @since   06-22-18
 */

public class MainActivity extends AppCompatActivity {


    public static User currentUser;

    Button loginButton;
    Button registerButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginButton = findViewById(R.id.login_button);
        registerButton = findViewById(R.id.register_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Log.d("debug.print", "main_activity: Login clicked");
                startActivity(new Intent(MainActivity.this, MainContentLogin.class));

            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Log.d("debug.print", "main_activity: register clicked");
                startActivity(new Intent(MainActivity.this, Registration_Form.class));
            }
        });

    }
}
