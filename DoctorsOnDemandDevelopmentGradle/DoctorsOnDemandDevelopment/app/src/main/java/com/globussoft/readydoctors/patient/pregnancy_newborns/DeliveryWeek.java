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

public class DeliveryWeek extends Activity 
{

	TextView next,question,title;
	ListView list;
	EditText editbox;
	BabyAgeAdapter adapter;
	String[] weeks={"21-33 weeks","34-37","38-40","Greater than 40 weeks"};
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

		list = (ListView) findViewById(R.id.list);
		next = (TextView) findViewById(R.id.next);
		title=(TextView)findViewById(R.id.edittext);
		title.setText("DELIVERY WEEK");

		question=(TextView) findViewById(R.id.textView1);
		question.setText("How many weeks gestution were you when you delivered ?");
		getSymtoms();
		
		next.setOnClickListener(new OnClickListener() 
		{

			@Override
			public void onClick(View v) 
			{
				LactationData.gestutionDuration=weeks[selectedPosition];
				System.out.println("Selected age is "+weeks[selectedPosition]);
				Intent intent = new Intent(getApplicationContext(),BreastfedChildren.class);
				startActivity(intent);

			}
		});
	}
	
	public void getSymtoms()
	{
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.baby_age_item, R.id.time, weeks) 
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
	                tv.setText(weeks[position]);
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
