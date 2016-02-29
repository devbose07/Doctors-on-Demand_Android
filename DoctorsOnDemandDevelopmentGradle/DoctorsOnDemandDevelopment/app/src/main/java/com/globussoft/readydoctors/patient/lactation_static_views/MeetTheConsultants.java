package com.globussoft.readydoctors.patient.lactation_static_views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.globussoft.readydoctors.patient.R;

/**
 * Created by GLB-276 on 10/17/2015.
 */
public class MeetTheConsultants extends Activity
{
    RelativeLayout introduction,meet_our_physicians,screening_training,quality_oversight;
    ImageView backImage;
    TextView edittext,videoTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mee_the_psychologists);
        initUI();
    }
    public void initUI()
    {
        edittext=(TextView)findViewById(R.id.edittext);
        edittext.setText("Meet the Lactation Consultants");
        introduction=(RelativeLayout)findViewById(R.id.introduction);
        introduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), IntroductionLactation.class);
                startActivity(intent);
//                MeetTheConsultants.this.finish();
            }
        });
        videoTxt=(TextView)findViewById(R.id.videoTxt);
        videoTxt.setText("Meet Our Lactation Consultants");
        meet_our_physicians=(RelativeLayout)findViewById(R.id.meet_our_psychologists);
        meet_our_physicians.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), OurConsultants.class);
            startActivity(intent);
                  /*  MeetTheConsultants.this.finish();*/
            }
        });
        screening_training=(RelativeLayout)findViewById(R.id.screening_training);
        screening_training.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), ScreeningTrainingLactation.class);
                startActivity(intent);
//                MeetTheConsultants.this.finish();
            }
        });
        quality_oversight=(RelativeLayout)findViewById(R.id.quality_oversight);
        quality_oversight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), QualityOversightLactation.class);
                startActivity(intent);
//                MeetTheConsultants.this.finish();
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
       /* Intent intent = new Intent(getApplicationContext(),Pediatrics.class);
        startActivity(intent);
        MeetTheConsultants.this.finish();*/
    }
}

