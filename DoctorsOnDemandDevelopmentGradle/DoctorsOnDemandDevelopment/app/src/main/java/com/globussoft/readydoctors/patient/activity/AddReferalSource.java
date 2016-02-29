package com.globussoft.readydoctors.patient.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;


import com.globussoft.readydoctors.patient.R;
import com.globussoft.readydoctors.patient.Utills.AppData;

/**
 * Created by GLB-276 on 2/1/2016.
 */
public class AddReferalSource extends Activity
{
    ImageView employer, provider, pharmacy, none;
    ImageView backImage;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.refferral);
        initUI();
    }

    private void initUI()
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
        employer = (ImageView) findViewById(R.id.my_employer);
        employer.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                AppData.couponType=1;
                AppData.orgType=0;
                Intent intent = new Intent(getApplicationContext(),ApplyCoupon.class);
                startActivity(intent);
            }
        });

        provider = (ImageView) findViewById(R.id.provider);
        provider.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AppData.couponType=1;
                AppData.orgType=1;
                Intent intent = new Intent(getApplicationContext(),ApplyCoupon.class);
                startActivity(intent);

            }
        });

        pharmacy = (ImageView) findViewById(R.id.pharmacy);
        pharmacy.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AppData.couponType=1;
                AppData.orgType=2;
                Intent intent = new Intent(getApplicationContext(),ApplyCoupon.class);
                startActivity(intent);

            }
        });

        none = (ImageView) findViewById(R.id.none);
        none.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
            }
        });

    }
}
