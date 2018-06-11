package com.example.rito.groupapp;//Project of courses to display the information of the courses

public class Courses {
    //Variables of courses
    //Can be extend if more information is needed
    private int num;
    private String Cname;
    private String Sdate;
    private String Edate;
    private String Insct;

    //empty constructor
    public Courses() {
    }

    //constructor
    public Courses(int num, String name, String Sd, String Ed, String Ins ){
        this.num = num;
        this.Cname = name;
        this.Sdate = Sd;
        this.Edate = Ed;
        this.Insct = Ins;
    }

    //Set methods
    //Use to assign the data when we receive information from firebase
    public void SetNo(int num){
        this.num = num;
    }

    public void SetNam(String Nam){
        this.Cname = Nam;
    }

    public void SetSd(String date){
        this.Sdate = date;
    }

    public void SetEd(String date){
        this.Edate = date;
    }

    public void SetIns(String name){
        this.Insct = name;
    }

    //Get methods
    //For get the information to display on screen
    public String GetCname(){
        return this.Cname;
    }

    public String GetSd(){
        return this.Sdate;
    }

    public String GetEd(){
        return this.Edate;
    }

    public String GetIns(){
        return this.Insct;
    }

    public int GetNum(){
        return this.num;
    }
}
