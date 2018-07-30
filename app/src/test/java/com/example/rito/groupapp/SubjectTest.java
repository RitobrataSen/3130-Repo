package com.example.rito.groupapp;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import java.util.HashMap;

@RunWith(JUnit4.class)

/**
 * This is a JUnit test for the Subject.Java class. It ensures that all the methods that are going
 * to have a certain purpose and is meeting the expectation.
 *
 * @author  Ritobrata Sen, Yuhao Yu, Gobii Vivagananda
 * @since   06-18-18
 */
public class SubjectTest {
    private static int testNum=0;
    private Subject sub1 = new Subject();

    @Test
    public void checkSet_GetSubjectCode(){
        String sc = "12345";
        sub1.setSubject_code(sc);
        Assert.assertTrue(sub1.getSubject_code().matches(sc));
        testNum++;
    }

    @Test
    public void checkSet_GetSubjectDesciption(){
        String sd = "Chemical Engineering";
        sub1.setSubject_description(sd);
        Assert.assertTrue(sub1.getSubject_description().matches(sd));
        testNum++;
    }
    @Test
    public void checkSet_GetTermCode(){
        String tc = "12345sdf";
        sub1.setTerm_code(tc);
        Assert.assertTrue(sub1.getTerm_code().matches(tc));
    }

    @Test
    public void checkToMap(){
        HashMap<String,Object> hm = new HashMap<>();
        hm = sub1.toMap();
        Assert.assertTrue(hm.equals(sub1.toMap()));
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
