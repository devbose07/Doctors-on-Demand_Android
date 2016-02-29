package com.globussoft.readydoctors.doctor.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
import com.globussoft.readydoctors.doctor.videochat.Consts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Appointments extends Fragment {
    View rootView;
    ListView listView;
    ProgressBar progress;
    RelativeLayout videocall;
    ArrayList<AppointmentsModel> appList = new ArrayList<AppointmentsModel>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.appointment_fragment, container,
                false);
        Consts.second_user_id_list.clear();
       Consts.second_user_id_list.add(Consts.second_user_id);
        initUI();

        return rootView;

    }

    private void initUI() {

        //videocall = (RelativeLayout) rootView.findViewById(R.id.videocall);
        listView = (ListView) rootView.findViewById(R.id.list);
        progress = (ProgressBar) rootView.findViewById(R.id.progress);
        if(new ConnectionDetector(getActivity()).isConnectingToInternet())
        {
            FetchAllAp(MainSingleton.doctor_id);
        }
        else
        {
            Toast.makeText(getActivity(), "You dont have Internet...!", Toast.LENGTH_SHORT).show();
        }


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if(new ConnectionDetector(getActivity()).isConnectingToInternet())
                {
                    MainSingleton.selectedappointmentsModel = appList.get(position);
                    MainSingleton.appointment_id = appList.get(position).getAppointment_id();
                    System.out.print("selected app ==" + MainSingleton.appointment_id);
                    Intent intent = new Intent(getActivity(),ApoinmentDetials.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getActivity(), "You dont have Internet...!", Toast.LENGTH_SHORT).show();
                }

            }
        });



    }

    // fetch upcoming apointment

    public void FetchAllAp(final String doctor_id)
    {
        System.out.print("doctor_id ==" + doctor_id);
        progress.setVisibility(View.VISIBLE);
        appList.clear();
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequest sr = new StringRequest(Request.Method.POST,
                ConstantUrls.UrlMain + ConstantUrls.UrlAllApointment,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {

                        System.out.println("UrlAllApointment response" + response);
                        try {
                            JSONObject json = new JSONObject(response);
                            if (json.getString("code").equals("200")) {

                                JSONArray data = json
                                        .getJSONArray(ConstantTag.tag_Data);
                                for (int i = 0; i < data.length(); i++) {

                                    JSONObject obj = data.getJSONObject(i);

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




                                    appList.add(model);
                                }

                                AppointmentAdapter adapter = new AppointmentAdapter(
                                        getActivity(), appList);
                                listView.setAdapter(adapter);

                            } else {
                            }

                        } catch (Exception e) {
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
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(sr);
    }

    String GetJsonDate(JSONObject obj, String key) throws JSONException {
        String data = null;
        if (obj.has(key)) {
            data = obj.getString(key);
        }

        return data;

    }
}
