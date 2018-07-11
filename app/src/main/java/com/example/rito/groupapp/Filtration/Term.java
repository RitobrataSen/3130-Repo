package com.example.rito.groupapp.Filtration;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.HashMap;

@IgnoreExtraProperties //only maps fields during serialization
public class Term implements Serializable {
    private String term_code;
    private String term_description;
    //public String uid;

    public Term(){
    }

    public Term(String term_code, String term_description, String uid){
        this.term_code = term_code;
        this.term_description = term_description;
        //this.uid = uid;
    }


    public String getTerm_code(){
        return this.term_code;
    }

    public String getTerm_description(){
        return this.term_description;
    }

    public void setTerm_code(String term_code){
        this.term_code = term_code;
    }

    public void setTerm_description(String term_description){
        this.term_description = term_description;
    }

    public String generatePath(){
        return String.format("TERMS/%s", this.term_code);
    }

    @Override
    public String toString(){
        //return String.format("(TermCode: %s, TermDescription: %s)", term_code, term_description);
        return String.format("%s (%s)", term_description, term_code);
    }

    @Exclude //ignores method from javadocs
    public HashMap<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("term_code", this.term_code);
        result.put("term_description", this.term_description);
        //result.put("uid", this.uid);
        return result;
    }

}