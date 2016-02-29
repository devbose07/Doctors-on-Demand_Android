package com.globussoft.readydoctors.patient.mediacal;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.globussoft.readydoctors.patient.R;
import com.globussoft.readydoctors.patient.Utills.AppData;
import com.globussoft.readydoctors.patient.adapter.MedicalConditionAdapter;
import com.globussoft.readydoctors.patient.model.MedicalConditionModel;
import com.globussoft.readydoctors.patient.see_a_doctor_now.SeeDoctorPharmacy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MedicalCondition extends Activity 
{

	ListView list;
	TextView next,note;
	ArrayList<MedicalConditionModel> conditions = new ArrayList<MedicalConditionModel>();
	ArrayList<MedicalConditionModel> conditions_selected = new ArrayList<MedicalConditionModel>();
	MedicalConditionAdapter adapter;
	ImageView backImage;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.medical_condition);
		MedicalData.medicalConditions="";
		InitUI();
	}

	private void InitUI() 
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
		list=(ListView) findViewById(R.id.list);
		next=(TextView) findViewById(R.id.next);
		note=(TextView) findViewById(R.id.textView1);
		note.setText(Html.fromHtml("<b>please let us know if you have any medical conditions.</b> Tap all that apply."));
		
		// adding values
		MedicalConditionModel model = new MedicalConditionModel();
		model.setHeader(true);
		model.setCondition("CONDITIONS");
		conditions.add(model);
		for (int i = 0; i < AppData.medicalcondition_array.length; i++) 
		{
			MedicalConditionModel model1 = new MedicalConditionModel();
			model1.setHeader(false);
			model1.setCondition(AppData.medicalcondition_array[i]);
			conditions.add(model1);
		}
		
		
		
		adapter=new MedicalConditionAdapter(getApplicationContext(), conditions);
		list.setAdapter(adapter);
		
		next.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				getConditions();
				Intent intent=new Intent(getApplicationContext(), SeeDoctorPharmacy.class);
				startActivity(intent);
			}
		});
	}

	protected void getConditions() 
	{
		System.out.println("selected symtoms");
		conditions_selected.clear();
		for (int i = 0; i < adapter.mCheckStates.size(); i++) 
		{
			if (adapter.mCheckStates.get(i, false)) 
			{
				MedicalConditionModel model = new MedicalConditionModel();
				model.setCondition(conditions.get(i).getCondition());
				conditions_selected.add(model);
				System.out.println("checked "+ conditions.get(i).getCondition() );
			}
		}
		
		JSONObject jsonObject = new JSONObject();
		
		JSONArray medical_conditions_array = new JSONArray();
		for (int i = 0; i < conditions_selected.size(); i++) 
		{
				medical_conditions_array.put(conditions_selected.get(i).getCondition());

			if (!conditions_selected.get(i).getCondition().toString().equalsIgnoreCase(""))
			{
				if (MedicalData.medicalConditions.equalsIgnoreCase(""))
				{
					MedicalData.medicalConditions = conditions_selected.get(i).getCondition().toString() + ",";
				}
				else
				{
					MedicalData.medicalConditions = MedicalData.medicalConditions + conditions_selected.get(i).getCondition() + ",";

				}
			}
		}
		/*try
		{
			if (medical_conditions_array.length() > 0) 
			{
				jsonObject.put("medical_condition",medical_conditions_array);
			}
		}
		catch (JSONException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MedicalData.medical_condition = jsonObject.toString();*/
		System.out.println("medical condition ==> " + MedicalData.medicalConditions);
	}
	@Override
	protected void onResume()
	{
		System.out.println("i am in onrewume");
		super.onResume();
		MedicalData.medicalConditions="";

	}
	
}
