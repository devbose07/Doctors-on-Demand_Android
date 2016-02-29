package com.globussoft.readydoctors.doctor.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.globussoft.readydoctors.doctor.R;
import com.globussoft.readydoctors.doctor.uttils.MainSingleton;

import java.util.TimeZone;


public class SplashActivity extends Activity 
{
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_activity);

		MainSingleton.timeZone = TimeZone.getDefault().getID();
		System.out.println("@@@@@@@@@@@" + MainSingleton.timeZone);
		if (getIntent().getBooleanExtra("EXIT", false))
        {
			Log.e("SPLASH","FINISHING");
	         finish();
	     	android.os.Process.killProcess(android.os.Process.myPid());
	    }
        else
        {
        	new Handler().postDelayed(new Runnable()
        	{
				@Override
				public void run()
				{				
					startActivity(new Intent(SplashActivity.this,LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP));
					finish();
					
				}
        	}, 3000);
        }
	}
}
