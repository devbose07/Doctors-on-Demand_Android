package com.globussoft.readydoctors.doctor.adapters;

/**
 * Created by GLB-217 on 10/9/2015.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.globussoft.readydoctors.doctor.R;
import com.globussoft.readydoctors.doctor.models.PaymentModel;

import java.util.ArrayList;

public class PaymentAdapter extends BaseAdapter {
    Context context;
    ArrayList<PaymentModel> list;

    public PaymentAdapter(Context context, ArrayList<PaymentModel> list) {
        super();
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
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PaymentModel temp = list.get(position);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.payment_list_item, parent, false);


        TextView price = (TextView) convertView.findViewById(R.id.price);
        price.setText(temp.getAmount());
        TextView timings = (TextView) convertView.findViewById(R.id.date);
        timings.setText(temp.getPaymentTime());

        return convertView;
    }

}
