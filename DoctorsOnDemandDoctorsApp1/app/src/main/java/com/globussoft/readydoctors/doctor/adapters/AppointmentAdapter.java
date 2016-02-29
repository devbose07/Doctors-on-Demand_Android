package com.globussoft.readydoctors.doctor.adapters;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


import com.globussoft.readydoctors.doctor.R;
import com.globussoft.readydoctors.doctor.models.AppointmentsModel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AppointmentAdapter extends BaseAdapter
{
	Context context;
	public AppointmentAdapter(Context context, ArrayList<AppointmentsModel> appList) 
	{
		super();
		this.context = context;
		this.appList = appList;
	}

	ArrayList<AppointmentsModel> appList;
	@Override
	public int getCount() 
	{
		
		return appList.size();
	}

	@Override
	public Object getItem(int position) 
	{
		
		return appList.get(position);
	}

	@Override
	public long getItemId(int position) 
	{
		
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		
		AppointmentsModel temp=appList.get(position);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = inflater.inflate(R.layout.appointment_item, parent,false);
		TextView name=(TextView)convertView.findViewById(R.id.drName);
		name.setText(temp.getPatientFirstName()+" "+temp.getPatientLastName());
		TextView timings=(TextView)convertView.findViewById(R.id.timings);
	try
	{
		timings.setText(""+ConvertUtcToLocal(temp.getAppointment_start_time())+" - "+ConvertUtcToLocal(temp.getAppointment_end_time()));
	}
	catch(ParseException e)
	{

	}
//		timings.setText(""+temp.getAppointment_start_time()+" - "+temp.getAppointment_end_time());
		
		
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

		System.out.print("finalLocalDateString   " + finalLocalDateString);

		return finalLocalDateString;
	}

}
