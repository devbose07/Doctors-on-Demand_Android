package com.globussoft.readydoctors.doctor.Activity;



import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.globussoft.readydoctors.doctor.R;
import com.globussoft.readydoctors.doctor.fragments.Appointments;
import com.globussoft.readydoctors.doctor.fragments.Payment;
import com.globussoft.readydoctors.doctor.fragments.Review;
import com.globussoft.readydoctors.doctor.fragments.Upcoming;
import com.globussoft.readydoctors.doctor.fragments.profile;

public class MainActivity extends FragmentActivity  
{

	boolean doubleBackToExitPressedOnce;
	RelativeLayout tab1,tab2,tab3,tab4,tab5;
	public static FragmentManager fragmentmanager;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initUI();

		fragmentmanager=getSupportFragmentManager();
		FragmentTransaction ftran=fragmentmanager.beginTransaction();
		ftran.replace(R.id.container, new Appointments()).commit();

	}
	private void initUI()
	{
		tab1=(RelativeLayout) findViewById(R.id.tab1);
		tab2=(RelativeLayout) findViewById(R.id.tab2);
		tab3=(RelativeLayout) findViewById(R.id.tab3);
		tab4=(RelativeLayout) findViewById(R.id.tab4);
		tab5= (RelativeLayout) findViewById(R.id.tab5);
		tab1.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{

				Toast.makeText(getApplicationContext(), "Appointments", Toast.LENGTH_SHORT).show();
				fragmentmanager=getSupportFragmentManager();
				SwipFragment(new Appointments());
			}
		});
		tab2.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{

				Toast.makeText(getApplicationContext(), "Profile", Toast.LENGTH_SHORT).show();
				fragmentmanager=getSupportFragmentManager();
				SwipFragment(new profile());
			}
		});
		tab3.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{

				Toast.makeText(getApplicationContext(), "Review", Toast.LENGTH_SHORT).show();
				fragmentmanager=getSupportFragmentManager();
				SwipFragment(new Review());
			}
		});
		tab4.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{

				Toast.makeText(getApplicationContext(), "Upcoming Appointments", Toast.LENGTH_SHORT).show();
				fragmentmanager=getSupportFragmentManager();
				SwipFragment(new Upcoming());
			}
		});

		tab5.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{

				Toast.makeText(getApplicationContext(), "Upcoming Appointments", Toast.LENGTH_SHORT).show();
				fragmentmanager=getSupportFragmentManager();
				SwipFragment(new Payment());
			}
		});
	}
	public static void SwipFragment(Fragment fragment)
	{
		FragmentTransaction ftran=fragmentmanager.beginTransaction();
		ftran.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
		ftran.replace(R.id.container, fragment).commit();
	}

	@Override
	public void onBackPressed()
	{

		//		System.out.println("back pressed "+fragmentmanager.getBackStackEntryCount());


		if (fragmentmanager.getBackStackEntryCount() <1)
		{
			if (doubleBackToExitPressedOnce)
			{
				super.onBackPressed();

				return;
			}
			this.doubleBackToExitPressedOnce = true;

			Toast.makeText(getApplicationContext(), "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

			new Handler().postDelayed(new Runnable()
			{

				@Override
				public void run()
				{
					doubleBackToExitPressedOnce = false;
				}
			}, 2000);
		}
		else if (fragmentmanager.getBackStackEntryCount() >= 1)
		{
			fragmentmanager.popBackStack();
		}



	}
}
