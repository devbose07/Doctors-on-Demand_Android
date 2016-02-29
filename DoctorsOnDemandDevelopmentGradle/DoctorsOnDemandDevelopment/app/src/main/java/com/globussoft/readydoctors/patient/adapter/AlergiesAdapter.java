package com.globussoft.readydoctors.patient.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.globussoft.readydoctors.patient.R;
import com.globussoft.readydoctors.patient.model.MedicationModel;

public class AlergiesAdapter extends BaseAdapter {
	Context context;
	MedicationModel model;
	ArrayList<String> array;

	public AlergiesAdapter(Context context,
			ArrayList<String> array) {
		this.context = context;
		this.array = array;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return array.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	
		if (convertView == null) {

			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.allergies_listitem, parent,
					false);
			
			
			
		}
			

			TextView time = (TextView) convertView.findViewById(R.id.time);


		
		return convertView;
	}

}
