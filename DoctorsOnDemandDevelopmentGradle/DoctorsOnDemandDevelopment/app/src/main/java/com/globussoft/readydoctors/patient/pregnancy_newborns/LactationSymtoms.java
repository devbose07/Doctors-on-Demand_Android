package com.globussoft.readydoctors.patient.pregnancy_newborns;

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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.globussoft.readydoctors.patient.R;
import com.globussoft.readydoctors.patient.Utills.AppData;
import com.globussoft.readydoctors.patient.activity.PaymentActivity;
import com.globussoft.readydoctors.patient.activity.SignUp;
import com.globussoft.readydoctors.patient.adapter.SymtomsAdapter;
import com.globussoft.readydoctors.patient.model.SelectedSymtoms;
import com.globussoft.readydoctors.patient.model.SymtomsModel;
import com.globussoft.readydoctors.patient.see_a_doctor_now.SeeDoctorPharmacy;

public class LactationSymtoms extends Activity 
{

	ListView list;
	TextView next,note;
	ArrayList<SymtomsModel> symptoms = new ArrayList<SymtomsModel>();
	ArrayList<SelectedSymtoms> symptoms_selected = new ArrayList<SelectedSymtoms>();
	AlertDialog alert;
	SymtomsAdapter adapter;
	ImageView backImage;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.symtoms);
		LactationData.babySymptoms="";
		LactationData.nippleSymptoms="";
		LactationData.breastSymptoms="";
		InitUI();
		System.out.println("Symtoms");
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
		System.out.println("Symtoms " + symptoms.size());
		list = (ListView) findViewById(R.id.list);
		note=(TextView) findViewById(R.id.textView1);
		note.setText(Html.fromHtml("<b>Do you have any of these ?</b> Tap all that apply."));
		next = (TextView) findViewById(R.id.next);

		// adding values
		SymtomsModel model = new SymtomsModel();
		model.setHeader(true);
		model.setmSymtom("TO YOUR BREAST");
		symptoms.add(model);
		for (int i = 0; i < AppData.lactation_breast_symtoms_array.length; i++) 
		{
			SymtomsModel model1 = new SymtomsModel();

			model1.setHeader(false);
			model1.setmSymtom(AppData.lactation_breast_symtoms_array[i]);
			model1.setmBelongsTo("TO YOUR BREAST");
			symptoms.add(model1);
		}
		
		SymtomsModel model2 = new SymtomsModel();
		model2.setHeader(true);
		model2.setmSymtom("TO YOUR NIPPLES");
		symptoms.add(model2);
		for (int i = 0; i < AppData.lactation_nipples_symtoms_array.length; i++) 
		{
			SymtomsModel model3 = new SymtomsModel();
			model3.setHeader(false);
			model3.setmSymtom(AppData.lactation_nipples_symtoms_array[i]);
			model3.setmBelongsTo("TO YOUR NIPPLES");
			symptoms.add(model3);
		}
		SymtomsModel model3 = new SymtomsModel();
		model3.setHeader(true);
		model3.setmSymtom("TO YOUR BABY");
		symptoms.add(model3);
		for (int i = 0; i < AppData.lactation_baby_symtoms_array.length; i++) 
		{
			SymtomsModel model4 = new SymtomsModel();
			model4.setHeader(false);
			model4.setmSymtom(AppData.lactation_baby_symtoms_array[i]);
			model4.setmBelongsTo("TO YOUR BABY");
			symptoms.add(model4);
		}
		
		
		

		// setadapter
		adapter = new SymtomsAdapter(getApplicationContext(), symptoms);
		list.setAdapter(adapter);

		next.setOnClickListener(new OnClickListener() 
		{

			@Override
			public void onClick(View v) 
			{
				getSymtoms();
				Intent intent = new Intent(getApplicationContext(),	SeeDoctorPharmacy.class);
				startActivity(intent);
//				ShowDialog2();
				
			}
		});
	}
	public void ShowDialog2()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(LactationSymtoms.this);
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
			{if(AppData.loginStatus)
			{
				Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
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
	@Override
	protected void onResume()
	{
		System.out.println("i am in onrewume");
		super.onResume();
		LactationData.babySymptoms="";
		LactationData.nippleSymptoms="";
		LactationData.breastSymptoms="";
	}

	public void getSymtoms()
{
	System.out.println("selected symtoms");
	symptoms_selected.clear();
	for (int i = 0; i < adapter.mCheckStates.size(); i++) 
	{
		if (adapter.mCheckStates.get(i, false)) 
		{
			SelectedSymtoms model = new SelectedSymtoms();
			model.setSymtom(symptoms.get(i).getmSymtom());
			model.setSymtomHeader(symptoms.get(i).getmBelongsTo());
			symptoms_selected.add(model);
			System.out.println("checked "
					+ symptoms.get(i).getmSymtom() + " Belongs to "
					+ symptoms.get(i).getmBelongsTo());
		}
	}

	JSONObject jsonObject = new JSONObject();
	
	JSONArray breast_symtoms_array = new JSONArray();
	JSONArray nipple_symtoms_array = new JSONArray();
	JSONArray baby_symptoms_array = new JSONArray();
	for (int i = 0; i < symptoms_selected.size(); i++) 
	{
		if (symptoms_selected.get(i).getSymtomHeader().equalsIgnoreCase("TO YOUR BREAST")) 
		{
			breast_symtoms_array.put(symptoms_selected.get(i).getSymtom());

			if (!symptoms_selected.get(i).getSymtom().toString().equalsIgnoreCase(""))
			{
				if (LactationData.breastSymptoms.equalsIgnoreCase("") )
				{
					LactationData.breastSymptoms = symptoms_selected.get(i).getSymtom() + ",";
				} else
				{
					LactationData.breastSymptoms = LactationData.breastSymptoms + symptoms_selected.get(i).getSymtom() + ",";
				}
			}


		}
		
		if (symptoms_selected.get(i).getSymtomHeader().equalsIgnoreCase("TO YOUR NIPPLES")) 
		{
			nipple_symtoms_array.put(symptoms_selected.get(i).getSymtom());

			if (!symptoms_selected.get(i).getSymtom().toString().equalsIgnoreCase(""))
			{
				if (LactationData.nippleSymptoms.equalsIgnoreCase("") )
				{
					LactationData.nippleSymptoms = symptoms_selected.get(i).getSymtom() + ",";
				} else
				{
					LactationData.nippleSymptoms = LactationData.nippleSymptoms + symptoms_selected.get(i).getSymtom() + ",";
				}
			}
		}

		if (symptoms_selected.get(i).getSymtomHeader().equalsIgnoreCase("TO YOUR BABY")) 
		{
			baby_symptoms_array.put(symptoms_selected.get(i).getSymtom());
			if (!symptoms_selected.get(i).getSymtom().toString().equalsIgnoreCase(""))
			{
				if (LactationData.babySymptoms.equalsIgnoreCase("") )
				{
					LactationData.babySymptoms = symptoms_selected.get(i).getSymtom() + ",";
				} else
				{
					LactationData.babySymptoms = LactationData.babySymptoms + symptoms_selected.get(i).getSymtom() + ",";
				}
			}


		}
	}

	try {
			if (breast_symtoms_array.length() > 0) 
			{
				jsonObject.put("breast-symptoms",breast_symtoms_array);
			}
			if (nipple_symtoms_array.length() > 0) 
			{
				jsonObject.put("nipple-symptoms", nipple_symtoms_array);
			}
			if (baby_symptoms_array.length() > 0) 
			{
				jsonObject.put("baby-symptoms",baby_symptoms_array);
			}
	} 
	catch (JSONException e) 
	{
		e.printStackTrace();
	}

//	LactationData.symtopms_json_list = jsonObject.toString();
	System.out.println("LactationData.babySymptoms ==> "+LactationData.babySymptoms);
	System.out.println("LactationData.nippleSymptoms ==> "+LactationData.nippleSymptoms);
	System.out.println("LactationData.breastSymptoms ==> "+LactationData.breastSymptoms);
}
}
