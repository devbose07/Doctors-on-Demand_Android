package com.globussoft.readydoctors.doctor.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.globussoft.readydoctors.doctor.Activity.ApoinmentDetials;
import com.globussoft.readydoctors.doctor.R;
import com.globussoft.readydoctors.doctor.adapters.AppointmentAdapter;
import com.globussoft.readydoctors.doctor.models.AppointmentsModel;
import com.globussoft.readydoctors.doctor.uttils.ConnectionDetector;
import com.globussoft.readydoctors.doctor.uttils.ConstantTag;
import com.globussoft.readydoctors.doctor.uttils.ConstantUrls;
import com.globussoft.readydoctors.doctor.uttils.MainSingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Upcoming extends Fragment {
	View rootView;
	ListView listView;
	ProgressBar progress;
	ArrayList<AppointmentsModel> upcomingList = new ArrayList<AppointmentsModel>();
	long startTime,endTime,currentTime;
	String givenStartDateTimeString,givenEndDateTimeString;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.upcoming_fragment, container,
				false);
		initUI();
		return rootView;
	}

	private void initUI() {

		listView = (ListView) rootView.findViewById(R.id.list);
		progress = (ProgressBar) rootView.findViewById(R.id.progress);
		if(new ConnectionDetector(getActivity()).isConnectingToInternet())
		{
			FetchUpcomingAp(MainSingleton.doctor_id);
		}
		else
		{
			Toast.makeText(getActivity(), "You dont have Internet...!", Toast.LENGTH_SHORT).show();
		}

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id)
			{
				if(new ConnectionDetector(getActivity()).isConnectingToInternet())
				{
					MainSingleton.appointment_id = upcomingList.get(position).getAppointment_id();
					Intent intent = new Intent(getActivity(),
							ApoinmentDetials.class);
					startActivity(intent);
				}
				else
				{
					Toast.makeText(getActivity(), "You dont have Internet...!", Toast.LENGTH_SHORT).show();
				}


			}
		});
	}

	public boolean checkUpcoming()
	{
		//2016-01-23 08:00:00
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try
		{
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println("*********Schedule timings*********** ");
			Date startDate = sdf.parse(givenStartDateTimeString);
			startTime = startDate.getTime();
			System.out.println(givenStartDateTimeString+" start_time in milli :: " + startTime);

			Date endDate = sdf.parse(givenEndDateTimeString);
			endTime = endDate.getTime();
			System.out.println(givenEndDateTimeString + " end_time in milli :: " + endTime);

			Date currentDate = sdf.parse(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.format(Calendar.getInstance()
							.getTime()));
			currentTime = Calendar.getInstance().getTimeInMillis();
			System.out.println(currentDate+"Current in milli :: "+currentTime);
			System.out.println("*********___________*********** ");
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println();
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}


		if(currentTime>=endTime)
		{
			return false;
		}

		return true;
	}

	public String ConvertUtcToLocal(String originalUTCTime) throws ParseException
	{
		System.out.print("Time in UTC <"+ originalUTCTime+">");

		String format = "yyyy-MM-dd HH:mm:ss";

		SimpleDateFormat sdf = new SimpleDateFormat(format);

		Date date = sdf.parse(originalUTCTime.trim());

		long utcStamp = date.getTime();

		// Add/Substract Zone offset into UTC time
		long localTimeStamp = utcStamp + Calendar.getInstance().get(Calendar.ZONE_OFFSET);

		Timestamp timestamp = new Timestamp(localTimeStamp);

		Date finalLocatDate = new Date(timestamp.getTime());

		String finalLocalDateString = sdf.format(finalLocatDate);

		System.out.print("finalLocalDateString   "+finalLocalDateString);

		return finalLocalDateString;
	}
	// fetch upcoming apointment

	public void FetchUpcomingAp(final String doctor_id) {
		System.out.print("doctor_id ==" + doctor_id);
		progress.setVisibility(View.VISIBLE);
		upcomingList.clear();
		RequestQueue queue = Volley.newRequestQueue(getActivity());
		StringRequest sr = new StringRequest(Request.Method.POST,
				ConstantUrls.UrlMain + ConstantUrls.UrlAllApointment,
				new Response.Listener<String>()
				{
					@Override
					public void onResponse(String response)
					{

						System.out.println("UrlAllApointment response" + response);
						try
						{
							JSONObject json = new JSONObject(response);
							if (json.getString("code").equals("200"))
							{
								JSONArray data = json.getJSONArray(ConstantTag.tag_Data);
								for (int i = 0; i < data.length(); i++)
								{
									JSONObject obj = data.getJSONObject(i);
									givenStartDateTimeString=ConvertUtcToLocal(GetJsonDate(obj,"appointment_start_time"));
									givenEndDateTimeString=ConvertUtcToLocal(GetJsonDate(obj,"appointment_end_time"));
									boolean upcoming=checkUpcoming();
									if (upcoming)
									{
										System.out.println("upcoming");
										AppointmentsModel model = new AppointmentsModel();
										model.setPatientMetaID(GetJsonDate(obj,"patientMetaID"));
										model.setPatient_id(GetJsonDate(obj,"patient_id"));
										model.setPatientFirstName(GetJsonDate(obj,"patientFirstName"));
										model.setPatientLastName(GetJsonDate(obj,"patientLastName"));
										model.setAge(GetJsonDate(obj,"age"));
										model.setSex(GetJsonDate(obj,"sex"));
										model.setPatientBloodGroup(GetJsonDate(obj,"patientBloodGroup"));
										model.setPatientDateOfBirth(GetJsonDate(obj,"patientDateOfBirth"));
										model.setPatientAddress(GetJsonDate(obj,"patientAddress"));
										model.setPatientWeight(GetJsonDate(obj,"patientWeight"));
										model.setLatitude(GetJsonDate(obj,"latitude"));
										model.setLongitude(GetJsonDate(obj,"longitude"));
										model.setPatientHeight(GetJsonDate(obj,"patientHeight"));
										model.setPatientPersonalNotes(GetJsonDate(obj,"patientPersonalNotes"));
										model.setPatientProfilePictureUrl(GetJsonDate(obj,"patientProfilePictureUrl"));
										model.setUpdated_at(GetJsonDate(obj,"updated_at"));
										model.setCreated_at(GetJsonDate(obj,"created_at"));
										model.setAppointment_id(GetJsonDate(obj,"appointment_id"));
										model.setDoctor_id(GetJsonDate(obj,"doctor_id"));
										model.setPlanId(GetJsonDate(obj,"planId"));
										model.setAppointment_start_time(GetJsonDate(obj,"appointment_start_time"));
										model.setAppointment_end_time(GetJsonDate(obj,"appointment_end_time"));
										model.setAppointmentStatus(GetJsonDate(obj,"appointmentStatus"));
										model.setPatient(GetJsonDate(obj,"patient"));
										model.setChildId(GetJsonDate(obj,"childId"));
										model.setReferredBy(GetJsonDate(obj,"referredBy"));
										model.setPurposeOfVisit(GetJsonDate(obj,"purposeOfVisit"));
										model.setAllergies(GetJsonDate(obj,"allergies"));
										model.setGeneralSymptoms(GetJsonDate(obj,"generalSymptoms"));
										model.setRelationshipSymptoms(GetJsonDate(obj,"relationshipSymptoms"));
										model.setHeadNeckSymptoms(GetJsonDate(obj,"headNeckSymptoms"));
										model.setBreastSymtoms(GetJsonDate(obj,"breastSymtoms"));
										model.setBabySymptoms(GetJsonDate(obj,"babySymptoms"));
										model.setNippleSymptoms(GetJsonDate(obj,"nippleSymptoms"));
										model.setChestSymptoms(GetJsonDate(obj,"chestSymptoms"));
										model.setPelvisSymptoms(GetJsonDate(obj,"pelvisSymptoms"));
										model.setSkinSymptoms(GetJsonDate(obj,"skinSymptoms"));
										model.setMuscleSymptoms(GetJsonDate(obj,"muscleSymptoms"));
										model.setDigestiveTrack(GetJsonDate(obj,"digestiveTrack"));
										model.setHabits(GetJsonDate(obj,"habits"));
										model.setMedicalConditions(GetJsonDate(obj,"medicalConditions"));
										model.setPharmacyId(GetJsonDate(obj,"pharmacyId"));
										model.setMedications(GetJsonDate(obj,"medications"));
										model.setMedicationTime(GetJsonDate(obj,"medicationTime"));
										model.setCallTime(GetJsonDate(obj,"callTime"));
										model.setCallCost(GetJsonDate(obj,"callCost"));
										model.setNeedToPay(GetJsonDate(obj,"needToPay"));
										model.setExerciseOne(GetJsonDate(obj,"exerciseOne"));
										model.setExerciseTwo(GetJsonDate(obj,"exerciseTwo"));
										model.setBabyAge(GetJsonDate(obj,"babyAge"));
										model.setGestutionDuration(GetJsonDate(obj,"gestutionDuration"));
										model.setBreastFedChildNum(GetJsonDate(obj,"breastFedChildNum"));
										model.setBreastFedDuration(GetJsonDate(obj,"breastFedDuration"));
										model.setBreastPump(GetJsonDate(obj,"breastPump"));
										model.setTransactionId(GetJsonDate(obj,"transactionId"));
										upcomingList.add(model);
									}
									else
									{
										System.out.println("not an upcoming");
									}
								}
								if(upcomingList.size()>0)
								{
									AppointmentAdapter adapter = new AppointmentAdapter(getActivity(), upcomingList);
									listView.setAdapter(adapter);
								}
								else
								{
									Toast.makeText(getActivity(), "Sory, you dont have any upcoming appointments..!", Toast.LENGTH_SHORT).show();
								}


							}
							else
							{
								Toast.makeText(getActivity(), json.getString("message"), Toast.LENGTH_SHORT).show();
							}

						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
						progress.setVisibility(View.INVISIBLE);
					}
				}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				progress.setVisibility(View.INVISIBLE);
			}
		}) {
			@Override
			protected Map<String, String> getParams() {
				Map<String, String> params = new HashMap<String, String>();
				params.put("doctorId", doctor_id);

				return params;
			}

			@Override
			public Map<String, String> getHeaders() throws AuthFailureError
			{
				Map<String, String> params = new HashMap<String, String>();
				params.put("Content-Type", "application/x-www-form-urlencoded");
				return params;
			}
		};
		queue.add(sr);
	}

	String GetJsonDate(JSONObject obj, String key) throws JSONException
	{
		String data = null;
		if (obj.has(key)) {
			data = obj.getString(key);
		}

		return data;

	}
}