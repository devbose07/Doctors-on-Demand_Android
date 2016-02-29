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
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.globussoft.readydoctors.patient.R;
import com.globussoft.readydoctors.patient.adapter.BabyAgeAdapter;

public class BreastfedChildren extends Activity 
{

	TextView next,question,title;
	ListView list;
	EditText editbox;
	BabyAgeAdapter adapter;
	String[] children={"0","1","2","3 +"};
	 int selectedPosition = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.provide_baby_age);
		InitUI();
	}
	private void InitUI() 
	{
		title=(TextView)findViewById(R.id.edittext);
		title.setText("BREASTFED CHILDREN");
		list = (ListView) findViewById(R.id.list);
		next = (TextView) findViewById(R.id.next);
		question=(TextView) findViewById(R.id.textView1);
		question.setText("How many children have you breast fed previouly ?");
		getSymtoms();
		
		next.setOnClickListener(new OnClickListener() 
		{

			@Override
			public void onClick(View v) 
			{
				LactationData.breastFedChildNum=children[selectedPosition];
				System.out.println("Selected age is "+children[selectedPosition]);
				Intent intent = new Intent(getApplicationContext(),BreastFeeding.class);
				startActivity(intent);

			}
		});
	}
	
	public void getSymtoms()
	{
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.baby_age_item, R.id.time, children) 
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
	                tv.setText(children[position]);
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
