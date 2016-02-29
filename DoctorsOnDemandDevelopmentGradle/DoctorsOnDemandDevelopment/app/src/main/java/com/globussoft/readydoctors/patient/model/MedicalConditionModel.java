package com.globussoft.readydoctors.patient.model;

public class MedicalConditionModel 
{
	public String Condition;
	public boolean isHeader;
	public String getCondition() {
		return Condition;
	}
	public void setCondition(String condition) {
		Condition = condition;
	}
	public boolean isHeader() {
		return isHeader;
	}
	public void setHeader(boolean isHeader) {
		this.isHeader = isHeader;
	}
	

}
