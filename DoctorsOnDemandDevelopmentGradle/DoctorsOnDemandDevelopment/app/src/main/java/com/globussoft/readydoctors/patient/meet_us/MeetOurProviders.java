package com.globussoft.readydoctors.patient.meet_us;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.globussoft.readydoctors.patient.R;

/**
 * Created by GLB-276 on 10/12/2015.
 */
public class MeetOurProviders extends Activity
{
    ImageView backImage;
    RelativeLayout introduction,meet_our_physicians,meet_our_psychologists,meet_our_lactation_consultants,screening_training,quality_oversight;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meet_our_providers);
        initUI();
    }
    public void initUI()
    {
        backImage=(ImageView)findViewById(R.id.backImage);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        introduction=(RelativeLayout)findViewById(R.id.introduction);
        introduction.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(),ProvidersIntroduction.class);
                startActivity(intent);
                MeetOurProviders.this.finish();
            }
        });
        meet_our_physicians=(RelativeLayout)findViewById(R.id.meet_our_physicians);
        meet_our_physicians.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(),MeetOurPhysicians.class);
                startActivity(intent);
                MeetOurProviders.this.finish();
            }
        });
        meet_our_psychologists=(RelativeLayout)findViewById(R.id.meet_our_psychologists);
        meet_our_psychologists.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(),MeetOurPsychologists.class);
                startActivity(intent);
                MeetOurProviders.this.finish();
            }
        });
        meet_our_lactation_consultants=(RelativeLayout)findViewById(R.id.meet_our_lactation_consultants);
        meet_our_lactation_consultants.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(),MeetOurLactationConsultants.class);
                startActivity(intent);
                MeetOurProviders.this.finish();
            }
        });
        screening_training=(RelativeLayout)findViewById(R.id.screening_training);
        screening_training.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(),ProvidersScreeningTraining.class);
                startActivity(intent);
                MeetOurProviders.this.finish();
            }
        });
        quality_oversight=(RelativeLayout)findViewById(R.id.quality_oversight);
        quality_oversight.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(),ProvidersQualityOversight.class);
                startActivity(intent);
                MeetOurProviders.this.finish();
            }
        });
    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(),MeetUs.class);
        startActivity(intent);
        MeetOurProviders.this.finish();
    }
}
