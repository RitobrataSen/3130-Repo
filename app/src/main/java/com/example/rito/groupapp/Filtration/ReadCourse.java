package com.example.rito.groupapp.Filtration;

import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.rito.groupapp.Courses;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ReadCourse {
    private DatabaseReference mr;
    private String url;
    private ArrayList<Courses> courses;



//    constructor method
    public ReadCourse(String url){
        mr = FirebaseDatabase.getInstance().getReferenceFromUrl(url);

    }
//    set and get methods

    public void setDatabaseUrl(String url){
        this.url = url;
        mr = FirebaseDatabase.getInstance().getReferenceFromUrl(url);
    }
    public String getDataBaseUrl(){
        return this.url;
    }
    public void getSummer(){
        mr = mr.child("TERMS").child("201830");
    }
    public void getFall(){
        mr = mr.child("TERMS").child("201910");
    }
    public void getWinter(){
        mr = mr.child("TERMS").child("201920");
    }

}
