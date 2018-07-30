package com.example.rito.groupapp;

import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.Assert;

@RunWith(JUnit4.class)

public class ExampleUnitTest {

    String username = "aaaaaa>aaa";
    String password = "aaaaa$aaaa";
    static int c = 0;

    @Test
    public void min_user_length() {
        Assert.assertTrue(username.length()>7);
        c++;
    }
    @Test
    public void min_pw_length() {
        Assert.assertTrue(password.length()>7);
        c++;
    }

    @AfterClass
    public static void run_once() {
        System.out.println(c + "/4 passed");
    }
}