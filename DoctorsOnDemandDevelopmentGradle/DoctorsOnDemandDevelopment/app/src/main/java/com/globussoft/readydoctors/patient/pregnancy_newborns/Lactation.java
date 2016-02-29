package com.globussoft.readydoctors.patient.pregnancy_newborns;

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
import com.globussoft.readydoctors.patient.lactation_static_views.LactationConsultingThroughVideo;
import com.globussoft.readydoctors.patient.lactation_static_views.MeetTheConsultants;
import com.globussoft.readydoctors.patient.lactation_static_views.PricingLactation;
import com.globussoft.readydoctors.patient.lactation_static_views.WhatDoWeTreatLactation;
import com.globussoft.readydoctors.patient.lactation_static_views.WhatIsLactationConsulting;
import com.globussoft.readydoctors.patient.meet_us.MeetUs;
import com.globussoft.readydoctors.patient.psychological.PsychologyData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Lactation extends Activity 
{
	ImageView schedule;
	ImageView backImage;
	RelativeLayout what_is_lactation,video_visit,wat_we_treat,abtus,meet_dr,pricing;
	AlertDialog alert;
	String cost1=" minutes for ",cost2=" minutes for ";
	public boolean gotCost=false;
	TextView next,schemeATxt,schemeBTxt;
	String callcost1,calltime1,creditremains1,needtopay1,planid1,callcost2,calltime2,creditremains2,needtopay2,planid2;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.lactation);

		initUI();
	}
	private void initUI()
	{
		backImage=(ImageView)findViewById(R.id.backImage);
		backImage.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				onBackPressed();
			}
		});
		schedule=(ImageView) findViewById(R.id.schedule);
		schedule.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View v) 
			{
				gotCost=false;
				if(new ConnectionDetector(getApplicationContext()).isConnectingToInternet())
				{
					GetCalCost(Singleton.PatientID, "" +1);
				}
				else
				{
					Toast.makeText(Lactation.this, "You dont have Internet Connection....!", Toast.LENGTH_SHORT).show();
				}
				ShowDialog1();

			}
		});
		what_is_lactation=(RelativeLayout)findViewById(R.id.what_is_lactation);
		what_is_lactation.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(getApplicationContext(), WhatIsLactationConsulting.class);
				startActivity(intent);
//				Lactation.this.finish();
			}
		});
		video_visit=(RelativeLayout)findViewById(R.id.video_visit);
		video_visit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(getApplicationContext(), LactationConsultingThroughVideo.class);
				startActivity(intent);
//				Lactation.this.finish();
			}
		});
		wat_we_treat=(RelativeLayout)findViewById(R.id.wat_we_treat);
		wat_we_treat.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(getApplicationContext(), WhatDoWeTreatLactation.class);
				startActivity(intent);
//				Lactation.this.finish();
			}
		});
		meet_dr=(RelativeLayout)findViewById(R.id.meet_dr);
		meet_dr.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(getApplicationContext(), MeetTheConsultants.class);
				startActivity(intent);
//				Lactation.this.finish();
			}
		});
		abtus=(RelativeLayout)findViewById(R.id.pricing);
		abtus.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(getApplicationContext(), PricingLactation.class);
				startActivity(intent);
//				Lactation.this.finish();
			}
		});
		abtus=(RelativeLayout)findViewById(R.id.abtus);
		abtus.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(getApplicationContext(), MeetUs.class);
				startActivity(intent);
				Lactation.this.finish();
			}
		});
	}
	public void GetCalCost(final String id,final String did)
	{
		RequestQueue queue = Volley.newRequestQueue(Lactation.this);
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
						Toast.makeText(getApplicationContext(), "Something went wrong..please try again later..!", Toast.LENGTH_LONG).show();
						Intent intent = new Intent(getApplicationContext(), Home.class);
						intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
						finish();
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
	public void ShowDialog1()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(Lactation.this);
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
					AppData.appointmentType=1;
					LactationData.departmentId="1";


					if(AppData.loginStatus)
					{
						Intent intent = new Intent(getApplicationContext(),LactationScheduleType.class);
						startActivity(intent);
					}
					else
					{
						Toast.makeText(getApplicationContext(), "Please Sign in to Schedule an appointment.", Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(getApplicationContext(),SignIn.class);
						startActivity(intent);
						Lactation.this.finish();
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
}
