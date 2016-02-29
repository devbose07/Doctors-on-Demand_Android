package com.globussoft.readydoctors.doctor.adapters;

import java.util.ArrayList;

import com.globussoft.readydoctors.doctor.R;
import com.globussoft.readydoctors.doctor.models.RatingModel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

public class RatingAdapter extends BaseAdapter {
	Context context;
	ArrayList<RatingModel> ratingList;

	public RatingAdapter(Context context, ArrayList<RatingModel> ratingList) {
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
		return ratingList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		RatingModel temp = ratingList.get(position);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = inflater.inflate(R.layout.rating_item, parent, false);
		RatingBar ratingbar = (RatingBar) convertView.findViewById(R.id.rating);
		ratingbar.setRating(temp.getRating());
		TextView name = (TextView) convertView.findViewById(R.id.byName);
		name.setText(temp.getByName());
		TextView timings = (TextView) convertView.findViewById(R.id.comment);
		timings.setText(temp.getComment());

		return convertView;
	}

}
