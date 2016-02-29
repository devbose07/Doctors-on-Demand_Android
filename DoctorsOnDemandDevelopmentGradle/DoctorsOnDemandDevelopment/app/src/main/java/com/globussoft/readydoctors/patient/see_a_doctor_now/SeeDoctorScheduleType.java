package com.globussoft.readydoctors.patient.see_a_doctor_now;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.globussoft.readydoctors.patient.R;
import com.globussoft.readydoctors.patient.Utills.ConnectionDetector;
import com.globussoft.readydoctors.patient.Utills.ConstantUrls;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by GLB-276 on 11/21/2015.
 */
public class SeeDoctorScheduleType extends Activity
{
    RelativeLayout schemeA,schemeB;
    TextView schemeATxt,schemeBTxt,title,next;

    ImageView backImage;
    String callcost1,calltime1,creditremains1,needtopay1,planid1,callcost2,calltime2,creditremains2,needtopay2,planid2;
    String cost1=" minutes for ",cost2=" minutes for ";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        System.out.println("PatientID " + SeeDoctorData.patientId);
        setContentView(R.layout.select_psychology_scheme);
        initUI();

    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
    }
    void initUI()
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
        next=(TextView)findViewById(R.id.next);
        next.setVisibility(View.INVISIBLE);
        if(new ConnectionDetector(getApplicationContext()).isConnectingToInternet())
        {
            GetCalCost(SeeDoctorData.patientId, "" + "5");
        }
        else
        {
            Toast.makeText(SeeDoctorScheduleType.this, "You dont have Internet Connection....!", Toast.LENGTH_SHORT).show();
        }


        title=(TextView)findViewById(R.id.edittext);
        title.setText("APPOINTMENT DURATION");

        schemeATxt=(TextView)findViewById(R.id.schemeATxt);
        schemeATxt.setText(cost1);

        schemeBTxt=(TextView)findViewById(R.id.schemeBTxt);
        schemeBTxt.setText(cost2);

        schemeA=(RelativeLayout)findViewById(R.id.schemeA);
        schemeA.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                SeeDoctorData.AMT=needtopay1;
                SeeDoctorData.callcost=callcost1;
                SeeDoctorData.calltime=callcost1;
                SeeDoctorData.needtopay=needtopay1;
                SeeDoctorData.creditremains=creditremains1;
                SeeDoctorData.planId=planid1;
                Intent intent = new Intent(getApplicationContext(),SeeDoctorAllergies.class);
                startActivity(intent);

            }
        });

        schemeB=(RelativeLayout)findViewById(R.id.schemeB);
        schemeB.setVisibility(View.INVISIBLE);
        schemeB.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                SeeDoctorData.AMT=needtopay2;
                SeeDoctorData.callcost=callcost2;
                SeeDoctorData.calltime=callcost2;
                SeeDoctorData.needtopay=needtopay2;
                SeeDoctorData.creditremains=creditremains2;
                SeeDoctorData.planId=planid2;
                Intent intent = new Intent(getApplicationContext(),SeeDoctorAllergies.class);
                startActivity(intent);

            }
        });

    }

    public void GetCalCost(final String id,final String did)
    {
        RequestQueue queue = Volley.newRequestQueue(SeeDoctorScheduleType.this);
        StringRequest sr = new StringRequest(Request.Method.POST,
                ConstantUrls.UrlMain + ConstantUrls.UrlGetUserCalCost,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
						/*Apply contest response{"code":200,"message":"Appointment created successfully","data":
						{"doctor_id":"40","patient_id":"16","appointment_start_time":"2015-09-21 10:00:00.000000",
							"appointment_end_time":"2015-09-21 10:30:00.000000","patient":"1","referredBy":"friends",
							"purposeOfVisit":"jjfhfdhdhx","timePeriod":"today","medications":
								"[{\"time\":\"The past week\",\"name\":\"dyhfufhfjfjgj\"}]",
								"allergies":"[\"fufhfjjfj\"]","symptoms":"{\"general-symptoms\":"
										+ "[\"Fever\",\"Weight loss\\\/gain\",\"Difficulty sleeping\"]}",
										"medicalConditions":"good","pharmacy":"1"}}*/
                        System.out.println("getuser cal cost" + response);
                        try
                        {
                            JSONObject json = new JSONObject(response);
                            if (json.getString("code").equals("200"))
                            {
                                JSONArray array=new  JSONArray();
                                if(json.has("data"))
                                {
                                    array=new JSONArray(json.getString("data").toString());

                                    String time	=array.getJSONObject(0).getString("calltime");
                                    String cost =array.getJSONObject(0).getString("callcost");
                                    callcost1=array.getJSONObject(0).getString("callcost");
                                    calltime1=array.getJSONObject(0).getString("calltime");
                                    creditremains1=array.getJSONObject(0).getString("creditremains");
                                    needtopay1=array.getJSONObject(0).getString("needtopay");
                                    planid1=array.getJSONObject(0).getString("pid");
                                    cost1=time+" minutes for $"+cost;

                                    String time_1 =array.getJSONObject(1).getString("calltime");
                                    String cost_1 =array.getJSONObject(1).getString("callcost");
                                    callcost2=array.getJSONObject(1).getString("callcost");
                                    calltime2=array.getJSONObject(1).getString("calltime");
                                    creditremains2=array.getJSONObject(1).getString("creditremains");
                                    needtopay2=array.getJSONObject(1).getString("needtopay");
                                    planid2=array.getJSONObject(1).getString("pid");
                                    cost2=time_1+" minutes for $"+cost_1;

                                    System.out.println(cost1);
                                    System.out.println(cost2);

                                    schemeATxt.setText(cost1);
                                    schemeBTxt.setText(cost2);
                                }
                                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                                System.out.println("array "+array);
                            }

                        } catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {

                        System.out.println("error "+error.getMessage());
                    }
                })
        {
            @Override
            protected Map<String, String> getParams()
            {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("id", id);
                params.put("did", did);


                return params;
            }


        };
        queue.add(sr);
    }

}