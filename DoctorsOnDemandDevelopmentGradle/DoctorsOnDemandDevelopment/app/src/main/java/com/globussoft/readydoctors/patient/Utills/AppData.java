package com.globussoft.readydoctors.patient.Utills;

import java.util.ArrayList;

import com.globussoft.readydoctors.patient.model.AppointmentsModel;
import com.globussoft.readydoctors.patient.model.CalenderTimeModel;
import com.globussoft.readydoctors.patient.model.DeptDoctorModel;
import com.globussoft.readydoctors.patient.model.DoctorModel;
import com.globussoft.readydoctors.patient.model.FavProviderModel;
import com.globussoft.readydoctors.patient.model.PsychologistsModel;
import com.globussoft.readydoctors.patient.model.VisitHistoryModel;

public class AppData 
{



	//Doctors timing for weekdays
	public static String [] weekdays_array={"12:00-12:15 PM","12:15-12:30 PM","12:00-12:15 PM","12:00-12:15 PM",
			"12:00-12:15 PM","12:00-12:15 PM","12:00-12:15 PM","12:00-12:15 PM","12:00-12:15 PM"};
	
	//Doctors timing for weekends
	public static String [] weekend_array={"10:10-10-20 AM","10:15-10-30 AM","10:30-10:45 AM","10:45-11:00 AM","10:10 AM",
			"10:10 AM","10:10 AM","10:10 AM","10:10 AM"};
	
	//appointmentType 1=Medical, 2=psychology, 3=Pediatrics, 4=lactation, 5=see a doctor now
	public static int appointmentType=0;
	public static boolean fromAppointment=false;
	public static boolean loginStatus=false;
	public static int scheduleTiming;
	
	public static ArrayList<CalenderTimeModel> WeekdaysList() 
	{
		ArrayList<CalenderTimeModel> weekdays_arraylist=new ArrayList<CalenderTimeModel>(); 
		
		CalenderTimeModel model1=new CalenderTimeModel();
		model1.setTime("12:00");
		model1.setTimelimit("12:00-12:15 PM");
		
		weekdays_arraylist.add(model1);
		
		CalenderTimeModel model2=new CalenderTimeModel();
		model2.setTime("12:15");
		model2.setTimelimit("12:15-12:30 PM");
		
		weekdays_arraylist.add(model2);
		
		CalenderTimeModel model3=new CalenderTimeModel();
		model3.setTime("12:30");
		model3.setTimelimit("12:30-12:45 PM");
		
		weekdays_arraylist.add(model3);
		
		CalenderTimeModel model4=new CalenderTimeModel();
		model4.setTime("12:00");
		model4.setTimelimit("12:00-12:15 PM");
		
		weekdays_arraylist.add(model4);
		
		CalenderTimeModel model5=new CalenderTimeModel();
		model5.setTime("12:00");
		model5.setTimelimit("12:00-12:15 PM");
		
		weekdays_arraylist.add(model5);
		
		return weekdays_arraylist;
		
	}
	
	
	public static ArrayList<CalenderTimeModel> WeekendList() 
	{
		ArrayList<CalenderTimeModel> weekend_arraylist=new ArrayList<CalenderTimeModel>(); 
		
		CalenderTimeModel model1=new CalenderTimeModel();
		model1.setTime("10:00");
		model1.setTimelimit("10:00-10:15 PM");
		
		weekend_arraylist.add(model1);

		CalenderTimeModel model2=new CalenderTimeModel();
		model2.setTime("10:15");
		model2.setTimelimit("10:15-10:30 PM");
		
		weekend_arraylist.add(model2);
		
		CalenderTimeModel model3=new CalenderTimeModel();
		model3.setTime("10:30");
		model3.setTimelimit("10:30-10:45 PM");
		
		weekend_arraylist.add(model3);
		
		CalenderTimeModel model4=new CalenderTimeModel();
		model4.setTime("10:00");
		model4.setTimelimit("10:00-10:15 PM");
		
		weekend_arraylist.add(model4);
		
		CalenderTimeModel model5=new CalenderTimeModel();
		model5.setTime("12:00");
		model5.setTimelimit("12:00-12:15 PM");
		
		weekend_arraylist.add(model5);
		
		return weekend_arraylist;
		
	}
	
	
	public static String[] symptoms_genaral={"Fever","Weight loss/gain","Difficulty sleeping","Loss of appetite","Mood changes","Fatigue/Weekness","Foreign travel (past month)","Hospitalized (past 6 months)"};
	
	public static String[] symptoms_head_neck={"Headach","Dizzy / lightheaded","vision changes","Hearing loss / ringing ","Ear drainage","Nasal discharge","Congestion / Sinus problem","Sore throat","Allergies","Numbness / tingling","History of fainting / seizure","Memory loss","History of strock","History of falls"};
	public static String[] symptoms_chest={"Chest pressure / pain","Palpitations","Cough","Sputum","Shortness of breath","Decreased ecercise tolerance","History of smoking"};
	public static String[] symptoms_degestive_track={"Sore throat","Nausea / vomiting","Difficulty / pain swallowing","Heartburn / reflux","Diarrhea","Constipation","Abdominal pain / discomfort"};
	public static String[] symptoms_pelvis={"Flank pain","Discomfort / burning with urination","Blood in urine","Frequent urination","History of Sexually Transmitted Infections","Irregular periods","Vaginal bleeding","Vaginal discharge","Penile Discharge","Testicular swelling","Testicular pain",};
	public static String[] symptoms_muscle_joints={"Muscle pain","Limited motion / mobility","Muscle weakness","Back pain","Swelling","Joint replacements"};
	public static String[] symptoms_skin={"Bleeding","Itching","Swelling","Skin rashes / bumps","Bruising / discoloration","Sores","Bites"};
	
	public static String[] medicalcondition_array={"Cancer","Diabetes","Heart Dieses(CHF,MI)","Stroke","High blood pressure","High cholesterol"};
	
	public static String[] lactation_medicalcondition_array={"Breast augmentation","Breast reduction","Biopsy","Chest trauma","Cesarean delivery","vaginal delivery","Difficulty getting pregnant","Thyroid problems","Hypertension","Gestational diabetes","IDDM","PCOS","Breast growth duringpregnancy","Inverted nipples","Flat nipples"};
	public static String[] lactation_breast_symtoms_array={"Pain","Itching","Swelling","Lump","Redness","Too soft","Not enough milk","Too much milk"};
	public static String[] lactation_nipples_symtoms_array={"Cracked","Bleeding","Constant pain","Pain with initial latch","Pain with entire latch",};
	public static String[] lactation_baby_symtoms_array={"Refusing breasst","Increase fussness while feeding","Falls asleep at breast","Not stooling","Being supplemented","Clicking sound","Cries at breast","Cries at bottle"};
	public static DeptDoctorModel doctormodel;
	public static DoctorModel selecteddoctormodel;
	public static FavProviderModel favProviderModel;
	public static boolean deletedFav;

	public static int psychologyScheduleType=0;
	public static AppointmentsModel appointmentsModel;
	public static VisitHistoryModel visitHistoryModel;
	public static PsychologistsModel selectedconsultant;
	public static int LactationScheduleType=0;
	public static int couponType=0;
	public static int orgType=0;
	public static String apponantName;
	public static String appointmentId;
	
	//psychology data
	public static String[] psychology_symptoms_genaral={"Anxiety / Stress / Worry","Feeling lonely / down / depressed","Life transition or traumatic event","Agitation or lack of focus","Grief, guilty, or loss","Trouble sleeping","Anger Management","Improve mental / athletic / life performance"};
	public static String[] psychology_symptoms_habbits={"Smoking","Alcohol","Diet","Body image"};
	public static String[] psychology_symptoms_relationships={"With partner","With family","With friends","Sexual issues","Other"};
	
	//questions for first exercise of psycological appointment
	public static String exercise_1a="Feeling nervous anxious or on edge";
	public static String exercise_1b="Not being able to stop or control worrying";
	public static String exercise_1c="worrying too much about different things";
	public static String exercise_1d="Trouble relaxing";
	public static String exercise_1e="Being so restless that it is hard to sit still";
	public static String exercise_1f="Becoming easily annoyed or irritable";
	public static String exercise_1g="Feeling afraid as if something awful might happen";
	
	//questions for second exercise of psycological appointment
	public static String exercise_2a="Little interest or plesure in doing things";
	public static String exercise_2b="Feeling down, depressed or hopeless";
	public static String exercise_2c="Trouble falling or staying asleep, or sleeping too much";
	public static String exercise_2d="Feeling tired or having little energy";
	public static String exercise_2e="Poor appetite or overeating";
	public static String exercise_2f="Feeling bad about yourself- or that you are a failure or have let yourself or your family down";
	public static String exercise_2g="Trouble concentrating on things, such as reading the newspaper or watching television";
	public static String exercise_2h="Moving or speaking so slowly that other people could have noticed ? Or the opposite being so fidgety or restless that you have been moving around a lot more than usual";
	public static String exercise_2i="Thoughts that you would be better off dead or of hurting yourself in some way";
}
