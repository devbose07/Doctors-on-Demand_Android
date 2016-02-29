package com.globussoft.readydoctors.patient.pediatrics_static_views;

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
public class ShowDoctorPediatrics extends Activity
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
        title.setText("Medical Doctor Bio");

        name = (TextView) findViewById(R.id.name);

        doctorAddress = (TextView) findViewById(R.id.doctorAddress);

        doctorSex = (TextView) findViewById(R.id.doctorSex);

        zipCode = (TextView) findViewById(R.id.zipCode);

        biography = (TextView) findViewById(R.id.biography);
        biography.setText(OurDoctorsPediatrics.doctormodel.getDoctorAbout());
        zipCode.setText(OurDoctorsPediatrics.doctormodel.getZipCode());
        doctorSex.setText(OurDoctorsPediatrics.doctormodel.getDocotrSex());
        doctorAddress.setText(OurDoctorsPediatrics.doctormodel.getDoctorAddress());
        name.setText(OurDoctorsPediatrics.doctormodel.getFirstName() + " " + OurDoctorsPediatrics.doctormodel.getLastName());

    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(),OurDoctorsPediatrics.class);
        startActivity(intent);
        ShowDoctorPediatrics.this.finish();
    }
}

