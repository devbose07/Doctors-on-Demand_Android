package com.globussoft.readydoctors.patient.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.globussoft.readydoctors.patient.R;
import com.globussoft.readydoctors.patient.Utills.AppData;

public class profileEdit extends Activity {

	Button feedback, support, privacy_policy,applycoupon,
			Updatename,signout,UpdatePwd,businessRequirements,getfreecoupon,addreferral;
	ImageView backImage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.profileedit);
		InitUi();
	}

	void InitUi()
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
		signout = (Button) findViewById(R.id.signout);
		signout.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				SharedPreferences preferences=getSharedPreferences("dod_login_status", Context.MODE_PRIVATE);
				SharedPreferences.Editor editor=preferences.edit();
				AppData.loginStatus=false;
				editor.putBoolean("loginStatus", AppData.loginStatus);
				editor.commit();

				Intent intent = new Intent(getApplicationContext(),LandingScreen.class);
				startActivity(intent);
				profileEdit.this.finish();
			}
		});




		feedback = (Button) findViewById(R.id.feedback);
		feedback.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{


			}
		});
		support = (Button) findViewById(R.id.Support);
		support.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),UserSupport.class);
				startActivity(intent);

			}
		});
		privacy_policy = (Button) findViewById(R.id.PrivacyPolicy);
		privacy_policy.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(getApplicationContext(),
						privacy_policy.class);
				startActivity(intent);
			}
		});
		applycoupon=(Button) findViewById(R.id.applycoupon);
		applycoupon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AppData.couponType=0;
				Intent intent = new Intent(getApplicationContext(),ApplyCoupon.class);
				startActivity(intent);
			}
		});
		addreferral=(Button) findViewById(R.id.addreferral);
		addreferral.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(getApplicationContext(),AddReferalSource.class);
				startActivity(intent);
			}
		});
		Updatename = (Button) findViewById(R.id.Updatename);
		Updatename.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(getApplicationContext(),
						EditProfileActivity.class);
				startActivity(intent);
			}
		});
		UpdatePwd= (Button) findViewById(R.id.UpdatePwd);
		UpdatePwd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(getApplicationContext(),UpdatePassword.class);
				startActivity(intent);
			}
		});
		businessRequirements= (Button) findViewById(R.id.businessRequirements);
		businessRequirements.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(getApplicationContext(),BusinessRequirements.class);
				startActivity(intent);
			}
		});
		getfreecoupon= (Button) findViewById(R.id.getfreecoupon);
		getfreecoupon.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(getApplicationContext(),GetFreeVisits.class);
				startActivity(intent);
			}
		});
	}

	@Override
	public void onBackPressed()
	{
		Intent intent = new Intent(getApplicationContext(), Home.class);
		startActivity(intent);
		profileEdit.this.finish();

	}
}
