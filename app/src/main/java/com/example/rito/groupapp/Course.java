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

	//constructors
	public Course() {
	}

	public Course(String course_code, String course_name, String subject_code,
				  String term_code, boolean has_supplement){
		this.course_code= course_code;
		this.course_name = course_name;
		this.subject_code = subject_code;
		this.term_code = term_code;
		this.has_supplement = has_supplement;
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

	public String generatePath(){
		return String.format("COURSE/%s/%s/%s",
				this.term_code, this.subject_code, this.course_code);
	}

	public boolean equals(Course c){
		//if (!(c == null)){
			if (c == null){
				return false;
			} else if(
					((this.course_code == c.getCourse_code()) || this.course_code.equals(c.getCourse_code())) &&
					((this.course_name == c.getCourse_code()) || this.course_name.equals(c.getCourse_name())) &&
					((this.subject_code == c.getSubject_code()) || this.subject_code.equals(c.getSubject_code())) &&
					((this.term_code == c.getTerm_code()) || this.term_code.equals(c.getTerm_code())) &&
					((this.has_supplement == c.getHas_supplement()) || this.has_supplement == c.getHas_supplement())){
				return true;
			} else if(this.toString().equals(c.toString())){
				return true;
			}


		//}

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
		result.put("has_supplement", this.has_supplement);
		return result;
	}

}