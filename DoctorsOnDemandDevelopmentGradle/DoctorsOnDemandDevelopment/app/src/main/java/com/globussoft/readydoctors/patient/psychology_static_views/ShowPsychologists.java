package com.globussoft.readydoctors.patient.psychology_static_views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.globussoft.readydoctors.patient.R;

/**
 * Created by GLB-276 on 10/15/2015.
 */
public class ShowPsychologists extends Activity
{
    ImageView profilePic;
    TextView name, biography, doctorAddress, doctorSex, zipCode,title;
    ImageView backImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_doctor);

        InitUI();
    }

    private void InitUI()
    {
        profilePic = (ImageView) findViewById(R.id.profile_pic);
        profilePic.setBackgroundResource(R.drawable.ic_launcher);

        backImage=(ImageView)findViewById(R.id.backImage);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        title=(TextView)findViewById(R.id.edittext);
        title.setText("Psychologist Bio");

        name = (TextView) findViewById(R.id.name);

        doctorAddress = (TextView) findViewById(R.id.doctorAddress);

        doctorSex = (TextView) findViewById(R.id.doctorSex);

        zipCode = (TextView) findViewById(R.id.zipCode);

        biography = (TextView) findViewById(R.id.biography);
        biography.setText(OurPsychologists.doctormodel.getDoctorAbout());
        zipCode.setText(OurPsychologists.doctormodel.getZipCode());
        doctorSex.setText(OurPsychologists.doctormodel.getDocotrSex());
        doctorAddress.setText(OurPsychologists.doctormodel.getDoctorAddress());
        name.setText(OurPsychologists.doctormodel.getFirstName() + " " + OurPsychologists.doctormodel.getLastName());

    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(),OurPsychologists.class);
        startActivity(intent);
        ShowPsychologists.this.finish();
    }
}

