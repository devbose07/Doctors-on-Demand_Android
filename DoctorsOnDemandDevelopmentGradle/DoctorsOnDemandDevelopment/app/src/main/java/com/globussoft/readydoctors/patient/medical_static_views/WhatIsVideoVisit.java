package com.globussoft.readydoctors.patient.medical_static_views;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.globussoft.readydoctors.patient.R;

/**
 * Created by GLB-276 on 10/9/2015.
 */
public class WhatIsVideoVisit extends Activity
{
    ImageView backImage;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.video_visit);
        initUI();
    }
    public void initUI()
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
    }
}
