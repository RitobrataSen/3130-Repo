package com.example.rito.groupapp.Filtration;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.HashMap;

@IgnoreExtraProperties //only maps fields during serialization
public class Subject implements Serializable {
    private String subject_code;
    private String subject_description;
    private String term_code;

    public Subject(){
    }

    public Subject(String subject_code, String subject_description, String term_code){
        this.subject_code = subject_code;
        this.subject_description = subject_description;
        this.term_code = term_code;
    }

    public String getSubject_code(){
        return this.subject_code;
    }
    public String getSubject_description(){
        return this.subject_description;
    }
    public String getTerm_code(){
        return this.term_code;
    }

    public void setSubject_code(String subject_code){
        this.subject_code = subject_code;
    }
    public void setSubject_description(String subject_description){
        this.subject_description = subject_description;
    }
    public void setTerm_code(String term_code){
        this.term_code = term_code;
    }

    public String generatePath(){
        return String.format("SUBJECTS/%s/%s", this.term_code, this.subject_code);
    }

    @Override
    public String toString(){
        //return String.format("(TermCode: %s, TermDescription: %s)", term_code, term_description);
        return String.format("%s (%s)", this.subject_description, this.subject_code);
    }

    @Exclude //ignores method from javadocs
    public HashMap<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("subject_code", this.subject_code);
        result.put("subject_description", this.subject_description);
        result.put("term_code", this.term_code);
        return result;
    }
}