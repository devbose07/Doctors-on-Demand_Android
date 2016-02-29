package com.globussoft.readydoctors.patient.pregnancy_newborns;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.globussoft.readydoctors.patient.R;
import com.globussoft.readydoctors.patient.adapter.BabyAgeAdapter;

public class BreastFeeding extends Activity 
{

	TextView next,question,title;
	ListView list;
	EditText editbox;
	BabyAgeAdapter adapter;
	String[] breast_feeding={"Few days","Less than 2 weeks","Couple of months","3-6 months","More than 6 months"};
	 int selectedPosition = 0;

	ImageView backImage;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.provide_baby_age);
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
		title=(TextView)findViewById(R.id.edittext);
		title.setText("BREASTFEEDING");
		list = (ListView) findViewById(R.id.list);
		next = (TextView) findViewById(R.id.next);
		question=(TextView) findViewById(R.id.textView1);
		question.setText("How long did you exclusively breastfeed that child/those children ?");
		getSymtoms();
		
		next.setOnClickListener(new OnClickListener() 
		{

			@Override
			public void onClick(View v) 
			{
				LactationData.breastFedDuration=breast_feeding[selectedPosition];
				System.out.println("Selected age is "+breast_feeding[selectedPosition]);
				Intent intent = new Intent(getApplicationContext(),LactationMedications.class);
				startActivity(intent);

			}
		});
	}
	
	public void getSymtoms()
	{
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.baby_age_item, R.id.time, breast_feeding) 
		{	            
	            @Override
	            public View getView(final int position, View convertView, ViewGroup parent) 
	            {
	                View v = convertView;
	                if (v == null) 
	                {
	                    LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	                    v = vi.inflate(R.layout.baby_age_item, null);
	                    RadioButton r = (RadioButton)v.findViewById(R.id.radio);
	                }
	                TextView tv = (TextView)v.findViewById(R.id.time);
	                tv.setText(breast_feeding[position]);
	                RadioButton r = (RadioButton)v.findViewById(R.id.radio);
	                r.setChecked(position == selectedPosition);
	                r.setTag(position);
	                r.setOnClickListener(new View.OnClickListener() 
	                {
	                    @Override
	                    public void onClick(View view) 
	                    {
	                        selectedPosition = (Integer)view.getTag();
	                        notifyDataSetChanged();
	                        
	                    }
	                });
	                return v;
	            }

	        };
	        list.setAdapter(adapter);
	}
}
