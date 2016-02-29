package com.globussoft.readydoctors.patient.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.globussoft.readydoctors.patient.R;
import com.globussoft.readydoctors.patient.imagelib.ImageLoader;
import com.globussoft.readydoctors.patient.model.PharmacyModel;

public class PharmacyAdapter extends BaseAdapter {
	Context context;
	ArrayList<PharmacyModel> pharmaciesList;
	ImageLoader imageLoader;
	PharmacyModel pharmacieModel;

	public PharmacyAdapter(Context context,
			ArrayList<PharmacyModel> psychologistsList) {
		super();
		this.context = context;
		this.pharmaciesList = psychologistsList;
		imageLoader = new ImageLoader(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return pharmaciesList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return pharmaciesList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		pharmacieModel = pharmaciesList.get(position);
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.pharmaciesitem, parent,
					false);
		}

		TextView name = (TextView) convertView.findViewById(R.id.name);
		name.setText(pharmacieModel.getPharmacyName());

		TextView city = (TextView) convertView.findViewById(R.id.city);
		city.setText("City :"+pharmacieModel.getCity());

		TextView state = (TextView) convertView.findViewById(R.id.state);
		state.setText("State :"+pharmacieModel.getState());

		TextView phoneno = (TextView) convertView.findViewById(R.id.phoneno);
		phoneno.setText("Mob :"+pharmacieModel.getPhoneNumber());

		TextView country = (TextView) convertView.findViewById(R.id.country);
		country.setText("Country :"+pharmacieModel.getCountry());

		return convertView;
	}

}
