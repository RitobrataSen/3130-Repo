package com.example.rito.groupapp;
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class Navigation_spinner extends Activity implements AdapterView.OnItemSelectedListener {
    private Spinner sp;
    private String[] terms = {"Courses","Calendar","Log Out"};

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.spinner_main);

            sp = (Spinner)findViewById(R.id.main_spinner);
            ArrayAdapter<String>adapter = new ArrayAdapter<String>(Navigation_spinner.this,
                    android.R.layout.simple_spinner_item,terms);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            sp.setAdapter(adapter);

            sp.setOnItemSelectedListener(this);
        }


        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if(position==1) {
//                Toast.makeText(parent.getContext(), "Course selected", Toast.LENGTH_LONG).show();
                setContentView(R.layout.feature_incomplete_page);
            }
            if(position==2) {
//                Toast.makeText(parent.getContext(), "Course selected", Toast.LENGTH_LONG).show();
                setContentView(R.layout.feature_incomplete_page);

            }
            if(position==3) {
//                Toast.makeText(parent.getContext(), "Course selected", Toast.LENGTH_LONG).show();
                setContentView(R.layout.feature_incomplete_page);

            }

        }


    @Override
    public void onNothingSelected(AdapterView<?> parent){
            sp.setPrompt("Please select something");
    }
}


