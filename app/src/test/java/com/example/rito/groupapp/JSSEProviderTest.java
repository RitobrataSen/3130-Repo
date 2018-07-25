package com.example.rito.groupapp;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Unit tests for the JSSEProvider.java class. There are no private variables within the tested
 * class; tests here are verifying that the email properties specified in JSSEProvider match.
 *
 * @since 07-24-18
 * @author Shane, Gobii
 */

@RunWith(JUnit4.class)
public class JSSEProviderTest {
    private static int testNum=0;
    private JSSEProvider jsp = new JSSEProvider();

    //These 4 tests are for the different key values of the Access Controller
    @Test
    public void testKey1(){
        String key1_SSLContextTLS = "SSLContext.TLS";
        String expected_val1 = "org.apache.harmony.xnet.provider.jsse.SSLContextImpl";
        Assert.assertEquals(jsp.getProperty(key1_SSLContextTLS),expected_val1);
        testNum++;
    }
    @Test
    public void testKey2(){
        String key2_SSLContextTLSv1 = "Alg.Alias.SSLContext.TLSv1";
        String expected_val2 = "TLS";
        Assert.assertEquals(jsp.getProperty(key2_SSLContextTLSv1),expected_val2);
        testNum++;
    }
    @Test
    public void testKey3(){
        String key3_KeyManagerFactory = "KeyManagerFactory.X509";
        String expected_val3 = "org.apache.harmony.xnet.provider.jsse.KeyManagerFactoryImpl";
        Assert.assertEquals(jsp.getProperty(key3_KeyManagerFactory),expected_val3);
        testNum++;
    }
    @Test
    public void testKey4(){
        String key4_TrustManagerFactory = "TrustManagerFactory.X509";
        String expected_val4 = "org.apache.harmony.xnet.provider.jsse.TrustManagerFactoryImpl";
        Assert.assertEquals(jsp.getProperty(key4_TrustManagerFactory),expected_val4);
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
