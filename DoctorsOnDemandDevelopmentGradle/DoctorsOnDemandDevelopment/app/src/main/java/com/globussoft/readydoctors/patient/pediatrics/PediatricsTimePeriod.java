package com.globussoft.readydoctors.patient.pediatrics;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.TextView;

import com.globussoft.readydoctors.patient.R;

public class PediatricsTimePeriod extends Activity 
{
 
	TextView nextbutton;
	RadioButton today,yesterday,pastWeek,pastMonth,pastYear,moreThanAYear;
	String timePeriod="";
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.time_period);
		InitUI();
	}

	void InitUI(){
		
		nextbutton=(TextView) findViewById(R.id.next);
		today=(RadioButton) findViewById(R.id.todayR);
		today.setChecked(true);
		timePeriod="today";
		today.setOnClickListener(new OnClickListener()
		{	
			@Override
			public void onClick(View v) 
			{
			
				clicked(R.id.todayR);
			}
		});
		yesterday=(RadioButton) findViewById(R.id.yesterdayR);
		yesterday.setOnClickListener(new OnClickListener()
		{	
			@Override
			public void onClick(View v) 
			{
			
				clicked(R.id.yesterdayR);
			}
		});
		pastWeek=(RadioButton) findViewById(R.id.pastweekR);
		pastWeek.setOnClickListener(new OnClickListener()
		{	
			@Override
			public void onClick(View v) 
			{
				clicked(R.id.pastweekR);
				
			}
		});
		pastMonth=(RadioButton) findViewById(R.id.pastmonthR);
		pastMonth.setOnClickListener(new OnClickListener()
		{	
			@Override
			public void onClick(View v) 
			{
			
				clicked(R.id.pastmonthR);
			}
		});
		pastYear=(RadioButton) findViewById(R.id.pastyearR);
		pastYear.setOnClickListener(new OnClickListener()
		{	
			@Override
			public void onClick(View v) 
			{
				clicked(R.id.pastyearR);
				
			}
		});
		moreThanAYear=(RadioButton) findViewById(R.id.morethanyearR);
		moreThanAYear.setOnClickListener(new OnClickListener()
		{	
			@Override
			public void onClick(View v) 
			{
				clicked(R.id.morethanyearR);
				
			}
		});
		nextbutton.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View v) 
			{
				System.out.println("Time period "+timePeriod);
				PediatricData.time_period=timePeriod;
				Intent intent=new Intent(getApplicationContext(), PediatricsMedications.class);
				startActivity(intent);
				
			}
		});
	}
	public void clicked(int id)
	{
		switch (id) 
		{
			case R.id.todayR:
				today.setChecked(true);
				yesterday.setChecked(false);
				pastWeek.setChecked(false);
				pastMonth.setChecked(false);
				pastYear.setChecked(false);
				moreThanAYear.setChecked(false);
				timePeriod="today";
				break;
			case R.id.yesterdayR:
				today.setChecked(false);
				yesterday.setChecked(true);
				pastWeek.setChecked(false);
				pastMonth.setChecked(false);
				pastYear.setChecked(false);
				moreThanAYear.setChecked(false);	
				timePeriod="yesterday";
						break;
			case R.id.pastweekR:
				today.setChecked(false);
				yesterday.setChecked(false);
				pastWeek.setChecked(true);
				pastMonth.setChecked(false);
				pastYear.setChecked(false);
				moreThanAYear.setChecked(false);	
				timePeriod="pastweek";
				break;
			case R.id.pastmonthR:
				today.setChecked(false);
				yesterday.setChecked(false);
				pastWeek.setChecked(false);
				pastMonth.setChecked(true);
				pastYear.setChecked(false);
				moreThanAYear.setChecked(false);	
				timePeriod="pastmonth";
				break;
			case R.id.pastyearR:
				today.setChecked(false);
				yesterday.setChecked(false);
				pastWeek.setChecked(false);
				pastMonth.setChecked(false);
				pastYear.setChecked(true);
				moreThanAYear.setChecked(false);	
				timePeriod="pastyear";
				break;
			case R.id.morethanyearR:
				today.setChecked(false);
				yesterday.setChecked(false);
				pastWeek.setChecked(false);
				pastMonth.setChecked(false);
				pastYear.setChecked(false);
				moreThanAYear.setChecked(true);	
				timePeriod="morethanyear";
				break;
	
			default:
				break;
		}
		System.out.println("Time period "+timePeriod);
	}
}
