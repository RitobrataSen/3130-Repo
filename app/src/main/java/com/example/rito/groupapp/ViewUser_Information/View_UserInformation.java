package com.example.rito.groupapp.ViewUser_Information;
import com.example.rito.groupapp.*;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
/**
 * View_UserInformation displays all the current information of the user that is currently logged into the app
 * It also allows the user to update their information
 * It updates the database dynamically as it does so
 *
 * @Author: Ritobrata Sen, Qu Yuze
 * @DateStarted: 18th July
 * @DateEnded:
 *
 */

public class View_UserInformation extends AppCompatActivity {

    private TextView tv1,tv2,tv3;
    private EditText et1,et2,et3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        while(MainActivity.currentUser!=null){
            tv1 = findViewById(R.id.textView2);
            tv2 = findViewById(R.id.textView3);
            tv3 = findViewById(R.id.textView4);

            et1 = findViewById(R.id.editText);
            et2 = findViewById(R.id.editText2);
            et3 = findViewById(R.id.editText3);

            tv1.setText("Email");
            et1.setText(MainActivity.currentUser.getEmail().toString());

            tv2.setText("Username");
            et2.setText(MainActivity.currentUser.getUsername().toString());

            tv3.setText("Password");
            et3.setText(MainActivity.currentUser.getPassword().toString());
        }
        //setContentView(R.layout.activity_view__user_information);
    }
}
