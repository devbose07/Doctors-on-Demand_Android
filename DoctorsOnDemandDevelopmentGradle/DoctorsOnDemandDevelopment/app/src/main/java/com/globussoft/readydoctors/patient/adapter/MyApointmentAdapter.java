package com.globussoft.readydoctors.patient.adapter;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.globussoft.readydoctors.patient.R;
import com.globussoft.readydoctors.patient.model.AppointmentsModel;

public class MyApointmentAdapter extends BaseAdapter
{
	Context context;
	AppointmentsModel model;
	ArrayList<AppointmentsModel> array;

	public MyApointmentAdapter(Context context, ArrayList<AppointmentsModel> array)
	{
		this.context = context;
		this.array = array;
	}

	@Override
	public int getCount()
	{

		return array.size();
	}

	@Override
	public Object getItem(int position)
	{
		// TODO Auto-generated method stub
		return null;
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
		model=array.get(position);
		TextView time, name, dept;
		if (convertView == null)
		{

			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.myappoinment_list_item,
					parent, false);

		}

		time = (TextView) convertView.findViewById(R.id.time);
		name = (TextView) convertView.findViewById(R.id.name);
		dept = (TextView) convertView.findViewById(R.id.dept);
		name.setText("Dr."+model.getFirstName()+" "+model.getLastName());
		try
		{
			time.setText(""+ConvertUtcToLocal(model.getAppointment_start_time())+"   To   "+""+ConvertUtcToLocal(model.getAppointment_end_time()));
		}
		catch (ParseException e)
		{
			System.out.println("gone......>"+e);
		}

		switch(Integer.parseInt(model.getAppointmentStatus().toString()))
		{
			case 0:
				dept.setText("Rejected");
				dept.setTextColor(context.getResources().getColor(R.color.blue_krayola));
				break;
			case 1:
				dept.setText("Accepted");
				dept.setTextColor(context.getResources().getColor(R.color.green));
				break;
			/*case 2:
				dept.setText("Rejected");
				dept.setTextColor(context.getResources().getColor(R.color.red));
				break;
			case 3:
				dept.setText("Cancelled");
				dept.setTextColor(context.getResources().getColor(R.color.red));
				break;
			case 4:
				dept.setText("Completed");
				dept.setTextColor(context.getResources().getColor(R.color.red));
				break;*/
			default:

		}
		return convertView;
	}

	public String ConvertUtcToLocal(String originalUTCTime) throws ParseException
	{
		System.out.print("Time in UTC <"+ originalUTCTime+">");

		String format = "yyyy-MM-dd HH:mm:ss";

		SimpleDateFormat sdf = new SimpleDateFormat(format);

		Date date = sdf.parse(originalUTCTime.trim());

		long utcStamp = date.getTime();

		// Add/Substract Zone offset into UTC time
		long localTimeStamp = utcStamp + Calendar.getInstance().get(Calendar.ZONE_OFFSET);

		Timestamp timestamp = new Timestamp(localTimeStamp);

		Date finalLocatDate = new Date(timestamp.getTime());

		String finalLocalDateString = sdf.format(finalLocatDate);

		System.out.print("finalLocalDateString   "+finalLocalDateString);

		return finalLocalDateString;
	}

}
