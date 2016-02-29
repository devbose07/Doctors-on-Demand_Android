package com.globussoft.readydoctors.patient.pregnancy_newborns;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.globussoft.readydoctors.patient.R;
import com.globussoft.readydoctors.patient.mediacal.Medical;
import com.globussoft.readydoctors.patient.pediatrics.Pediatrics;
import com.globussoft.readydoctors.patient.psychological.Psychology;

public class Pregnancy extends Activity 
{

	RelativeLayout mSchedule,pediatric,psychology,lactation;
	ImageView backImage;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.pregnancy_newborns);

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
		backImage.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				onBackPressed();
			}
		});
		mSchedule=(RelativeLayout)findViewById(R.id.medical);
		mSchedule.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View v) 
			{

				Intent intent = new Intent(getApplicationContext(),Medical.class);
				startActivity(intent);
			}
		});
		
		pediatric=(RelativeLayout)findViewById(R.id.pediatric);
		pediatric.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View v) 
			{

				Intent intent = new Intent(getApplicationContext(),Pediatrics.class);
				startActivity(intent);
			}
		});
		
		lactation=(RelativeLayout)findViewById(R.id.lactation);
		lactation.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View v) 
			{

				Intent intent = new Intent(getApplicationContext(),Lactation.class);
				startActivity(intent);
			}
		});
		
		psychology=(RelativeLayout)findViewById(R.id.psychology);
		psychology.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View v) 
			{

				Intent intent = new Intent(getApplicationContext(),Psychology.class);
				startActivity(intent);
			}
		});
	}
}
