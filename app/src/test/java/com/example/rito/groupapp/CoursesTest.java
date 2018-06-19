package com.example.rito.groupapp;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


@RunWith(JUnit4.class)
public class CoursesTest{

        private static int testNum=0;

        private Courses c1= new Courses();

        @Test
        public void checkSet_GetCourseCode(){

            String no= "TY412";
            c1.SetNo(no);
            Assert.assertTrue(c1.GetCode().matches(no));
            testNum++;
        }
        @Test
        public void checkSet_GetCourseName(){
            String name= "Intro to Computer Science";
            c1.SetNam(name);
            Assert.assertTrue(c1.GetCname().matches(name));
            testNum++;
        }
        @Test
        public void checkSet_GetStartDate(){
            String SD= "June 2nd";
            c1.SetSd(SD);
            Assert.assertTrue(c1.GetSd().matches(SD));
            testNum++;
        }
        @Test
        public void checkSet_GetEndDate(){
            String ED= "August_2nd";
            c1.SetEd(ED);
            Assert.assertTrue(c1.GetEd().matches(ED));
            testNum++;
        }
        @Test
        public void checkSet_GetMax(){
            String max = "100";
            c1.SetMax(max);
            Assert.assertTrue(c1.GetMax().matches(max));
            testNum++;
        }
        @Test
        public void checkSet_GetInstructor(){
            String ins= "Dr.Sen";
            c1.SetInstructor(ins);
            Assert.assertTrue(c1.GetIns().matches(ins));
            testNum++;
        }

        @Test
        public void checkSet_GetStartTime(){
            String st= "2:00 pm";
            c1.SetStime(st);
            Assert.assertTrue(c1.GetSt().matches(st));
            testNum++;
        }
        @Test
        public void checkSet_GetEndTime(){
            String et= "4:00 pm";
            c1.SetEtime(et);
            Assert.assertTrue(c1.GetEt().matches(et));
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
