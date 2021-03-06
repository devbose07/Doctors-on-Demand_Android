package com.globussoft.readydoctors.patient.mediacal;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
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
import com.globussoft.readydoctors.patient.Utills.AppData;
import com.globussoft.readydoctors.patient.Utills.ConnectionDetector;
import com.globussoft.readydoctors.patient.Utills.ConstantUrls;
import com.globussoft.readydoctors.patient.Utills.Singleton;
import com.globussoft.readydoctors.patient.activity.ApplyCoupon;
import com.globussoft.readydoctors.patient.activity.Home;
import com.globussoft.readydoctors.patient.activity.SignIn;
import com.globussoft.readydoctors.patient.medical_static_views.MeetTheDoctors;
import com.globussoft.readydoctors.patient.medical_static_views.Pricing;
import com.globussoft.readydoctors.patient.medical_static_views.WhatDoWeTreat;
import com.globussoft.readydoctors.patient.medical_static_views.WhatIsVideoVisit;
import com.globussoft.readydoctors.patient.meet_us.MeetUs;
import com.globussoft.readydoctors.patient.see_a_doctor_now.SeeDoctorSelectPatient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Medical extends Activity {

	RelativeLayout mSchedule,video_visit,what_we_treat,meet_dr,pricing,abtus;
	ImageView backImage,seemedical;
	TextView next,schemeATxt,schemeBTxt;
	AlertDialog alert;
	String cost1=" minutes for ",cost2=" minutes for ";
	String callcost1,calltime1,creditremains1,needtopay1,planid1,callcost2,calltime2,creditremains2,needtopay2,planid2;
	public boolean gotCost=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.medical);

		initUI();
		
	}
	public void ShowDialog1(final boolean seeDoctor)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(Medical.this);
		LayoutInflater inflater = this.getLayoutInflater();
		View convertView = (View) inflater.inflate(R.layout.price_dialog, null);
		TextView title=(TextView) convertView.findViewById(R.id.title);
		TextView txt=(TextView) convertView.findViewById(R.id.desc);
		schemeATxt=(TextView) convertView.findViewById(R.id.cost1);

		schemeBTxt=(TextView) convertView.findViewById(R.id.cost2);

		schemeBTxt.setVisibility(View.INVISIBLE);
		TextView apply=(TextView) convertView.findViewById(R.id.apply);
		apply.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Intent intent=new Intent(getApplicationContext(), ApplyCoupon.class);
				startActivity(intent);
				alert.dismiss();
			}
		});
		RelativeLayout continuebtn=(RelativeLayout) convertView.findViewById(R.id.letsgo);
		builder.setView(convertView);
		alert = builder.create();
		alert.show();
		continuebtn.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				if(gotCost)
				{
					if(seeDoctor)
					{
						if(AppData.loginStatus)
						{
							Intent intent=new Intent(getApplicationContext(),SeeDoctorSelectPatient.class);
							startActivity(intent);
						}
						else
						{
							Toast.makeText(getApplicationContext(), "Please Sign in to Schedule an appointment.", Toast.LENGTH_SHORT).show();
							Intent intent = new Intent(getApplicationContext(),SignIn.class);
							startActivity(intent);
							Medical.this.finish();
						}
					}
					else
					{
						AppData.appointmentType=4;
						if(AppData.loginStatus)
						{
							Intent intent = new Intent(getApplicationContext(),SelectMedicalPatient.class);
							startActivity(intent);
						}
						else
						{
							Toast.makeText(getApplicationContext(), "Please Sign in to Schedule an appointment.", Toast.LENGTH_SHORT).show();
							Intent intent = new Intent(getApplicationContext(),SignIn.class);
							startActivity(intent);
							Medical.this.finish();
						}
					}
					alert.dismiss();
				}
				else
				{
					Toast.makeText(getApplicationContext(),"Please wait...",Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	@Override
	public void onBackPressed()
	{
		super.onBackPressed();
	}

	void initUI()
	{
		next=(TextView)findViewById(R.id.next);
		seemedical=(ImageView) findViewById(R.id.seemedical);
		seemedical.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
				gotCost=false;
				if(new ConnectionDetector(getApplicationContext()).isConnectingToInternet())
				{
					GetCalCost(Singleton.PatientID, "" + 5);
				}
				else
				{
					Toast.makeText(Medical.this, "You dont have Internet Connection....!", Toast.LENGTH_SHORT).show();
				}
				ShowDialog1(true);
			}
		});
		backImage=(ImageView)findViewById(R.id.backImage);
		backImage.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				onBackPressed();
			}
		});
		video_visit=(RelativeLayout)findViewById(R.id.video_visit);
		video_visit.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(getApplicationContext(),WhatIsVideoVisit.class);
				startActivity(intent);
			}
		});
		mSchedule=(RelativeLayout)findViewById(R.id.schedule);
		mSchedule.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View v) 
			{
				gotCost=false;
				if(new ConnectionDetector(getApplicationContext()).isConnectingToInternet())
				{
					GetCalCost(Singleton.PatientID, "" + AppData.appointmentType);
				}
				else
				{
					Toast.makeText(Medical.this, "You dont have Internet Connection....!", Toast.LENGTH_SHORT).show();
				}
				ShowDialog1(false);
			}
		});
		what_we_treat=(RelativeLayout)findViewById(R.id.wat_we_treat);
		what_we_treat.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(getApplicationContext(), WhatDoWeTreat.class);
				startActivity(intent);
				Medical.this.finish();
			}
		});

		meet_dr=(RelativeLayout)findViewById(R.id.meet_dr);
		meet_dr.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(getApplicationContext(), MeetTheDoctors.class);
				startActivity(intent);
				Medical.this.finish();
			}
		});
		pricing=(RelativeLayout)findViewById(R.id.pricing);
		pricing.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(getApplicationContext(), Pricing.class);
				startActivity(intent);
				Medical.this.finish();
			}
		});
		abtus=(RelativeLayout)findViewById(R.id.abtus);
		abtus.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(getApplicationContext(), MeetUs.class);
				startActivity(intent);
				Medical.this.finish();
			}
		});
		
	}
	public void GetCalCost(final String id,final String did)
	{
		RequestQueue queue = Volley.newRequestQueue(Medical.this);
		StringRequest sr = new StringRequest(Request.Method.POST,
				ConstantUrls.UrlMain + ConstantUrls.UrlGetUserCalCost,
				new Response.Listener<String>()
				{
					@Override
					public void onResponse(String response)
					{
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
									gotCost=true;
									schemeATxt.setText(cost1);
									schemeBTxt.setText(cost2);
								}
								else
								{
									Toast.makeText(getApplicationContext(), "Something went wrong..please try again later..!", Toast.LENGTH_LONG).show();
									Intent intent = new Intent(getApplicationContext(), Home.class);
									intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
									startActivity(intent);
									finish();
								}

							}
							else
							{
								Toast.makeText(getApplicationContext(), "Something went wrong..please try again later..!", Toast.LENGTH_LONG).show();
								Intent intent = new Intent(getApplicationContext(), Home.class);
								intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
								startActivity(intent);
								finish();
							}

						} catch (Exception e)
						{
							e.printStackTrace();

								Toast.makeText(getApplicationContext(), "Something went wrong..please try again later..!", Toast.LENGTH_LONG).show();
								Intent intent = new Intent(getApplicationContext(), Home.class);
								intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
								startActivity(intent);
								finish();

						}
					}
				},
				new Response.ErrorListener()
				{
					@Override
					public void onErrorResponse(VolleyError error)
					{
						System.out.println("error "+error.getMessage());
							Toast.makeText(getApplicationContext(), "Something went wrong..please try again later..!", Toast.LENGTH_LONG).show();
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
				HashMap<String, String> params = new HashMap<String, String>();
				params.put("id", id);
				params.put("did", did);

				return params;
			}


		};
		queue.add(sr);
	}
}
