package com.example.rito.groupapp;

import android.support.annotation.RequiresPermission;

import com.example.rito.groupapp.Filtration.ReadCourse;

import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.Assert;

@RunWith(JUnit4.class)


public class ReadCourseTest {
    private static int testNum =0;
    private ReadCourse rc1 = new ReadCourse("www.google.com");

    @Test
    public void checkSet_GetDataBaseUrl(){
        String e= "asdfgh";
        rc1.setDatabaseUrl(e);
        Assert.assertTrue(rc1.getDataBaseUrl().matches(e));
        testNum++;
    }
    @Test
    public void check_GetFall(){

    }
    @Test
    public void check_GetSummer(){

    }
    @Test
    public void check_GetWinter(){

    }

}
