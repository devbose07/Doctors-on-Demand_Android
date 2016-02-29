package com.globussoft.readydoctors.patient.mediacal;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.globussoft.readydoctors.patient.R;
import com.globussoft.readydoctors.patient.Utills.AppData;
import com.globussoft.readydoctors.patient.activity.Home;
import com.globussoft.readydoctors.patient.meet_us.MeetUs;

public class SelectMedicalPatient extends Activity 
{

	//
	TextView mNext,mNoThanks,mWho;
	RelativeLayout mMe,mMyChild,mSomeOne;
	ImageView next,mSchedule,backImage;
	CheckBox meChk,myChildChk,someOneChk;
	AlertDialog alert;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.select_medical_patient);

		
		
		initUI();
		
	}

	void initUI() 
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
		MedicalData.patient="0";
		
		mWho=(TextView)findViewById(R.id.who);
		String who="Who is </br>the Patient ?";
		mWho.setText(Html.fromHtml(who));
		mNoThanks=(TextView)findViewById(R.id.nothanks);
		String noThanks = "<u> No Thanks, I'm Just Browsing....</u>";
		mNoThanks.setText(Html.fromHtml(noThanks));
		mNoThanks.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v) 
			{

				Intent intent=new Intent(getApplicationContext(), MeetUs.class);
				startActivity(intent);
			}
		});
		meChk=(CheckBox)findViewById(R.id.meChk);
		myChildChk=(CheckBox)findViewById(R.id.myChildChk);
		someOneChk=(CheckBox)findViewById(R.id.someOneChk);
		mNext=(TextView)findViewById(R.id.next);
		mNext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (MedicalData.patient.equalsIgnoreCase("2"))
				{
					ShowDialog1();
				}
				else
				{
					System.out.println("check");
					if (AppData.loginStatus) {
						Intent intent = new Intent(getApplicationContext(), MedicalScheduleType.class);
						startActivity(intent);
					} else {
						Intent intent = new Intent(getApplicationContext(), MedicalReferal.class);
						startActivity(intent);
					}
				}


			}
		});
		
		mMe=(RelativeLayout)findViewById(R.id.me_Rlt);
		meChk.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v) 
			{
			    MedicalData.patient="0";
			    
				meChk.setChecked(true);
				myChildChk.setChecked(false);
				someOneChk.setChecked(false);
				
				
			}
		});
		mMyChild=(RelativeLayout)findViewById(R.id.my_child_Rlt);
		myChildChk.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v) 
			{
				 MedicalData.patient="1";
				meChk.setChecked(false);
				myChildChk.setChecked(true);
				someOneChk.setChecked(false);
				
			}
		});
		mSomeOne=(RelativeLayout)findViewById(R.id.some_one_Rlt);
		someOneChk.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v) 
			{
				
				 MedicalData.patient="2";
				meChk.setChecked(false);
				myChildChk.setChecked(false);
				someOneChk.setChecked(true);
				
			}
		});
		
		
		
	}
	public void ShowDialog1()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(SelectMedicalPatient.this);
		LayoutInflater inflater = this.getLayoutInflater();
		View convertView = (View) inflater.inflate(R.layout.price_dialog, null);
		TextView title=(TextView) convertView.findViewById(R.id.title);
		title.setText("Create New Account");
		TextView txt=(TextView) convertView.findViewById(R.id.desc);
		txt.setText("So sorry for the trouble but you need to create a new account in the name of the person visiting the doctor.");
		TextView yourCost=(TextView) convertView.findViewById(R.id.yourCost);
		yourCost.setVisibility(View.INVISIBLE);
		TextView cost1Txt=(TextView) convertView.findViewById(R.id.cost1);
		cost1Txt.setVisibility(View.INVISIBLE);

		TextView cost2Txt=(TextView) convertView.findViewById(R.id.cost2);
		cost2Txt.setVisibility(View.INVISIBLE);

		TextView apply=(TextView) convertView.findViewById(R.id.apply);
		apply.setVisibility(View.INVISIBLE);
		TextView buttonTxt=(TextView) convertView.findViewById(R.id.letsgoTxt);
		apply.setText("OK!");
		RelativeLayout continuebtn=(RelativeLayout) convertView.findViewById(R.id.letsgo);

		builder.setView(convertView);
		alert = builder.create();
		alert.show();
		continuebtn.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(getApplicationContext(), Home.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				finish();
				alert.dismiss();
			}
		});
	}
}
