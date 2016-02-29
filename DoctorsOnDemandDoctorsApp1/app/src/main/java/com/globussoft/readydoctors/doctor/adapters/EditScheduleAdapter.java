package com.globussoft.readydoctors.doctor.adapters;

import android.app.TimePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.TimePicker;

import com.globussoft.readydoctors.doctor.R;
import com.globussoft.readydoctors.doctor.models.DoctorSheduleModel;
import com.globussoft.readydoctors.doctor.uttils.ConstantData;

import java.util.ArrayList;

public class EditScheduleAdapter extends BaseAdapter {
	Context context;
	ArrayList<DoctorSheduleModel> ratingList;

	public EditScheduleAdapter(Context context,
			ArrayList<DoctorSheduleModel> ratingList) {
		super();
		this.context = context;
		this.ratingList = ratingList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return ratingList.size();
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		final DoctorSheduleModel temp = ratingList.get(position);
		final TextView starttime;
		final TextView endtime;
		
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.edit_schedule_adapter,
					parent, false);
		}
			TextView day = (TextView) convertView.findViewById(R.id.day);

			day.setText(ConstantData.days[position]);

			starttime = (TextView) convertView.findViewById(R.id.starttime);
			endtime = (TextView) convertView.findViewById(R.id.endtime);
			starttime.setText(temp.getStart_time());

			endtime.setText(temp.getEnd_time());




			starttime.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					TimePickerDialog mTimePicker;
					mTimePicker = new TimePickerDialog(context,
							new TimePickerDialog.OnTimeSetListener() {
								@Override
								public void onTimeSet(TimePicker timePicker,
													  int selectedHour, int selectedMinute) {




									if (selectedHour < 10 || selectedMinute < 10) {
										if (selectedHour < 10 && selectedMinute >= 10) {
											starttime.setText("0" + selectedHour + ":"
													+ selectedMinute + ":00");

											ratingList.get(position).setStart_time("0" + selectedHour + ":"
													+ selectedMinute + ":00");

										} else if (selectedHour >= 10 && selectedMinute < 10) {
											starttime.setText(selectedHour + ":0"
													+ selectedMinute + ":00");

											ratingList.get(position).setStart_time(selectedHour + ":0"
													+ selectedMinute + ":00");
										} else

										{
											starttime.setText("0"+selectedHour + ":0"
													+ selectedMinute + ":00");

											ratingList.get(position).setStart_time("0"+selectedHour + ":0"
													+ selectedMinute + ":00");
										}
									} else {
										starttime.setText(selectedHour + ":"
												+ selectedMinute + ":00");
										ratingList.get(position).setStart_time(selectedHour + ":"
												+ selectedMinute + ":00");
									}


								}
							}, 10, 00, true);// Yes 24 hour time
					mTimePicker.setTitle("Select Time");
					mTimePicker.show();
				}
			});

			endtime.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					TimePickerDialog mTimePicker;
					mTimePicker = new TimePickerDialog(context,
							new TimePickerDialog.OnTimeSetListener() {
								@Override
								public void onTimeSet(TimePicker timePicker,
													  int selectedHour, int selectedMinute) {
									if (selectedHour < 10 || selectedMinute < 10) {
										if (selectedHour < 10 && selectedMinute >= 10) {
											endtime.setText("0" + selectedHour + ":"
													+ selectedMinute + ":00");
											ratingList.get(position).setEnd_time("0" + selectedHour + ":"
													+ selectedMinute + ":00");


										} else if (selectedHour >= 10 && selectedMinute < 10) {
											endtime.setText(selectedHour + ":0"
													+ selectedMinute + ":00");
											ratingList.get(position).setEnd_time(selectedHour + ":0"
													+ selectedMinute + ":00");
										} else

										{
											endtime.setText("0"+selectedHour + ":0"
													+ selectedMinute + ":00");
											ratingList.get(position).setEnd_time("0"+selectedHour + ":0"
													+ selectedMinute + ":00");
										}
									} else {
										endtime.setText(selectedHour + ":"
												+ selectedMinute + ":00");
										ratingList.get(position).setEnd_time(selectedHour + ":"
												+ selectedMinute + ":00");

									}


								}
							}, 18, 00, true);// Yes 24 hour time
					mTimePicker.setTitle("Select Time");
					mTimePicker.show();
				}
			});


		return convertView;
	}
}
