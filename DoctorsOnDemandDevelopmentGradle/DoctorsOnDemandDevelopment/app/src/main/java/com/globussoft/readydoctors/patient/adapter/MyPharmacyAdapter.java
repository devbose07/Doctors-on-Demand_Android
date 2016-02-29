package com.globussoft.readydoctors.patient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.globussoft.readydoctors.patient.R;
import com.globussoft.readydoctors.patient.Utills.ConnectionDetector;
import com.globussoft.readydoctors.patient.Utills.ConstantUrls;
import com.globussoft.readydoctors.patient.Utills.Singleton;
import com.globussoft.readydoctors.patient.imagelib.ImageLoader;
import com.globussoft.readydoctors.patient.model.PharmacyModels;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by GLB-276 on 11/2/2015.
 */
public class MyPharmacyAdapter extends BaseAdapter
{
    Context context;
    ArrayList<PharmacyModels> pharmaciesList;
    ImageLoader imageLoader;
    PharmacyModels pharmacieModel;

    public MyPharmacyAdapter(Context context,ArrayList<PharmacyModels> psychologistsList)
    {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        pharmacieModel = pharmaciesList.get(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.my_pharmacy_item, parent,
                    false);
        }


        Button delete=(Button)convertView.findViewById(R.id.delete);
        if(Singleton.editPharmacyStatus)
            delete.setVisibility(View.VISIBLE);
        else
            delete.setVisibility(View.INVISIBLE);
        delete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(new ConnectionDetector(context).isConnectingToInternet())
                {
                    deletePharmacy(pharmaciesList.get(position).getFavouritePharmacyId(), position);
                }
                else
                {
                    Toast.makeText(context, "You dont have Internet Connection....!", Toast.LENGTH_SHORT).show();
                }

                System.out.println("pharmacieModel.getFavouritePharmacyId() "+pharmaciesList.get(position).getFavouritePharmacyId());
            }
        });

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
    public void deletePharmacy(final String favoriteId,final int position)
    {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest sr = new StringRequest(Request.Method.POST,
                ConstantUrls.UrlMain + ConstantUrls.UrlDeleteFavoritePharmacy,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        System.out.println("delete pharmacy response" + response+" id "+favoriteId);
                        try
                        {
                            JSONObject json = new JSONObject(response);
                            if (json.getString("code").equals("200"))
                            {
                                pharmaciesList.remove(position);
//                                MyPharmacies.favoriteList.remove(position);
                                Toast.makeText(context,"Deleted",Toast.LENGTH_SHORT).show();
                                notifyDataSetChanged();
                            }
                            else
                            {
                                Toast.makeText(context,json.getString("message") , Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("favPharmacyId", favoriteId);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(sr);
    }


}
