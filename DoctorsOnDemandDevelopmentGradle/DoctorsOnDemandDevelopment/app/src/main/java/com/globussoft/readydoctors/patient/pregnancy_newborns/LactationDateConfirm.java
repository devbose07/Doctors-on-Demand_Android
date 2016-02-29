package com.globussoft.readydoctors.patient.pregnancy_newborns;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.globussoft.readydoctors.patient.R;
import com.globussoft.readydoctors.patient.Utills.Singleton;

public class LactationDateConfirm extends Activity 
{

	Button continue_btn;
	TextView time_text;
	ImageView backImage;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.dateconfirm_activity);
        InitUI();
	}

	private void InitUI() 
	{
		continue_btn=(Button) findViewById(R.id.continue_btn);
		time_text=(TextView) findViewById(R.id.time);
		time_text.setText(Singleton.Startdate+" and "+Singleton.Enddate +" On " +Singleton.month+" "+Singleton.date );
		
		continue_btn.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View v) 
			{
				Intent intent=new Intent(getApplicationContext(), LactationVisitPurpose.class);
				startActivity(intent);
				
			}
		});
	}
}
