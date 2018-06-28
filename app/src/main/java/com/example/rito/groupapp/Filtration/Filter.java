package com.example.rito.groupapp.Filtration;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.*;

import com.example.rito.groupapp.R;
import com.example.rito.groupapp.ReadCourses;
import com.google.firebase.database.DatabaseReference;

public class Filter extends Activity implements AdapterView.OnItemSelectedListener {

    private Spinner sp;
    private String []terms = {"Fall","Winter","Summer","All"};

    private ReadCourses rc = new ReadCourses();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        sp = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(Filter.this,
                android.R.layout.simple_spinner_item,terms);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(this);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position==0){
            rc.getFall();

              startActivity(new Intent(Filter.this, ReadCourses.class));
        }
        if(position==1){
            rc.getWinter();
            startActivity(new Intent(Filter.this, ReadCourses.class));
        }
        if(position==2){
            rc.getSummer();
            startActivity(new Intent(Filter.this, ReadCourses.class));

        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        sp.setPrompt("Please select a term that you wish to view");
    }
}
