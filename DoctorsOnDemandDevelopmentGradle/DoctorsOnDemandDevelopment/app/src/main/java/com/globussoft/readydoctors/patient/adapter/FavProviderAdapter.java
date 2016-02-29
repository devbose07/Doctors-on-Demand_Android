package com.globussoft.readydoctors.patient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.globussoft.readydoctors.patient.R;
import com.globussoft.readydoctors.patient.imagelib.ImageLoader;
import com.globussoft.readydoctors.patient.model.FavProviderModel;

import java.util.ArrayList;

/**
 * Created by GLB-276 on 12/21/2015.
 */
public class FavProviderAdapter extends BaseAdapter {
    Context context;
    ArrayList<FavProviderModel> List;
    ImageLoader imageLoader;
    FavProviderModel favProviderModel;

    public FavProviderAdapter(Context context, ArrayList<FavProviderModel> List) {
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
        favProviderModel = List.get(position);
        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.deptdoctor_item, parent,false);
        }
        ImageView profilePic = (ImageView) convertView.findViewById(R.id.profile_pic);
        if(favProviderModel.getDoctorProfilePicUrl().equalsIgnoreCase(""))
        {
            profilePic.setImageResource(R.drawable.head_logo);
        }
        else
        {
            imageLoader.DisplayImage(favProviderModel.getDoctorProfilePicUrl(),profilePic);
        }

        System.out.println("getDoctorProfilePicUrl ........" + favProviderModel.getDoctorProfilePicUrl());

        TextView name = (TextView) convertView.findViewById(R.id.name);
        name.setText("Dr."+favProviderModel.getFirstName()+" "+favProviderModel.getLastName());

        TextView address = (TextView) convertView.findViewById(R.id.address);
        address.setText(favProviderModel.getDoctorAddress());
        TextView biography = (TextView) convertView.findViewById(R.id.biography);
        biography.setText(favProviderModel.getDoctorContactNumber());

        return convertView;
    }
}
