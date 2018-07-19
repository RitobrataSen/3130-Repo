package com.example.rito.groupapp.ViewUser_Information;
import com.example.rito.groupapp.*;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

    private TextView tv1, tv2, tv3,tv4,tv5;
    private EditText et1, et2, et3,et4,et5;
    private Button bt1;

    private DatabaseReference databaseRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__user_information);
       final Database db = new Database();
        databaseRef = db.getDb().getReference("STUDENT");

        if (MainActivity.currentUser != null) {
            //initializing the textViews
            tv1 = findViewById(R.id.textView2);
            tv2 = findViewById(R.id.textView3);
            tv3 = findViewById(R.id.textView4);
            tv4 = findViewById(R.id.textView5);
            tv5 = findViewById(R.id.textView6);
            //initializing the editsTexts
            et1 = findViewById(R.id.editText);
            et2 = findViewById(R.id.editText2);
            et3 = findViewById(R.id.editText3);
            et4 = findViewById(R.id.editText4);
            et5 = findViewById(R.id.editText5);
            //initializing the button for update
            bt1 = findViewById(R.id.button2);


            //displaying the information
            tv1.setText("Email");
            et1.setText(MainActivity.currentUser.getEmail().toString());

            tv2.setText("Username");
            et2.setText(MainActivity.currentUser.getUsername().toString());

            tv3.setText("Password");
            et3.setText(MainActivity.currentUser.getPassword().toString());

            tv4.setText("New Password");
            tv5.setText("Confirm Password");


            //updating the information
            bt1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {

                    if(!MainActivity.currentUser.getUsername().toString().equals(et2.getText().toString())){
                    User user1 = new User(et1.getText().toString(),et2.getText().toString(),et3.getText().toString(),MainActivity.currentUser.getRegistration());
                     db.updateUser(MainActivity.currentUser,user1);
                    }
                    if(!MainActivity.currentUser.getEmail().toString().equals(et1.getText().toString())){
                        User user1 = new User(et1.getText().toString(),et2.getText().toString(),et3.getText().toString(),MainActivity.currentUser.getRegistration());
                        db.updateUser(MainActivity.currentUser,user1);
                        }
                    if(MainActivity.currentUser.getPassword().toString()!=et4.getText().toString() && et4.getText().toString().equals(et5.getText().toString()) && et4.getText().length()>0 && et5.getText().length()>0){
                        User user1 = new User(et1.getText().toString(),et2.getText().toString(),et5.getText().toString(),MainActivity.currentUser.getRegistration());
                        db.updateUser(MainActivity.currentUser,user1);

                    }

                }




        });
    }


}
}
