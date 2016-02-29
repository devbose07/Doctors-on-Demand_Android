package com.globussoft.readydoctors.patient.psychological;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.globussoft.readydoctors.patient.R;
import com.globussoft.readydoctors.patient.Utills.AppData;
import com.globussoft.readydoctors.patient.adapter.SymtomsAdapter;
import com.globussoft.readydoctors.patient.model.SelectedSymtoms;
import com.globussoft.readydoctors.patient.model.SymtomsModel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class PsychologySymtoms extends Activity 
{
	ListView list;
	TextView next,note;
	ArrayList<SymtomsModel> symptoms = new ArrayList<SymtomsModel>();
	ArrayList<SelectedSymtoms> symptoms_selected = new ArrayList<SelectedSymtoms>();
	SymtomsAdapter adapter;
	ImageView backImage;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.symtoms);
		PsychologyData.generalSymptoms="";
		PsychologyData.habitSymptoms="";
		PsychologyData.relationshipSymptoms="";
		InitUI();
		System.out.println(" psychology Symtoms");
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
		list = (ListView) findViewById(R.id.list);
		note=(TextView) findViewById(R.id.textView1);
		note.setText(Html.fromHtml("<b>Do you have any of these symtoms.</b> Tap all that apply."));
		next = (TextView) findViewById(R.id.next);
		
		symptoms.clear();
		// adding values
		SymtomsModel model = new SymtomsModel();
		model.setHeader(true);
		model.setmSymtom("GENERAL");
		symptoms.add(model);
		for (int i = 0; i < AppData.psychology_symptoms_genaral.length; i++) 
		{
			SymtomsModel model1 = new SymtomsModel();

			model1.setHeader(false);
			model1.setmSymtom(AppData.psychology_symptoms_genaral[i]);
			model1.setmBelongsTo("GENERAL");
			symptoms.add(model1);
		}
		
		SymtomsModel model2 = new SymtomsModel();
		model2.setHeader(true);
		model2.setmSymtom("HABIT CHANGE");
		symptoms.add(model2);
		for (int i = 0; i < AppData.psychology_symptoms_habbits.length; i++) 
		{
			SymtomsModel model3 = new SymtomsModel();

			model3.setHeader(false);
			model3.setmSymtom(AppData.psychology_symptoms_habbits[i]);
			model3.setmBelongsTo("HABIT CHANGE");
			symptoms.add(model3);
		}
		
		SymtomsModel model4 = new SymtomsModel();
		model4.setHeader(true);
		model4.setmSymtom("RELATIONSHIP ISSUES");
		symptoms.add(model4);
		for (int i = 0; i < AppData.psychology_symptoms_relationships.length; i++) 
		{
			SymtomsModel model5 = new SymtomsModel();

			model5.setHeader(false);
			model5.setmSymtom(AppData.psychology_symptoms_relationships[i]);
			model5.setmBelongsTo("RELATIONSHIP ISSUES");
			symptoms.add(model5);
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
				Intent intent = new Intent(getApplicationContext(),Psychology_medications.class);
				startActivity(intent);

			}
		});
	}
	public void getSymtoms()
	{
		symptoms_selected.clear();
		for (int i = 0; i < adapter.mCheckStates.size(); i++) 
		{
			if (adapter.mCheckStates.get(i, false)) 
			{
				SelectedSymtoms model = new SelectedSymtoms();
				model.setSymtom(symptoms.get(i).getmSymtom());
				model.setSymtomHeader(symptoms.get(i).getmBelongsTo());
				symptoms_selected.add(model);
				System.out.println("checked "+ symptoms.get(i).getmSymtom() + " Belongs to "+ symptoms.get(i).getmBelongsTo());
			}
		}
		
		JSONObject jsonObject = new JSONObject();
		
		JSONArray general_symtoms = new JSONArray();
		JSONArray habit_symtoms = new JSONArray();
		JSONArray relationship_symtoms = new JSONArray();
		
		for (int i = 0; i < symptoms_selected.size(); i++) 
		{
			if (symptoms_selected.get(i).getSymtomHeader().equalsIgnoreCase("GENERAL")) 
			{
				general_symtoms.put(symptoms_selected.get(i).getSymtom());

				if (!symptoms_selected.get(i).getSymtom().toString().equalsIgnoreCase(""))
				{
					if (PsychologyData.generalSymptoms.equalsIgnoreCase("") )
					{
						PsychologyData.generalSymptoms = symptoms_selected.get(i).getSymtom() + ",";
					} else
					{
						PsychologyData.generalSymptoms = PsychologyData.generalSymptoms + symptoms_selected.get(i).getSymtom() + ",";
					}
				}


			}
			if (symptoms_selected.get(i).getSymtomHeader().equalsIgnoreCase("HABIT CHANGE")) 
			{
				habit_symtoms.put(symptoms_selected.get(i).getSymtom());
				if (!symptoms_selected.get(i).getSymtom().toString().equalsIgnoreCase(""))
				{
					if (PsychologyData.habitSymptoms.equalsIgnoreCase("") )
					{
						PsychologyData.habitSymptoms = symptoms_selected.get(i).getSymtom() + ",";
					} else
					{
						PsychologyData.habitSymptoms = PsychologyData.habitSymptoms + symptoms_selected.get(i).getSymtom() + ",";
					}
				}

			}

			if (symptoms_selected.get(i).getSymtomHeader().equalsIgnoreCase("RELATIONSHIP ISSUES")) 
			{
				relationship_symtoms.put(symptoms_selected.get(i).getSymtom());

				if (!symptoms_selected.get(i).getSymtom().toString().equalsIgnoreCase(""))
				{
					if (PsychologyData.relationshipSymptoms.equalsIgnoreCase("") )
					{
						PsychologyData.relationshipSymptoms = symptoms_selected.get(i).getSymtom() + ",";
					} else
					{
						PsychologyData.relationshipSymptoms = PsychologyData.relationshipSymptoms + symptoms_selected.get(i).getSymtom() + ",";
					}
				}
			}
		}
		
		try 
		{
			if (general_symtoms.length() > 0) 
			{
				jsonObject.put("general-symptoms",general_symtoms);
			}
			if (habit_symtoms.length() > 0) 
			{
				jsonObject.put("headnneck", habit_symtoms);
			}
			if (relationship_symtoms.length() > 0) 
			{
				jsonObject.put("symptoms_chest",relationship_symtoms);
			}
			
			
		} 
		catch (JSONException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		PsychologyData.symtopms_json_list = jsonObject.toString();
		System.out.println("Selected PsychologyData.relationshipSymptoms ==> "+PsychologyData.relationshipSymptoms);
		System.out.println("Selected PsychologyData.habitSymptoms ==> "+PsychologyData.habitSymptoms);
		System.out.println("Selected PsychologyData.generalSymptoms ==> "+PsychologyData.generalSymptoms);
	}
	@Override
	protected void onResume()
	{
		System.out.println("i am in onrewume");
		super.onResume();
		PsychologyData.generalSymptoms="";
		PsychologyData.habitSymptoms="";
		PsychologyData.relationshipSymptoms="";
	}
}
