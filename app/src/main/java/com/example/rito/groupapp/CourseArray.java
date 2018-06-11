package com.example.rito.groupapp;

import java.util.ArrayList;

public class CourseArray {
    public ArrayList<Courses> addCourse (ArrayList<Courses> CL, Courses C){
        CL.add(C);
        return CL;
    }

    public static void main (String[] args){
        ArrayList<Courses> list = new ArrayList<>();
        Courses c1 = new Courses (23345, "Networking", "May.7", "Aug.11", "Aziz");
        Courses c2 = new Courses ();
        c2.SetNo(19527);
        c2.SetNam("PPL");
        c2.SetSd("May.7");
        c2.SetEd("Aug.11");
        c2.SetIns("Alex");
        list.add(c1);
        list.add(c2);
    }
}
