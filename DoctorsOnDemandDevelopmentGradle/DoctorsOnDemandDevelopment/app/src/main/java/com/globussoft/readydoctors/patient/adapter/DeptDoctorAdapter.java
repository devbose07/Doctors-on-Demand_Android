package com.globussoft.readydoctors.patient.adapter;

import java.util.ArrayList;

import com.globussoft.readydoctors.patient.R;
import com.globussoft.readydoctors.patient.imagelib.ImageLoader;

import com.globussoft.readydoctors.patient.model.DoctorModel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DeptDoctorAdapter extends BaseAdapter {
	Context context;
	ArrayList<DoctorModel> List;
	ImageLoader imageLoader;
	DoctorModel deptdoctorModel;

	public DeptDoctorAdapter(Context context, ArrayList<DoctorModel> List) {
		super();
		this.context = context;
		this.List = List;

		imageLoader = new ImageLoader(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return List.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return List.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		deptdoctorModel = List.get(position);
		if (convertView == null)
		{
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.deptdoctor_item, parent,false);
		}
		ImageView profilePic = (ImageView) convertView.findViewById(R.id.profile_pic);
		imageLoader.DisplayImage(deptdoctorModel.getDoctorProfilePicUrl(),profilePic);
		System.out.println("getDoctorProfilePicUrl ........" + deptdoctorModel.getDoctorProfilePicUrl());
		TextView name = (TextView) convertView.findViewById(R.id.name);
		name.setText(deptdoctorModel.getFirstName());

		TextView address = (TextView) convertView.findViewById(R.id.address);
		address.setText(deptdoctorModel.getDoctorAddress());
		TextView biography = (TextView) convertView.findViewById(R.id.biography);
		biography.setText(deptdoctorModel.getDoctorAbout());

		return convertView;
	}
}
