package com.globussoft.readydoctors.patient.psychological;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.globussoft.readydoctors.patient.R;
import com.globussoft.readydoctors.patient.Utills.AppData;
import com.globussoft.readydoctors.patient.activity.SignUp;
import com.globussoft.readydoctors.patient.adapter.MedicalConditionAdapter;
import com.globussoft.readydoctors.patient.model.MedicalConditionModel;
import com.globussoft.readydoctors.patient.psychological_exercises.Exercise1A;
import com.globussoft.readydoctors.patient.see_a_doctor_now.SeeDoctorPharmacy;

public class PsychologyMedicalConditions extends Activity 
{

	ListView list;
	AlertDialog alert,alert2;
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
		PsychologyData.medicalConditions="";
		InitUI();
	}
	@Override
	protected void onResume()
	{
		System.out.println("i am in onrewume");
		super.onResume();
		PsychologyData.medicalConditions="";
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
				/*if(PsychologyData.medicalConditions.length()>1)
				{*/
					ShowDialog1();
//				}
				/*Intent intent=new Intent(getApplicationContext(), ApplyAppointment.class);
				startActivity(intent);*/
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
				if (PsychologyData.medicalConditions.equalsIgnoreCase(""))
				{
					PsychologyData.medicalConditions = conditions_selected.get(i).getCondition().toString() + ",";
				}
				else
				{
					PsychologyData.medicalConditions = PsychologyData.medicalConditions + conditions_selected.get(i).getCondition() + ",";

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
		PsychologyData.medical_condition = jsonObject.toString();*/
		System.out.println("medical condition ==> " + PsychologyData.medicalConditions);
	}
	public void ShowDialog2()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(PsychologyMedicalConditions.this);
		LayoutInflater inflater = this.getLayoutInflater();

	      View convertView = (View) inflater.inflate(R.layout.lets_setup_a_account, null);
	      TextView title=(TextView) convertView.findViewById(R.id.title);
	      title.setText("Going Good!");
	      
	      TextView desc=(TextView) convertView.findViewById(R.id.desc);
	      desc.setText("Now we just need to set you up with an account.");
	      
	      TextView desc2=(TextView) convertView.findViewById(R.id.desc2);
	      desc2.setText("Don't worry it won't take long!");
	      
	      RelativeLayout letsGo=(RelativeLayout) convertView.findViewById(R.id.letsgo_Rlt);
	   
	      builder.setView(convertView);
	      alert2 = builder.create();
	      alert2.show();
	      letsGo.setOnClickListener(new OnClickListener()
	      {
			
			@Override
			public void onClick(View v) 
			{
				if(AppData.loginStatus)
				{
					Intent intent = new Intent(getApplicationContext(), SeeDoctorPharmacy.class);
					startActivity(intent);
				}
				else
				{
					AppData.fromAppointment=true;
					Intent intent=new Intent(getApplicationContext(), SignUp.class);
					startActivity(intent);

				}
				alert2.dismiss();
			}
		});
	      
	     
	}
	public void ShowDialog1()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(PsychologyMedicalConditions.this);
		LayoutInflater inflater = this.getLayoutInflater();
	      /*View view = inflater.inflate(R.layout.categoryfilter_title, null);
	      builder.setCustomTitle(view);*/
		
//	      builder.setTitle("Choose Category");
	      View convertView = (View) inflater.inflate(R.layout.custom_alertdialog, null);
	      TextView title=(TextView) convertView.findViewById(R.id.title);
	      title.setText("MAXIMIZE YOUR TIME");//MAXIMIZE YOUR TIME
	      TextView txt=(TextView) convertView.findViewById(R.id.desc);
	      txt.setText("please help us by answering some simple questions which are going to help us to examine you briefly.");
	      RelativeLayout skip=(RelativeLayout) convertView.findViewById(R.id.skipRlt);
	      RelativeLayout continuebtn=(RelativeLayout) convertView.findViewById(R.id.continueRlt);
	      builder.setView(convertView);
	      alert = builder.create();
	      alert.show();
	      skip.setOnClickListener(new OnClickListener()
	      {
			
			@Override
			public void onClick(View v) 
			{
				ShowDialog2();
				alert.dismiss();
			}
		});
	      
	      continuebtn.setOnClickListener(new OnClickListener()
	      {
			
			@Override
			public void onClick(View v) 
			{
				Intent intent=new Intent(getApplicationContext(), Exercise1A.class);
				startActivity(intent);
				alert.dismiss();
			}
		});
	}
	
	
}
