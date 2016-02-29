package com.globussoft.readydoctors.patient.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.globussoft.readydoctors.patient.R;
import com.globussoft.readydoctors.patient.Utills.AppData;
import com.globussoft.readydoctors.patient.Utills.ConstantUrls;
import com.globussoft.readydoctors.patient.Utills.Singleton;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ApplyCoupon extends Activity{

	TextView skipcoupon;
	RelativeLayout applycouponbtn;
	EditText couponcode;
	ImageView backImage;
	ProgressDialog dialog;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.applycoupon);
		initView();
	}
	
	void initView(){
		backImage=(ImageView)findViewById(R.id.backImage);
		backImage.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				onBackPressed();
			}
		});
		skipcoupon=(TextView) findViewById(R.id.skip);
		applycouponbtn=(RelativeLayout) findViewById(R.id.addanother);
		couponcode=(EditText) findViewById(R.id.data);
		
		applycouponbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{

				if (couponcode.getText().length() > 1)
				{
					if (AppData.couponType == 0)
						ApplyCouponRequest(couponcode.getText().toString(), Singleton.PatientID);
					else
						ApplyReferalCouponRequest(couponcode.getText().toString(), AppData.orgType+"",Singleton.PatientID);
				}
				else
				{
					couponcode.setError("Enter coupon");
				}
			}
		});
		
		skipcoupon.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	
	
	public void ApplyCouponRequest(final String coupon, final String userid) {
		 dialog = ProgressDialog.show(ApplyCoupon.this, "", 
                "Loading. Please wait...", true);

		RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
		StringRequest sr = new StringRequest(Request.Method.POST,
				ConstantUrls.UrlMain + ConstantUrls.UrlApplycoupon,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						
						System.out.println("coupon response" + response);
						try {
							JSONObject json = new JSONObject(response);
							if (json.getString("code").equals("200"))
							{
								/*Intent intent = new Intent(getApplicationContext(), Home.class);
								startActivity(intent);*/
								onBackPressed();
								Toast.makeText(getApplicationContext(), json.getString("message"),Toast.LENGTH_LONG).show();
							}
							else
							{
								onBackPressed();
								Toast.makeText(getApplicationContext(), json.getString("message"),Toast.LENGTH_LONG).show();
							}

						} catch (Exception e) {
							e.printStackTrace();
						}
						dialog.cancel();
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						dialog.cancel();
					}
				}) {
			@Override
			protected Map<String, String> getParams() {
				Map<String, String> params = new HashMap<String, String>();
				params.put("couponCode", coupon);
				params.put("userId", userid);

				return params;
			}

			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				params.put("Content-Type", "application/x-www-form-urlencoded");
				return params;
			}
		};
		queue.add(sr);
	}
//	referralCode,orgType,userId
	public void ApplyReferalCouponRequest(final String referralCode, final String orgType,final String userId)
	{
		dialog = ProgressDialog.show(ApplyCoupon.this, "","Loading. Please wait...", true);

		RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
		StringRequest sr = new StringRequest(Request.Method.POST,
				ConstantUrls.UrlMain + ConstantUrls.UrlApplyBusinessCoupon,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {

						System.out.println("coupon response" + response);
						try
						{
							JSONObject json = new JSONObject(response);
							if (json.getString("code").equals("200"))
							{
								/*Intent intent = new Intent(getApplicationContext(), Home.class);
								startActivity(intent);*/
								onBackPressed();
								Toast.makeText(getApplicationContext(), json.getString("message"),Toast.LENGTH_LONG).show();
							}
							else
							{
								onBackPressed();
								Toast.makeText(getApplicationContext(), json.getString("message"),Toast.LENGTH_LONG).show();
							}

						} catch (Exception e)
						{
							e.printStackTrace();
						}
						dialog.cancel();
					}
				}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				dialog.cancel();
			}
		}) {
			@Override
			protected Map<String, String> getParams() {
				Map<String, String> params = new HashMap<String, String>();
				params.put("referralCode", referralCode);
				params.put("orgType", orgType);
				params.put("userId", userId);

				return params;
			}

			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				params.put("Content-Type", "application/x-www-form-urlencoded");
				return params;
			}
		};
		queue.add(sr);
	}
}
