package com.globussoft.readydoctors.patient.pregnancy_newborns;

import com.globussoft.readydoctors.patient.R;
import com.globussoft.readydoctors.patient.activity.SignUp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class LactationRefferral extends Activity 
{
	ImageView employer,provider,pharmacy,none;
	ImageView backImage;
	 @Override
	protected void onCreate(Bundle savedInstanceState) 
	 {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.refferral);
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
		employer=(ImageView)findViewById(R.id.my_employer);
		employer.setOnClickListener(new OnClickListener()
		{
		
			@Override
			public void onClick(View v) 
			{
				Intent intent=new Intent(getApplicationContext(), SignUp.class);
				startActivity(intent);
			}
		});
		
		provider=(ImageView)findViewById(R.id.provider);
		provider.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				Intent intent=new Intent(getApplicationContext(), SignUp.class);
				startActivity(intent);

			}
		});
		
		pharmacy=(ImageView)findViewById(R.id.pharmacy);
		pharmacy.setOnClickListener(new OnClickListener()
		{
		
			@Override
			public void onClick(View v) 
			{
				Intent intent=new Intent(getApplicationContext(), SignUp.class);
				startActivity(intent);
				
			}
		});
		
		none=(ImageView)findViewById(R.id.none);
		none.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				Intent intent=new Intent(getApplicationContext(), LactationScheduleType.class);
				startActivity(intent);
			}
		});
		
	}
}
