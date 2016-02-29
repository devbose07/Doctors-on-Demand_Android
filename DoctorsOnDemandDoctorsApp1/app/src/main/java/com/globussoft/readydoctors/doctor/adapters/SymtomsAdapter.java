package com.globussoft.readydoctors.doctor.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.globussoft.readydoctors.doctor.R;
import com.globussoft.readydoctors.doctor.models.SymtomsModel;

import java.util.ArrayList;

public class SymtomsAdapter extends BaseAdapter {
    Context context;
    ArrayList<SymtomsModel> List;

    public SymtomsAdapter(Context context, ArrayList<SymtomsModel> List) {
        super();
        this.context = context;
        this.List = List;
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
        SymtomsModel temp = List.get(position);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (temp.getType() != null) {
            convertView = inflater.inflate(R.layout.symtoms_header_list_item, parent, false);
            if (temp.getType().equals("1")) {
                TextView header = (TextView) convertView.findViewById(R.id.header);
                header.setText("General Symptoms");
            } else if (temp.getType().equals("2")) {
                TextView header = (TextView) convertView.findViewById(R.id.header);
                header.setText("Relationship Symptoms");
            }
        } else {
            convertView = inflater.inflate(R.layout.symtoms_content_list_item, parent, false);
            TextView header = (TextView) convertView.findViewById(R.id.symtoms_content);
            header.setText(temp.getName());
        }


        return convertView;
    }

}
