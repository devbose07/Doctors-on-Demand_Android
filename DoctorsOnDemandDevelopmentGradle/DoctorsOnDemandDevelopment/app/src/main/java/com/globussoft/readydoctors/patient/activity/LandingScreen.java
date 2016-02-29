package com.globussoft.readydoctors.patient.activity;


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


import com.globussoft.readydoctors.patient.R;
import com.globussoft.readydoctors.patient.Utills.AppData;
import com.globussoft.readydoctors.patient.see_a_doctor_now.SeeDoctorSelectPatient;

public class LandingScreen extends Activity
{

	TextView sign_in;
	RelativeLayout sign_up,meet_us;
	ImageView medical,physcology,pediatrics,pregnancy,seemedical;
	AlertDialog alert;
	String cost1=" minutes for ",cost2=" minutes for ";
	String callcost1,calltime1,creditremains1,needtopay1,planid1,callcost2,calltime2,creditremains2,needtopay2,planid2;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.landingpage);
		InitUI();
	}

	void InitUI()
	{
		seemedical=(ImageView)findViewById(R.id.seemedical);
		seemedical.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
					Toast.makeText(getApplicationContext(), "Please Sign in to continue.", Toast.LENGTH_SHORT).show();
					/*Intent intent = new Intent(getApplicationContext(),SignIn.class);
					startActivity(intent);
					LandingScreen.this.finish();*/
			}
		});
		sign_in = (TextView) findViewById(R.id.signin_btn);
		sign_up = (RelativeLayout) findViewById(R.id.signup_layout);
		medical=(ImageView) findViewById(R.id.medical);
		AppData.loginStatus=false;
		medical.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				/*Intent intent = new Intent(getApplicationContext(),Medical.class);
				startActivity(intent);*/
				Toast.makeText(getApplicationContext(), "Please Sign in to continue.", Toast.LENGTH_SHORT).show();
			}
		});
		physcology=(ImageView) findViewById(R.id.physcology);
		physcology.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				System.out.println("psychology");
				/*Intent intent = new Intent(getApplicationContext(),Psychology.class);
				startActivity(intent);*/
				Toast.makeText(getApplicationContext(), "Please Sign in to continue.", Toast.LENGTH_SHORT).show();
			}
		});
		
		pediatrics=(ImageView) findViewById(R.id.pediatrics);
		pediatrics.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				System.out.println("pediatrics");
				/*Intent intent = new Intent(getApplicationContext(),Pediatrics.class);
				startActivity(intent);*/
				Toast.makeText(getApplicationContext(), "Please Sign in to continue.", Toast.LENGTH_SHORT).show();
			}
		});
		
		pregnancy=(ImageView) findViewById(R.id.pregnancy);
		pregnancy.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				System.out.println("pregnancy");
				/*Intent intent = new Intent(getApplicationContext(),Pregnancy.class);
				startActivity(intent);*/
				Toast.makeText(getApplicationContext(), "Please Sign in to continue.", Toast.LENGTH_SHORT).show();
			}
		});
		sign_up.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(getApplicationContext(),SignUp.class);
				startActivity(intent);

			}
		});

		sign_in.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) {

				Intent intent = new Intent(getApplicationContext(),	SignIn.class);
				startActivity(intent);
				LandingScreen.this.finish();
			}
		});
		meet_us = (RelativeLayout) findViewById(R.id.meet_us);
		meet_us.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				/*Intent intent = new Intent(getApplicationContext(),MeetUs.class);
				startActivity(intent);*/
				Toast.makeText(getApplicationContext(), "Please Sign in to continue.", Toast.LENGTH_SHORT).show();

			}
		});
	}
	public void ShowDialog1()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(LandingScreen.this);
		LayoutInflater inflater = this.getLayoutInflater();
	      /*View view = inflater.inflate(R.layout.categoryfilter_title, null);
	      builder.setCustomTitle(view);*/

//	      builder.setTitle("Choose Category");
		View convertView = (View) inflater.inflate(R.layout.price_dialog, null);
		TextView title=(TextView) convertView.findViewById(R.id.title);
		TextView txt=(TextView) convertView.findViewById(R.id.desc);
		TextView cost1Txt=(TextView) convertView.findViewById(R.id.cost1);
		cost1Txt.setText(cost1);
		TextView cost2Txt=(TextView) convertView.findViewById(R.id.cost2);
		cost2Txt.setText(cost2);
		TextView apply=(TextView) convertView.findViewById(R.id.apply);
		apply.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				if(AppData.loginStatus)
				{
					Intent intent = new Intent(getApplicationContext(), ApplyCoupon.class);
					startActivity(intent);
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Please Sign in to Schedule an appointment.", Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(getApplicationContext(),SignIn.class);
					startActivity(intent);
					LandingScreen.this.finish();
				}

				alert.dismiss();
			}
		});
		RelativeLayout continuebtn=(RelativeLayout) convertView.findViewById(R.id.letsgo);
		builder.setView(convertView);
		alert = builder.create();
		alert.show();
		continuebtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v)
			{

				if(AppData.loginStatus)
				{
					Intent intent = new Intent(getApplicationContext(), SeeDoctorSelectPatient.class);
					startActivity(intent);
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Please Sign in to Schedule an appointment.", Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(getApplicationContext(),SignIn.class);
					startActivity(intent);
					LandingScreen.this.finish();
				}


				alert.dismiss();
			}
		});
	}
}
