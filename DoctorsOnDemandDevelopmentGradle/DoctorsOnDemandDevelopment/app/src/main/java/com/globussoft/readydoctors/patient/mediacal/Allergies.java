package com.globussoft.readydoctors.patient.mediacal;

import java.util.ArrayList;

import org.json.JSONArray;

import com.globussoft.readydoctors.patient.R;
import com.globussoft.readydoctors.patient.adapter.AlergiesAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Allergies extends Activity {

	TextView next_btn;
	AlergiesAdapter adapter;
	ListView list;
	RelativeLayout addanother_layout;
	ArrayList<String> arraylist=new ArrayList<String>();
	ImageView backImage;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.allergies);
	MedicalData.allergies="";
		InitUI();
	}
	private void InitUI() {
		backImage=(ImageView)findViewById(R.id.backImage);
		backImage.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				onBackPressed();
			}
		});
		next_btn=(TextView) findViewById(R.id.next);
		list=(ListView) findViewById(R.id.list);
		
		 //code to set adapter to populate list
        View footerView =  ((LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.allergies_footer, null, false);
        list.addFooterView(footerView);
        addanother_layout=(RelativeLayout) footerView.findViewById(R.id.addanother);
        
        arraylist.add(null);
        adapter=new AlergiesAdapter(getApplicationContext(), arraylist);
        
        list.setAdapter(adapter);
		next_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				JSONArray ja = new JSONArray();
				for (int i = 0; i < arraylist.size(); i++)
				{
					View view = list.getChildAt(i);
					EditText allergiesname = (EditText) view.findViewById(R.id.data);
					if (!allergiesname.getText().toString().toString().equalsIgnoreCase(""))
					{
						if (MedicalData.allergies.equalsIgnoreCase("") )
						{
							MedicalData.allergies = allergiesname.getText().toString().toString() + ",";
						} else
						{
							MedicalData.allergies = MedicalData.allergies + allergiesname.getText().toString().toString() + ",";
						}
					}
				ja.put(allergiesname.getText().toString());
			}
				
				MedicalData.allergies_json_list=ja.toString();

				System.out.println("MedicalData.allergies "+MedicalData.allergies);
				
				Intent intent=new Intent(getApplicationContext(),Symtoms.class);
				startActivity(intent);
				
			}
		} );
		
		
		addanother_layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				arraylist.add(null);
				adapter.notifyDataSetChanged();

			}
		});
	}

	@Override
	protected void onResume()
	{
		System.out.println("i am in onrewume");
		super.onResume();
		MedicalData.allergies="";
	}
	
}
