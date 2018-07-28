
package com.example.rito.groupapp;
import com.example.rito.groupapp.old.CRN;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.HashMap;

@RunWith(JUnit4.class)
/**
 * This is a JUnit test for the CRN.Java class. It ensures that all the methods that are going
 * to have a certain purpose and is meeting the expectation.
 *
 * @author    Ritobrata Sen, Yuhao Yu, Gobii Vivagananda
 * @since     07-14-18
 */
public class CourseTypeTest {
	private static int testNum=0;
	private CourseType coursetype = new CourseType();
	private String descrip = "description";
	private boolean core = true;
	private String term_code = "201830";
	private String subject_code = "CSCI";
	private String course_code = "CSCI1234";
	private ArrayList<String> al = new ArrayList<>();

	@Test
	public void checkSet_GetCore(){
		coursetype.setCore(core);
		Assert.assertTrue(coursetype.getCore() == core);
		testNum++;
	}



	@Test
	public void checkSet_GetDescrip(){
		coursetype.setDescrip(descrip);
		Assert.assertTrue(coursetype.getDescrip().matches(descrip));
		testNum++;
	}



	@Test
	public void checkSet_GetTerm_Code(){
		coursetype.setTerm_Code(term_code);
		Assert.assertTrue(coursetype.getTerm_Code().matches(term_code));
		testNum++;
	}

	@Test
	public void checkSet_GetSubject_Code(){
		coursetype.setSubject_Code(subject_code);
		Assert.assertTrue(coursetype.getSubject_Code().matches(subject_code));
		testNum++;
	}

	@Test
	public void checkSet_GetCourse_Code(){
		coursetype.setCourse_Code(course_code);
		Assert.assertTrue(coursetype.getCourse_Code().matches(course_code));
		testNum++;
	}

	@Test
	public void checkSet_GetKeys(){
		al.add("123456");
		al.add("654321");
		al.add("121212");

		coursetype.setKeys(al);
		Assert.assertTrue(al.equals(coursetype.getKeys()));
		testNum++;
	}

	@Test
	public void checkEquals(){
		coursetype.setCore(core);
		coursetype.setDescrip(descrip);
		coursetype.setTerm_Code(term_code);
		coursetype.setSubject_Code(subject_code);
		coursetype.setCourse_Code(course_code);

		Assert.assertTrue(coursetype.equals(coursetype));
		testNum++;
	}

	@Test
	public void checkToString(){
		coursetype.setDescrip(descrip);
		Assert.assertTrue(coursetype.toString().equals(descrip));
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

