package com.example.rito.groupapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Detail_Page extends AppCompatActivity {

    private ArrayList<String> list;
    private String selectedCRN = CalendarView.selectedCRN;
    private ListView courseInfo;
    private String date;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://group-10-9598f.firebaseio.com");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail__page);

        courseInfo = (ListView)findViewById(R.id.listView);

        final Query courseQ = databaseReference.child("CRN_Data").child(selectedCRN);
        courseQ.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.add("Course Code: " + dataSnapshot.child("course_code").getValue().toString());
                list.add("Course Name: " + dataSnapshot.child("course_name").getValue().toString());
                list.add("CRN: " + dataSnapshot.child("crn").getValue().toString());
                list.add("Start Date: " + dataSnapshot.child("start_date").getValue().toString());
                list.add("End Date: " + dataSnapshot.child("end_date").getValue().toString());
                list.add("Location: " + dataSnapshot.child("location").getValue().toString());
                if (dataSnapshot.child("mon").getValue().toString() == "1"){
                    date = date + " MON ";
                }
                else if(dataSnapshot.child("thu").getValue().toString() == "1"){
                    date = date + " THU ";
                }
                else if(dataSnapshot.child("wed").getValue().toString() == "1"){
                    date = date + " WED ";
                }
                else if(dataSnapshot.child("tue").getValue().toString() == "1"){
                    date = date + " TUE ";
                }
                else if(dataSnapshot.child("fir").getValue().toString() == "1"){
                    date = date + " FRI ";
                }
                list.add("Course Date: " + date );
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(Detail_Page.this, R.layout.activity_detail__page, list);
                courseInfo.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
