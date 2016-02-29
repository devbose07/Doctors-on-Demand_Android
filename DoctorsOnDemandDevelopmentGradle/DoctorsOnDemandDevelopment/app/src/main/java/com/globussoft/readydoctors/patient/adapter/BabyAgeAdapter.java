package com.globussoft.readydoctors.patient.adapter;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.globussoft.readydoctors.patient.R;
import com.globussoft.readydoctors.patient.model.SymtomsModel;

public class BabyAgeAdapter extends BaseAdapter implements CompoundButton.OnCheckedChangeListener
{
	Context context;

	String[] babyAges;
	private LayoutInflater inflater;
	public SparseBooleanArray mCheckStates;
	SymtomsModel model ;

	public BabyAgeAdapter(Context context,String[] babyAges) 
	{
		this.context = context;
		this.babyAges = babyAges;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mCheckStates = new SparseBooleanArray(babyAges.length);
		for (int i = 0; i < babyAges.length; i++) 
		{
			setChecked(i, false);
		}

	}

	@Override
	public int getCount()
	{
		// TODO Auto-generated method stub
		return babyAges.length;
	}

	@Override
	public Object getItem(int position)
	{
		// TODO Auto-generated method stub
		return babyAges[position];
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

		String babyage=babyAges[position];
		if (convertView == null) 
		{
				convertView = inflater.inflate(R.layout.symptoms_listitem, parent,false);
				holder.textView = (TextView) convertView.findViewById(R.id.time);
				holder.check = (CheckBox) convertView.findViewById(R.id.checkbox);
				holder.check.setTag(position);
				holder.check.setChecked(mCheckStates.get(position, false));
				holder.check.setOnCheckedChangeListener(this);
				holder.textView.setText(babyage);
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
