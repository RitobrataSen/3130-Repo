package com.example.rito.groupapp;

import com.example.rito.groupapp.*;


import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.HashMap;

@RunWith(JUnit4.class)
/*
 This is a JUnit test for the Term.Java class. It ensures that all the methods that are going
 to have a certain purpose and is meeting the expectation.

 Authors:
 Ritobrata Sen,
 Yuhao Yu,
 Gobii Vivagananda

 Date:
 June 26th, 2018


 */
public class TermTest {
    private static int testNum=0;
    private Term t1 = new Term();
    @Test
    public void checkSet_GetTermCode(){
        String tc = "12345tsdf";
        t1.setTerm_code(tc);
        Assert.assertTrue(t1.getTerm_code().matches(tc));
        testNum++;
    }
    @Test
    public void checkSet_GetTermDescription(){
        String td = "Maths";
        t1.setTerm_description(td);
        Assert.assertTrue(t1.getTerm_description().matches(td));
        testNum++;
    }
    @Test
    public void checkToMap(){
        HashMap<String,Object> hm = new HashMap<>();
        hm = t1.toMap();
        Assert.assertTrue(hm.equals(t1.toMap()));
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
