package com.globussoft.readydoctors.patient.psychological_exercises;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.globussoft.readydoctors.patient.R;
import com.globussoft.readydoctors.patient.Utills.AppData;
import com.globussoft.readydoctors.patient.psychological.PsychologyData;
import com.globussoft.readydoctors.patient.activity.SignUp;
import com.globussoft.readydoctors.patient.model.ExerciseModel;
import com.globussoft.readydoctors.patient.see_a_doctor_now.SeeDoctorPharmacy;

public class Exercise1G extends Activity 
{
	RelativeLayout ans1,ans2,ans3,ans4;
	TextView header,question;
	AlertDialog alert,alert2;
	ImageView backImage;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.psychology_exercise_1a);
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
		header=(TextView)findViewById(R.id.edittext);
		header.setText("EXERSISE 1G");
		question=(TextView)findViewById(R.id.question);
		question.setText(AppData.exercise_1g);
		
		ans1=(RelativeLayout)findViewById(R.id.q1_Rlt);
		ans2=(RelativeLayout)findViewById(R.id.q2_Rlt);
		ans3=(RelativeLayout)findViewById(R.id.q3_Rlt);
		ans4=(RelativeLayout)findViewById(R.id.q4_Rlt);
		ans1.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v) 
			{
				PsychologyData.exercise_1g="Not at all";
				ExerciseModel model=new ExerciseModel();
				model.setQuestion("7");
				model.setAnswer("1");
				PsychologyData.exerciseListType1.add(model);
				ShowDialog1();
			}
		});
		
		ans2.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v) 
			{
				PsychologyData.exercise_1g="Several days";
				ExerciseModel model=new ExerciseModel();
				model.setQuestion("7");
				model.setAnswer("2");
				PsychologyData.exerciseListType1.add(model);
				ShowDialog1();
			}
		});
		
		ans3.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v) 
			{
				PsychologyData.exercise_1g="More than half the day";
				ExerciseModel model=new ExerciseModel();
				model.setQuestion("7");
				model.setAnswer("3");
				PsychologyData.exerciseListType1.add(model);
				ShowDialog1();
			}
		});
		
		ans4.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v) 
			{
				PsychologyData.exercise_1g="Nearly everyday";
				ExerciseModel model=new ExerciseModel();
				model.setQuestion("7");
				model.setAnswer("4");
				PsychologyData.exerciseListType1.add(model);
				ShowDialog1();
			}
		});
		
	}
	public void ShowDialog1()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(Exercise1G.this);
		LayoutInflater inflater = this.getLayoutInflater();
	      /*View view = inflater.inflate(R.layout.categoryfilter_title, null);
	      builder.setCustomTitle(view);*/
		
//	      builder.setTitle("Choose Category");
	      View convertView = (View) inflater.inflate(R.layout.custom_alertdialog, null);
	      TextView title=(TextView) convertView.findViewById(R.id.title);
	      title.setText("GETTING THERE!");//MAXIMIZE YOUR TIME
	      TextView txt=(TextView) convertView.findViewById(R.id.desc);
	      txt.setText("After success message selected Picture and Caption will be posted on Facebook timeline once after schedule and user will get a local ");
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
				Intent intent=new Intent(getApplicationContext(), Exercise2A.class);
				startActivity(intent);
				alert.dismiss();
			}
		});
	}
	public void ShowDialog2()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(Exercise1G.this);
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
	      alert2 = builder.create();
	      alert2.show();
	      letsGo.setOnClickListener(new OnClickListener()
	      {
			
			@Override
			public void onClick(View v) 
			{
				try 
				{
					pickExercise();
				} 
				catch (JSONException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
					alert.dismiss();
				}
				alert2.dismiss();
			}
		});
	      
	     
	}

	protected void pickExercise() throws JSONException 
	{
		JSONObject obj=new JSONObject();
		for (int i = 0; i < PsychologyData.exerciseListType1.size(); i++) 
		{
			//System.out.println("Exercise -"+i+" "+PsychologyData.exerciseListType1.get(i).getQuestion()+" "+PsychologyData.exerciseListType1.get(i).getAnswer());
			obj.put(PsychologyData.exerciseListType1.get(i).getQuestion(), PsychologyData.exerciseListType1.get(i).getAnswer());
			if (!PsychologyData.exerciseListType1.get(i).getAnswer().toString().equalsIgnoreCase(""))
			{
				if (PsychologyData.Exercise1.equalsIgnoreCase("") )
				{
					PsychologyData.Exercise1 = PsychologyData.exerciseListType1.get(i).getAnswer() + ",";
				} else
				{
					PsychologyData.Exercise1 = PsychologyData.Exercise1 + PsychologyData.exerciseListType1.get(i).getAnswer() + ",";
				}
			}
			System.out.println(obj);
		}
		
		PsychologyData.psyExerciseObj.put("type1", obj);
		System.out.println("PsychologyData.Exercise1 "+PsychologyData.Exercise1);
//		PsychologyData.psyExercise=PsychologyData.psyExerciseObj.toString();
		
	}
}
