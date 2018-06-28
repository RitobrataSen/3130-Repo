package com.example.rito.groupapp;

public class Course {
	//Variables of courses
	//Can be extend if more information is needed
	private String course_code;
	private String course_name;
	private String end_date;
	private String end_time;
	private String instructor;
	private String max;
	private String start_date;
	private String start_time;
	private HashMap<String, Boolean> days = new HashMap<String, Boolean>();

	//empty constructor
	public Course() {
		this.days = new HashMap<String, Boolean>();
	}

	//constructor
	public Course(String num, String name, 
		String sd, String ed, String st, String et, 
		String ins, String max, 
		Boolean m, Boolean t, Boolean w, Boolean r, Boolean f){
		this.course_code= num;
		this.course_name = name;
		
		this.start_date = sd;
		this.end_date = ed;
		this.start_time = st;
		this.end_time = et;

		this.instructor = ins;
		this.max = max;
		this.days = new HashMap<String, Boolean>();

		this.days.put("mon", m);
		this.days.put("tue", t);
		this.days.put("wed", w);
		this.days.put("thu", r);
		this.days.put("fri", f);
	}

	//Set methods
	//Use to assign the data when we receive information from firebase
	public void setCourseCode(String s){
		this.course_code = s;
	}

	public void setCourseName(String s){
		this.course_name = s;
	}

	public void setStartDate(String s){
		this.start_date = s;
	}

	public void setEndDate(String s){
		this.end_date = s;
	}

	public void setMax(String s){
		this.max = s;
	}

	public void setStartTime(String s) {
		this.start_time= s;
	}

	public void setEndTime(String s){
		this.end_time = s;
	}

	public void setInstructor(String s) {
		this.instructor = s;
	}

	public void setDays(Boolean m, Boolean t, Boolean w, Boolean r, Boolean f) {
		this.days.put("mon", m);
		this.days.put("tue", t);
		this.days.put("wed", w);
		this.days.put("thu", r);
		this.days.put("fri", f);
	}


	//Get methods
	//For get the information to display on screen
	public String getCourseCode(){
		return this.course_code;
	}

	public String getCourseName(){
		return this.course_name;
	}

	public String getStartDate(){
		return this.start_date;
	}

	public String getEndDate(){
		return this.end_date;
	}

	public String getMax(){
		return this.max;
	}

	public String getStartTime() {
		return this.start_time;
	}

	public String getEndTime(){
		return this.end_time;
	}

	public String getInstructor() {
		return this.instructor;
	}

	public HashMap<String, Boolean> getDays() {
		return this.days;
	}


}