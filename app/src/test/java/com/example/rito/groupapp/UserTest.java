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
 This is a JUnit test for the User.Java class. It ensures that all the methods that are going
 to have a certain purpose and is meeting the expectation.

 Authors:
 Ritobrata Sen,
 Yuhao Yu,
 Gobii Vivagananda

 Date:
 June 26th, 2018


 */
public class UserTest {

    private static int testNum=0;
    private HashMap h1 = new HashMap();

    private User user1 = new User("Hello@gmail.com","Hello","123",h1);


        @Test
        public void checkSet_GetEmail(){
            String email = "Hi@gmail.com";
            user1.setEmail(email);
            Assert.assertTrue(user1.getEmail().matches(email));
            testNum++;
        }
        @Test
        public void checkSet_GetUsername(){
            String username = "Joe";
            user1.setUsername(username);
            Assert.assertTrue(user1.getUsername().matches(username));
            testNum++;
        }
        @Test
        public void checkSet_GetPassword(){
            String password = "password1234";
            user1.setPassword(password);
            Assert.assertTrue(user1.getPassword().matches(password));
            testNum++;
        }
        @Test
        public void checkSet_GetHashMap(){
            HashMap h2 = new HashMap();
            user1.setRegistration(h2);
            Assert.assertTrue(user1.getRegistration().equals(h2));
            testNum++;
        }
        @Test
        public void checkToMap(){
            HashMap<String,Object> hm = new HashMap<>();
            hm = user1.toMap();
            Assert.assertTrue(hm.equals(user1.toMap()));
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


