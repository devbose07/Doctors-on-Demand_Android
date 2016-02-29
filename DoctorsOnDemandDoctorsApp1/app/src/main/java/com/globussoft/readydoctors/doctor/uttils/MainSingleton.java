package com.globussoft.readydoctors.doctor.uttils;

import com.globussoft.readydoctors.doctor.models.AppointmentsModel;
import com.globussoft.readydoctors.doctor.models.UpcomingModel;

public class MainSingleton {

	//doctors details
	public static String doctor_id;
	public static String name;
	public static String email;
	public static String timeZone;
	public static String dateofbirth;

	//apoinment details
	public static String appointment_id;
	
	public static String day1;
	public static String day2;
	public static String day3;
	public static String day4;
	public static String day5;
	public static String day6;
	public static String day7;
	
	
	public static UpcomingModel selectedUpcomingModel;
	public static AppointmentsModel selectedappointmentsModel;

	public static String patientId="";

}
