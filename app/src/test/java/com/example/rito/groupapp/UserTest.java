package com.example.rito.groupapp;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.HashMap;


@RunWith(JUnit4.class)

public class UserTest {

    private static int testNum=0;

    HashMap<String, Boolean> regist = new HashMap<>();

    private User user1 = new User("Hello@gmail.com","Ritobrata","password", regist);


        @Test
        public void checkSet_GetUserName(){

            String n= "asdfgh";
            user1.setUsername(n);
            Assert.assertTrue(user1.getUsername().matches(n));
            testNum++;
        }
        @Test
        public void checkSet_GetEmail(){
            String e= "asdfgh";
            user1.setEmail(e);
            Assert.assertTrue(user1.getEmail().matches(e));
            testNum++;
        }
        @Test
        public void checkSet_GetPass(){
            String pass= "asdfgh";
            user1.setPassword(pass);
            Assert.assertTrue(user1.getPassword().matches(pass));
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


