package com.globussoft.readydoctors.patient.meet_us;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.globussoft.readydoctors.patient.R;

/**
 * Created by GLB-276 on 10/13/2015.
 */
public class PressMedia extends Activity
{
    ImageView backImage;
    RelativeLayout press,mediaInquiries;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.press_media);
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
        press=(RelativeLayout)findViewById(R.id.press);
        press.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(),Press.class);
                startActivity(intent);
                PressMedia.this.finish();
            }
        });
        mediaInquiries=(RelativeLayout)findViewById(R.id.media_inquiries);
        mediaInquiries.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(),MediaInquiries.class);
                startActivity(intent);
                PressMedia.this.finish();
            }
        });
    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(),MeetUs.class);
        startActivity(intent);
        PressMedia.this.finish();
    }
}
