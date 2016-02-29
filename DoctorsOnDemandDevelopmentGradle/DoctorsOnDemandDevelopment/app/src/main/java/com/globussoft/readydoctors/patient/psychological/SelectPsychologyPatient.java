package com.globussoft.readydoctors.patient.psychological;

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
import com.globussoft.readydoctors.patient.Utills.Singleton;
import com.globussoft.readydoctors.patient.activity.Home;
import com.globussoft.readydoctors.patient.meet_us.MeetUs;

public class SelectPsychologyPatient extends Activity 
{

	//
	TextView mNext,mNoThanks,mWho,my_child_under8_Txt,my_child_under17_Txt;
	RelativeLayout mMe,mMyChildUnder8,mMyChildUnder17,mSomeOne;
	ImageView next,mSchedule,backImage;
	CheckBox meChk,myChildChkUnder8,myChildChkUnder17,someOneChk;
	AlertDialog alert;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.select_psychology_patient);
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
		backImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
		my_child_under8_Txt=(TextView)findViewById(R.id.my_child_under8_Txt);
		String temp="My Child : <font color=#B0B0B0 >under age 8 </font>";
		my_child_under8_Txt.setText(Html.fromHtml(temp));
		
		my_child_under17_Txt=(TextView)findViewById(R.id.my_child_under17_Txt);
		temp="My Child : <font color=#B0B0B0 >8-17 years old </font>";
		my_child_under17_Txt.setText(Html.fromHtml(temp));

		PsychologyData.patient="0";
		
		mWho=(TextView)findViewById(R.id.who);
		String who="Who is </br>the Patient ?";
		mWho.setText(Html.fromHtml(who));
		mNoThanks=(TextView)findViewById(R.id.nothanks);
		String noThanks = "<u> No Thanks, I'm Just Browsing....</u>";
		mNoThanks.setText(Html.fromHtml(noThanks));
		mNoThanks.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(getApplicationContext(), MeetUs.class);
				startActivity(intent);
			}
		});
		meChk=(CheckBox)findViewById(R.id.meChk);
		myChildChkUnder8=(CheckBox)findViewById(R.id.myChildChk_under8);
		myChildChkUnder17=(CheckBox)findViewById(R.id.myChildChk_under17);
		someOneChk=(CheckBox)findViewById(R.id.someOneChk);
		mNext=(TextView)findViewById(R.id.next);
		mNext.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v) 
			{
				if (PsychologyData.patient.equalsIgnoreCase("2"))
				{
					ShowDialog1();
				}
				else
				{
					PsychologyData.patient_id= Singleton.PatientID;
					Intent intent=new Intent(getApplicationContext(), PsychologyScheduleType.class);
					startActivity(intent);
				}

			}
		});
		
		mMe=(RelativeLayout)findViewById(R.id.me_Rlt);
		mMe.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v) 
			{
				PsychologyData.patient="0";
			    
				meChk.setChecked(true);
				myChildChkUnder8.setChecked(false);
				myChildChkUnder17.setChecked(false);
				someOneChk.setChecked(false);
				
				
			}
		});
		
		meChk.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v) 
			{
				PsychologyData.patient="0";
			    
				meChk.setChecked(true);
				myChildChkUnder8.setChecked(false);
				myChildChkUnder17.setChecked(false);
				someOneChk.setChecked(false);
				
				
			}
		});
		mMyChildUnder8=(RelativeLayout)findViewById(R.id.my_child_under8_Rlt);
		mMyChildUnder8.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v) 
			{
				PsychologyData.patient="1";
				meChk.setChecked(false);
				myChildChkUnder8.setChecked(true);
				myChildChkUnder17.setChecked(false);
				someOneChk.setChecked(false);
				
			}
		});
		myChildChkUnder8.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				PsychologyData.patient = "1";
				meChk.setChecked(false);
				myChildChkUnder8.setChecked(true);
				myChildChkUnder17.setChecked(false);
				someOneChk.setChecked(false);

			}
		});
		mMyChildUnder17=(RelativeLayout)findViewById(R.id.my_child_under17_Rlt);
//		mMyChildUnder17.setVisibility(View.INVISIBLE);
		mMyChildUnder17.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				PsychologyData.patient = "1";
				meChk.setChecked(false);
				myChildChkUnder8.setChecked(false);
				myChildChkUnder17.setChecked(true);
				someOneChk.setChecked(false);

			}
		});
//		myChildChkUnder17.setVisibility(View.INVISIBLE);
		myChildChkUnder17.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				PsychologyData.patient = "1";
				meChk.setChecked(false);
				myChildChkUnder8.setChecked(false);
				myChildChkUnder17.setChecked(true);
				someOneChk.setChecked(false);

			}
		});
		mSomeOne=(RelativeLayout)findViewById(R.id.some_one_Rlt);
//		mSomeOne.setVisibility(View.INVISIBLE);
		mSomeOne.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				PsychologyData.patient = "2";
				meChk.setChecked(false);
				myChildChkUnder8.setChecked(false);
				myChildChkUnder17.setChecked(false);
				someOneChk.setChecked(true);

			}
		});
//		someOneChk.setVisibility(View.INVISIBLE);
		someOneChk.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v) 
			{
				
				PsychologyData.patient="2";
				meChk.setChecked(false);
				myChildChkUnder8.setChecked(false);
				myChildChkUnder17.setChecked(false);
				someOneChk.setChecked(true);
				
			}
		});
		
		
	}
	public void ShowDialog1()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(SelectPsychologyPatient.this);
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
