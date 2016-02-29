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
public class HowItWorks extends Activity
{
    ImageView backImage;
    RelativeLayout what_is_video_visit,prepare_for_video_visit,new_user_guide,pricing;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.how_it_works);
        initUI();
    }

    public void initUI()
    {
        backImage = (ImageView) findViewById(R.id.backImage);
        backImage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
            }
        });
        what_is_video_visit = (RelativeLayout) findViewById(R.id.what_is_video_visit);
        what_is_video_visit.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), ProvidersWhatIsVideoVisit.class);
                startActivity(intent);
                HowItWorks.this.finish();
            }
        });
        prepare_for_video_visit = (RelativeLayout) findViewById(R.id.prepare_for_video_visit);
        prepare_for_video_visit.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), PrepareForVideoVisit.class);
                startActivity(intent);
                HowItWorks.this.finish();
            }
        });
        new_user_guide = (RelativeLayout) findViewById(R.id.new_user_guide);
        new_user_guide.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), NewUserGuide.class);
                startActivity(intent);
                HowItWorks.this.finish();
            }
        });
        pricing = (RelativeLayout) findViewById(R.id.pricing);
        pricing.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), PricingAllDept.class);
                startActivity(intent);
                HowItWorks.this.finish();
            }
        });
    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(),MeetUs.class);
        startActivity(intent);
        HowItWorks.this.finish();
    }
}
