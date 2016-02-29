package com.globussoft.readydoctors.patient.adapter;

import java.util.ArrayList;

import com.globussoft.readydoctors.patient.R;
import com.globussoft.readydoctors.patient.model.MedicalConditionModel;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

public class MedicalConditionAdapter extends BaseAdapter implements CompoundButton.OnCheckedChangeListener
{
	Context context;
	ArrayList<MedicalConditionModel> conditionsList;
	private LayoutInflater inflater;
	public SparseBooleanArray mCheckStates;
	MedicalConditionModel model ;
	public MedicalConditionAdapter(Context context,ArrayList<MedicalConditionModel> conditionsList) 
	{
		this.context = context;
		this.conditionsList = conditionsList;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mCheckStates = new SparseBooleanArray(conditionsList.size());
		for (int i = 0; i < conditionsList.size(); i++) 
		{
			setChecked(i, false);
		}
	}

	@Override
	public int getCount() 
	{
		// TODO Auto-generated method stub
		return conditionsList.size();
	}

	@Override
	public Object getItem(int position) 
	{
		// TODO Auto-generated method stub
		return conditionsList.get(position);
	}

	@Override
	public long getItemId(int position) 
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		
		ViewHolder holder = new ViewHolder();;

		model=conditionsList.get(position);
			if(model.isHeader())
			{
				convertView = inflater.inflate(R.layout.symptoms_header_listitem, parent,false);
				holder.textView = (TextView) convertView.findViewById(R.id.time);
				holder.textView.setText(model.getCondition());
			}
			else
			{
				convertView = inflater.inflate(R.layout.symptoms_listitem, parent,false);
				holder.textView = (TextView) convertView.findViewById(R.id.time);
				holder.check = (CheckBox) convertView.findViewById(R.id.checkbox);
				holder.check.setTag(position);
				holder.check.setChecked(mCheckStates.get(position, false));
				holder.check.setOnCheckedChangeListener(this);
				holder.textView.setText(model.getCondition());
			}
		return convertView;
	}
	public static class ViewHolder 
	{
		public TextView textView;
		public CheckBox check;
	}
	public boolean isChecked(int position) 
	{
		return mCheckStates.get(position, false);
	}

	public void setChecked(int position, boolean isChecked) 
	{
		mCheckStates.put(position, isChecked);
	}

	public void toggle(int position)
	{
		setChecked(position, !isChecked(position));
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) 
	{
		mCheckStates.put((Integer) buttonView.getTag(), isChecked);

		System.out.println("isChecked " + isChecked);
	}
}
