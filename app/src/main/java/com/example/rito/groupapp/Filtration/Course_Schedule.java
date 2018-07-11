package com.example.rito.groupapp.Filtration;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.HashMap;

@IgnoreExtraProperties //only maps fields during serialization
public class Course_Schedule implements Serializable {

    //fields
    private String crn;
    private String start_date;
    private String end_date;
    private String start_time;
    private String end_time;
    private String mon; //refactor to boolean
    private String tue; //refactor to boolean
    private String wed; //refactor to boolean
    private String thu; //refactor to boolean
    private String fri; //refactor to boolean
    private String location;

    //constructors
    public Course_Schedule() {
    }

    public Course_Schedule(String crn,
                           String start_date, String end_date, String start_time, String end_time,
                           String mon, String tue, String wed, String thu, String fri,
                           String location) {
        this.crn = crn;
        this.start_date = start_date;
        this.end_date = end_date;
        this.start_time = start_time;
        this.end_time = end_time;
        this.mon = mon;
        this.tue = tue;
        this.wed = wed;
        this.thu = thu;
        this.fri = fri;
        this.location = location;
    }

    //methods
    //getter/ setter methods
    public String getCRN() {
        return this.crn;
    }

    public String getStartDate() {
        return this.start_date;
    }

    public String getEndDate() {
        return this.end_date;
    }

    public String getStartTime() {
        return this.start_time;
    }

    public String getEndTime() {
        return this.end_time;
    }

    public String getMon() {
        return this.mon;
    }

    public String getTue() {
        return this.tue;
    }

    public String getWed() {
        return this.wed;
    }

    public String getThu() {
        return this.thu;
    }

    public String getFri() {
        return this.fri;
    }

    public String getLocation() {
        return this.location;
    }

    public void setCRN(String crn) {
        this.crn = crn;
    }

    public void setStartDate(String start_date) {
        this.start_date = start_date;
    }

    public void setEndDate(String end_date) {
        this.end_date = end_date;
    }

    public void setStartTime(String start_time) {
        this.start_time = start_time;
    }

    public void setEndTime(String end_time) {
        this.end_time = end_time;
    }

    public void setMon(String mon) {
        this.mon = mon;
    }

    public void setTue(String tue) {
        this.tue = tue;
    }

    public void setWed(String wed) {
        this.wed = wed;
    }

    public void setThu(String thu) {
        this.thu = thu;
    }

    public void setFri(String fri) {
        this.fri = fri;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String generatePath() {
        return String.format("COURSE_SCHEDULE/%s", this.crn);
    }

    @Override
    public String toString() {
        //return String.format("(TermCode: %s, TermDescription: %s)", term_code, term_description);
        return String.format("%s\n%s - %s\n%s - %s\nM:%s T:%s W:%s R:%s F:%s\n(%s)",
                this.crn,
                this.start_date, this.end_date, this.start_time, this.end_time,
                this.mon, this.tue, this.wed, this.thu, this.fri,
                this.location);
    }


    @Exclude //ignores method from javadocs
    public HashMap<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("crn", this.crn);
        result.put("start_date", this.start_date);
        result.put("end_date", this.end_date);
        result.put("start_time", this.start_time);
        result.put("end_time", this.end_time);
        result.put("mon", this.mon);
        result.put("tue", this.tue);
        result.put("wed", this.wed);
        result.put("thu", this.thu);
        result.put("fri", this.fri);
        result.put("location", this.location);
        return result;
    }

}