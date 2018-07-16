package com.example.rito.groupapp;

import com.example.rito.groupapp.Course_Enrollment;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.HashMap;


@RunWith(JUnit4.class)
/**
 * This is a JUnit test for the Course_Enrollment.Java class. It ensures that all the methods that are going
 * to have a certain purpose and is meeting the expectation.
 *
 * @author    Ritobrata Sen, Yuhao Yu, Gobii Vivagananda
 * @since     06-26-18
 */
public class Course_EnrollmentTest {

    private static int testNum=0;

    private Course_Enrollment ce1 = new Course_Enrollment();


    @Test
    public void checkSet_GetCrn(){
        String crn = "102344";
        ce1.setCrn(crn);
        Assert.assertTrue(ce1.getCrn().matches(crn));
        testNum++;
    }
    @Test
    public void checkSet_GetMax(){
        String max = "100";
        ce1.setMax(max);
        Assert.assertTrue(ce1.getMax().matches(max));
        testNum++;
    }
    @Test
    public void checkSet_GetCurr(){
        String curr = "hello";
        ce1.setCur(curr);
        Assert.assertTrue(ce1.getCur().matches(curr));
        testNum++;
    }
    @Test
    public void checkSet_GetEnrollment(){
        HashMap h2 = new HashMap();
        ce1.setEnrollment(h2);
        Assert.assertTrue(ce1.getEnrollment().equals(h2));
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
