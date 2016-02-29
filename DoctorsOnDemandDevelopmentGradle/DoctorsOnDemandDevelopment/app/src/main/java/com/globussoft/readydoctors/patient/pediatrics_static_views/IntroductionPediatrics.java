package com.globussoft.readydoctors.patient.pediatrics_static_views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.globussoft.readydoctors.patient.R;

/**
 * Created by GLB-276 on 10/15/2015.
 */
public class IntroductionPediatrics extends Activity
{
    ImageView backImage;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.introduction_pediatrics);
        backImage=(ImageView)findViewById(R.id.backImage);
        backImage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(),MeetTheDoctorsPediatrics.class);
        startActivity(intent);
        IntroductionPediatrics.this.finish();
    }
}