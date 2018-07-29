package com.example.rito.groupapp;


import com.example.rito.groupapp.old.Student;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.HashMap;

@RunWith(JUnit4.class)

/**
 * This is a JUnit test for the Student.Java class. It ensures that all the methods that are going
 * to have a certain purpose and is meeting the expectation.
 *
 * @author  Ritobrata Sen, Yuhao Yu, Gobii Vivagananda
 * @since   06-18-18
 */
public class StudentTest {
    private static int testNum=0;
    private Student s1 = new Student();

    @Test
    public void checkSet_GetEmail(){
        String email = "Hello@gmail.com";
        s1.setEmail(email);
        Assert.assertTrue(s1.getEmail().matches(email));
        testNum++;
    }
    @Test
    public void checkSet_GetUsername(){
        String username = "Max";
        s1.setUsername(username);
        Assert.assertTrue(s1.getUsername().matches(username));
        testNum++;
    }
    @Test
    public void checkSet_GetPassword(){
        String pass = "Hello1234";
        s1.setPassword(pass);
        Assert.assertTrue(s1.getPassword().matches(pass));
        testNum++;
    }
    @Test
    public void checkSet_GetRegistration(){
        HashMap h2 = new HashMap();
        s1.setRegistration(h2);
        Assert.assertTrue(s1.getRegistration().equals(h2));
        testNum++;
    }
    @Test
    public void checkToMap(){
        HashMap<String,Object> hm = new HashMap<>();
        hm = s1.toMap();
        Assert.assertTrue(hm.equals(s1.toMap()));
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
