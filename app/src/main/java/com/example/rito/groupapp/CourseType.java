package com.example.rito.groupapp;

import java.util.ArrayList;
import java.util.HashMap;

public class CourseType {
	private boolean core;
	private String descrip;
	private String term_code;
	private String subject_code;
	private String course_code;
	private ArrayList<String> keys;

	public CourseType(){
		this.keys = new ArrayList<>();
	}

	public CourseType(
			String term_code, String subject_code, String course_code, boolean core,
			HashMap<String, Boolean> keys
	){
		this.core = core;
		this.descrip = core ?
				"Core classes (lectures, co-op, etc)" :
				"Supplementary classes (labs, tutorials, etc)";

		this.term_code = term_code;
		this.subject_code = subject_code;
		this.course_code = course_code;

		this.keys = new ArrayList<>();
		if (keys != null) {
			for(String x : keys.keySet()){
				this.keys.add(x);
			}
		}
	}

	public CourseType(
			boolean core,
			String term_code,
			String subject_code,
			String course_code,
			ArrayList<String> keys
	){
		this.core = core;
		this.descrip = core ?
				"Core classes (lectures, co-op, etc)" :
				"Supplementary classes (labs, tutorials, etc)";

		this.term_code = term_code;
		this.subject_code = subject_code;
		this.course_code = course_code;

		this.keys = keys;
	}

	public boolean getCore(){
		return this.core;
	}

	public String getDescrip() {
		return this.descrip;
	}

	public String getTerm_Code() {
		return this.term_code;
	}

	public String getSubject_Code() {
		return this.subject_code;
	}

	public String getCourse_Code() {
		return this.course_code;
	}

	public ArrayList<String> getKeys() {
		return this.keys;
	}

	public void setCore(boolean core){
		this.core = core;
	}

	public void setDescrip(String descrip) {
		this.descrip = descrip;
	}

	public void setTerm_Code(String term_code) {
		this.term_code = term_code;
	}

	public void setSubject_Code(String subject_code) {
		this.subject_code = subject_code;
	}

	public void setCourse_Code(String course_code) {
		this.course_code = course_code;
	}

	public void setKeys(ArrayList<String> keys) {
		this.keys = keys;
	}
/*
	public void setKeys(HashMap<String, Boolean> keys) {
		this.keys = new ArrayList<>();
		if (keys != null) {
			for(String x : keys.keySet()){
				this.keys.add(x);
			}
		}
	}
*/
	public boolean equals(CourseType ct){
		if (ct == null){
			return false;
		} else if (
				this.term_code.equals(ct.getTerm_Code())
				&& this.subject_code.equals(ct.getSubject_Code())
				&& this.course_code.equals(ct.getCourse_Code())
				&& this.core == ct.getCore()
		){
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return this.descrip;
	}
}
