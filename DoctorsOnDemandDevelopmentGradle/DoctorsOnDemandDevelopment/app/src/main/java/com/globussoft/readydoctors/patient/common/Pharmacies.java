package com.globussoft.readydoctors.patient.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.globussoft.readydoctors.patient.R;
import com.globussoft.readydoctors.patient.Utills.AppController;
import com.globussoft.readydoctors.patient.Utills.ConnectionDetector;
import com.globussoft.readydoctors.patient.Utills.ConstantUrls;
import com.globussoft.readydoctors.patient.pregnancy_newborns.LactationData;
import com.globussoft.readydoctors.patient.mediacal.MedicalData;
import com.globussoft.readydoctors.patient.activity.ApplyAppointment;
import com.globussoft.readydoctors.patient.activity.PaymentActivity;
import com.globussoft.readydoctors.patient.adapter.PharmacyAdapter;
import com.globussoft.readydoctors.patient.model.PharmacyModel;
import com.globussoft.readydoctors.patient.pediatrics.PediatricData;
import com.globussoft.readydoctors.patient.psychological.PsychologyData;

public class Pharmacies extends Activity
{
	ProgressBar progressBar;
	ListView list;
	TextView next;
	ArrayList<PharmacyModel> arraylist = new ArrayList<PharmacyModel>();
	PharmacyAdapter adapter;
	private String tag_json_obj = "jobj_req";
	ImageView backImage;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pharmacies);
		InitView();
	}

	void InitView()
	{
		backImage=(ImageView)findViewById(R.id.backImage);
		backImage.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				onBackPressed();
			}
		});
		next = (TextView) findViewById(R.id.next);
		list = (ListView) findViewById(R.id.list);
		progressBar = (ProgressBar) findViewById(R.id.progressbar);
		if(new ConnectionDetector(getApplicationContext()).isConnectingToInternet())
		{
			FetchPharmacyList();
		}
		else
		{
			Toast.makeText(Pharmacies.this, "You dont have Internet Connection....!", Toast.LENGTH_SHORT).show();
		}

		next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),ApplyAppointment.class);
				startActivity(intent);
			}
		});

		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				MedicalData.pharmacyID = arraylist.get(position).getPharmacyId();
				PediatricData.pharmacyId=arraylist.get(position).getPharmacyId();
				LactationData.pharmacyId=arraylist.get(position).getPharmacyId();
				PsychologyData.pharmacyId=arraylist.get(position).getPharmacyId();

				Intent intent = new Intent(getApplicationContext(),PaymentActivity.class);
				startActivity(intent);
			}
		});
	}

	/**
	 * Making json object request
	 * */
	private void FetchPharmacyList() {
		progressBar.setVisibility(View.VISIBLE);
		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET,
				ConstantUrls.UrlMain + ConstantUrls.UrlGetShowpharmacies, null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {

						arraylist.clear();
						System.out.println("json response entertainment "
								+ response);

						try {

							JSONArray array = response.getJSONArray("data");
							for (int i = 0; i < array.length(); i++) {
								JSONObject obj = array.getJSONObject(i);
								PharmacyModel model = new PharmacyModel();
								model.setPharmacyName(obj
										.getString("pharmacyName"));
								model.setPharmacyId(obj.getString("pharmacyId"));
								model.setCity(obj.getString("city"));
								model.setCountry(obj.getString("country"));
								model.setPhoneNumber(obj
										.getString("phoneNumber"));
								model.setState(obj.getString("state"));
								model.setZipcode(obj.getString("zipcode"));
								arraylist.add(model);
							}
							adapter = new PharmacyAdapter(getApplicationContext(), arraylist);
							list.setAdapter(adapter);

							progressBar.setVisibility(View.INVISIBLE);
						} catch (Exception e) {
							e.printStackTrace();
						}

					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						progressBar.setVisibility(View.INVISIBLE);
					}
				}) {

			/**
			 * Passing some request headers
			 * */
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				HashMap<String, String> headers = new HashMap<String, String>();
				headers.put("Content-Type", "application/json");
				return headers;
			}

		};
		AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
	}

}
