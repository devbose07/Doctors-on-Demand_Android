package com.globussoft.readydoctors.patient.pediatrics;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.globussoft.readydoctors.patient.R;

/**
 * Created by GLB-276 on 12/11/2015.
 */
public class PediatricsScheduleType extends Activity
{
    RelativeLayout findAPsychologist,findATime;
    TextView next,doctor;
    ImageView backImage;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.psychology_schedule_type);
        initUI();

    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
    }
    void initUI()
    {
        doctor=(TextView)findViewById(R.id.consultant);
        doctor.setText("Find Physician");
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
        findAPsychologist=(RelativeLayout)findViewById(R.id.findpsychologist);
        findAPsychologist.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                PediatricData.pediatricsScheduleType=1;
                Intent intent = new Intent(getApplicationContext(),SelectPhysician.class);
                startActivity(intent);

            }
        });

        findATime=(RelativeLayout)findViewById(R.id.findTime);
        findATime.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                PediatricData.pediatricsScheduleType=0;
                Intent intent = new Intent(getApplicationContext(),PediatricsScheme.class);
                startActivity(intent);

            }
        });

    }

}
