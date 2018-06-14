package com.example.rito.groupapp;

import com.example.rito.groupapp.Course.Course;
import com.example.rito.groupapp.Student.Student;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.Assert;

import java.util.ArrayList;

@RunWith(JUnit4.class)

public class StudentTest {
    private static int testNum=0;

    private Student s1 = new Student("Ritobrata","Hello@gmail.com","password");


    @Test
    public void addCourse(){
        Course c1 = new Course();
        Assert.assertTrue(s1.addCourse(c1));
        testNum++;
    }

    @Test
    public void checkSet_GetName(){

        String n= "asdfgh";
        s1.setName(n);
        Assert.assertTrue(s1.getName().matches(n));
        testNum++;
    }
    @Test
    public void checkSet_GetEmail(){
        String e= "asdfgh";
        s1.setEmail(e);
        Assert.assertTrue(s1.getEmail().matches(e));
        testNum++;
    }
    @Test
    public void checkSet_GetPass(){
        String pass= "asdfgh";
        s1.setPassword(pass);
        Assert.assertTrue(s1.getPassword().matches(pass));
        testNum++;
    }

    @Test
    public void checkSet_GetCourse(){
        ArrayList<Course> c2 = new ArrayList<Course>();
        s1.setCourses(c2);
        Assert.assertTrue(s1.getCourse() == c2);
        testNum++;
    }
    @After
    public void runAfterEach() {
        System.out.println("Test number: "+testNum + " passed");
    }
    @AfterClass
    public static void runAfterAll(){
        System.out.print("All " + testNum + " passed");
    }
}
