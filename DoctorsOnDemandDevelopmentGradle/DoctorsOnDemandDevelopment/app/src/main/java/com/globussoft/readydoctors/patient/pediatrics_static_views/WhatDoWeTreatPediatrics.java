package com.globussoft.readydoctors.patient.pediatrics_static_views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.globussoft.readydoctors.patient.R;
import com.globussoft.readydoctors.patient.medical_static_views.WhatWeTreatAdapter;
import com.globussoft.readydoctors.patient.pediatrics.Pediatrics;

/**
 * Created by GLB-276 on 10/15/2015.
 */
public class WhatDoWeTreatPediatrics extends Activity
{
    String[] mArray = {"Pediatric Triage","Cough / Cold / Flu / allergies  ","Rashes","What we don't treat"};
    ListView listView;
    ImageView backImage;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.what_do_we_treat);
        listView=(ListView)findViewById(R.id.list);
        listView.setAdapter(new WhatWeTreatAdapter(mArray, getApplicationContext()));
        listView.setOnItemClickListener(new showPages());
        backImage=(ImageView)findViewById(R.id.backImage);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private class showPages implements ListView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,long id)
        {
            Intent intent;
            switch (position)
            {
                case 0:
                    intent = new Intent(getApplicationContext(),PediatricTriage.class);
                    startActivity(intent);
                    WhatDoWeTreatPediatrics.this.finish();
                    break;
                case 1:
                    intent = new Intent(getApplicationContext(),CoughCold.class);
                    startActivity(intent);
                    WhatDoWeTreatPediatrics.this.finish();
                    break;
                case 2:
                    intent = new Intent(getApplicationContext(),Rashes.class);
                    startActivity(intent);
                    WhatDoWeTreatPediatrics.this.finish();

                    break;
                case 3:
                    intent = new Intent(getApplicationContext(),WeDontTreatPediatrics.class);
                    startActivity(intent);
                    WhatDoWeTreatPediatrics.this.finish();
                    break;

                default:
                    break;
            }
        }
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(),Pediatrics.class);
        startActivity(intent);
        WhatDoWeTreatPediatrics.this.finish();
    }
}
