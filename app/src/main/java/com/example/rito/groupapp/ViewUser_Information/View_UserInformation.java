package com.example.rito.groupapp.ViewUser_Information;
import com.example.rito.groupapp.*;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
/**
 * View_UserInformation displays all the current information of the user that is currently logged into the app
 * It also allows the user to update their information
 * It updates the database dynamically as it does so
 * It gives the user a toast message upon successfully updating their information (if there is any information to be updated)
 *
 * @Author: Ritobrata Sen, Qu Yuze
 * @DateStarted: 18th July 2018
 * @DateEnded: 24th July 2018
 *
 */

public class View_UserInformation extends AppCompatActivity {

    private TextView tv1, tv2, tv3,tv4,tv5;
    private EditText et1, et2, et3,et4,et5;
    private Button bt1;
    private Toolbar hdrToolBar;
    private DatabaseReference databaseRef;
	private int em_len = 1;
	private int un_len = 1;
	private int pw_len = 1;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.go_to_course:
                startActivity(new Intent(View_UserInformation.this, CourseFilterActivity.class));
                return true;

            case R.id.go_to_calender:
                startActivity(new Intent(View_UserInformation.this, CalendarView.class));
                return true;

            case R.id.go_to_add_crn:
                startActivity(new Intent(View_UserInformation.this, CourseRegistration.class));
                return true;

            case R.id.go_to_view_remove_registered:
                startActivity(new Intent(View_UserInformation.this, MyCoursesActivity.class));
                return true;
            case R.id.view_user_information:
                startActivity(new Intent(View_UserInformation.this, View_UserInformation.class));

            case R.id.log_out:
                startActivity(new Intent(View_UserInformation.this, Logout_Activity.class));
                return true;
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__user_information);

        hdrToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(hdrToolBar);


       final Database db = new Database();

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
            tv1.setText(String.valueOf("Email"));
            et1.setText(MainActivity.currentUser.getEmail());

            tv2.setText(String.valueOf("Username"));
            et2.setText(MainActivity.currentUser.getUsername());

            tv3.setText(String.valueOf("Password"));
            et3.setText(MainActivity.currentUser.getPassword());

            tv4.setText(String.valueOf("New Password"));
            tv5.setText(String.valueOf("Confirm Password"));


            //updating the information
            bt1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    databaseRef = db.getDb().getReference("STUDENT");
                    databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
						@Override
						public void onDataChange(DataSnapshot dataSnapshot) {
							//check if username is a child of STUDENT
							CharSequence text;
							boolean exists = false;

							final Context context = getApplicationContext();
							final int duration = Toast.LENGTH_SHORT;
							final Database db = new Database();
							String un = et2.getText().toString();
							String pw1 = et4.getText().toString();
							String pw2 = et5.getText().toString();
							String pw = MainActivity.currentUser.getPassword();
							String em = et1.getText().toString();
							if (!(pw1.equals("")) && !(pw2.equals(""))){
								pw = pw1;
							}

							if (dataSnapshot.child(un).exists() && !(un.equalsIgnoreCase
									(MainActivity.currentUser.getUsername()))) {
								if(un.length() != 0){
									text = "Sorry, username already exists.";
									Toast toast = Toast.makeText(context, text, duration);
									toast.show();
								}
								exists = true;
							}else if(!(pw1.equals("")) && !(pw2.equals("")) && !(pw1.equals(pw2))){
								text = "Sorry, passwords do not match.";
								Toast toast = Toast.makeText(context, text, duration);
								toast.show();
								exists = true;
							//apply field validation here
							} else if(un.length() < un_len ||
									pw.length() < pw_len ||
									em.length() < em_len){
								text = "Sorry, 1 or more fields are not correct.";
								Toast toast = Toast.makeText(context, text, duration);
								toast.show();
								exists = true;
							}
							else {
								//verify if email/ username exists
								for (DataSnapshot data : dataSnapshot.getChildren()) {
									User u = data.getValue(User.class);

									if (u.getEmail().equalsIgnoreCase(em) &&
											!(em.equalsIgnoreCase(
													MainActivity.currentUser.getEmail()))){
										text = "Sorry, email already exists.";
										Toast toast = Toast.makeText(context, text, duration);
										toast.show();
										exists = true;
										break;
									} else if (u.getUsername().equalsIgnoreCase(un) &&
											!(un.equalsIgnoreCase(
													MainActivity.currentUser.getUsername()))){
										text = "Sorry, username already exists.";
										Toast toast = Toast.makeText(context, text, duration);
										toast.show();
										exists = true;
										break;
									}
								}
							}

							if (!(exists)) {
								User user1 = new User(em, un, pw,
										MainActivity.currentUser.getRegistration());

								if (user1.equals(MainActivity.currentUser)){
									text = "You did not make any changes!";
									Toast toast = Toast.makeText(context, text, duration);
									toast.show();
								} else {
									db.updateUser(MainActivity.currentUser, user1);
									text = "Update Successful!";
									Toast toast = Toast.makeText(context, text, duration);
									toast.show();
									startActivity(new Intent(View_UserInformation.this,
											CourseFilterActivity.class));
								}

							}

						}

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                }

        });
    }


}
}
