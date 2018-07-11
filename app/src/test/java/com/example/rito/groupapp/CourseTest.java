package com.example.rito.groupapp;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.HashMap;


@RunWith(JUnit4.class)
/*
 This is a JUnit test for the Courses.Java class. It ensures that all the methods that are going
 to have a certain purpose and is meeting the expectation.

 Authors:
 Ritobrata Sen,
 Yuhao Yu,
 Gobii Vivagananda

 Date:
 June 26th, 2018
 */
public class CourseTest{

        private static int testNum=0;

        private Course c1= new Course();

        @Test
        public void checkSet_GetCourseCode(){

            String no= "TY412";
            c1.setCourse_code(no);
            Assert.assertTrue(c1.getCourse_code().matches(no));
            testNum++;
        }
        @Test
        public void checkSet_GetCourseName(){
            String name= "Intro to Computer Science";
            c1.setCourse_name(name);
            Assert.assertTrue(c1.getCourse_name().matches(name));
            testNum++;
        }
        @Test
        public void checkSet_GetSubjectCode(){
            String SC= "V1234";
            c1.setSubject_code(SC);
            Assert.assertTrue(c1.getSubject_code().matches(SC));
            testNum++;
        }

        @Test
        public void checkSet_GetTermCode(){
            String TC= "1092010";
            c1.setTerm_code(TC);
            Assert.assertTrue(c1.getTerm_code().matches(TC));
            testNum++;
        }
        @Test
        public void checkHasSupplement(){
            boolean supp = true;

            c1.setHas_supplement(supp);
            Assert.assertTrue(c1.getHas_supplement());
            testNum++;
        }
        @Test
        public void checkEquals(){
            Course c2 = new Course();
            Assert.assertTrue(c1.equals(c2));
            testNum++;

        }
        @Test
        public void checkToString(){
            String s = c1.toString();
            Assert.assertTrue(c1.toString().equals(s));
            testNum++;
        }
        @Test
        public void checkToString2(){
            String s = c1.toString2();
            System.out.println(s);
            System.out.println(c1.toString2());
            Assert.assertTrue(c1.toString2().matches(s));
            testNum++;

        }
        @Test
        public void checkHashMap(){
            HashMap newMap = new HashMap();
            newMap = c1.toMap();
            Assert.assertTrue(c1.toMap().equals(newMap));
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
