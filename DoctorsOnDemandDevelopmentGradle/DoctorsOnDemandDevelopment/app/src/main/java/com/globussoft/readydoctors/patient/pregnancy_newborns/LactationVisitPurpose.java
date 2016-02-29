package com.globussoft.readydoctors.patient.pregnancy_newborns;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.globussoft.readydoctors.patient.R;

public class LactationVisitPurpose extends Activity 
{

	TextView next_btn;
	EditText editbox;
	ImageView backImage;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.visit_purpose);
		InitUI();
	}
	private void InitUI(){
		
		next_btn=(TextView) findViewById(R.id.next);
		editbox=(EditText) findViewById(R.id.editText1);
		backImage=(ImageView)findViewById(R.id.backImage);
		backImage.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				onBackPressed();
			}
		});
		
		
		next_btn.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View v) 
			{
				
				if (editbox.getText().toString().length()<1) 
				{
					editbox.setError("Please specify the reason");
				}
				else 
				{
					LactationData.purpose=editbox.getText().toString();
					Intent intent=new Intent(getApplicationContext(),ProvideBabyAge.class);
					startActivity(intent);
				}
				
				
			}
		});
	}
}