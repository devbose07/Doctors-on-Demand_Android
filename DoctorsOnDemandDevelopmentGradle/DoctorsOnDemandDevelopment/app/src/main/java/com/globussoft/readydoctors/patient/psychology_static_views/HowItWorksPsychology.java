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
public class HowItWorksPsychology extends Activity
{
    ImageView backImage;
    RelativeLayout how_we_can_help,why_its_effective;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.how_it_works_psychology);
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
        how_we_can_help=(RelativeLayout)findViewById(R.id.how_we_can_help);
        how_we_can_help.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),HowWeCanHelp.class);
                startActivity(intent);
                HowItWorksPsychology.this.finish();
            }
        });
        why_its_effective=(RelativeLayout)findViewById(R.id.why_its_effective);
        why_its_effective.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),WhyItsEffective.class);
                startActivity(intent);
                HowItWorksPsychology.this.finish();
            }
        });
    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(),Psychology.class);
        startActivity(intent);
        HowItWorksPsychology.this.finish();
    }
}