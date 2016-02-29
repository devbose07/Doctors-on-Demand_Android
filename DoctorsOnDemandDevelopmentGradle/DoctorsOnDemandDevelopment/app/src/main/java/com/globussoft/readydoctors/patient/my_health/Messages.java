package com.globussoft.readydoctors.patient.my_health;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.globussoft.readydoctors.patient.R;

/**
 * Created by GLB-276 on 10/30/2015.
 */
public class Messages extends Activity
{
    ImageView backImage;
    RelativeLayout visit_history,messages,my_favorite,my_pharmacies;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messages);
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
        /*Intent intent = new Intent(getApplicationContext(),MeetUs.class);
        startActivity(intent);
        MyHealth.this.finish();*/
    }
}
