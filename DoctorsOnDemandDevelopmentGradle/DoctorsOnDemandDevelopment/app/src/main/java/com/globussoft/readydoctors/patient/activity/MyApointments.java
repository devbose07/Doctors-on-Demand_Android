package com.globussoft.readydoctors.patient.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.globussoft.readydoctors.patient.R;
import com.globussoft.readydoctors.patient.Utills.AppData;
import com.globussoft.readydoctors.patient.Utills.ConnectionDetector;
import com.globussoft.readydoctors.patient.Utills.ConstantTag;
import com.globussoft.readydoctors.patient.Utills.ConstantUrls;
import com.globussoft.readydoctors.patient.Utills.Singleton;
import com.globussoft.readydoctors.patient.adapter.MyApointmentAdapter;
import com.globussoft.readydoctors.patient.model.AppointmentsModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyApointments extends Activity
{

    ListView list;
    ProgressBar progress;
    ArrayList<AppointmentsModel> arrayList=new ArrayList<AppointmentsModel>();
    ImageView backImage;
    TextView noAppointments;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.my_apointment);
        InitUI();
    }

    void InitUI()
    {
        backImage=(ImageView)findViewById(R.id.backImage);
        backImage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
            }
        });
        list = (ListView) findViewById(R.id.list);
        progress= (ProgressBar) findViewById(R.id.progress);
        noAppointments= (TextView) findViewById(R.id.no_appointments);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AppData.appointmentsModel = arrayList.get(position);
                Intent intent = new Intent(getApplicationContext(), MyAppointmentDetails.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onResume()
    {
        System.out.println("i am in onrewume");
        super.onResume();
        if(new ConnectionDetector(getApplicationContext()).isConnectingToInternet())
        {
            ApoinmentHistory(Singleton.PatientID);
        }
        else
        {
            Toast.makeText(getApplicationContext(),"You don't have internet...!",Toast.LENGTH_SHORT).show();
            onBackPressed();
        }


    }
// fetch upcoming apointment

    public void ApoinmentHistory(final String patient_id)
    {
        progress.setVisibility(View.VISIBLE);
        arrayList.clear();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest sr = new StringRequest(Request.Method.POST,
                ConstantUrls.UrlMain + ConstantUrls.UrlGetPatientApointmentNew,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        System.out.println("appointments response" + response);
                        try
                        {
                            JSONObject json = new JSONObject(response);
                            if (json.getString("code").equals("200"))
                            {
                                JSONArray data = json.getJSONArray(ConstantTag.tag_Data);
                                for (int i = 0; i < data.length(); i++)
                                {
                                    JSONObject obj = data.getJSONObject(i);
                                    AppointmentsModel model = new AppointmentsModel();
                                    model.setFirstName(obj.getString("FirstName"));
                                    model.setLastName(obj.getString("LastName"));
                                    model.setAppointment_id(obj.getString("appointment_id"));
                                    model.setPatient_id(obj.getString("patient_id"));
                                    model.setDoctor_id(obj.getString("doctor_id"));
                                    model.setPlanId(obj.getString("planId"));
                                    model.setAppointment_start_time(obj.getString("appointment_start_time"));
                                    model.setAppointment_end_time(obj.getString("appointment_end_time"));
                                    model.setAppointmentStatus(obj.getString("appointmentStatus"));
                                    model.setPatient(obj.getString("patient"));
                                    model.setChildId(obj.getString("childId"));
                                    model.setReferredBy(obj.getString("referredBy"));
                                    model.setPurposeOfVisit(obj.getString("purposeOfVisit"));
                                    model.setAllergies(obj.getString("allergies"));
                                    model.setGeneralSymptoms(obj.getString("generalSymptoms"));
                                    model.setRelationshipSymptoms(obj.getString("relationshipSymptoms"));
                                    model.setHeadNeckSymptoms(obj.getString("headNeckSymptoms"));
                                    model.setBreastSymtoms(obj.getString("breastSymtoms"));
                                    model.setBabySymptoms(obj.getString("babySymptoms"));
                                    model.setNippleSymptoms(obj.getString("nippleSymptoms"));
                                    model.setChestSymptoms(obj.getString("chestSymptoms"));
                                    model.setPelvisSymptoms(obj.getString("pelvisSymptoms"));
                                    model.setSkinSymptoms(obj.getString("skinSymptoms"));
                                    model.setMuscleSymptoms(obj.getString("muscleSymptoms"));
                                    model.setDigestiveTrack(obj.getString("digestiveTrack"));
                                    model.setHabits(obj.getString("habits"));
                                    model.setMedicalConditions(obj.getString("medicalConditions"));
                                    model.setPharmacyId(obj.getString("pharmacyId"));
                                    model.setMedications(obj.getString("medications"));
                                    model.setMedicationTime(obj.getString("medicationTime"));
                                    model.setCreated_at(obj.getString("created_at"));
                                    model.setUpdated_at(obj.getString("updated_at"));
                                    model.setCallTime(obj.getString("callTime"));
                                    model.setCallCost(obj.getString("callCost"));
                                    model.setNeedToPay(obj.getString("needToPay"));
                                    model.setExerciseOne(obj.getString("exerciseOne"));
                                    model.setExerciseTwo(obj.getString("exerciseTwo"));
                                    model.setBabyAge(obj.getString("babyAge"));
                                    model.setGestutionDuration(obj.getString("gestutionDuration"));
                                    model.setBreastFedChildNum(obj.getString("breastFedChildNum"));
                                    model.setBreastFedDuration(obj.getString("breastFedDuration"));
                                    model.setBreastPump(obj.getString("breastPump"));
                                    model.setDoctorContactNumber(obj.getString("doctorContactNumber"));
                                    model.setDoctorOnlineStatus(obj.getString("doctorOnlineStatus"));
                                    model.setDoctorAvailableStatus(obj.getString("doctorAvailableStatus"));
                                    model.setDoctorSex(obj.getString("doctorSex"));
                                    model.setDoctorAbout(obj.getString("doctorAbout"));
                                    model.setDoctorAvailableFrom(obj.getString("doctorAvailableFrom"));
                                    model.setDoctorAvailableTo(obj.getString("doctorAvailableTo"));
                                    model.setLatitude(obj.getString("latitude"));
                                    model.setLongitude(obj.getString("longitude"));
                                    model.setDoctorRegistrationDate(obj.getString("doctorRegistrationDate"));
                                    model.setDoctorProfilePicUrl(obj.getString("doctorProfilePicUrl"));
                                    model.setDoctorStatus(obj.getString("doctorStatus"));



                                    arrayList.add(model);
                                }
                                if(arrayList.size()<=0)
                                {
                                    Toast.makeText(getApplicationContext(),"You don't have any appointments..!",Toast.LENGTH_SHORT).show();
                                    noAppointments.setVisibility(View.VISIBLE);
                                   /* Intent intent = new Intent(getApplicationContext(), Home.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();*/
                                }else
                                {
                                    MyApointmentAdapter adapter = new MyApointmentAdapter( getApplicationContext(), arrayList);
                                    list.setAdapter(adapter);
                                }


                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),json.getString("message"),Toast.LENGTH_SHORT).show();
                               /* Intent intent = new Intent(getApplicationContext(), Home.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();*/
                                noAppointments.setVisibility(View.VISIBLE);
                            }

                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),"Something went wrong...!",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), Home.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                        }
                        progress.setVisibility(View.INVISIBLE);
                    }
                }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                progress.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(),"Something went wrong...!",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Home.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        })
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("userId", patient_id);

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
}
