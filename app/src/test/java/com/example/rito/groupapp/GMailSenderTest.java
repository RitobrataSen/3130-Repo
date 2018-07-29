package com.example.rito.groupapp;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Unit tests for the GMailSender.java class. The getter/setter methods are tested for the
 * mailhost, user, and password attributes. The email properties are then tested to ensure that
 * they are mapped correctly after being assigned in the GMailSender constructor.
 *
 * @since 07-24-18
 * @author Shane, Gobii
 */

@RunWith(JUnit4.class)
public class GMailSenderTest {
    private static int testNum=0;
    private GMailSender gms = new GMailSender();

    @Test
    public void checkSet_GetMailhost(){
        String mHost = "smtp.live.com";
        gms.setMailhost(mHost);
        Assert.assertTrue(gms.getMailhost().matches(mHost));
        testNum++;
    }
    @Test
    public void checkSet_GetUser(){
        String user = "meep-moop@gmail.com";
        gms.setUser(user);
        Assert.assertTrue(gms.getUser().matches(user));
        testNum++;
    }
    @Test
    public void checkSet_GetPassword(){
        String pw = "secure-pw321";
        gms.setPassword(pw);
        Assert.assertTrue(gms.getPassword().matches(pw));
        testNum++;
    }

    //Remaining tests check the email Properties
    @Test
    public void checkTransportProtocol(){
        String actual_key = "mail.transport.protocol";
        String expected_val = "smtp";
        Assert.assertEquals(gms.getEmailProps().getProperty(actual_key),expected_val);
        testNum++;
    }
    @Test
    public void checkMailHost(){
        String actual_key = "mail.host";
        String expected_val = "smtp.gmail.com";
        Assert.assertEquals(gms.getEmailProps().getProperty(actual_key),expected_val);
        testNum++;
    }
    @Test
    public void checkSmtpAuth(){
        String actual_key = "mail.smtp.auth";
        String expected_val = "true";
        Assert.assertEquals(gms.getEmailProps().getProperty(actual_key),expected_val);
        testNum++;
    }
    @Test
    public void checkSmtpPort(){
        String actual_key = "mail.smtp.port";
        String expected_val = "465";
        Assert.assertEquals(gms.getEmailProps().getProperty(actual_key),expected_val);
        testNum++;
    }
    @Test
    public void checkSmtpSocketFactPort(){
        String actual_key = "mail.smtp.socketFactory.port";
        String expected_val = "465";
        Assert.assertEquals(gms.getEmailProps().getProperty(actual_key),expected_val);
        testNum++;
    }
    @Test
    public void checkSmtpSocketFactClass(){
        String actual_key = "mail.smtp.socketFactory.class";
        String expected_val = "javax.net.ssl.SSLSocketFactory";
        Assert.assertEquals(gms.getEmailProps().getProperty(actual_key),expected_val);
        testNum++;
    }
    @Test
    public void checkSmtpSocketFactFallback(){
        String actual_key = "mail.smtp.socketFactory.fallback";
        String expected_val = "false";
        Assert.assertEquals(gms.getEmailProps().getProperty(actual_key),expected_val);
        testNum++;
    }
    @Test
    public void checkSmtpQuitWait(){
        String actual_key = "mail.smtp.quitwait";
        String expected_val = "false";
        Assert.assertEquals(gms.getEmailProps().getProperty(actual_key),expected_val);
        testNum++;
    }

    @After
    public void runAfterEach() {
        System.out.println("Test number: " + testNum + " passed");
    }
    @AfterClass
    public static void runAfterAll(){
        System.out.print("All " + testNum + " passed");
    }


}
