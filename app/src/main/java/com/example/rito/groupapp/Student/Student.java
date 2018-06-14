package com.example.rito.groupapp.Student;
import com.example.rito.groupapp.Course.Course;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Student {

    private String username;
    private String email;
    private String password;
    private static ArrayList<Course> a1;

    public Student(String name, String email,String password){
        this.username =name;
        this.email=email;
        this.password=password;
        this.a1=a1;
        a1 = new ArrayList<Course>();

    }
    public void setName(String username){
        this.username = username;

    }
    public void setEmail(String email){
        this.email=email;

    }
    public void setPassword(String password){
        this.password=password;
    }
    public void setCourses(ArrayList<Course> c2){
        a1=c2;
    }
    public String getName(){return this.username;}
    public String getPassword(){return this.password;}
    public String getEmail(){return this.email;}

    public ArrayList<Course> getCourse() {
        return a1;
    }

    public  boolean addCourse(Course c){


        a1.add(c);
        if(a1.contains(c))
            return true;
        else
            return false;

    }

}
