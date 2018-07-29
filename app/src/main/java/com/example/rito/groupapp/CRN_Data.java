package com.example.rito.groupapp;

import android.text.TextUtils;
import android.widget.Switch;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import java.io.Serializable;
import java.util.HashMap;

/**
 * CRN_Data class is used to unload firebase data using firebase ui adapter
 * to process. Holds the same structure as firebase nodes under CRN_Data.
 *
 * @author   Gobii
 * @since    2018-07-08
 */

@IgnoreExtraProperties //only maps fields during serialization
public class CRN_Data implements Serializable, Comparable<CRN_Data> {

	private String crn;
	private String term_code;
	private String course_code;
	private String section_number;
	private String section_type;
	private String instructor;
	private String start_date;
	private String end_date;
	private String start_time;
	private String end_time;
	private HashMap<String, Boolean> days;
	private String location;
	private String course_name;
	private String max;
	private HashMap<String, Boolean> enrollment;
	private String subject_code;

	public CRN_Data() {
		this.days = new HashMap<String, Boolean>();
		this.enrollment = new HashMap<String, Boolean>();
	}

	public String getCrn() {
		return this.crn;
	}
	public String getTerm_Code() {
		return this.term_code;
	}
	public String getCourse_Code() {
		return this.course_code;
	}
	public String getSection_Number() {
		return this.section_number;
	}
	public String getSection_Type() {
		return this.section_type;
	}
	public String getInstructor() {
		return this.instructor;
	}
	public String getStart_Date() {
		return this.start_date;
	}
	public String getEnd_Date() {
		return this.end_date;
	}
	public String getStart_Time() {
		return this.start_time;
	}
	public String getEnd_Time() {
		return this.end_time;
	}
	public HashMap<String, Boolean> getDays() {
		return this.days;
	}
	public String getLocation() {
		return this.location;
	}
	public String getCourse_Name() {
		return this.course_name;
	}
	public String getMax() {
		return this.max;
	}
	public HashMap<String, Boolean> getEnrollment() {
		return this.enrollment;
	}
	public String getSubject_Code() {
		return this.subject_code;
	}

	public void setCrn(String crn) {
		this.crn = crn;
	}
	public void setTerm_Code(String term_code) {
		this.term_code = term_code;
	}
	public void setCourse_Code(String course_code) {
		this.course_code = course_code;
	}
	public void setSection_Number(String section_number) {
		this.section_number = section_number;
	}
	public void setSection_Type(String section_type) {
		this.section_type = section_type;
	}
	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}
	public void setStart_Date(String start_date) {
		this.start_date = start_date;
	}
	public void setEnd_Date(String end_date) {
		this.end_date = end_date;
	}
	public void setStart_Time(String start_time) {
		this.start_time = start_time;
	}
	public void setEnd_Time(String end_time) {
		this.end_time = end_time;
	}
	public void setDays(HashMap<String, Boolean> days) {
		this.days = new HashMap<>();
		this.days.putAll(days);
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public void setCourse_Name(String course_name) {
		this.course_name = course_name;
	}
	public void setMax(String max) {
		this.max = max;
	}
	public void setEnrollment(HashMap<String, Boolean> enrollment) {
		this.enrollment = new HashMap<>();
		this.enrollment.putAll(enrollment);
	}
	public void setSubject_Code(String subject_code) {
		this.subject_code = subject_code;
	}

	public HashMap<String, Object> toMap() {
		HashMap<String, Object> result = new HashMap<>();
		result.put("crn", this.crn);
		result.put("term_code", this.term_code);
		result.put("course_code", this.course_code);
		result.put("section_number", this.section_number);
		result.put("section_type", this.section_type);
		result.put("instructor", this.instructor);
		result.put("start_date", this.start_date);
		result.put("end_date", this.end_date);
		result.put("start_time", this.start_time);
		result.put("end_time", this.end_time);
		result.put("days", this.days);
		result.put("location", this.location);
		result.put("course_name", this.course_name);
		result.put("max", this.max);
		result.put("enrollment", this.enrollment);
		result.put("subject_code", this.subject_code);
		return result;
	}

	public boolean equals(CRN_Data cd){
		if (cd == null){
			return false;
		} else if (
				this.term_code.equals(cd.getTerm_Code())
				&& this.subject_code.equals(cd.getSubject_Code())
				&& this.course_code.equals(cd.getCourse_Code())
				&& this.crn.equals(cd.getCrn())
		){
			return true;
		}
		return false;
	}

	public boolean isCore(){
		return (!(
				this.section_type.equalsIgnoreCase("lab") ||
						this.section_type.equalsIgnoreCase("tut")
		));
	}

	public long getCur(){
		long cur = this.enrollment != null ? this.enrollment.size() : 0;
		return cur;
	}

	@Override
	public String toString(){
		//return String.format("(TermCode: %s, TermDescription: %s)", term_code, term_description);
		return String.format("<%s-%s-%s-%s>", this.crn, this.term_code, this.course_code, this.section_type);
		//return this.crn;
	}

	public String toString_CFA_Basic(){
		//return String.format("(TermCode: %s, TermDescription: %s)", term_code, term_description);
		String str;

		String [] arr = {
				this.days.get("mon") ? "M" : "",
				this.days.get("tue") ? "T" : "",
				this.days.get("wed") ? "W" : "",
				this.days.get("thu") ? "R" : "",
				this.days.get("fri") ? "F" : ""
		};

		String days = join(arr);
		System.out.println(days);
		System.out.println(this.days);

		str = String.format(
				"CRN: %s" +
				"\tCourse: %s (%s)" +
				"\tStart/ End Times: %s to %s" +
				"\tDays: %s",
				this.crn,
				this.course_name, this.course_code,
				this.start_time, this.end_time,
				days)

		;
		return str;
	}

	public String toString_CFA_Full(){
		//return String.format("(TermCode: %s, TermDescription: %s)", term_code, term_description);
		String str;

		String [] arr = {
			this.days.get("mon") ? "M" : "",
			this.days.get("tue") ? "T" : "",
			this.days.get("wed") ? "W" : "",
			this.days.get("thu") ? "R" : "",
			this.days.get("fri") ? "F" : ""
		};

		String days = join(arr);
		int curr = this.enrollment.size();

		str = String.format(
				"CRN: %s" +
				"\tCourse: %s (%s)" +
				"\tSection: %s - %s" +
				"\tStart/ End Times: %s" +
				"\tDays: %s" +
				"\tEnrollment (Current / Max): %s / %s" +
				"\tLocation:\n%s" +
				"\tInstructor: \n%s",

				this.crn,
				this.course_name, this.course_code,
				this.section_number, this.section_type,
				this.start_time.equalsIgnoreCase("CD") ?
						"Consult Department" : this.start_time + " to " + this.end_time,
				days.equalsIgnoreCase("") ?
						"Consult Department" : days,
				curr, this.max,
				this.location,
				this.instructor)

				;
		return str;
	}

	public String[] getToStringArray(int i){
		/*
		0 = basic
		1 = full
		 */
		switch (i){
			case 0:
				return this.toString_CFA_Basic().split("\t");
			case 1:
				return this.toString_CFA_Full().split("\t");
			default:
				return this.toString_CFA_Basic().split("\t");
		}

	}

	public String join(String [] arr){
		//String days = TextUtils.join(" ", arr);
		String days = "";
		for (String x : arr){
			if (!(x.equalsIgnoreCase(""))){
				if (days.equalsIgnoreCase("")){
					days = x;
				} else {
					days = days + " " + x;
				}
			}
		}
		return days;
	}

	// CompareTo Method created to improve sorting in calendar.
	// @since 7/25/2018
	// @author Dryden Pick and Yuhao Hu.
	public int compareTo(CRN_Data other){
		if(this.getStart_Time() == null){
			return -1;
		}
		if(other.getStart_Time() == null){
			return 1;
		}
		return this.getStart_Time().compareTo(other.getStart_Time());
	}

}