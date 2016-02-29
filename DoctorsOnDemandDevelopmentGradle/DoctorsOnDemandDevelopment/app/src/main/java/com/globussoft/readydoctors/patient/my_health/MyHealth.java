package com.globussoft.readydoctors.patient.my_health;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.globussoft.readydoctors.patient.R;

/**
 * Created by GLB-276 on 10/30/2015.
 */
public class MyHealth extends Activity
{
    ImageView backImage;
    RelativeLayout visit_history,messages,my_favorite,my_pharmacies;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_health);
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
        visit_history=(RelativeLayout)findViewById(R.id.visit_history);
        visit_history.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(),MyVisitHistory.class);
                startActivity(intent);
//                MyHealth.this.finish();
            }
        });
        messages=(RelativeLayout)findViewById(R.id.messages);
        messages.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(),Messages.class);
                startActivity(intent);
//                MyHealth.this.finish();
            }
        });
        my_favorite=(RelativeLayout)findViewById(R.id.my_favorite);
        my_favorite.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(),MyFavoriteProviders.class);
                startActivity(intent);
//                MyHealth.this.finish();
            }
        });
        my_pharmacies=(RelativeLayout)findViewById(R.id.my_pharmacy);
        my_pharmacies.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(),MyPharmacies.class);
                startActivity(intent);
//                MyHealth.this.finish();
            }
        });

    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        /*Intent intent = new Intent(getApplicationContext(),MeetUs.class);
        startActivity(intent);
        MyHealth.this.finish();*/
    }
}
