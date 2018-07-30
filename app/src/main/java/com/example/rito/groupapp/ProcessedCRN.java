package com.example.rito.groupapp;

/**
 *The ProcessedCRN class gets the required information that is associated with that CRN and returns
 * those values.
 * It has 3 main methods:
 *  cloneData()
 *  	this clones all the information of the current CRN and created a new CRN
 *  	with the exact same i formation
 *  equal()
 *  	checks to see if two CRN's are equal
 *  toString()
 *  	upon calling this method, it will display all the information for a given CRN
 *
 * @author Gobii Viviagananda, Shane Mitravitz
 * @DateComplete 28 July 2018
 */
public class ProcessedCRN {

	String msg;
	String crn;
	Boolean status;

	public ProcessedCRN(){

	}

	public ProcessedCRN(String msg, String crn, boolean status){
		this.msg = msg;
		this.crn = crn;
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public String getCrn() {
		return crn;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void setCrn(String crn) {
		this.crn = crn;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public ProcessedCRN cloneData() {
		return new ProcessedCRN(
			this.msg,
			this.crn,
			this.status
		);
	}

	public boolean equals(ProcessedCRN p){

		return (
				this.msg.equals(p.getMsg()) &&
				this.crn.equals(p.getCrn()) &&
				this.status == p.getStatus()
				);
	}


	@Override
	public String toString() {
		return String.format("%s-%s-%s", this.status, this.crn, this.msg);
	}
}
