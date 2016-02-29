package com.globussoft.readydoctors.patient.pediatrics;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.globussoft.readydoctors.patient.R;
import com.globussoft.readydoctors.patient.Utills.AppData;
import com.globussoft.readydoctors.patient.adapter.SymtomsAdapter;
import com.globussoft.readydoctors.patient.model.SelectedSymtoms;
import com.globussoft.readydoctors.patient.model.SymtomsModel;

public class PediatricsSymtoms extends Activity 
{

	ListView list;
	TextView next,note;
	ArrayList<SymtomsModel> symptoms = new ArrayList<SymtomsModel>();
	ArrayList<SelectedSymtoms> symptoms_selected = new ArrayList<SelectedSymtoms>();
	SymtomsAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.symtoms);
		PediatricData.generalSymptoms="";
		PediatricData.neckSymptoms="";
		PediatricData.chestSymptoms="";
		PediatricData.digestiveSymptoms="";
		PediatricData.pelvisSymptoms="";
		PediatricData.musclesSymptoms="";
		PediatricData.skinSymptoms="";
		InitUI();
		System.out.println("Symtoms");
	}

	private void InitUI() 
	{
		System.out.println("Symtoms " + symptoms.size());
		list = (ListView) findViewById(R.id.list);
		note=(TextView) findViewById(R.id.textView1);
		note.setText(Html.fromHtml("<b>Do you have any of these symtoms.</b> Tap all that apply."));
		next = (TextView) findViewById(R.id.next);

		// adding values
		SymtomsModel model = new SymtomsModel();
		model.setHeader(true);
		model.setmSymtom("GENERAL SYMTOMS");
		symptoms.add(model);
		for (int i = 0; i < AppData.symptoms_genaral.length; i++) 
		{
			SymtomsModel model1 = new SymtomsModel();

			model1.setHeader(false);
			model1.setmSymtom(AppData.symptoms_genaral[i]);
			model1.setmBelongsTo("GENERAL SYMTOMS");
			symptoms.add(model1);
		}
		
		SymtomsModel model2 = new SymtomsModel();
		model2.setHeader(true);
		model2.setmSymtom("HEAD AND NECK");
		symptoms.add(model2);
		for (int i = 0; i < AppData.symptoms_head_neck.length; i++) 
		{
			SymtomsModel model3 = new SymtomsModel();
			model3.setHeader(false);
			model3.setmSymtom(AppData.symptoms_head_neck[i]);
			model3.setmBelongsTo("HEAD AND NECK");
			symptoms.add(model3);
		}
		SymtomsModel model3 = new SymtomsModel();
		model3.setHeader(true);
		model3.setmSymtom("CHEST");
		symptoms.add(model3);
		for (int i = 0; i < AppData.symptoms_chest.length; i++) 
		{
			SymtomsModel model4 = new SymtomsModel();
			model4.setHeader(false);
			model4.setmSymtom(AppData.symptoms_chest[i]);
			model4.setmBelongsTo("CHEST");
			symptoms.add(model4);
		}
		
		SymtomsModel model5 = new SymtomsModel();
		model5.setHeader(true);
		model5.setmSymtom("DIGESTIVE TRACK");
		symptoms.add(model5);
		for (int i = 0; i < AppData.symptoms_degestive_track.length; i++) 
		{
			SymtomsModel model6 = new SymtomsModel();
			model6.setHeader(false);
			model6.setmSymtom(AppData.symptoms_degestive_track[i]);
			model6.setmBelongsTo("DIGESTIVE TRACK");
			symptoms.add(model6);
		}
		
		SymtomsModel model7 = new SymtomsModel();
		model7.setHeader(true);
		model7.setmSymtom("PELVIS");
		symptoms.add(model7);
		for (int i = 0; i < AppData.symptoms_pelvis.length; i++) 
		{
			SymtomsModel model8 = new SymtomsModel();
			model8.setHeader(false);
			model8.setmSymtom(AppData.symptoms_pelvis[i]);
			model8.setmBelongsTo("PELVIS");
			symptoms.add(model8);
		}
		
		SymtomsModel model9 = new SymtomsModel();
		model9.setHeader(true);
		model9.setmSymtom("MUSCLES AND JOINTS");
		symptoms.add(model9);
		for (int i = 0; i < AppData.symptoms_muscle_joints.length; i++) 
		{
			SymtomsModel model10 = new SymtomsModel();
			model10.setHeader(false);
			model10.setmSymtom(AppData.symptoms_muscle_joints[i]);
			model10.setmBelongsTo("MUSCLES AND JOINTS");
			symptoms.add(model10);
		}
		
		SymtomsModel model12 = new SymtomsModel();
		model12.setHeader(true);
		model12.setmSymtom("SKIN");
		symptoms.add(model12);
		for (int i = 0; i < AppData.symptoms_skin.length; i++) 
		{
			SymtomsModel model13 = new SymtomsModel();
			model13.setHeader(false);
			model13.setmSymtom(AppData.symptoms_skin[i]);
			model13.setmBelongsTo("SKIN");
			symptoms.add(model13);
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
				Intent intent = new Intent(getApplicationContext(),PediatricsMedialConditions.class);
				startActivity(intent);

			}
		});
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
	
	JSONArray general_symtoms_array = new JSONArray();
	JSONArray head_neck_array = new JSONArray();
	JSONArray symptoms_chest = new JSONArray();
	JSONArray symptoms_degestive_track = new JSONArray();
	JSONArray symptoms_pelvis = new JSONArray();
	JSONArray symptoms_muscle_joints = new JSONArray();
	JSONArray symptoms_skin = new JSONArray();
	for (int i = 0; i < symptoms_selected.size(); i++) 
	{
		if (symptoms_selected.get(i).getSymtomHeader().equalsIgnoreCase("GENERAL SYMTOMS")) 
		{
			general_symtoms_array.put(symptoms_selected.get(i).getSymtom());
			if (!symptoms_selected.get(i).getSymtom().toString().equalsIgnoreCase(""))
			{
				if (PediatricData.generalSymptoms.equalsIgnoreCase("") )
				{
					PediatricData.generalSymptoms = symptoms_selected.get(i).getSymtom() + ",";
				} else
				{
					PediatricData.generalSymptoms = PediatricData.generalSymptoms + symptoms_selected.get(i).getSymtom() + ",";
				}
			}

		}
		if (symptoms_selected.get(i).getSymtomHeader().equalsIgnoreCase("HEAD AND NECK")) 
		{
			head_neck_array.put(symptoms_selected.get(i).getSymtom());
			if (!symptoms_selected.get(i).getSymtom().toString().equalsIgnoreCase(""))
			{
				if (PediatricData.neckSymptoms.equalsIgnoreCase("") )
				{
					PediatricData.neckSymptoms = symptoms_selected.get(i).getSymtom() + ",";
				} else
				{
					PediatricData.neckSymptoms = PediatricData.neckSymptoms + symptoms_selected.get(i).getSymtom() + ",";
				}
			}
		}

		if (symptoms_selected.get(i).getSymtomHeader().equalsIgnoreCase("CHEST")) 
		{
			symptoms_chest.put(symptoms_selected.get(i).getSymtom());
			if (!symptoms_selected.get(i).getSymtom().toString().equalsIgnoreCase(""))
			{
				if (PediatricData.chestSymptoms.equalsIgnoreCase("") )
				{
					PediatricData.chestSymptoms = symptoms_selected.get(i).getSymtom() + ",";
				} else
				{
					PediatricData.chestSymptoms = PediatricData.chestSymptoms + symptoms_selected.get(i).getSymtom() + ",";
				}
			}
		}

		if (symptoms_selected.get(i).getSymtomHeader().equalsIgnoreCase("DIGESTIVE TRACK")) 
		{
			symptoms_degestive_track.put(symptoms_selected.get(i).getSymtom());
			if (!symptoms_selected.get(i).getSymtom().toString().equalsIgnoreCase(""))
			{
				if (PediatricData.digestiveSymptoms.equalsIgnoreCase("") )
				{
					PediatricData.digestiveSymptoms = symptoms_selected.get(i).getSymtom() + ",";
				} else
				{
					PediatricData.digestiveSymptoms = PediatricData.digestiveSymptoms + symptoms_selected.get(i).getSymtom() + ",";
				}
			}
		}

		if (symptoms_selected.get(i).getSymtomHeader().equalsIgnoreCase("PELVIS")) 
		{
			symptoms_pelvis.put(symptoms_selected.get(i).getSymtom());
			if (!symptoms_selected.get(i).getSymtom().toString().equalsIgnoreCase(""))
			{
				if (PediatricData.pelvisSymptoms.equalsIgnoreCase("") )
				{
					PediatricData.pelvisSymptoms = symptoms_selected.get(i).getSymtom() + ",";
				} else
				{
					PediatricData.pelvisSymptoms = PediatricData.pelvisSymptoms + symptoms_selected.get(i).getSymtom() + ",";
				}
			}
		}

		if (symptoms_selected.get(i).getSymtomHeader().equalsIgnoreCase("MUSCLES AND JOINTS")) 
		{
			symptoms_muscle_joints.put(symptoms_selected.get(i).getSymtom());
			if (!symptoms_selected.get(i).getSymtom().toString().equalsIgnoreCase(""))
			{
				if (PediatricData.musclesSymptoms.equalsIgnoreCase("") )
				{
					PediatricData.musclesSymptoms = symptoms_selected.get(i).getSymtom() + ",";
				} else
				{
					PediatricData.musclesSymptoms = PediatricData.musclesSymptoms + symptoms_selected.get(i).getSymtom() + ",";
				}
			}
		}

		if (symptoms_selected.get(i).getSymtomHeader().equalsIgnoreCase("SKIN")) 
		{
			symptoms_skin.put(symptoms_selected.get(i).getSymtom());
			if (!symptoms_selected.get(i).getSymtom().toString().equalsIgnoreCase(""))
			{
				if (PediatricData.skinSymptoms.equalsIgnoreCase("") )
				{
					PediatricData.skinSymptoms = symptoms_selected.get(i).getSymtom() + ",";
				} else
				{
					PediatricData.skinSymptoms = PediatricData.skinSymptoms + symptoms_selected.get(i).getSymtom() + ",";
				}
			}
		}

	}

	try {
			if (general_symtoms_array.length() > 0) 
			{
				jsonObject.put("general-symptoms",general_symtoms_array);
			}
			if (head_neck_array.length() > 0) 
			{
				jsonObject.put("headnneck", head_neck_array);
			}
			if (symptoms_chest.length() > 0) 
			{
				jsonObject.put("symptoms_chest",symptoms_chest);
			}
			if (symptoms_degestive_track.length() > 0) 
			{
				jsonObject.put("symptoms_degestive_track", symptoms_degestive_track);
			}
			if (symptoms_pelvis.length() > 0) 
			{
				jsonObject.put("symptoms_pelvis",symptoms_pelvis);
			}
			if (symptoms_muscle_joints.length() > 0) 
			{
				jsonObject.put("symptoms_muscle_joints", symptoms_muscle_joints);
			}
			if (symptoms_skin.length() > 0) 
			{
				jsonObject.put("symptoms_skin",symptoms_skin);
			}
		System.out.println("MedicalData.generalSymptoms "+PediatricData.generalSymptoms);
		System.out.println("MedicalData.relationshipSymptoms "+PediatricData.neckSymptoms);
		System.out.println("MedicalData.habitSymptoms "+PediatricData.chestSymptoms);
		System.out.println("MedicalData.digestiveSymptoms "+PediatricData.digestiveSymptoms);
		System.out.println("MedicalData.pelvicSymptoms "+PediatricData.pelvisSymptoms);
		System.out.println("MedicalData.muscleSymptoms "+PediatricData.musclesSymptoms);
		System.out.println("MedicalData.skinSymptoms "+PediatricData.skinSymptoms);
	} 
	catch (JSONException e) 
	{
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

//	PediatricData.symtopms_json_list = jsonObject.toString();
	System.out.println("Selected Symtoms ==> "+jsonObject.toString());
}
	@Override
	protected void onResume()
	{
		System.out.println("i am in onrewume");
		super.onResume();
		PediatricData.generalSymptoms="";
		PediatricData.neckSymptoms="";
		PediatricData.chestSymptoms="";
		PediatricData.digestiveSymptoms="";
		PediatricData.pelvisSymptoms="";
		PediatricData.musclesSymptoms="";
		PediatricData.skinSymptoms="";
	}
}
