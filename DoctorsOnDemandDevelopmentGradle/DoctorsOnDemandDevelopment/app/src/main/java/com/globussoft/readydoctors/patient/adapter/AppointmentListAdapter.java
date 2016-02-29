package com.globussoft.readydoctors.patient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.globussoft.readydoctors.patient.R;
import com.globussoft.readydoctors.patient.model.AppointmentListModel;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by GLB-276 on 12/24/2015.
 */
public class AppointmentListAdapter extends BaseAdapter {
    Context context;
    AppointmentListModel model;
    ArrayList<AppointmentListModel> list;

    public AppointmentListAdapter(Context context,ArrayList<AppointmentListModel> list)
    {
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
        if (!model.isHeader())
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.calender_list_item, parent,false);
            convertView.setTag(Integer.valueOf(position));
            TextView time = (TextView) convertView.findViewById(R.id.time);
            time.setText(model.getTime()+"-"+model.getTimelimit());
        }
        else
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.appointment_list_date_item, parent,false);
            convertView.setTag(Integer.valueOf(position));

            Calendar cal = Calendar.getInstance();
            cal.setTime(model.getDate());
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);
            int year = cal.get(Calendar.YEAR);
            int Hrs = cal.get(Calendar.HOUR);
            int Hrs2 = cal.get(Calendar.HOUR_OF_DAY);
            int Minutes = cal.get(Calendar.MINUTE);
            System.out.println("Day " + day + "-" + month + "-" + year);
            TextView time = (TextView) convertView.findViewById(R.id.date);
            time.setText(day + "-" + month + "-" + year);
        }
        return convertView;
    }

}

