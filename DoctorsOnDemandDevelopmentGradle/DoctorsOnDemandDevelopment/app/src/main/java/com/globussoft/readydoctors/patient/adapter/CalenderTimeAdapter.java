package com.globussoft.readydoctors.patient.adapter;

import java.util.ArrayList;

import com.globussoft.readydoctors.patient.R;
import com.globussoft.readydoctors.patient.model.CalenderTimeModel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CalenderTimeAdapter extends BaseAdapter {
	Context context;
	CalenderTimeModel model;
	ArrayList<CalenderTimeModel> list;

	public CalenderTimeAdapter(Context context,
			ArrayList<CalenderTimeModel> list) {
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
	public View getView(int position, View convertView, ViewGroup parent) {
		model = list.get(position);
		if (convertView == null) {

			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.calender_list_item, parent,
					false);
			convertView.setTag(Integer.valueOf(position));

			TextView time = (TextView) convertView.findViewById(R.id.time);
			time.setText(model.getTime()+"-"+model.getTimelimit());

		}
		return convertView;
	}

}
