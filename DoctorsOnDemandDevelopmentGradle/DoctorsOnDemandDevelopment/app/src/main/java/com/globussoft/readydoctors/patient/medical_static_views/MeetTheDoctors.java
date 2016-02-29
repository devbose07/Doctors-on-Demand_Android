package com.globussoft.readydoctors.patient.medical_static_views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.globussoft.readydoctors.patient.R;
import com.globussoft.readydoctors.patient.mediacal.Medical;

/**
 * Created by GLB-276 on 10/10/2015.
 */
public class MeetTheDoctors extends Activity
{
    RelativeLayout introduction,meet_our_physicians,screening_training,quality_oversight;
    ImageView backImage;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meet_the_doctors);
        initUI();
    }
    public void initUI()
    {
        introduction=(RelativeLayout)findViewById(R.id.introduction);
        introduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), Introduction.class);
                startActivity(intent);
                MeetTheDoctors.this.finish();
            }
        });

        meet_our_physicians=(RelativeLayout)findViewById(R.id.meet_our_physicians);
        meet_our_physicians.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), OurDoctors.class);
                startActivity(intent);
                MeetTheDoctors.this.finish();
            }
        });
        screening_training=(RelativeLayout)findViewById(R.id.screening_training);
        screening_training.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), ScreeningTraining.class);
                startActivity(intent);
                MeetTheDoctors.this.finish();
            }
        });
        quality_oversight=(RelativeLayout)findViewById(R.id.quality_oversight);
        quality_oversight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), QualityOversight.class);
                startActivity(intent);
                MeetTheDoctors.this.finish();
            }
        });
        backImage=(ImageView)findViewById(R.id.backImage);
        backImage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
            }
        });
    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(),Medical.class);
        startActivity(intent);
        MeetTheDoctors.this.finish();
    }
}
