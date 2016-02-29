package com.globussoft.readydoctors.doctor.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.globussoft.readydoctors.doctor.R;
import com.globussoft.readydoctors.doctor.models.MedicationModel;

import java.util.ArrayList;

public class MedicationAdapter extends BaseAdapter {
    Context context;
    ArrayList<MedicationModel> ratingList;

    public MedicationAdapter(Context context, ArrayList<MedicationModel> ratingList) {
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
        MedicationModel temp = ratingList.get(position);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.medication_item, parent, false);

        TextView name = (TextView) convertView.findViewById(R.id.medication);
        name.setText(temp.getName());
        TextView timings = (TextView) convertView.findViewById(R.id.time);
        timings.setText(temp.getTime());

        return convertView;
    }

}
