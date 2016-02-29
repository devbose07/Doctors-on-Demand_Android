package com.globussoft.readydoctors.patient.pregnancy_newborns;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.globussoft.readydoctors.patient.R;
import com.globussoft.readydoctors.patient.adapter.BabyAgeAdapter;

public class ProvideBabyAge extends Activity 
{

	TextView next;
	ListView list;
	EditText editbox;
	BabyAgeAdapter adapter;
	String[] babyAges={"Less than 1 week","1 week","2 weeks","3 weeks","1 month","2 months","3 months","4 months","5 months","6 months","7 months","8 months","9 months","10 months","11 monthss","12 months","Greater than 12 months "};
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
		list = (ListView) findViewById(R.id.list);
		next = (TextView) findViewById(R.id.next);
		backImage=(ImageView)findViewById(R.id.backImage);
		backImage.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				onBackPressed();
			}
		});
		getSymtoms();
		
		next.setOnClickListener(new OnClickListener() 
		{

			@Override
			public void onClick(View v) 
			{
				LactationData.babyAge=babyAges[selectedPosition];
				System.out.println("Selected age is "+babyAges[selectedPosition]);
				Intent intent = new Intent(getApplicationContext(),DeliveryWeek.class);
				startActivity(intent);

			}
		});
	}
	
	public void getSymtoms()
	{
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.baby_age_item, R.id.time, babyAges) 
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
	                tv.setText(babyAges[position]);
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
