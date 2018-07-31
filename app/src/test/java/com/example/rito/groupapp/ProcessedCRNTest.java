package com.example.rito.groupapp;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
/**
 * This is a JUnit test for the CRN.Java class. It ensures that all the methods that are going
 * to have a certain purpose and is meeting the expectation.
 *
 * @author    Ritobrata Sen, Yuhao Yu, Gobii Vivagananda
 * @since     07-14-18
 */

public class ProcessedCRNTest {
	private static int testNum=0;
	private ProcessedCRN processedcrn = new ProcessedCRN();
	private String sNormal = "true-123456-MESSAGE";
	private String msg = "MESSAGE";
	private boolean status = true;
	private String crn = "123456";

	@Test
	public void checkSet_GetStatus(){
		processedcrn.setStatus(status);
		Assert.assertTrue(processedcrn.getStatus() == true);
		testNum++;
	}

	@Test
	public void checkSet_GetMsg(){

		processedcrn.setMsg(msg);
		Assert.assertTrue(processedcrn.getMsg().matches(msg));
		testNum++;
	}

	@Test
	public void checkSet_GetCrn(){
		processedcrn.setCrn(crn);
		Assert.assertTrue(processedcrn.getCrn().matches(crn));
		testNum++;
	}

	@Test
	public void checkEquals(){
		processedcrn.setStatus(status);
		processedcrn.setMsg(msg);
		processedcrn.setCrn(crn);
		Assert.assertTrue(processedcrn.equals(processedcrn));
		testNum++;
	}

	@Test
	public void checkToString(){
		processedcrn.setStatus(status);
		processedcrn.setMsg(msg);
		processedcrn.setCrn(crn);
		Assert.assertTrue(processedcrn.toString().equals(sNormal));
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
