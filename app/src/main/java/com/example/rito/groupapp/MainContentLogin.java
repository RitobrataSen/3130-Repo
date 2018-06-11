package com.example.rito.groupapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainContentLogin extends AppCompatActivity {

    Button loginButton;
    EditText userNumber;
    EditText userPassword;
    TextView showUserInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        loginButton = findViewById(R.id.login_submit_button);
        showUserInfo = findViewById(R.id.show_user_info);

        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                userNumber = findViewById(R.id.user_number);
                userPassword = findViewById(R.id.user_pw);
                String username = userNumber.getText().toString();
                String password = userPassword.getText().toString();
                String display = "user: " + username + "\npassword: " + password;
                showUserInfo.setText(display);
                /*
                authentication steps:
                if(username.equals() && password.equals()){
                    startActivity(new Intent(MainContentLogin.this, [featureIncomplete].class));
                }
                */


            }
        });
    }
}
