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
public class About extends Activity {
    ImageView backImage;
    RelativeLayout our_mission, medical_board, team, privacy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        initUI();
    }

    public void initUI()
    {
        backImage = (ImageView) findViewById(R.id.backImage);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
            }
        });
        our_mission = (RelativeLayout) findViewById(R.id.our_mission);
        our_mission.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), OurMission.class);
                startActivity(intent);
                About.this.finish();
            }
        });
        medical_board = (RelativeLayout) findViewById(R.id.medical_board);
        medical_board.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), MedicalBoard.class);
                startActivity(intent);
                About.this.finish();
            }
        });
        team = (RelativeLayout) findViewById(R.id.team);
        team.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), Team.class);
                startActivity(intent);
                About.this.finish();
            }
        });
        privacy = (RelativeLayout) findViewById(R.id.privacy);
        privacy.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), Privacy.class);
                startActivity(intent);
                About.this.finish();
            }
        });
    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(),MeetUs.class);
        startActivity(intent);
        About.this.finish();
    }
}