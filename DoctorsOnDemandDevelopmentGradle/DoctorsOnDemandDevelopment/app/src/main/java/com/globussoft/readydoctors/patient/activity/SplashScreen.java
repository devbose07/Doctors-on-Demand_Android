package com.globussoft.readydoctors.patient.activity;

import java.util.TimeZone;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.globussoft.readydoctors.patient.R;
import com.globussoft.readydoctors.patient.Utills.AppData;
import com.globussoft.readydoctors.patient.Utills.Singleton;
import com.globussoft.readydoctors.patient.video_chat.Consts;

public class SplashScreen extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);




		String timezoneID = TimeZone.getDefault().getID();
		System.out.println("@@@@@@@@@@@" + timezoneID);

		SharedPreferences preferences=getSharedPreferences("dod_login_status", Context.MODE_PRIVATE);
		AppData.loginStatus = preferences.getBoolean("loginStatus", false);
		if(AppData.loginStatus)
		{
			System.out.println("logged in already");
			getSavedLoginInstance(preferences);
			Intent intent = new Intent(getApplicationContext(), Home.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			finish();
		}
		else
		{
			Intent intent = new Intent(getApplicationContext(), LandingScreen.class);
			startActivity(intent);
			finish();
			System.out.println("have to login");
		}

	}

	public void getSavedLoginInstance(SharedPreferences preferences)
	{
		Singleton.PatientID = preferences.getString("PatientID", null);
		Singleton.Name = preferences.getString("Name", null);
		Singleton.Email = preferences.getString("Email", null);
		Singleton.TimeZone = preferences.getString("TimeZone", null);
		Singleton.Dateofbirth = preferences.getString("Dateofbirth", null);
		Singleton.ActivationKey = preferences.getString("ActivationKey", null);
		Singleton.Status = preferences.getString("Status", null);
		Singleton.Updated_at = preferences.getString("Updated_at", null);
		Singleton.Created_at = preferences.getString("Created_at", null);
		Singleton.Role = preferences.getString("Role", null);
		Singleton.RegistrationDate = preferences.getString("RegistrationDate", null);
		Singleton.PromoCode = preferences.getString("PromoCode", null);
		Singleton.UsedCoupons = preferences.getString("UsedCoupons", null);
		Singleton.Credit = preferences.getString("Credit", null);
		Singleton.FreeMinutes = preferences.getString("FreeMinutes", null);
		Singleton.Currency = preferences.getString("Currency", null);
		Singleton.Info = preferences.getString("Info", null);

		System.out.println("===============login instance==============");
		System.out.println("Singleton.PatientID -" + Singleton.PatientID);
		System.out.println("Singleton.Name -" + Singleton.Name);
		System.out.println("Singleton.Email -" + Singleton.Email);
		System.out.println("Singleton.TimeZone -" + Singleton.TimeZone);
		System.out.println("Singleton.Dateofbirth -" + Singleton.Dateofbirth);
		System.out.println("Singleton.ActivationKey -" + Singleton.ActivationKey);
		System.out.println("Singleton.Status -" + Singleton.Status);
		System.out.println("Singleton.Updated_at -" + Singleton.Updated_at);
		System.out.println("Singleton.Created_at -" + Singleton.Created_at);
		System.out.println("Singleton.Role -" + Singleton.Role);
		System.out.println("Singleton.RegistrationDate -" + Singleton.RegistrationDate);
		System.out.println("Singleton.PromoCode -" + Singleton.PromoCode);
		System.out.println("Singleton.UsedCoupons -" + Singleton.UsedCoupons);
		System.out.println("Singleton.Credit -" + Singleton.Credit);
		System.out.println("Singleton.FreeMinutes -" + Singleton.FreeMinutes);
		System.out.println("Singleton.Currency -" + Singleton.Currency);
		System.out.println("Singleton.Info -" + Singleton.Info);
		System.out.println("===============login instance==============");


		//assign email with qb user id
		Consts.qb_loginID=Singleton.Email;
	}

}
