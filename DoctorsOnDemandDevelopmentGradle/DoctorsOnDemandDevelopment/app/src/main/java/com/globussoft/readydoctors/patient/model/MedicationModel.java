package com.globussoft.readydoctors.patient.model;

public class MedicationModel {

	String meidcation_name;
	String time;
	public String getMeidcation_name() {
		return meidcation_name;
	}
	public void setMeidcation_name(String meidcation_name) {
		this.meidcation_name = meidcation_name;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	@Override
	public String toString() {
		
		// TODO Auto-generated method stub
		return meidcation_name+" "+time; 
	}

	
	
}
