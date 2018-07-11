package com.example.rito.groupapp;
import com.example.rito.groupapp.Filtration.*;


import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.HashMap;

@RunWith(JUnit4.class)
/*
 This is a JUnit test for the CRN.Java class. It ensures that all the methods that are going
 to have a certain purpose and is meeting the expectation.

 Authors:
 Ritobrata Sen,
 Yuhao Yu,
 Gobii Vivagananda

 Date:
 June 26th, 2018


 */
public class CRNTest {
    private static int testNum=0;
    private CRN crn1 = new CRN();


    @Test
    public void checkSet_GetCrn(){
        String crn = "10234";
        crn1.setCrn(crn);
        Assert.assertTrue(crn1.getCrn().matches(crn));
        testNum++;
    }
    @Test
    public void checkSet_GetTermCode(){
        String TC= "1092010";
        crn1.setTerm_code(TC);
        Assert.assertTrue(crn1.getTerm_code().matches(TC));
        testNum++;
    }
    @Test
    public void checkSet_GetCourseCode(){
        String cc = "122234";
        crn1.setTerm_code(cc);
        Assert.assertTrue(crn1.getCourse_code().matches(cc));
        testNum++;
    }
    @Test
    public void checkSet_GetSectionNumber(){
        String sn = "22";
        crn1.setSection_number(sn);
        Assert.assertTrue(crn1.getSection_number().matches(sn));
        testNum++;
    }
    @Test
    public void checkSet_GetSectionType(){
        String st = "Fall";
        crn1.setSection_type(st);
        Assert.assertTrue(crn1.getSection_type().matches(st));
        testNum++;
    }
    @Test
    public void checkSet_GetInstructor(){
        String ins = "Dr.Sen";
        crn1.setInstructor(ins);
        Assert.assertTrue(crn1.getInstructor().matches(ins));
        testNum++;
    }
    @Test
    public void checkToMap(){
        HashMap<String,Object> hm = new HashMap<>();
        hm = crn1.toMap();
        Assert.assertTrue(hm.equals(crn1.toMap()));
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
