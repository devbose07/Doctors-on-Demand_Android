package com.globussoft.readydoctors.doctor.adapters;

import java.util.ArrayList;

import com.globussoft.readydoctors.doctor.R;
import com.globussoft.readydoctors.doctor.models.UpcomingModel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class UpcomingAdapter extends BaseAdapter {

	Context context;
	ArrayList<UpcomingModel> upcomingList;

	public UpcomingAdapter(Context context, ArrayList<UpcomingModel> ratingList) {
		super();
		this.context = context;
		this.upcomingList = ratingList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return upcomingList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return upcomingList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		UpcomingModel temp = upcomingList.get(position);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = inflater.inflate(R.layout.upcoming_item, parent, false);

		TextView name = (TextView) convertView.findViewById(R.id.Name);
		name.setText(temp.getPatientFirstName() + " "
				+ temp.getPatientLastName());
		TextView startTime = (TextView) convertView
				.findViewById(R.id.startTime);
		startTime.setText(temp.getAppointment_start_time());
		TextView endTime = (TextView) convertView.findViewById(R.id.endTime);
		endTime.setText(temp.getAppointment_end_time());

		// date.setText(temp.getEndTime());
		TextView location = (TextView) convertView.findViewById(R.id.location);
		location.setText(temp.getPatientAddress());

		return convertView;
	}
}
