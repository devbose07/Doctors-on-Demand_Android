package com.globussoft.readydoctors.patient.pediatrics;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.globussoft.readydoctors.patient.R;
import com.globussoft.readydoctors.patient.Utills.AppData;
import com.globussoft.readydoctors.patient.activity.SignUp;
import com.globussoft.readydoctors.patient.adapter.MedicalConditionAdapter;
import com.globussoft.readydoctors.patient.model.MedicalConditionModel;

public class PediatricsMedialConditions extends Activity 
{

	ListView list;
	TextView next,note;
	ArrayList<MedicalConditionModel> conditions = new ArrayList<MedicalConditionModel>();
	ArrayList<MedicalConditionModel> conditions_selected = new ArrayList<MedicalConditionModel>();
	MedicalConditionAdapter adapter;
	AlertDialog alert;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.medical_condition);
		PediatricData.medicalConditions="";
		InitUI();
	}

	private void InitUI() 
	{
		
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
		
		next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				getConditions();
				Intent intent = new Intent(getApplicationContext(), Child.class);
				startActivity(intent);
				/*Intent intent=new Intent(getApplicationContext(), ApplyAppointment.class);
				startActivity(intent);*/
//				ShowDialog2();
			}
		});
	}
	@Override
	protected void onResume()
	{
		System.out.println("i am in onrewume");
		super.onResume();
		PediatricData.medicalConditions="";
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
				if (PediatricData.medicalConditions.equalsIgnoreCase(""))
				{
					PediatricData.medicalConditions = conditions_selected.get(i).getCondition().toString() + ",";
				}
				else
				{
					PediatricData.medicalConditions = PediatricData.medicalConditions + conditions_selected.get(i).getCondition() + ",";

				}
			}
		}
		try 
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
//		PediatricData.medical_condition = jsonObject.toString();
		System.out.println("Selected Conditions ==> "+jsonObject.toString());
	}
	
	public void ShowDialog2()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(PediatricsMedialConditions.this);
		LayoutInflater inflater = this.getLayoutInflater();
	      /*View view = inflater.inflate(R.layout.categoryfilter_title, null);
	      builder.setCustomTitle(view);*/
		
//	      builder.setTitle("Choose Category");
	      View convertView = (View) inflater.inflate(R.layout.lets_setup_a_account, null);
	      TextView title=(TextView) convertView.findViewById(R.id.title);
	      title.setText("Going Good!");
	      
	      TextView desc=(TextView) convertView.findViewById(R.id.desc);
	      desc.setText("Now we just need to set you up with an account.");
	      
	      TextView desc2=(TextView) convertView.findViewById(R.id.desc2);
	      desc2.setText("Don't worry it won't take long!");
	      
	      RelativeLayout letsGo=(RelativeLayout) convertView.findViewById(R.id.letsgo_Rlt);
	   
	      builder.setView(convertView);
	      alert = builder.create();
	      alert.show();
	      letsGo.setOnClickListener(new OnClickListener()
	      {
			
			@Override
			public void onClick(View v) 
			{
				
				if(AppData.loginStatus)
				{
					Intent intent = new Intent(getApplicationContext(), Child.class);
					startActivity(intent);
				}
				else
				{
					AppData.fromAppointment=true;
					Intent intent=new Intent(getApplicationContext(), SignUp.class);
					startActivity(intent);
					alert.dismiss();
				}
				
			}
		});
	      
	     
	}
}
