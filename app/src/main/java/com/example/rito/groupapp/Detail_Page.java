package com.example.rito.groupapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Detail_Page extends CalendarView {

    private Course_Schedule course;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://group-10-9598f.firebaseio.com");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail__page);
        final Query courseQ = databaseReference.child("COURSE_SCHEDULE");
        courseQ.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                course = dataSnapshot.child(CalendarView.selectedCRN);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
