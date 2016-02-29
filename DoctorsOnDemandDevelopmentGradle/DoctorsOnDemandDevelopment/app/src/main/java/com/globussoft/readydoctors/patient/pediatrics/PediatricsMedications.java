package com.globussoft.readydoctors.patient.pediatrics;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.globussoft.readydoctors.patient.R;
import com.globussoft.readydoctors.patient.adapter.MedicationAdapter;
import com.globussoft.readydoctors.patient.model.MedicationModel;

public class PediatricsMedications extends Activity 
{

	TextView next;
	ListView list;
	RelativeLayout add_another_btn;

	ArrayList<MedicationModel> arraylist = new ArrayList<MedicationModel>();
	MedicationAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.medication);
		PediatricData.medication="";
		PediatricData.medicationTime="";
		InitUI();
	}

	private void InitUI() 
	{

		next = (TextView) findViewById(R.id.next);
		list = (ListView) findViewById(R.id.list);

		// add footer to listview

		// code to set adapter to populate list
		View footerView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE))
				.inflate(R.layout.medication_footer, null, false);
		list.addFooterView(footerView);

		add_another_btn = (RelativeLayout) footerView
				.findViewById(R.id.addanother);

		// initialise 1st element of list

		MedicationModel model = new MedicationModel();
		arraylist.add(model);
		adapter = new MedicationAdapter(getApplicationContext(), arraylist);
		list.setAdapter(adapter);
		next.setOnClickListener(new OnClickListener() 
		{

			@Override
			public void onClick(View v) 
			{

				JSONArray ja = new JSONArray();
				
				for (int i = 0; i < arraylist.size(); i++) 
				{
					View view = list.getChildAt(i);
					EditText meidcation_name = (EditText) view.findViewById(R.id.data);
					Spinner spinner = (Spinner) view.findViewById(R.id.spinner);

					// [{"name":"Betadin","time":"Year"},{"name":"Tinovat","time":"More than a year"}]

					JSONObject jo = new JSONObject();
					try 
					{
						jo.put("name", meidcation_name.getText().toString());
						jo.put("time", spinner.getSelectedItem().toString());

						ja.put(jo);
					}
					catch (JSONException e) 
					{
						e.printStackTrace();
					}

					if (!meidcation_name.getText().toString().equalsIgnoreCase(""))
					{
						if (PediatricData.medication.equalsIgnoreCase("") && PediatricData.medicationTime.equalsIgnoreCase(""))
						{
							PediatricData.medication = meidcation_name.getText().toString() + ",";
							PediatricData.medicationTime = spinner.getSelectedItem().toString() + ",";
						} else
						{
							PediatricData.medication = PediatricData.medication + meidcation_name.getText().toString() + ",";
							PediatricData.medicationTime = PediatricData.medicationTime + spinner.getSelectedItem().toString() + ",";
						}
					}
					
					arraylist.get(i).setMeidcation_name(
							meidcation_name.getText().toString());
					arraylist.get(i).setTime(
							spinner.getSelectedItem().toString());
				}
				
//				PediatricData.medication_json_list=ja.toString();
				
				System.out.println("data form list" + arraylist.toString());
				System.out.println("json array="+ja);
				Intent intent = new Intent(getApplicationContext(),PediatricsAllergies.class);
				startActivity(intent);
			}
		});

		add_another_btn.setOnClickListener(new OnClickListener() 
		{

			@Override
			public void onClick(View v) 
			{
				MedicationModel model = new MedicationModel();
				arraylist.add(model);
				adapter.notifyDataSetChanged();

				System.out.println("list size " + arraylist.size());
			}
		});

	}
	@Override
	protected void onResume()
	{
		System.out.println("i am in onrewume");
		super.onResume();
		PediatricData.medication="";
		PediatricData.medicationTime="";
	}
}
