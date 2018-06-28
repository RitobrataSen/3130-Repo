//package com.example.rito.groupapp;

import java.util.ArrayList;
public class Database{
	//attributes



	//constructors
	public Database (){
	}



	//methods

	//------------------------get methods
	public Tree getRootTree(){
		Tree T = new Tree();
		return T;
	}
	// gets the firebase tree rooted at the main root

	public ArrayList<Integer> getTermList(){
		ArrayList<Integer> list = new ArrayList<Integer>();
		return list;
	}
	// gets a list of term codes
	public Tree getTermTree(){
		Tree T = new Tree();
		return T;
	}
	public Tree getTermTree(ArrayList<Integer> term_code){
		Tree T = new Tree();
		return T;
	}
	public Tree getTermTree(int term_code){
		Tree T = new Tree();
		return T;
	}
	// gets the course tree rooted at TERMS or rooted at the specified term_code

	public ArrayList<String> getSubjectList(int term_code){
		ArrayList<String> list = new ArrayList<String>();
		return list;
	}
	// gets a list of all subjects for the specified term_code
	public Tree getSubjectTree(int term_code){
		Tree T = new Tree();
		return T;
	}
	public Tree getSubjectTree(int term_code, ArrayList<String> subject_code){
		Tree T = new Tree();
		return T;
	}

	public Tree getSubjectTree(int term_code, String subject_code){
		Tree T = new Tree();
		return T;
	}
	// gets the subject tree rooted at SUBJECTS for a specific term_code
	//	 or the subject tree rooted at the specified subject_code for a specific term_code

	public ArrayList<String> getCourseList(int term_code, String subject_code){
		
	}
	// gets a list of all courses for the specified term_code, and subject_code	
	public Tree getCourseTree(int term_code, String subject_code){
		Tree T = new Tree();
		return T;
	}
	public Tree getCourseTree(int term_code, String subject_code, ArrayList<String> course_code){
		Tree T = new Tree();
		return T;
	}
	public Tree getCourseTree(int term_code, String subject_code, String course_code){
		Tree T = new Tree();
		return T;
	}
	// gets the course tree rooted at COURSES for a specific term_code, and subject_code
	//	 or the course tree rooted at the specified course_code for a specific term_code, and subject_code

	public ArrayList<Integer> getCRNList(int term_code, String subject_code, String course_code){
		ArrayList<Integer> list = new ArrayList<Integer>();
		return list;
	}
	// gets a list of all CRNs for the specified term_code, subject_code, and course_code
	public Tree getCRNTree(int term_code, String subject_code, String course_code){
		Tree T = new Tree();
		return T;
	}
	public Tree getCRNTree(int term_code, String subject_code, String course_code, ArrayList<Integer> crn){
		Tree T = new Tree();
		return T;
	}
	public Tree getCRNTree(int term_code, String subject_code, String course_code, int crn){
		Tree T = new Tree();
		return T;
	}
	// gets the CRN tree rooted at CRN for a specific term_code, subject_code, and course_code
	//	 or the CRN tree rooted at the specified crn for a specific term_code, subject_code, and course_code

	//set methods------------------------------------------------------------------------------------------------------------------
	public void setEnrollment(int term_code, String subject_code, String course_code, int crn, int user_id){
	}
	//in TERMS > term_code > SUBJECTS > subject_code > COURSES > course_code > CRN > crn > enrollment > student_id
	public void setRegister(int term_code, String subject_code, String course_code, int crn, int user_id){
	}
	//in STUDENT > user_id > REGISTER > crn

	//save to the student database
		//new student
		//edit student
		//delete students


	//delete methods---------------------------------------------------------------------------------------------------------------
	public void deleteEnrollment(int term_code, String subject_code, String course_code, int crn, int user_id){
	}
	//in TERMS > term_code > SUBJECTS > subject_code > COURSES > course_code > CRN > crn > enrollment > student_id
	public void deleteRegister(int term_code, String subject_code, String course_code, int crn, int user_id){
	}
	//in STUDENT > user_id > REGISTER > crn

 

	//search methods---------------------------------------------------------------------------------------------------------------
	public void findCRN(ArrayList<Integer> crn){
	}
	public void findCRN(int crn){
	}
	
	public void findCourse(ArrayList<String> course_code){
	}
	public void findCourse(String course_code){
	}
	
	public void findSubjects(ArrayList<String> subject_code){
	}
	public void findSubjects(String subject_code){
	}

	public void findTerms(ArrayList<String> term_code){
	}
	public void findTerms(String term_code){
	}


	/*
	https://group-10-9598f.firebaseio.com/TERMS.json?print=pretty
	https://group-10-9598f.firebaseio.com/STUDENT.json?print=pretty
	
	https://firebase.google.com/docs/database/rest/retrieve-data

	*/
}

/*Firebase Structure:
	|-TERMS
		|-term_code
			|-SUBJECTS
				|-subject_code
					|-COURSES
						|-course_code
							|-CRN
								|-crn
									|-section_number
									|-section_type
									|-start_date
									|-end_date
									|-start_time
									|-end_time
									|-mon
									|-tue
									|-wed
									|-thu
									|-fri
									|-instructor
									|-location
									|-max
									|-enrollment
							|-course_name
					|-subject_description
			|-term_description
*/