package com.globussoft.readydoctors.patient.model;

public class SymtomsModel 
{
	public boolean isHeader;
	public String mSymtom;
	public String mBelongsTo;
	
	public boolean isHeader() 
	{
		return isHeader;
	}
	
	public void setHeader(boolean isHeader) 
	{
		this.isHeader = isHeader;
	}
	
	public String getmSymtom() 
	{
		return mSymtom;
	}
	
	public void setmSymtom(String mSymptom) 
	{
		this.mSymtom = mSymptom;
	}

	public String getmBelongsTo() 
	{
		return mBelongsTo;
	}

	public void setmBelongsTo(String mBelongsTo) 
	{
		this.mBelongsTo = mBelongsTo;
	}
	
	

}
