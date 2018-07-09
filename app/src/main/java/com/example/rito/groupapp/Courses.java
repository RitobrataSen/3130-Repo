package com.example.rito.groupapp;

public class Courses {
    //Variables of courses
    //Can be extend if more information is needed
    private String course_code;
    private String course_name;
    private String end_date;
    private String end_time;
    private String instructor;
    private String max;
    private String start_date;
    private String start_time;

    //empty constructor
    public Courses() {
    }

    //constructor
    public Courses(String num, String name, String Sd, String Ed, String Ins, String max ){
        this.course_code= num;
        this.course_name = name;
        this.start_date = Sd;
        this.end_date = Ed;
        this.instructor = Ins;
        this.max = max;
    }

    //Set methods
    //Use to assign the data when we receive information from firebase
    public void SetNo(String num){
        this.course_code = num;
    }

    public void SetNam(String Nam){
        this.course_name = Nam;
    }

    public void SetSd(String date){
        this.start_date = date;
    }

    public void SetEd(String date){
        this.end_date = date;
    }

    public void SetMax(String name){
        this.max = name;
    }

    public void SetStime(String St) {
        this.start_time= St;
    }

    public void SetEtime(String Et){
        this.end_time = Et;
    }

    public void SetInstructor(String ins) {this.instructor=ins;}

    //Get methods
    //For get the information to display on screen
    public String GetCname(){
        return this.course_name;
    }

    public String GetSd(){
        return this.start_date;
    }

    public String GetEd(){
        return this.end_date;
    }

    public String GetIns(){
        return this.instructor;
    }

    public String GetMax() {
        return this.max;
    }

    public String GetCode(){
        return this.course_code;
    }

    public String GetSt(){
        return this.start_time;
    }

    public String GetEt(){
        return this.end_time;
    }

    public int compareTo(Courses other){
        return this.start_time.compareTo(other.GetSt());
    }
}