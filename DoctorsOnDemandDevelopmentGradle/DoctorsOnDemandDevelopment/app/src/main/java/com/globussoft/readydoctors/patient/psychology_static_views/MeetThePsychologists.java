package com.globussoft.readydoctors.patient.psychology_static_views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.globussoft.readydoctors.patient.R;
import com.globussoft.readydoctors.patient.psychological.Psychology;

/**
 * Created by GLB-276 on 10/14/2015.
 */
public class MeetThePsychologists extends Activity
{
    RelativeLayout introduction,meet_our_physicians,screening_training,quality_oversight;
    ImageView backImage;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mee_the_psychologists);
        initUI();
    }
    public void initUI()
    {
        introduction=(RelativeLayout)findViewById(R.id.introduction);
        introduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), IntroductionPsychology.class);
                startActivity(intent);
                MeetThePsychologists.this.finish();
            }
        });

        meet_our_physicians=(RelativeLayout)findViewById(R.id.meet_our_psychologists);
        meet_our_physicians.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), OurPsychologists.class);
                startActivity(intent);
                MeetThePsychologists.this.finish();
            }
        });
        screening_training=(RelativeLayout)findViewById(R.id.screening_training);
        screening_training.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), ScreeningTrainingPsychology.class);
                startActivity(intent);
                MeetThePsychologists.this.finish();
            }
        });
        quality_oversight=(RelativeLayout)findViewById(R.id.quality_oversight);
        quality_oversight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), QualityOversightPsychology.class);
                startActivity(intent);
                MeetThePsychologists.this.finish();
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
        Intent intent = new Intent(getApplicationContext(),Psychology.class);
        startActivity(intent);
        MeetThePsychologists.this.finish();
    }
}

