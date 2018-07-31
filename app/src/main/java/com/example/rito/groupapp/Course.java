package com.example.rito.groupapp;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Courses class is used to unload firebase data using firebase ui adapter
 * to process. Holds the same structure as firebase nodes under COURSE.
 *
 * @author   Gobii, Rito, Yuhao
 * @since    2018-07-08
 */

@IgnoreExtraProperties //only maps fields during serialization
public class Course implements Serializable {

	//fields
	private String course_code;
	private String course_name;
	private String subject_code;
	private String term_code;
	private boolean has_supplement;

	//new fields
	private HashMap<String, Boolean> core;
	private HashMap<String, Boolean> supplement;

	//constructors
	public Course() {
		this.core = new HashMap<String, Boolean>();
		this.supplement = new HashMap<String, Boolean>();
	}

	public Course(String term_code, String subject_code, String course_code,
				  String course_name,
				  boolean has_supplement,
				  HashMap<String, Boolean> core,
				  HashMap<String, Boolean> supplement) {

		this.course_code = course_code;
		this.course_name = course_name;
		this.subject_code = subject_code;
		this.term_code = term_code;
		this.has_supplement = has_supplement;
		this.core = core;
		this.supplement = supplement;

	}

	//methods

	//getter/ setter methods
	public String getCourse_code(){
		return this.course_code;
	}
	public String getCourse_name(){
		return this.course_name;
	}
	public String getSubject_code(){
		return this.subject_code;
	}
	public String getTerm_code(){
		return this.term_code;
	}
	public boolean getHas_supplement(){
		return this.has_supplement;
	}
	public HashMap<String, Boolean> getCore() {
		return this.core;
	}
	public HashMap<String, Boolean> getSupplement() {
		return this.supplement;
	}

	public void setCourse_code(String course_code){
		this.course_code = course_code;
	}
	public void setCourse_name(String course_name){
		this.course_name = course_name;
	}
	public void setSubject_code(String subject_code){
		this.subject_code = subject_code;
	}
	public void setTerm_code(String term_code){
		this.term_code = term_code;
	}
	public void setHas_supplement(boolean has_supplement){
		this.has_supplement = has_supplement;
	}
	public void setCore(HashMap<String, Boolean> core){
		//to add/ remove individual pairs get entire hash map first then call
		//hash map functions: hashMap.put(k, v) or hashMap.remove(k)
		this.core = new HashMap<>();
		this.core.putAll(core);
	}
	public void setSupplement(HashMap<String, Boolean> supplement){
		//to add/ remove individual pairs get entire hash map first then call
		//hash map functions: hashMap.put(k, v) or hashMap.remove(k)
		this.supplement = new HashMap<>();
		this.supplement.putAll(supplement);
	}

	public boolean equals(Course c){
		if (c == null){
			return false;
		} else if (
				this.term_code.equals(c.getTerm_code())
						&& this.subject_code.equals(c.getSubject_code())
						&& this.course_code.equals(c.getCourse_code())
				){
			return true;
		}
		return false;
	}

	@Override
	public String toString(){
		//return String.format("(TermCode: %s, TermDescription: %s)", term_code, term_description);
		return String.format("[%s, %s, %s, %s, %s]",
				this.course_code, this.subject_code, this.course_name,
				this.term_code, this.has_supplement);
	}


	public String toString2(){
		//return String.format("(TermCode: %s, TermDescription: %s)", term_code, term_description);
		return String.format("\nCourse Code: %s\nCourse Name: %s\nTerm Code: %s\nHas Supplement: " +
						"%s",
				this.course_code, this.course_name,
				this.term_code, this.has_supplement);
	}

	@Exclude //ignores method from javadocs
	public HashMap<String, Object> toMap() {
		HashMap<String, Object> result = new HashMap<>();
		result.put("course_code", this.course_code);
		result.put("course_name", this.course_name);
		result.put("subject_code", this.subject_code);
		result.put("term_code", this.term_code);
		result.put("core", this.core);
		result.put("supplement", this.supplement);
		return result;
	}

}