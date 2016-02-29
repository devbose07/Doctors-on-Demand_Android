package com.globussoft.readydoctors.patient.pregnancy_newborns;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import com.globussoft.readydoctors.patient.activity.Home;

public class LactationScheme extends Activity 
{
	RelativeLayout schemeA,schemeB;
	TextView schemeATxt,schemeBTxt,title;
	String cost1=" minutes for ",cost2=" minutes for ";
	TextView header;
	ImageView backImage;
	public boolean gotCost=false;
	String callcost1,calltime1,creditremains1,needtopay1,planid1,callcost2,calltime2,creditremains2,needtopay2,planid2;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.select_psychology_scheme);
		initUI();
	}

	private void initUI() 
	{
		backImage=(ImageView)findViewById(R.id.backImage);
		backImage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});



		if(new ConnectionDetector(getApplicationContext()).isConnectingToInternet())
		{
			GetCalCost(Singleton.PatientID, "" + AppData.appointmentType);
		}
		else
		{
			Toast.makeText(LactationScheme.this, "You dont have Internet Connection....!", Toast.LENGTH_SHORT).show();
		}
		title=(TextView)findViewById(R.id.edittext);
		title.setText("APPOINTMENT DURATION");
		header=(TextView)findViewById(R.id.textView1);
		header.setText("How long whould you like to meet with your lactation consultant ?");
		schemeATxt=(TextView)findViewById(R.id.schemeATxt);
//		schemeATxt.setText(cost1);
		
		schemeBTxt=(TextView)findViewById(R.id.schemeBTxt);
//		schemeBTxt.setText(cost2);
		schemeA=(RelativeLayout)findViewById(R.id.schemeA);
		schemeA.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				if(gotCost)
				{
					LactationData.callcost=callcost1;
					LactationData.calltime=calltime1;
					AppData.scheduleTiming=Integer.parseInt(LactationData.calltime);
					LactationData.creditremains=creditremains1;
					LactationData.needtopay=needtopay1;
					LactationData.planid=planid1;
					Intent intent = new Intent(getApplicationContext(),LactationCalender.class);
					startActivity(intent);
				}
				else
				{
					Toast.makeText(getApplicationContext(),"Please wait...",Toast.LENGTH_SHORT).show();
				}


			}
		});
		
		schemeB=(RelativeLayout)findViewById(R.id.schemeB);
		schemeB.setVisibility(View.INVISIBLE);
		schemeB.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				LactationData.callcost=callcost2;
				LactationData.calltime=calltime2;
				AppData.scheduleTiming=Integer.parseInt(LactationData.calltime);
				LactationData.creditremains=creditremains2;
				LactationData.needtopay=needtopay2;
				LactationData.planid=planid2;
				Intent intent = new Intent(getApplicationContext(),LactationCalender.class);
				startActivity(intent);
			}
		});
		
	}
	public void GetCalCost(final String id,final String did) 
	{
		RequestQueue queue = Volley.newRequestQueue(LactationScheme.this);
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
									schemeATxt.setText(cost1);
									schemeBTxt.setText(cost2);
									gotCost=true;
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