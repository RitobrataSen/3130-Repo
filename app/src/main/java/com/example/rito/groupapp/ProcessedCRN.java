package com.example.rito.groupapp;

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
