package com.globussoft.readydoctors.patient.psychological;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.globussoft.readydoctors.patient.R;
import com.globussoft.readydoctors.patient.Utills.AppData;
import com.globussoft.readydoctors.patient.imagelib.ImageLoader;

public class ShowPsychologist extends Activity
{
	ImageView profilePic,backImage;
	TextView next,name, biography, doctorAddress, doctorSex, zipCode;
	RelativeLayout selected;
	ImageLoader imageLoader;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_psychologist);
		imageLoader=new ImageLoader(getApplicationContext());
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
		backImage.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				onBackPressed();
			}
		});
		next=(TextView)findViewById(R.id.next);
		next.setVisibility(View.INVISIBLE);
		profilePic = (ImageView) findViewById(R.id.profile_pic);
		imageLoader.DisplayImage(AppData.selecteddoctormodel.getDoctorProfilePicUrl(), profilePic);
//        profilePic.setBackgroundResource(R.drawable.ic_launcher);

		name = (TextView) findViewById(R.id.name);

		doctorAddress = (TextView) findViewById(R.id.doctorAddress);

		doctorSex = (TextView) findViewById(R.id.doctorSex);

		zipCode = (TextView) findViewById(R.id.zipCode);

		biography = (TextView) findViewById(R.id.biography);

		biography.setText(AppData.selecteddoctormodel.getDoctorAbout());
		zipCode.setText(AppData.selecteddoctormodel.getDoctorContactNumber());
		doctorSex.setText(AppData.selecteddoctormodel.getDoctorSex());
		doctorAddress.setText(AppData.selecteddoctormodel.getDoctorAddress());
		name.setText(AppData.selecteddoctormodel.getFirstName() + " "+ AppData.selecteddoctormodel.getLastName());

		selected = (RelativeLayout) findViewById(R.id.selected);
		selected.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v) {
				PsychologyData.doctor_id=AppData.selecteddoctormodel.getDoctor_id();
				if (PsychologyData.psychologyScheduleType == 1)
				{
					Intent intent = new Intent(getApplicationContext(),SelectPsychologyScheme.class);
					startActivity(intent);
				}
				else
				{
					Intent intent = new Intent(getApplicationContext(),PsychologyDateConfirm.class);
					startActivity(intent);
				}
			}
		});

	}
}
