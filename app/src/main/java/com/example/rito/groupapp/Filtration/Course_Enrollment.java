package com.example.rito.groupapp.Filtration;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.HashMap;


/*
 This class

 Authors:
 Ritobrata Sen,
 Yuhao Yu,
 Gobii Vivagananda

 Date:
 June 30th, 2018


 */
@IgnoreExtraProperties //only maps fields during serialization
public class Course_Enrollment implements Serializable {

    //fields
    private String crn;
    private String max;
    private String cur;
    private HashMap<String, Boolean> enrollment;

    //constructors
    public Course_Enrollment() {
    }

    public Course_Enrollment(String crn, String max, String cur,
                             HashMap<String, Boolean> enrollment){
        this.crn= crn;
        this.max = max;
        this.cur = cur;
        this.enrollment = new HashMap<>();
        this.enrollment.putAll(enrollment);
    }

    //methods
    public String getCrn(){
        return this.crn;
    }

    public String getMax(){
        return this.max;
    }

    public String getCur(){
        return this.cur;
    }

    public HashMap<String, Boolean> getEnrollment(){
        return this.enrollment;
    }

    public void setCrn(String crn){
        this.crn = crn;
    }
    public void setMax(String max){
        this.max = max;
    }
    public void setCur(String cur){
        this.cur = cur;
    }
    public void setEnrollment(HashMap<String, Boolean> enrollment){
        //to add/ remove individual pairs get entire hash map first then call
        //hash map functions: hashMap.put(k, v) or hashMap.remove(k)
        this.enrollment = new HashMap<>();
        this.enrollment.putAll(enrollment);
    }

    public String generatePath(){
        return String.format("COURSE_ENROLLEMENT/%s", this.crn);
    }

    @Exclude //ignores method from javadocs
    public HashMap<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("crn", this.crn);
        result.put("max", this.max);
        result.put("cur", this.cur);
        result.put("enrollment", this.enrollment);
        return result;
    }

}