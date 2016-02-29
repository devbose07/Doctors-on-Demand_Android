package com.globussoft.readydoctors.patient.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.globussoft.readydoctors.patient.R;
import com.globussoft.readydoctors.patient.Utills.AppData;
import com.globussoft.readydoctors.patient.Utills.ConnectionDetector;
import com.globussoft.readydoctors.patient.Utills.ConstantUrls;
import com.globussoft.readydoctors.patient.see_a_doctor_now.SeeDoctorData;
import com.globussoft.readydoctors.patient.mediacal.MedicalData;
import com.globussoft.readydoctors.patient.pediatrics.PediatricData;
import com.globussoft.readydoctors.patient.pregnancy_newborns.LactationData;
import com.globussoft.readydoctors.patient.psychological.PsychologyData;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class ApplyAppointment extends Activity
{
	Button apply_button;
	ProgressBar progress;
	ImageView backImage;
	TextView next;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.apply_apointment);
		InitUI();
	}

	void InitUI() 
	{
		next=(TextView)findViewById(R.id.next);
		next.setVisibility(View.INVISIBLE);
		backImage=(ImageView)findViewById(R.id.backImage);
		backImage.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				onBackPressed();
			}
		});
		apply_button = (Button) findViewById(R.id.apply);
		progress=(ProgressBar) findViewById(R.id.progress);
		apply_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (new ConnectionDetector(getApplicationContext()).isConnectingToInternet()) {
					switch (AppData.appointmentType)
					{
						case 0:
							System.out.println("SeeDoctorMedicalAppointment");
							System.out.println("PatientID " + SeeDoctorData.patientId);
							SeeDoctorNow(SeeDoctorData.generalSymptoms,
									SeeDoctorData.headNeckSymptoms,
									SeeDoctorData.digestiveTrack,
									SeeDoctorData.allergies,
									SeeDoctorData.medicalConditions,
									SeeDoctorData.appointmentStartTime,
									SeeDoctorData.appointmentEndTime,
									SeeDoctorData.doctorID,
									SeeDoctorData.patientId,
									SeeDoctorData.planId,
									SeeDoctorData.pharmacyId,
									SeeDoctorData.AMT,
									SeeDoctorData.TRANSACTIONID,
									SeeDoctorData.ACK,
									SeeDoctorData.purposeOfVisit = "wewewew",
									SeeDoctorData.creditremains);
							break;
						case 4:
							System.out.println("MakeMedicalAppointment");
							MakeMedicalAppointment(MedicalData.appointment_start_time,
									MedicalData.appointment_end_time,
									MedicalData.patient_id,
									MedicalData.doctor_id,
									MedicalData.planid,
									MedicalData.departmentId,
									MedicalData.calltime,
									MedicalData.callcost,
									MedicalData.needtopay,
									MedicalData.creditremains,
									MedicalData.id,
									MedicalData.purpose_of_visit,
									MedicalData.allergies,
									MedicalData.medication,
									MedicalData.medicationTime,
									MedicalData.generalSymptoms,
									MedicalData.headNeckSymptoms,
									MedicalData.chestSymptoms,
									MedicalData.digestiveSymptoms,
									MedicalData.pelvicSymptoms,
									MedicalData.muscleSymptoms,
									MedicalData.skinSymptoms,
									MedicalData.medicalConditions,
									MedicalData.pharmacyID,
									MedicalData.transactionId,
									MedicalData.acknowledgement,
									MedicalData.amount,
									MedicalData.paymentTime,
									MedicalData.patient,
									MedicalData.childId);
							break;

						case 3:
							System.out.println("MakePsychologyAppointment");
							MakePsychologyAppointment(PsychologyData.appointment_start_time,
									PsychologyData.appointment_end_time,
									PsychologyData.patient_id,
									PsychologyData.doctor_id,
									PsychologyData.planid,
									PsychologyData.departmentId,
									PsychologyData.calltime,
									PsychologyData.callcost,
									PsychologyData.needtopay,
									PsychologyData.creditremains,
									PsychologyData.id,
									PsychologyData.purpose,
									PsychologyData.medication,
									PsychologyData.medicationTime,
									PsychologyData.generalSymptoms,
									PsychologyData.relationshipSymptoms,
									PsychologyData.habitSymptoms,
									PsychologyData.medicalConditions,
									PsychologyData.pharmacyId,
									PsychologyData.transactionId,
									PsychologyData.acknowledgement,
									PsychologyData.amount,
									PsychologyData.paymentTime,
									PsychologyData.patient,
									PsychologyData.Exercise1,
									PsychologyData.Exercise2,
									PsychologyData.allergies,
									PsychologyData.childId);
							break;

						case 2:

							System.out.println("MakePediatricsAppointment");
							MakePediatricsAppointment(PediatricData.appointment_start_time,
									PediatricData.appointment_end_time,
									PediatricData.patient_id,
									PediatricData.doctor_id,
									PediatricData.planid,
									PediatricData.departmentId,
									PediatricData.calltime,
									PediatricData.callcost,
									PediatricData.needtopay,
									PediatricData.creditremains,
									PediatricData.id,
									PediatricData.purpose,
									PediatricData.allergies,
									PediatricData.medication,
									PediatricData.medicationTime,
									PediatricData.generalSymptoms,
									PediatricData.neckSymptoms,
									PediatricData.chestSymptoms,
									PediatricData.digestiveSymptoms,
									PediatricData.pelvisSymptoms,
									PediatricData.musclesSymptoms,
									PediatricData.skinSymptoms,
									PediatricData.medicalConditions,
									PediatricData.pharmacyId,
									PediatricData.transactionId,
									PediatricData.acknowledgement,
									PediatricData.amount,
									PediatricData.paymentTime,
									PediatricData.patient,
									PediatricData.childId);
							break;

						case 1:
							System.out.println("MakeLactationAppointment");
							MakeLactationAppointment(LactationData.appointment_start_time,
									LactationData.appointment_end_time,
									LactationData.patient_id,
									LactationData.doctor_id,
									LactationData.planid,
									LactationData.departmentId,
									LactationData.calltime,
									LactationData.callcost,
									LactationData.needtopay,
									LactationData.creditremains,
									LactationData.purpose,
									LactationData.babyAge,
									LactationData.gestutionDuration,
									LactationData.breastFedChildNum,
									LactationData.breastFedDuration,
									LactationData.breastPump,
									LactationData.allergies,
									LactationData.medication,
									LactationData.transactionId,
									LactationData.acknowledgement,
									LactationData.amount,
									LactationData.paymentTime,
									LactationData.pharmacyId,
									LactationData.medicationTime,
									LactationData.generalSymptoms,
									LactationData.breastSymptoms,
									LactationData.babySymptoms,
									LactationData.nippleSymptoms,
									LactationData.medicalConditions,
									LactationData.childId,
									LactationData.patient);
							break;
						default:
							break;
					}
				} else {
					Toast.makeText(ApplyAppointment.this, "You dont have Internet Connection....!", Toast.LENGTH_SHORT).show();
				}


			}
		});
	}

	public void MakeMedicalAppointment(final String appointment_start_time,
									   final String appointment_end_time,
									   final String patient_id,
									   final String doctor_id,
									   final String planid,
									   final String departmentId,
									   final String calltime,
									   final String callcost,
									   final String needtopay,
									   final String creditremains,
									   final String id,
									   final String purpose_of_visit,
									   final String allergies,
									   final String medication,
									   final String medicationTime,
									   final String generalSymptoms,
									   final String headNeckSymptoms,
									   final String chestSymptoms,
									   final String digestiveSymptoms,
									   final String pelvicSymptoms,
									   final String muscleSymptoms,
									   final String skinSymptoms,
									   final String medicalConditions,
									   final String pharmacyID,
									   final String transactionId,
									   final String acknowledgement,
									   final String amount,
									   final String paymentTime,
									   final String patient,
									   final String childId)
	{
		
		progress.setVisibility(View.VISIBLE);

		RequestQueue queue = Volley.newRequestQueue(ApplyAppointment.this);
		StringRequest sr = new StringRequest(Request.Method.POST,
				ConstantUrls.UrlMain + ConstantUrls.UrlScheduleAppointment,
				new Response.Listener<String>() 
				{
					@Override
					public void onResponse(String response) 
					{
						System.out.println("Apply contest response" + response);
						try
						{
							JSONObject json = new JSONObject(response);
							if (json.getString("code").equals("200")) 
							{
								Toast.makeText(getApplicationContext(),json.getString("message"), Toast.LENGTH_LONG).show();
								Intent intent = new Intent(getApplicationContext(), Home.class);
								intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
								startActivity(intent);
								ApplyAppointment.this.finish();
							}
							else
							{
								Toast.makeText(getApplicationContext(),json.getString("message")+", please try again..!", Toast.LENGTH_LONG).show();
							}

						} catch (Exception e) 
						{
							e.printStackTrace();
						}
						progress.setVisibility(View.INVISIBLE);
					}
				}, 
				new Response.ErrorListener() 
				{
					@Override
					public void onErrorResponse(VolleyError error) 
					{
						progress.setVisibility(View.INVISIBLE);
						System.out.println("error "+error.getMessage());
					}
				}) 
		{
			@Override
			protected Map<String, String> getParams() 
			{
				HashMap<String, String> params = new HashMap<String, String>();
				params.put("appointment_start_time", appointment_start_time);
				params.put("appointment_end_time", appointment_end_time);
				params.put("patient_id", patient_id);
				params.put("doctor_id", doctor_id);
				params.put("planid", planid);
				params.put("departmentId", departmentId);
				params.put("calltime", calltime);
				params.put("callcost", callcost);
				params.put("needtopay", needtopay);
				params.put("creditremains", creditremains);
				params.put("id", id);
				params.put("purpose", purpose_of_visit);
				params.put("allergies", allergies);
				params.put("medication", medication);
				params.put("medicationTime", medicationTime);
				params.put("generalSymptoms", generalSymptoms);
				params.put("headNeckSymptoms", headNeckSymptoms);
				params.put("chestSymptoms", chestSymptoms);
				params.put("digestiveSymptoms", digestiveSymptoms);
				params.put("pelvicSymptoms", pelvicSymptoms);
				params.put("muscleSymptoms", muscleSymptoms);
				params.put("skinSymptoms", skinSymptoms);
				params.put("medicalConditions", medicalConditions);
				params.put("pharmacyId", pharmacyID);
				params.put("transactionId", transactionId);
				params.put("acknowledgement", acknowledgement);
				params.put("amount", amount);
				params.put("paymentTime", paymentTime);
				params.put("patient", patient);
				params.put("childId", childId);
				
				return params;

			}
		};
		queue.add(sr);
	}

	public void MakePediatricsAppointment(final String appointment_start_time,
										  final String appointment_end_time,
										  final String patient_id,
										  final String doctor_id,
										  final String planid,
										  final String departmentId,
										  final String calltime,
										  final String callcost,
										  final String needtopay,
										  final String creditremains,
										  final String id,
										  final String purpose_of_visit,
										  final String allergies,
										  final String medication,
										  final String medicationTime,
										  final String generalSymptoms,
										  final String headNeckSymptoms,
										  final String chestSymptoms,
										  final String digestiveSymptoms,
										  final String pelvicSymptoms,
										  final String muscleSymptoms,
										  final String skinSymptoms,
										  final String medicalConditions,
										  final String pharmacyID,
										  final String transactionId,
										  final String acknowledgement,
										  final String amount,
										  final String paymentTime,
										  final String patient,
										  final String childId)
	{
		
		progress.setVisibility(View.VISIBLE);

		RequestQueue queue = Volley.newRequestQueue(ApplyAppointment.this);
		StringRequest sr = new StringRequest(Request.Method.POST,
				ConstantUrls.UrlMain + ConstantUrls.UrlScheduleAppointment,
				new Response.Listener<String>() 
				{
					@Override
					public void onResponse(String response) 
					{
						System.out.println("Apply contest response" + response);
						try
						{
							JSONObject json = new JSONObject(response);
							if (json.getString("code").equals("200")) 
							{
								Toast.makeText(getApplicationContext(),json.getString("message"), Toast.LENGTH_LONG).show();
								Intent intent = new Intent(getApplicationContext(), Home.class);
								intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
								startActivity(intent);
								ApplyAppointment.this.finish();
							}
							else
							{
								Toast.makeText(getApplicationContext(),json.getString("message")+", please try again..!", Toast.LENGTH_LONG).show();
							}

						} catch (Exception e) 
						{
							e.printStackTrace();
						}
						progress.setVisibility(View.INVISIBLE);
					}
				}, 
				new Response.ErrorListener() 
				{
					@Override
					public void onErrorResponse(VolleyError error) 
					{
						progress.setVisibility(View.INVISIBLE);
						System.out.println("error "+error.getMessage());
					}
				}) 
		{
			@Override
			protected Map<String, String> getParams() 
			{
				HashMap<String, String> params = new HashMap<String, String>();
				params.put("appointment_start_time", appointment_start_time);
				params.put("appointment_end_time", appointment_end_time);
				params.put("patient_id", patient_id);
				params.put("doctor_id", doctor_id);
				params.put("planid", planid);
				params.put("departmentId", departmentId);
				params.put("calltime", calltime);
				params.put("callcost", callcost);
				params.put("needtopay", needtopay);
				params.put("creditremains", creditremains);
				params.put("id", id);
				params.put("purpose", purpose_of_visit);
				params.put("allergies", allergies);
				params.put("medication", medication);
				params.put("medicationTime", medicationTime);
				params.put("generalSymptoms", generalSymptoms);
				params.put("neckSymptoms", headNeckSymptoms);
				params.put("chestSymptoms", chestSymptoms);
				params.put("digestiveSymptoms", digestiveSymptoms);
				params.put("pelvisSymptoms", pelvicSymptoms);
				params.put("musclesSymptoms", muscleSymptoms);
				params.put("skinSymptoms", skinSymptoms);
				params.put("medicalConditions", medicalConditions);
				params.put("pharmacyId", pharmacyID);
				params.put("transactionId", transactionId);
				params.put("acknowledgement", acknowledgement);
				params.put("amount", amount);
				params.put("paymentTime", paymentTime);
				params.put("patient", patient);
				params.put("childId", childId);



				System.out.println("Pedriatrics params.................... ");
				System.out.println("appointment_start_time " + appointment_start_time);
				System.out.println("appointment_end_time " + appointment_end_time);
				System.out.println("patient_id " + patient_id);
				System.out.println("doctor_id " + doctor_id);
				System.out.println("planid "+planid);
				System.out.println("departmentId "+departmentId);
				System.out.println("calltime "+calltime);
				System.out.println("callcost "+callcost);
				System.out.println("needtopay "+needtopay);
				System.out.println("creditremains "+creditremains);
				System.out.println("id "+id);
				System.out.println("purpose_of_visit "+purpose_of_visit);
				System.out.println("allergies "+allergies);
				System.out.println("medication "+medication);
				System.out.println("medicationTime "+medicationTime);
				System.out.println("generalSymptoms "+generalSymptoms);
				System.out.println("headNeckSymptoms "+headNeckSymptoms);
				System.out.println("chestSymptoms "+chestSymptoms);
				System.out.println("digestiveSymptoms "+digestiveSymptoms);
				System.out.println("pelvicSymptoms "+pelvicSymptoms);
				System.out.println("muscleSymptoms "+muscleSymptoms);
				System.out.println("skinSymptoms "+skinSymptoms);
				System.out.println("medicalConditions "+medicalConditions);
				System.out.println("pharmacyID "+pharmacyID);
				System.out.println("transactionId "+transactionId);
				System.out.println("acknowledgement "+acknowledgement);
				System.out.println("amount "+amount);
				System.out.println("paymentTime "+paymentTime);
				System.out.println("patient "+patient);
				System.out.println("childId "+childId);

				return params;
			}

		
		};
		queue.add(sr);
	}
	public void MakePsychologyAppointment(final String appointment_start_time,
										  final String appointment_end_time,
										  final String patient_id,
										  final String doctor_id,
										  final String planid,
										  final String departmentId,
										  final String calltime,
										  final String callcost,
										  final String needtopay,
										  final String creditremains,
										  final String id,
										  final String purpose,
										  final String medication,
										  final String medicationTime,
										  final String generalSymptoms,
										  final String relationshipSymptoms,
										  final String habitSymptoms,
										  final String medicalConditions,
										  final String pharmacyId,
										  final String transactionId,
										  final String acknowledgement,
										  final String amount,
										  final String paymentTime,
										  final String patient,
										  final String Exercise1,
										  final String Exercise2,
										  final String allergies,
										  final String childId)
	{
		
		progress.setVisibility(View.VISIBLE);

		RequestQueue queue = Volley.newRequestQueue(ApplyAppointment.this);
		StringRequest sr = new StringRequest(Request.Method.POST,
				ConstantUrls.UrlMain + ConstantUrls.UrlMakeAppointment,
				new Response.Listener<String>() 
				{
					@Override
					public void onResponse(String response) 
					{
						System.out.println("Apply contest response" + response);
						try
						{
							JSONObject json = new JSONObject(response);
							if (json.getString("code").equals("200")) 
							{
								Toast.makeText(getApplicationContext(),json.getString("message"), Toast.LENGTH_LONG).show();
								Intent intent = new Intent(getApplicationContext(), Home.class);
								intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
								startActivity(intent);
								ApplyAppointment.this.finish();
							}
							else
							{
								Toast.makeText(getApplicationContext(),json.getString("message")+", please try again..!", Toast.LENGTH_LONG).show();
							}
						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
						progress.setVisibility(View.INVISIBLE);
					}
				}, 
				new Response.ErrorListener() 
				{
					@Override
					public void onErrorResponse(VolleyError error) 
					{
						progress.setVisibility(View.INVISIBLE);
						System.out.println("error "+error.getMessage());
					}
				}) 
		{
			@Override
			protected Map<String, String> getParams() 
			{
				HashMap<String, String> params = new HashMap<String, String>();
				params.put("appointment_start_time",appointment_start_time );
				params.put("appointment_end_time",appointment_end_time );
				params.put("patient_id",patient_id );
				params.put("doctor_id",doctor_id );
				params.put("planid",planid );
				params.put("departmentId",departmentId );
				params.put("calltime",calltime );
				params.put("callcost",callcost );
				params.put("needtopay",needtopay );
				params.put("creditremains",creditremains );
				params.put("id",id );
				params.put("purpose",purpose );
				params.put("medication",medication );
				params.put("medicationTime",medicationTime );
				params.put("generalSymptoms",generalSymptoms );
				params.put("relationshipSymptoms",relationshipSymptoms );
				params.put("habitSymptoms",habitSymptoms );
				params.put("medicalConditions",medicalConditions );
				params.put("pharmacyId",pharmacyId );
				params.put("transactionId",transactionId );
				params.put("acknowledgement",acknowledgement );
				params.put("amount",amount );
				params.put("paymentTime",paymentTime );
				params.put("patient",patient );
				params.put("Exercise1",Exercise1 );
				params.put("Exercise2",Exercise2 );
				params.put("allergies", allergies);
				params.put("childId", childId);

				System.out.println("===============params===============");
				System.out.println("appointment_start_time" + appointment_start_time);
				System.out.println("appointment_end_time" +appointment_end_time);
				System.out.println("patient_id" +patient_id);
				System.out.println("doctor_id" +doctor_id);
				System.out.println("planid" +planid);
				System.out.println("departmentId" +departmentId);
				System.out.println("calltime" +calltime);
				System.out.println("callcost" +callcost);
				System.out.println("needtopay" +needtopay);
				System.out.println("creditremains" +creditremains);
				System.out.println("id" +id);
				System.out.println("purpose" +purpose);
				System.out.println("medication" +medication);
				System.out.println("medicationTime" +medicationTime);
				System.out.println("generalSymptoms" +generalSymptoms);
				System.out.println("relationshipSymptoms" +relationshipSymptoms);
				System.out.println("habitSymptoms" +habitSymptoms);
				System.out.println("medicalConditions" +medicalConditions);
				System.out.println("pharmacyId" +pharmacyId);
				System.out.println("transactionId" +transactionId);
				System.out.println("acknowledgement" +acknowledgement);
				System.out.println("amount" +amount);
				System.out.println("paymentTime" +paymentTime);
				System.out.println("patient" +patient);
				System.out.println("Exercise1" +Exercise1);
				System.out.println("Exercise2" +Exercise2);
				System.out.println("allergies" +allergies);
				System.out.println("childId" +childId);

				return params;
			}
		};
		queue.add(sr);
	}

	public void MakeLactationAppointment(final String appointment_start_time,
										 final String appointment_end_time,
										 final String patient_id,
										 final String doctor_id,
										 final String planid,
										 final String departmentId,
										 final String calltime,
										 final String callcost,
										 final String needtopay,
										 final String creditremains,
										 final String purpose,
										 final String babyAge,
										 final String gestutionDuration,
										 final String breastFedChildNum,
										 final String breastFedDuration,
										 final String breastPump,
										 final String allergies,
										 final String medication,
										 final String transactionId,
										 final String acknowledgement,
										 final String amount,
										 final String paymentTime,
										 final String pharmacyId,
										 final String medicationTime,
										 final String generalSymptoms,
										 final String breastSymptoms,
										 final String babySymptoms,
										 final String nippleSymptoms,
										 final String medicalConditions,
										 final String childId,
										 final String patient
	)
	{

		progress.setVisibility(View.VISIBLE);

		RequestQueue queue = Volley.newRequestQueue(ApplyAppointment.this);
		StringRequest sr = new StringRequest(Request.Method.POST,
				ConstantUrls.UrlMain + ConstantUrls.UrlScheduleAppointment,
				new Response.Listener<String>() 
				{
					@Override
					public void onResponse(String response) 
					{
						System.out.println("Apply contest response" + response);
						try
						{
							JSONObject json = new JSONObject(response);
							if (json.getString("code").equals("200")) 
							{
								Toast.makeText(getApplicationContext(),json.getString("message"), Toast.LENGTH_LONG).show();
								Intent intent = new Intent(getApplicationContext(), Home.class);
								intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
								startActivity(intent);
								ApplyAppointment.this.finish();
							}
							else
							{
								Toast.makeText(getApplicationContext(),json.getString("message")+", please try again..!", Toast.LENGTH_LONG).show();
							}

						} catch (Exception e) 
						{
							e.printStackTrace();
						}
						progress.setVisibility(View.INVISIBLE);
					}
				}, 
				new Response.ErrorListener() 
				{
					@Override
					public void onErrorResponse(VolleyError error) 
					{
						progress.setVisibility(View.INVISIBLE);
						System.out.println("error "+error.getMessage());
					}
				}) 
		{
			@Override
			protected Map<String, String> getParams() 
			{
				HashMap<String, String> params = new HashMap<String, String>();
				params.put("appointment_start_time",appointment_start_time );
				params.put("appointment_end_time",appointment_end_time );
				params.put("patient_id",patient_id );
				params.put("doctor_id",doctor_id );
				params.put("planid",planid );
				params.put("departmentId",departmentId );
				params.put("calltime",calltime );
				params.put("callcost",callcost );
				params.put("needtopay",needtopay );
				params.put("creditremains",creditremains );
				params.put("purpose",purpose );
				params.put("babyAge",babyAge );
				params.put("gestutionDuration",gestutionDuration );
				params.put("breastFedChildNum",breastFedChildNum );
				params.put("breastFedDuration",breastFedDuration );
				params.put("breastPump",breastPump );
				params.put("allergies",allergies );
				params.put("medication",medication );
				params.put("transactionId",transactionId );
				params.put("acknowledgement",acknowledgement );
				params.put("amount",amount );
				params.put("paymentTime",paymentTime );
				params.put("pharmacyId",pharmacyId );
				params.put("medicationTime",medicationTime );
				params.put("generalSymptoms",generalSymptoms );
				params.put("breastSymptoms",breastSymptoms );
				params.put("babySymptoms", babySymptoms );
				params.put("nippleSymptoms", nippleSymptoms );
				params.put("medicalConditions",medicalConditions);
				params.put("childId",childId );
				params.put("patient",patient );



				System.out.println("appointment_start_time" + appointment_start_time);
				System.out.println("appointment_end_time" + appointment_end_time);
				System.out.println("patient_id" +patient_id);
				System.out.println("doctor_id" +doctor_id);
				System.out.println("planid" +planid);
				System.out.println("departmentId" +departmentId);
				System.out.println("calltime" +calltime);
				System.out.println("callcost"+callcost);
				System.out.println("needtopay"+needtopay);
				System.out.println("creditremains"+creditremains);
				System.out.println("purpose"+purpose);
				System.out.println("babyAge" +babyAge);
				System.out.println("gestutionDuration"+gestutionDuration);
				System.out.println("breastFedChildNum"+breastFedChildNum);
				System.out.println("breastFedDuration"+breastFedDuration);
				System.out.println("breastPump"+breastPump);
				System.out.println("allergies" +allergies);
				System.out.println("medication"+medication);
				System.out.println("transactionId"+transactionId);
				System.out.println("acknowledgement"+acknowledgement);
				System.out.println("amount"+amount);
				System.out.println("paymentTime" +paymentTime);
				System.out.println("pharmacyId"+pharmacyId);
				System.out.println("medicationTime"+medicationTime);
				System.out.println("generalSymptoms"+generalSymptoms);
				System.out.println("breastSymptoms"+breastSymptoms);
				System.out.println("babySymptoms" +babySymptoms);
				System.out.println("nippleSymptoms"+nippleSymptoms);
				System.out.println("medicalConditions"+medicalConditions);
				System.out.println("childId"+childId);
				System.out.println("patient"+patient);
				
				return params;
			}
		};
		queue.add(sr);
	}
	public void SeeDoctorNow( final String generalSymptoms,
							  final String headNeckSymptoms,
							  final String digestiveTrack,
							  final String allergies,
							  final String medicalConditions,
							  final String appointmentStartTime,
							  final String appointmentEndTime,
							  final String doctorID,
							  final String patientId,
							  final String planId,
							  final String pharmacyId,
							  final String AMT,
							  final String TRANSACTIONID,
							  final String ACK,
							  final String purposeOfVisit,
							  final String creditRemains)

	{


		progress.setVisibility(View.VISIBLE);
		RequestQueue queue = Volley.newRequestQueue(ApplyAppointment.this);
		StringRequest sr = new StringRequest(Request.Method.POST,
				ConstantUrls.UrlMain + ConstantUrls.UrlSeeDoctorNow,
				new Response.Listener<String>()
				{
					@Override
					public void onResponse(String response)
					{
						System.out.println("See doctor response" + response);
						try
						{
							JSONObject json = new JSONObject(response);
							if (json.getString("code").equals("200"))
							{
								Toast.makeText(getApplicationContext(),json.getString("message"), Toast.LENGTH_LONG).show();
//								SeeDoctorData.appointmentId=json.getString("data");

								Intent intent = new Intent(getApplicationContext(), Home.class);
								intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
								startActivity(intent);
								ApplyAppointment.this.finish();
							}
							else
							{
								Toast.makeText(getApplicationContext(),json.getString("message")+", please try again..!", Toast.LENGTH_LONG).show();
							}
						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
						progress.setVisibility(View.INVISIBLE);
					}
				},
				new Response.ErrorListener()
				{
					@Override
					public void onErrorResponse(VolleyError error)
					{
						progress.setVisibility(View.INVISIBLE);
						System.out.println("error " + error.getMessage());
					}
				})
		{
			@Override
			protected Map<String, String> getParams()
			{
				HashMap<String, String> params = new HashMap<String, String>();
				params.put("generalSymptoms",generalSymptoms);
				params.put("headNeckSymptoms",headNeckSymptoms );
				params.put("digestiveTrack",digestiveTrack );
				params.put("allergies",allergies );
				params.put("medicalConditions",medicalConditions );
				params.put("appointmentStartTime",appointmentStartTime );
				params.put("appointmentEndTime",appointmentEndTime );
				params.put("doctorID",doctorID );
				params.put("patientId",patientId );
				params.put("planId",planId );
				params.put("pharmacyId",pharmacyId );
				params.put("AMT",AMT );
				params.put("TRANSACTIONID",TRANSACTIONID );
				params.put("ACK",ACK );
				params.put("purposeOfVisit", purposeOfVisit);
				params.put("creditRemains", creditRemains);

				System.out.println("generalSymptoms " + generalSymptoms);
				System.out.println("headNeckSymptoms "+headNeckSymptoms);
				System.out.println("digestiveTrack "+digestiveTrack);
				System.out.println("allergies "+allergies);
				System.out.println("medicalConditions "+medicalConditions);
				System.out.println("appointmentStartTime "+appointmentStartTime);
				System.out.println("appointmentEndTime "+appointmentEndTime);
				System.out.println("doctorID "+doctorID);
				System.out.println("patientId "+patientId);
				System.out.println("planId "+planId);
				System.out.println("pharmacyId "+pharmacyId);
				System.out.println("AMT "+AMT);
				System.out.println("TRANSACTIONID "+TRANSACTIONID);
				System.out.println("ACK "+ACK);
				System.out.println("purposeOfVisit "+purposeOfVisit);
				System.out.println("creditRemains "+creditRemains);
				return params;
			}
		};
		queue.add(sr);
	}
}
