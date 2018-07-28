package com.example.rito.groupapp.old;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.HashMap;

/**
 * CRN class is used to unload firebase data using firebase ui adapter
 * to process. Holds the same structure as firebase nodes under CRN.
 *
 * @author   Gobii, Rito, Yuhao
 * @since    2018-07-08
 */

@IgnoreExtraProperties //only maps fields during serialization
public class CRN implements Serializable {

	private String crn;
	private String term_code;
	private String course_code;
	private String section_number;
	private String section_type;
	private String instructor;


	public CRN(){
	}

	public CRN(String crn, String term_code, String course_code, String section_number, String
			section_type, String instructor){
		this.crn = crn;
		this.term_code = term_code;
		this.course_code = course_code;
		this.section_number = section_number;
		this.section_type = section_type;
		this.instructor = instructor;
	}

	public String getCrn(){
		return this.crn;
	}
	public String getTerm_code(){
		return this.term_code;
	}
	public String getCourse_code(){
		return this.course_code;
	}
	public String getSection_number(){
		return this.section_number;
	}
	public String getSection_type(){
		return this.section_type;
	}
	public String getInstructor(){
		return this.instructor;
	}


	public void setCrn(String crn){
		this.crn = crn;
	}
	public void setTerm_code(String term_code){
		this.term_code = term_code;
	}
	public void setCourse_code(String course_code){
		this.course_code = course_code;
	}
	public void setSection_number(String section_number){
		this.section_number = section_number;
	}
	public void setSection_type(String section_type){
		this.section_type = section_type;
	}
	public void setInstructor(String instructor){
		this.instructor = instructor;
	}
	public String generatePath(){
		return String.format("CRN/%s/%s/%s", this.term_code, this.course_code, this.crn);
	}

	public boolean equals(CRN c){
		if((this.crn.equals(c.getCrn())) &&
				(this.term_code.equals(c.getTerm_code())) &&
				(this.course_code.equals(c.getCourse_code())) &&
				(this.section_number.equals(c.getSection_number())) &&
				(this.section_type.equals(c.getSection_type())) &&
				(this.instructor.equals(c.getInstructor()))
				){
			return true;
		}

		return false;
	}

	@Override
	public String toString(){
		//return String.format("(TermCode: %s, TermDescription: %s)", term_code, term_description);
/*		return String.format("%s\n%s\n%s, %s %s\n%s", crn, term_code, course_code,
				section_number, section_type, instructor);
*/		return this.crn;
	}

	@Exclude //ignores method from javadocs
	public HashMap<String, Object> toMap() {
		HashMap<String, Object> result = new HashMap<>();
		result.put("crn", this.crn);
		result.put("term_code", this.term_code);
		result.put("course_code", this.course_code);
		result.put("section_number", this.section_number);
		result.put("section_type", this.section_type);
		result.put("instructor", this.instructor);
		return result;
	}

}