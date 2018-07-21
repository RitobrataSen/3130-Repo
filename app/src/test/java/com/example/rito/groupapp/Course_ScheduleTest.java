package com.example.rito.groupapp;

import com.example.rito.groupapp.old.Course_Schedule;


import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.HashMap;

@RunWith(JUnit4.class)

/**
 * This is a JUnit test for the CourseSchedule.Java class. It ensures that all the methods that are going
 * to have a certain purpose and is meeting the expectation.
 *
 * @author    Ritobrata Sen, Yuhao Yu, Gobii Vivagananda
 * @since     06-26-18
 */
public class Course_ScheduleTest {
    private static int testNum=0;
    private Course_Schedule cs1 = new Course_Schedule();

    @Test
    public void checkSet_GetCRN(){
        String crn = "C1234";
        cs1.setCRN(crn);
        Assert.assertTrue(cs1.getCRN().matches(crn));
        testNum++;
    }
    @Test
    public void checkSet_GetStartDate(){
        String sd = "Jan 10, 2019";
        cs1.setStartDate(sd);
        Assert.assertTrue(cs1.getStartDate().matches(sd));
        testNum++;
    }
    @Test
    public void checkSet_GetEndDate(){
        String ed = "April 10, 2019";
        cs1.setEndDate(ed);
        Assert.assertTrue(cs1.getEndDate().matches(ed));
        testNum++;
    }
    @Test
    public void checkSet_GetStartTime(){
        String st = "10.35 am";
        cs1.setStartTime(st);
        Assert.assertTrue(cs1.getStartTime().matches(st));
        testNum++;
    }
    @Test
    public void checkSet_GetEndTime(){
        String et = "11.25 am";
        cs1.setEndTime(et);
        Assert.assertTrue(cs1.getEndTime().matches(et));
        testNum++;
    }
    @Test
    public void checkSet_GetMon(){
        String mon = "mon";
        cs1.setMon(mon);
        Assert.assertTrue(cs1.getMon().matches(mon));
        testNum++;
    }
    @Test
    public void checkSet_GetTue(){
        String tue = "tue";
        cs1.setTue(tue);
        Assert.assertTrue(cs1.getTue().matches(tue));
        testNum++;
    }
    @Test
    public void checkSet_GetWed(){
        String wed = "wed";
        cs1.setWed(wed);
        Assert.assertTrue(cs1.getWed().matches(wed));
        testNum++;
    }
    @Test
    public void checkSet_GetThu(){
        String thu = "thu";
        cs1.setThu(thu);
        Assert.assertTrue(cs1.getThu().matches(thu));
        testNum++;
    }
    @Test
    public void checkSet_GetFri(){
        String fri = "fri";
        cs1.setFri(fri);
        Assert.assertTrue(cs1.getFri().matches(fri));
        testNum++;
    }
    @Test
    public void checkSet_GetLocation(){
        String loc = "Cheb";
        cs1.setLocation(loc);
        Assert.assertTrue(cs1.getLocation().matches(loc));
        testNum++;
    }
    @Test
    public void checkToMap(){
        HashMap<String,Object> hm = new HashMap<>();
        hm = cs1.toMap();
        Assert.assertTrue(hm.equals(cs1.toMap()));
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
