
package com.example.rito.groupapp;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.HashMap;

@RunWith(JUnit4.class)
/**
 * This is a JUnit test for the CRN.Java class. It ensures that all the methods that are going
 * to have a certain purpose and is meeting the expectation.
 *
 * @author    Ritobrata Sen, Yuhao Yu, Gobii Vivagananda
 * @since     07-14-18
 */

public class CRN_DataTest {
	private static int testNum=0;
	private String crn = "123456";
	private String term_code = "201820";
	private String course_code = "CSCI1234";
	private String section_number = "01";
	private String section_type = "Lec";
	private String instructor = "Prof";
	private String start_time = "09:00:00";
	private String end_time = "11:00:00";
	private String start_date = "2018-01-01";
	private String end_date = "2018-05-05";
	private String location = "DAL";
	private String course_name = "CS COURSE";
	private String max = "111";
	private String subject_code = "CSCI";
	private CRN_Data crn_data = setTestObj();
	private String [] arr = {"M","W","F"};
	private String sDays = "M W F";

	@Test
	public void checkSet_GetCrn(){

		crn_data.setCrn(crn);
		Assert.assertTrue(crn_data.getCrn().matches(crn));
		testNum++;
	}

	@Test
	public void checkSet_GetTerm_Code(){
		crn_data.setTerm_Code(term_code);
		Assert.assertTrue(crn_data.getTerm_Code().matches(term_code));
		testNum++;
	}

	@Test
	public void checkSet_GetCourse_Code(){
		crn_data.setCourse_Code(course_code);
		Assert.assertTrue(crn_data.getCourse_Code().matches(course_code));
		testNum++;
	}

	@Test
	public void checkSet_GetSection_Number(){
		crn_data.setSection_Number(section_number);
		Assert.assertTrue(crn_data.getSection_Number().matches(section_number));
		testNum++;
	}

	@Test
	public void checkSet_GetSection_Type(){
		crn_data.setSection_Type(section_type);
		Assert.assertTrue(crn_data.getSection_Type().matches(section_type));
		testNum++;
	}

	@Test
	public void checkSet_GetInstructor(){
		crn_data.setInstructor(instructor);
		Assert.assertTrue(crn_data.getInstructor().matches(instructor));
		testNum++;
	}

	@Test
	public void checkSet_GetStart_Date(){
		crn_data.setStart_Date(start_date);
		Assert.assertTrue(crn_data.getStart_Date().matches(start_date));
		testNum++;
	}

	@Test
	public void checkSet_GetEnd_Date(){
		crn_data.setEnd_Date(end_date);
		Assert.assertTrue(crn_data.getEnd_Date().matches(end_date));
		testNum++;
	}

	@Test
	public void checkSet_GetStart_Time(){
		crn_data.setStart_Time(start_time);
		Assert.assertTrue(crn_data.getStart_Time().matches(start_time));
		testNum++;
	}

	@Test
	public void checkSet_GetEnd_Time(){
		crn_data.setEnd_Time(end_time);
		Assert.assertTrue(crn_data.getEnd_Time().matches(end_time));
		testNum++;
	}

	@Test
	public void checkSet_GetDays(){
		HashMap<String, Boolean> hm = new HashMap<>();
		hm.put("mon", true);
		hm.put("tue", false);
		hm.put("wed", true);
		hm.put("thu", false);
		hm.put("fri", true);
		crn_data.setDays(hm);
		Assert.assertTrue(hm.equals(crn_data.getDays()));
		testNum++;
	}

	@Test
	public void checkSet_GetLocation(){
		crn_data.setLocation(location);
		Assert.assertTrue(crn_data.getLocation().matches(location));
		testNum++;
	}

	@Test
	public void checkSet_GetCourse_Name(){
		crn_data.setCourse_Name(course_name);
		Assert.assertTrue(crn_data.getCourse_Name().matches(course_name));
		testNum++;
	}

	@Test
	public void checkSet_GetMax(){
		crn_data.setMax(max);
		Assert.assertTrue(crn_data.getMax().matches(max));
		testNum++;
	}

	@Test
	public void checkSet_GetEnrollment(){
		HashMap<String, Boolean> hm = new HashMap<>();
		hm.put("Student1", true);
		hm.put("Student2", false);
		hm.put("Student3", true);
		hm.put("Student4", false);
		crn_data.setEnrollment(hm);
		Assert.assertTrue(hm.equals(crn_data.getEnrollment()));
		testNum++;
	}

	@Test
	public void checkSet_GetSubject_Code(){
		crn_data.setSubject_Code(subject_code);
		Assert.assertTrue(crn_data.getSubject_Code().matches(subject_code));
		testNum++;
	}

	@Test
	public void checkToMap(){
		HashMap<String,Object> hm = new HashMap<>();
		hm = crn_data.toMap();
		Assert.assertTrue(hm.equals(crn_data.toMap()));
		testNum++;
	}

	@Test
	public void checkEquals(){
		CRN_Data c = new CRN_Data();

		c.setTerm_Code(term_code);
		c.setSubject_Code(subject_code);
		c.setCourse_Code(course_code);
		c.setCrn(crn);

		crn_data.setTerm_Code(term_code);
		crn_data.setSubject_Code(subject_code);
		crn_data.setCourse_Code(course_code);
		crn_data.setCrn(crn);

		Assert.assertTrue(crn_data.equals(crn_data));
		testNum++;
	}

	@Test
	public void checkIsCore(){
		crn_data.setSection_Type("Lec");
		Assert.assertTrue(crn_data.isCore());
		testNum++;
	}

	@Test
	public void checkGetCur(){
		Assert.assertTrue(crn_data.getCur() >= 0);
		testNum++;
	}

	@Test
	public void checkToString(){
		crn_data = setTestObj();
		CRN_Data crn_data2 = setTestObj();
		Assert.assertTrue(crn_data.toString().equals(crn_data2.toString()));
		testNum++;
	}

	@Test
	public void checkToString_CFA_Basic(){
		crn_data = setTestObj();
		CRN_Data crn_data2 = setTestObj();
		Assert.assertTrue(crn_data.toString_CFA_Basic().equals(crn_data2.toString_CFA_Basic()));
		testNum++;
	}

	@Test
	public void checkToString_CFA_Full(){
		crn_data = setTestObj();
		CRN_Data crn_data2 = setTestObj();
		Assert.assertTrue(crn_data.toString_CFA_Full().equals(crn_data2.toString_CFA_Full()));
		testNum++;
	}

	@Test
	public void checkGetToStringArray(){
		crn_data = setTestObj();
		CRN_Data crn_data2 = setTestObj();
		boolean [] fl = {true, true, true};

		String [] arr;
		String [] arr2;

		for (int f = 0; f < fl.length; f++){

			switch (f){
				case 1:
					arr = crn_data2.toString_CFA_Full().split("\t");
					break;
				default:
					arr = crn_data2.toString_CFA_Basic().split("\t");
					break;
			}

			arr2 = crn_data.getToStringArray(f);

			if (arr.length != arr2.length) {
				fl[f] = false;
			} else {
				for (int i = 0; i < arr.length; i++){
					if (!(arr[i].equals(arr2[i]))) {
						fl[f] = false;
					}
				}
			}
		}

		for (boolean x : fl){
			Assert.assertTrue(x);
		}

		testNum++;
	}

	@Test
	public void checkJoin(){
		crn_data = setTestObj();
		Assert.assertTrue(crn_data.join(arr).equals(sDays));
		testNum++;
	}

	public CRN_Data setTestObj(){
		CRN_Data c = new CRN_Data();
		c.setTerm_Code(term_code);
		c.setSubject_Code(subject_code);
		c.setCourse_Code(course_code);
		c.setCrn(crn);

		c.setSection_Number(section_number);
		c.setSection_Type(section_type);
		c.setInstructor(instructor);
		c.setStart_Date(start_date);
		c.setEnd_Date(end_date);
		c.setStart_Time(start_time);
		c.setEnd_Time(end_time);

		HashMap<String, Boolean> hm = new HashMap<>();
		hm.put("mon", true);
		hm.put("tue", false);
		hm.put("wed", true);
		hm.put("thu", false);
		hm.put("fri", true);
		c.setDays(hm);

		c.setLocation(location);
		c.setCourse_Name(course_name);
		c.setMax(max);

		HashMap<String, Boolean> hm2 = new HashMap<>();
		hm2.put("Student1", true);
		hm2.put("Student2", false);
		hm2.put("Student3", true);
		hm2.put("Student4", false);
		c.setEnrollment(hm2);

		return c;
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
