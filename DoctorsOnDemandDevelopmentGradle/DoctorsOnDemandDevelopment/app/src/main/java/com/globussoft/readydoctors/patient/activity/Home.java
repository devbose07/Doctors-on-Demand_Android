package com.globussoft.readydoctors.patient.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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
import com.globussoft.readydoctors.patient.Utills.MyCustomProgressDialog;
import com.globussoft.readydoctors.patient.Utills.Singleton;
import com.globussoft.readydoctors.patient.mediacal.Medical;
import com.globussoft.readydoctors.patient.meet_us.MeetUs;
import com.globussoft.readydoctors.patient.my_health.MyHealth;
import com.globussoft.readydoctors.patient.pediatrics.Pediatrics;
import com.globussoft.readydoctors.patient.pregnancy_newborns.Pregnancy;
import com.globussoft.readydoctors.patient.psychological.Psychology;
import com.globussoft.readydoctors.patient.see_a_doctor_now.SeeDoctorSelectPatient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Home extends Activity
{
	ImageView medical, physcology, pediatrics, pregnancy, seemedical;
	TextView me,schemeATxt,schemeBTxt;
	RelativeLayout appoinmetn_btn, meet_us, my_health;
	AlertDialog alert;
	String cost1 = " minutes for ", cost2 = " minutes for ";
	String callcost1, calltime1, creditremains1, needtopay1, planid1, callcost2, calltime2, creditremains2, needtopay2, planid2;
	public ProgressDialog progressDialog;
	public boolean gotCost=false;

//	private GoogleApiClient client;


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		System.out.println("Patient id : "+Singleton.PatientID);
		setContentView(R.layout.home);
		InitUI();

	}

	public void loadeProgressDialog()
	{
		new VeryLongAsyncTask(Home.this).execute();
	}

	class VeryLongAsyncTask extends AsyncTask<Void, Void, Void>
	{
		/*private final ProgressDialog progressDialog;*/

		public VeryLongAsyncTask(Context ctx)
		{
			progressDialog = MyCustomProgressDialog.ctor(ctx);
		}

		@Override
		protected void onPreExecute()
		{
			super.onPreExecute();

			progressDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params)
		{
			// sleep for 5 seconds
			try { Thread.sleep(5000); }
			catch (InterruptedException e)
			{ e.printStackTrace(); }

			return null;
		}

		@Override
		protected void onPostExecute(Void result)
		{
			super.onPostExecute(result);

			progressDialog.hide();
		}
	}

	@Override
	public void onBackPressed()
	{
		super.onBackPressed();
	}

	void InitUI() {
		seemedical = (ImageView) findViewById(R.id.seemedical);
		seemedical.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (new ConnectionDetector(getApplicationContext()).isConnectingToInternet())
				{
					gotCost=false;
					GetCalCost(Singleton.PatientID, "" + 5);
				}
				else
				{
					Toast.makeText(Home.this, "You dont have Internet Connection....!", Toast.LENGTH_SHORT).show();
					Home.this.finish();
				}
				ShowDialog1();
			}
		});

		my_health = (RelativeLayout) findViewById(R.id.my_health);

		my_health.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent = new Intent(getApplicationContext(), MyHealth.class);
				startActivity(intent);
			}
		});

		medical = (ImageView) findViewById(R.id.medical);
		appoinmetn_btn = (RelativeLayout) findViewById(R.id.appointment_layout);

		appoinmetn_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent = new Intent(getApplicationContext(), MyApointments.class);
				startActivity(intent);
			}
		});
		meet_us = (RelativeLayout) findViewById(R.id.meet_us);

		meet_us.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent = new Intent(getApplicationContext(), MeetUs.class);
				startActivity(intent);
			}
		});

		medical.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				System.out.println("medical");
				AppData.appointmentType = 4;
				Intent intent = new Intent(getApplicationContext(), Medical.class);
				startActivity(intent);
			}
		});
		physcology = (ImageView) findViewById(R.id.physcology);
		physcology.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				System.out.println("psychology");
				AppData.appointmentType = 3;
				Intent intent = new Intent(getApplicationContext(), Psychology.class);
				startActivity(intent);
			}
		});
		me = (TextView) findViewById(R.id.me);
		me.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(getApplicationContext(), profileEdit.class);
				startActivity(intent);
				Home.this.finish();

			}
		});

		pediatrics = (ImageView) findViewById(R.id.pediatrics);
		pediatrics.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				System.out.println("pediatrics");
				AppData.appointmentType = 2;
				Intent intent = new Intent(getApplicationContext(), Pediatrics.class);
				startActivity(intent);
			}
		});

		pregnancy = (ImageView) findViewById(R.id.pregnancy);
		pregnancy.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				System.out.println("pregnancy");
				AppData.appointmentType = 1;
				Intent intent = new Intent(getApplicationContext(), Pregnancy.class);
				startActivity(intent);
			}
		});


	}


	public void ShowDialog1()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
		LayoutInflater inflater = this.getLayoutInflater();

		View convertView = (View) inflater.inflate(R.layout.price_dialog, null);
		TextView title = (TextView) convertView.findViewById(R.id.title);
		TextView txt = (TextView) convertView.findViewById(R.id.desc);
		schemeATxt = (TextView) convertView.findViewById(R.id.cost1);
//		cost1Txt.setText(cost1);
		schemeBTxt = (TextView) convertView.findViewById(R.id.cost2);
//		cost2Txt.setText(cost2);
		schemeBTxt.setVisibility(View.INVISIBLE);

		TextView apply = (TextView) convertView.findViewById(R.id.apply);
		apply.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(getApplicationContext(), ApplyCoupon.class);
				startActivity(intent);
				alert.dismiss();
			}
		});
		RelativeLayout continuebtn = (RelativeLayout) convertView.findViewById(R.id.letsgo);
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
					if (AppData.loginStatus)
					{
						Intent intent = new Intent(getApplicationContext(), SeeDoctorSelectPatient.class);
						startActivity(intent);
					}
					else
					{
						Toast.makeText(getApplicationContext(), "Please Sign in to Schedule an appointment.", Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(getApplicationContext(), SignIn.class);
						startActivity(intent);
						Home.this.finish();
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

	public void GetCalCost(final String id, final String did)
	{
		loadeProgressDialog();
		RequestQueue queue = Volley.newRequestQueue(Home.this);
		StringRequest sr = new StringRequest(Request.Method.POST,
				ConstantUrls.UrlMain + ConstantUrls.UrlGetUserCalCost,
				new Response.Listener<String>()
				{
					@Override
					public void onResponse(String response)
					{
						progressDialog.cancel();

						System.out.println("getuser cal cost" + response);
						try
						{
							JSONObject json = new JSONObject(response);
							if (json.getString("code").equals("200"))
							{
								JSONArray array = new JSONArray();
								if (json.has("data"))
								{
									array = new JSONArray(json.getString("data").toString());

									String time = array.getJSONObject(0).getString("calltime");
									String cost = array.getJSONObject(0).getString("callcost");
									callcost1 = array.getJSONObject(0).getString("callcost");
									calltime1 = array.getJSONObject(0).getString("calltime");
									creditremains1 = array.getJSONObject(0).getString("creditremains");
									needtopay1 = array.getJSONObject(0).getString("needtopay");
									planid1 = array.getJSONObject(0).getString("pid");
									cost1 = time + " minutes for $" + cost;

									String time_1 = array.getJSONObject(1).getString("calltime");
									String cost_1 = array.getJSONObject(1).getString("callcost");
									callcost2 = array.getJSONObject(1).getString("callcost");
									calltime2 = array.getJSONObject(1).getString("calltime");
									creditremains2 = array.getJSONObject(1).getString("creditremains");
									needtopay2 = array.getJSONObject(1).getString("needtopay");
									planid2 = array.getJSONObject(1).getString("pid");
									cost2 = time_1 + " minutes for $" + cost_1;

									System.out.println(cost1);
									System.out.println(cost2);
									schemeATxt.setText(cost1);
									gotCost=true;

								}
								else
								{
									Toast.makeText(getApplicationContext(), json.getString("message"), Toast.LENGTH_LONG).show();
									Home.this.finish();
								}
								System.out.println("array " + array);
							}
							else
							{
								Toast.makeText(getApplicationContext(), json.getString("message"), Toast.LENGTH_LONG).show();
								Home.this.finish();
							}
						}
						catch (Exception e)
						{
							e.printStackTrace();
							Toast.makeText(getApplicationContext(), "Something went wrong..please try again later..!", Toast.LENGTH_LONG).show();
							Home.this.finish();
						}

					}
				},
				new Response.ErrorListener()
				{
					@Override
					public void onErrorResponse(VolleyError error)
					{
						System.out.println("error " + error.getMessage());
						progressDialog.cancel();
						Toast.makeText(getApplicationContext(), "Something went wrong..please try again later..!", Toast.LENGTH_LONG).show();
						Home.this.finish();
					}
				}) {
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

	@Override
	public void onStart()
	{
		super.onStart();

		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
//		client.connect();
		/*Action viewAction = Action.newAction(
				Action.TYPE_VIEW, // TODO: choose an action type.
				"Home Page", // TODO: Define a title for the content shown.
				// TODO: If you have web page content that matches this app activity's content,
				// make sure this auto-generated web page URL is correct.
				// Otherwise, set the URL to null.
				Uri.parse("http://host/path"),
				// TODO: Make sure this auto-generated app deep link URI is correct.
				Uri.parse("android-app://com.example.doctorsondemand.activity/http/host/path")
		);*/
		//AppIndex.AppIndexApi.start(client, viewAction);
	}

	@Override
	public void onStop()
	{
		super.onStop();

		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		/*Action viewAction = Action.newAction(
				Action.TYPE_VIEW, // TODO: choose an action type.
				"Home Page", // TODO: Define a title for the content shown.
				// TODO: If you have web page content that matches this app activity's content,
				// make sure this auto-generated web page URL is correct.
				// Otherwise, set the URL to null.
				Uri.parse("http://host/path"),
				// TODO: Make sure this auto-generated app deep link URI is correct.
				Uri.parse("android-app://com.example.doctorsondemand.activity/http/host/path")
		);
		AppIndex.AppIndexApi.end(client, viewAction);
		client.disconnect();*/
	}
}