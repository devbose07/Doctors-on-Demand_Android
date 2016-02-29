package com.globussoft.readydoctors.patient.medical_static_views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.globussoft.readydoctors.patient.R;
import com.globussoft.readydoctors.patient.mediacal.Medical;

/**
 * Created by GLB-276 on 10/10/2015.
 */
public class WhatDoWeTreat extends Activity
{
    String[] mArray = {"Cough/Cold/allergies  ","Sore Throat","UTIs","Travel","Sports Injuries","Skin issues/Rashes","Diarrhea&Vomiting","Eye conditions","What we don't treat"};
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
                Intent intent = new Intent(getApplicationContext(),Medical.class);
                startActivity(intent);
                WhatDoWeTreat.this.finish();
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
                    intent = new Intent(getApplicationContext(),Cough.class);
                    startActivity(intent);
                    WhatDoWeTreat.this.finish();
                    break;
                case 1:
                    intent = new Intent(getApplicationContext(),SoreThroat.class);
                    startActivity(intent);
                    WhatDoWeTreat.this.finish();
                    break;
                case 2:
                    intent = new Intent(getApplicationContext(),UTI.class);
                    startActivity(intent);
                    WhatDoWeTreat.this.finish();

                    break;
                case 3:
                    intent = new Intent(getApplicationContext(),Travel.class);
                    startActivity(intent);
                    WhatDoWeTreat.this.finish();
                    break;
                case 4:
                    intent = new Intent(getApplicationContext(),SportsInjuries.class);
                    startActivity(intent);
                    WhatDoWeTreat.this.finish();
                    break;
                case 5:
                    intent = new Intent(getApplicationContext(),SkinIssues.class);
                    startActivity(intent);
                    WhatDoWeTreat.this.finish();
                    break;
                case 6:
                    intent = new Intent(getApplicationContext(),DiarrHea.class);
                    startActivity(intent);
                    WhatDoWeTreat.this.finish();
                    break;
                case 7:
                    intent = new Intent(getApplicationContext(),EyeConditions.class);
                    startActivity(intent);
                    WhatDoWeTreat.this.finish();
                    break;
                case 8:
                    intent = new Intent(getApplicationContext(),WeDontTreat.class);
                    startActivity(intent);
                    WhatDoWeTreat.this.finish();
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
        Intent intent = new Intent(getApplicationContext(),Medical.class);
        startActivity(intent);
        WhatDoWeTreat.this.finish();
    }
}
