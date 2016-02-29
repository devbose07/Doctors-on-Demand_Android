package com.globussoft.readydoctors.patient.psychological;

import java.util.ArrayList;

import org.json.JSONObject;

import com.globussoft.readydoctors.patient.Utills.Singleton;
import com.globussoft.readydoctors.patient.model.ExerciseModel;

public class PsychologyData 
{
	/*public static String patient;
	public static int psychologyScheduleType;
	
	public static String doctor_id="40";
	public static String patient_id="16";;
	
	public static String appointment_status="0";
	
	public static String appointment_start_time;
	public static String appointment_end_time;
	public static String time_period;
	
	
	public static String purpose_of_visit;
	
	public static String medication_json_list;
	
	public static String allergies_json_list;
	
	public static String symtopms_json_list;
	
	public static String refered_by="friends";
	
	public static String medical_condition;
	
	public static String pharmacy="1";
	
	public static String psyExercise="1";*/
	
	public static ArrayList<ExerciseModel> exerciseListType1=new ArrayList<ExerciseModel>();
	public static ArrayList<ExerciseModel> exerciseListType2=new ArrayList<ExerciseModel>();
	
	
	public static JSONObject psyExerciseObj=new JSONObject();



	public static String exercise_1a="";
	public static String exercise_1b="";
	public static String exercise_1c="";
	public static String exercise_1d="";
	public static String exercise_1e="";
	public static String exercise_1f="";
	public static String exercise_1g="";
	
	public static String exercise_2a="";
	public static String exercise_2b="";
	public static String exercise_2c="";
	public static String exercise_2d="";
	public static String exercise_2e="";
	public static String exercise_2f="";
	public static String exercise_2g="";
	public static String exercise_2h="";
	public static String exercise_2i="";

	public static int psychologyScheduleType;
	public static String appointment_start_time;
	public static String appointment_end_time;
	public static String patient_id= Singleton.PatientID;
	public static String doctor_id;
	public static String planid;
	public static String departmentId;
	public static String calltime;
	public static String callcost;
	public static String needtopay;
	public static String creditremains;
	public static String id= Singleton.PatientID;
	public static String purpose;
	public static String medication;
	public static String medicationTime;
	public static String generalSymptoms;
	public static String relationshipSymptoms;
	public static String habitSymptoms;
	public static String medicalConditions;
	public static String pharmacyId;
	public static String transactionId;
	public static String acknowledgement;
	public static String amount;
	public static String paymentTime;
	public static String patient="0";
	public static String Exercise1="";
	public static String Exercise2="";
	public static String allergies="dfdsfsd";
	public static String childId="0";



}
