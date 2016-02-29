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
import com.globussoft.readydoctors.patient.model.ExerciseModel;
import com.globussoft.readydoctors.patient.see_a_doctor_now.SeeDoctorPharmacy;

public class Exercise2I extends Activity 
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
		ImageView backImage;
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
		header.setText("EXERSISE 2I");
		question=(TextView)findViewById(R.id.question);
		question.setText(AppData.exercise_2i);
		
		ans1=(RelativeLayout)findViewById(R.id.q1_Rlt);
		ans2=(RelativeLayout)findViewById(R.id.q2_Rlt);
		ans3=(RelativeLayout)findViewById(R.id.q3_Rlt);
		ans4=(RelativeLayout)findViewById(R.id.q4_Rlt);
		ans1.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v) 
			{
				PsychologyData.exercise_2i="Not at all";
				ExerciseModel model=new ExerciseModel();
				model.setQuestion("9");
				model.setAnswer("1");
				PsychologyData.exerciseListType2.add(model);
				ShowDialog2();
			}
		});
		
		ans2.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v) 
			{
				PsychologyData.exercise_2i="Several days";
				ExerciseModel model=new ExerciseModel();
				model.setQuestion("9");
				model.setAnswer("2");
				PsychologyData.exerciseListType2.add(model);
				ShowDialog2();
			}
		});
		
		ans3.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v) 
			{
				PsychologyData.exercise_2i="More than half the day";
				ExerciseModel model=new ExerciseModel();
				model.setQuestion("9");
				model.setAnswer("3");
				PsychologyData.exerciseListType2.add(model);
				ShowDialog2();
			}
		});
		
		ans4.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v) 
			{
				PsychologyData.exercise_2i="Nearly everyday";
				ExerciseModel model=new ExerciseModel();
				model.setQuestion("9");
				model.setAnswer("4");
				PsychologyData.exerciseListType2.add(model);
				ShowDialog2();
			}
		});
		
	}
	
	public void ShowDialog2()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(Exercise2I.this);
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

					Intent intent = new Intent(getApplicationContext(), SeeDoctorPharmacy.class);
					startActivity(intent);

				alert2.dismiss();
			}
		});
	      
	     
	}
	protected void pickExercise() throws JSONException 
	{
		
		JSONObject type1=new JSONObject();
		for (int i = 0; i < PsychologyData.exerciseListType1.size(); i++) 
		{
			//System.out.println("Exercise -"+i+" "+PsychologyData.exerciseListType1.get(i).getQuestion()+" "+PsychologyData.exerciseListType1.get(i).getAnswer());
			type1.put(PsychologyData.exerciseListType1.get(i).getQuestion(), PsychologyData.exerciseListType1.get(i).getAnswer());
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
//			System.out.println(obja);
		}
		
		PsychologyData.psyExerciseObj.put("type1", type1);
		System.out.println(PsychologyData.psyExerciseObj);
		
		
		
		JSONObject type2=new JSONObject();
		for (int i = 0; i < PsychologyData.exerciseListType2.size(); i++) 
		{
			System.out.println("Exercise -"+i+" "+PsychologyData.exerciseListType2.get(i).getQuestion()+" "+PsychologyData.exerciseListType2.get(i).getAnswer());
			type2.put(PsychologyData.exerciseListType2.get(i).getQuestion(), PsychologyData.exerciseListType2.get(i).getAnswer());
			if (!PsychologyData.exerciseListType2.get(i).getAnswer().toString().equalsIgnoreCase(""))
			{
				System.out.println("Exercise xxx-"+i+" "+PsychologyData.exerciseListType2.get(i).getQuestion()+" "+PsychologyData.exerciseListType2.get(i).getAnswer());
				if (PsychologyData.Exercise2.equalsIgnoreCase("") )
				{
					PsychologyData.Exercise2 = PsychologyData.exerciseListType2.get(i).getAnswer() + ",";
				} else
				{
					PsychologyData.Exercise2 = PsychologyData.Exercise2 + PsychologyData.exerciseListType2.get(i).getAnswer() + ",";
				}
			}
//			System.out.println(type2);
		}
		
		PsychologyData.psyExerciseObj.put("type2", type2);
		System.out.println(PsychologyData.psyExerciseObj);
		System.out.println("PsychologyData.Exercise1 "+PsychologyData.Exercise1);
		System.out.println("PsychologyData.Exercise2 "+PsychologyData.Exercise2);
		
	}
}




