package com.example.rito.groupapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.graphics.Color;
import android.widget.TableRow;

import java.util.ArrayList;


/**
 * ReadCourses for reading all courses under a specific path in the firebase
 * and display them as a table view
 * @author Yuze Qu & Yuhao Hu
 * @since 2018-06-20
 */
public class ReadCourses extends AppCompatActivity {
    public DatabaseReference mCourseReference;

    //defined the ArrayList for Courses object
    public ArrayList<Courses> list = new ArrayList<Courses>();

    //defined table display variables
    public TableLayout tableLayout;
    public TableRow tableRow;
    public TextView id;
    public Button logout;
    public Button cal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_load);

        //Initialize Database
        //Setting the path of the database to read
        mCourseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://group-10-9598f.firebaseio.com").child("TERMS").child("201830").child("SUBJECTS").child("CSCI").child("COURSES");

        //Defined variables for the display table
        tableLayout =  this.findViewById(R.id.tableLayout);
        id = new TextView(this);
        tableRow = new TableRow(this);
        TableRow header = new TableRow(this);
        TextView h1 = new TextView(this);
        logout = findViewById(R.id.logout_button1);
        cal = findViewById(R.id.cal_button);

        //create a header for the table
        //text view crn
        h1.setText(String.valueOf("      CRN      "));
        h1.setBackgroundColor(0xffABCDEF);
        h1.setPadding(32, 32, 32, 32);

        //text view Name
        TextView h2 = new TextView(this);
        h2.setText(String.valueOf("       Name       "));
        h2.setBackgroundColor(0xffABCDEF);
        h2.setPadding(32, 32, 32, 32);

        //empty header, for the add button
        TextView h3 = new TextView(this);
        h3.setText(String.valueOf("     Register    "));
        h3.setBackgroundColor(0xffABCDEF);
        h3.setPadding(32, 32, 32, 32);

        //add all the header to the new row
        header.addView(h1);
        header.addView(h2);
        header.addView(h3);

        //add the table row to the table
        tableLayout.addView(header);

        logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(ReadCourses.this, Logout_Activity.class));
            }
        });
        cal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(ReadCourses.this, CalendarView.class));
            }
        });

        mCourseReference.addValueEventListener( new ValueEventListener() {
            //Reading from database
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Read all the course under the database reference
                //This for loop will keep reading data as long as there are unread value
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    //using set methods to change the value of the course and add it to the list
                    Courses course = new Courses();
                    course.SetNam(ds.child("course_name").getValue().toString());
                    course.SetNo(ds.child("course_code").getValue().toString());
                    list.add(course);
                }

                for(int i=0; i<list.size(); i++){
                    //call the method to write the table and add to the tableLayout
                    addRow(list.get(i));
                }
            }

            //onCancelled method for cancel the loading process
            @Override
            public void onCancelled (DatabaseError databaseError){
            }
        });
    }

    //method to add the table row
    public void addRow(Courses c){
        TableRow tableRow = new TableRow(this);
        TextView id = new TextView(this);
        TextView name = new TextView(this);

        id.setText(String.valueOf("  "+c.GetCode()+" "));
        id.setPadding(32, 32, 32, 32);
        id.setBackgroundColor(0xffff346d);
        id.setTextColor(Color.WHITE);

        name.setText(String.valueOf(c.GetCname()));
        name.setPadding(32, 32, 32, 32);
        name.setBackgroundColor(0xffFFDBAC);

        Button button = new Button(this);
        button.setText("Add");

        tableRow.addView(id);
        tableRow.addView(name);
        tableRow.addView(button);

        tableLayout.addView(tableRow);

    }
}
