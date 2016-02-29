package com.globussoft.readydoctors.patient.adapter;

/**
 * Created by GLB-217 on 10/6/2015.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.globussoft.readydoctors.patient.R;
import com.globussoft.readydoctors.patient.imagelib.ImageLoader;
import com.globussoft.readydoctors.patient.model.ChildModel;

import java.util.ArrayList;

public class ChildAdapter extends BaseAdapter {
    Context context;
    ArrayList<ChildModel> List;
    ImageLoader imageLoader;
    ChildModel childmodel;

    public ChildAdapter(Context context, ArrayList<ChildModel> List) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        childmodel = List.get(position);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.child_list_item, parent,
                    false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.name);
        name.setText(childmodel.getFirstName());


        return convertView;
    }

}
