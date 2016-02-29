package com.globussoft.readydoctors.patient.lactation_static_views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.globussoft.readydoctors.patient.R;
import com.globussoft.readydoctors.patient.medical_static_views.WhatWeTreatAdapter;

/**
 * Created by GLB-276 on 10/16/2015.
 */
public class WhatDoWeTreatLactation extends Activity
{
    String[] mArray = {"Top Mom Issues","Top Baby Issues"};
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
                    intent = new Intent(getApplicationContext(),IssuesMom.class);
                    startActivity(intent);
//                    WhatDoWeTreatLactation.this.finish();
                    break;
                case 1:
                    intent = new Intent(getApplicationContext(),IssuesBaby.class);
                    startActivity(intent);
//                    WhatDoWeTreatLactation.this.finish();
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
        /*Intent intent = new Intent(getApplicationContext(),Medical.class);
        startActivity(intent);
        WhatDoWeTreatLactation.this.finish();*/
    }
}
