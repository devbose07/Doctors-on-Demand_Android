package com.globussoft.readydoctors.patient.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.globussoft.readydoctors.patient.R;
import com.globussoft.readydoctors.patient.model.MedicationModel;

public class MedicationAdapter extends BaseAdapter {
	Context context;
	MedicationModel model;
	ArrayList<MedicationModel> list;
	String[] spinner_items = { "The past week", "The past month",
			"The past year", "More than a year" };

	public MedicationAdapter(Context context, ArrayList<MedicationModel> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
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
	public View getView(int position, View convertView, ViewGroup parent)
	{
		model = list.get(position);
		if (convertView == null)
		{
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.medication_list_item,parent, false);
		}

		EditText data = (EditText) convertView.findViewById(R.id.data);

		Spinner spinner = (Spinner) convertView.findViewById(R.id.spinner);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item, spinner_items);

		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);

		
		
		
		
		
		return convertView;
	}

}
