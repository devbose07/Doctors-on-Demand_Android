package com.globussoft.readydoctors.patient.meet_us;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.globussoft.readydoctors.patient.R;
import com.globussoft.readydoctors.patient.Utills.AppData;
import com.globussoft.readydoctors.patient.activity.Home;
import com.globussoft.readydoctors.patient.activity.LandingScreen;

/**
 * Created by GLB-276 on 10/12/2015.
 */

public class MeetUs extends Activity
{
    RelativeLayout meet_providers,how_it_works,press_media,about_dod,our_blog,faq;
    ImageView backImage;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meet_us);
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

        meet_providers=(RelativeLayout)findViewById(R.id.meet_providers);
        meet_providers.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(),MeetOurProviders.class);
                startActivity(intent);
                MeetUs.this.finish();
            }
        });
        how_it_works=(RelativeLayout)findViewById(R.id.how_it_works);
        how_it_works.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(),HowItWorks.class);
                startActivity(intent);
                MeetUs.this.finish();
            }
        });
        press_media=(RelativeLayout)findViewById(R.id.press_media);
        press_media.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(),PressMedia.class);
                startActivity(intent);
                MeetUs.this.finish();
            }
        });
        about_dod=(RelativeLayout)findViewById(R.id.about_dod);
        about_dod.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(),About.class);
                startActivity(intent);
                MeetUs.this.finish();
            }
        });
        our_blog=(RelativeLayout)findViewById(R.id.our_blog);
        our_blog.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(),OurBlog.class);
                startActivity(intent);
                MeetUs.this.finish();
            }
        });
        faq=(RelativeLayout)findViewById(R.id.faq);
        faq.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(),FAQ.class);
                startActivity(intent);
                MeetUs.this.finish();
            }
        });
    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        if(AppData.loginStatus)
        {
            Intent intent = new Intent(getApplicationContext(), Home.class);
            startActivity(intent);
            MeetUs.this.finish();
        }
        else
        {
            Intent intent = new Intent(getApplicationContext(),LandingScreen.class);
            startActivity(intent);
            MeetUs.this.finish();
    }

    }
}
