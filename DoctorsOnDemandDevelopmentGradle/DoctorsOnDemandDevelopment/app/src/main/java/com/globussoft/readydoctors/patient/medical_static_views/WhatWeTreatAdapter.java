package com.globussoft.readydoctors.patient.medical_static_views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.globussoft.readydoctors.patient.R;

/**
 * Created by GLB-276 on 10/10/2015.
 */
public class WhatWeTreatAdapter extends BaseAdapter
{
    String[] list;
    Context context;
    public WhatWeTreatAdapter(String[] list,Context context)
    {
        this.list=list;
        this.context=context;
    }
    @Override
    public int getCount()
    {
        return list.length;
    }

    @Override
    public Object getItem(int position)
    {
        return list[position];
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.what_do_we_treat_item, parent,false);
        TextView item=(TextView)convertView.findViewById(R.id.item);
        item.setText(list[position]);
        return convertView;
    }
}
