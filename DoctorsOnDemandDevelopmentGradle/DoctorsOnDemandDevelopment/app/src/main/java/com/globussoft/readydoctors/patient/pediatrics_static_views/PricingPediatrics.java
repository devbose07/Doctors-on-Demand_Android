package com.globussoft.readydoctors.patient.pediatrics_static_views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.globussoft.readydoctors.patient.R;
import com.globussoft.readydoctors.patient.pediatrics.Pediatrics;

/**
 * Created by GLB-276 on 10/15/2015.
 */
public class PricingPediatrics extends Activity
{
    ImageView backImage;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pricing_pediatrics);
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
    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(),Pediatrics.class);
        startActivity(intent);
        PricingPediatrics.this.finish();
    }
}
