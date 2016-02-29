package com.globussoft.readydoctors.patient.Utills;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import android.net.ParseException;

public class AppUtills {

	
	
public static Date getCurrentDate(long time) throws java.text.ParseException
	{    
	    Calendar cal = Calendar.getInstance();
	    
	       TimeZone tz = cal.getTimeZone();//get your local time zone.
	       
	       SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
	       sdf.setTimeZone(tz);//set time zone.
	       
	       String localTime = sdf.format(new Date((time) * 1000));
	       Date date = new Date();
	       
	       try 
	       {
	            date = sdf.parse(localTime);//get local date
	       }
	       catch (ParseException e) 
	       {
	            e.printStackTrace();
	       }
	      return date;
	    }

	public static Date getUTCdate(long time) throws java.text.ParseException
	{
		Calendar cal = Calendar.getInstance();

		//get your local time zone.

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a");


		String localTime = sdf.format(new Date((time) * 1000));
		Date date = new Date();

		try
		{
			date = sdf.parse(localTime);//get local date
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		return date;
	}


	public static String getDate(long timeStamp)
	{

	    try
	    {
	        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	        Date netDate = (new Date(timeStamp));
	        return sdf.format(netDate);
	    }
	    catch(Exception ex)
	    {
	        return "xx";
	    }
	} 
	
	
	
	public static String ConverCalenderToDate(Calendar selected)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strDate = sdf.format(selected.getTime());
		return strDate;
	}

	public static String AddTime(int minutes, String startTime)
	{
		String totalTime;

		String[] hm = startTime.split(":");
		int h = Integer.parseInt(hm[0]);
		int m = Integer.parseInt(hm[1]);

		int t = h * 60 + m; // total minutes
		t += minutes; // add the desired offset

		while (t < 0) 
		{ // fix `t` so that it's never negative
			t += 1440; // 1440 minutes in a day
		}

		int nh = (t / 60) % 24; // calculate new hours
		int nm = t % 60; // calculate new minutes



			totalTime = String.format("%02d:%02d", nh, nm);

System.out.print("totalTime=="+totalTime);

		return totalTime;

	}
	
	 public static String getMonthForInt(int num) 
	 {
	        String month = "wrong";
	        DateFormatSymbols dfs = new DateFormatSymbols();
	        String[] months = dfs.getMonths();
	        if (num >= 0 && num <= 11 ) 
	        {
	            month = months[num];
	        }
	        return month;
	    }
	 
	 
	 public static String ConverCalenderToDateOnly(Calendar selected) 
	 {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String strDate = sdf.format(selected.getTime());
			return strDate;
		}
}
