package com.example.rito.groupapp;

import java.util.Set;

public class CourseType {
	boolean core;
	String descrip;
	Set<String> keys;

	public CourseType(){
	}

	public CourseType(boolean core){
		this.core = core;
		this.descrip = core ? "Core classes (lectures, co-op, etc" : "Supplementary classes (labs, tutorials, etc";
		this.keys = null;
	}

	public CourseType(boolean core, String descrip){
		this.core = core;
		this.descrip = descrip;
		this.keys = null;
	}

	public CourseType(boolean core, Set<String> keys){
		this.core = core;
		this.descrip = core ? "Core classes (lectures, co-op, etc" : "Supplementary classes (labs, tutorials, etc";
		this.keys = keys;
	}

	public CourseType(boolean core, String descrip, Set<String> keys){
		this.core = core;
		this.descrip = descrip;
		this.keys = keys;
	}

	public boolean getCore(){
		return this.core;
	}

	public String getDescrip() {
		return this.descrip;
	}

	public Set<String> getKeys() {
		return this.keys;
	}

	public void setCore(boolean core){
		this.core = core;
	}

	public void setDescrip(String descrip) {
		this.descrip = descrip;
	}

	public void setKeys(Set<String> keys) {
		this.keys = keys;
	}

	@Override
	public String toString() {
		return this.descrip;
	}
}
