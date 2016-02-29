package com.globussoft.readydoctors.doctor.models;


public class RatingModel
{
	String byName="";
	String comment="";
	int rating =0;
	public String getByName() 
	{
		return byName;
	}
	public void setByName(String byName) 
	{
		this.byName = byName;
	}
	public String getComment() 
	{
		return comment;
	}
	public void setComment(String comment)
	{
		this.comment = comment;
	}
	public int getRating() 
	{
		return rating;
	}
	public void setRating(int rating) 
	{
		this.rating = rating;
	}
	
}
