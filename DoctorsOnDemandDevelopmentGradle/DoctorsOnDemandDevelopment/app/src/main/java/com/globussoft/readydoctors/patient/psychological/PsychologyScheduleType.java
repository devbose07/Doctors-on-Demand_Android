package com.globussoft.readydoctors.patient.psychological;

import com.globussoft.readydoctors.patient.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PsychologyScheduleType extends Activity 
{
	RelativeLayout findAPsychologist,findATime;
	TextView next;
	ImageView backImage;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.psychology_schedule_type);
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
		next=(TextView)findViewById(R.id.next);
		next.setVisibility(View.INVISIBLE);
		findAPsychologist=(RelativeLayout)findViewById(R.id.findpsychologist);
		findAPsychologist.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v) 
			{
				PsychologyData.psychologyScheduleType=1;
				Intent intent = new Intent(getApplicationContext(),SelectPsychologist.class);
				startActivity(intent);
				
			}
		});
		
		findATime=(RelativeLayout)findViewById(R.id.findTime);
		findATime.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v) 
			{
				PsychologyData.psychologyScheduleType=0;
				Intent intent = new Intent(getApplicationContext(),SelectPsychologyScheme.class);
				startActivity(intent);
				
			}
		});
		
	}

}
