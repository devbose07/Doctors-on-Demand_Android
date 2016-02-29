package com.globussoft.readydoctors.patient.pregnancy_newborns;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.globussoft.readydoctors.patient.R;

public class LactationScheduleType extends Activity 
{
	RelativeLayout findAPsychologist,findATime;
	TextView findLactation,next;
	ImageView backImage;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.psychology_schedule_type);
		initUI();
		
	}

	private void initUI() 
	{
		next = (TextView) findViewById(R.id.next);
		next.setVisibility(View.INVISIBLE);
		backImage=(ImageView)findViewById(R.id.backImage);
		backImage.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				onBackPressed();
			}
		});
		findLactation=(TextView)findViewById(R.id.consultant);
		findLactation.setText("Find a Lactation Consultant");
		findAPsychologist=(RelativeLayout)findViewById(R.id.findpsychologist);
		findAPsychologist.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v) 
			{
				LactationData.LactationScheduleType=1;
				Intent intent = new Intent(getApplicationContext(),SelectLactationConsultant.class);
				startActivity(intent);
				
			}
		});
		
		findATime=(RelativeLayout)findViewById(R.id.findTime);
		findATime.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v) 
			{
				LactationData.LactationScheduleType=0;
				Intent intent = new Intent(getApplicationContext(),LactationScheme.class);
				startActivity(intent);
				
			}
		});
		
	}

}
