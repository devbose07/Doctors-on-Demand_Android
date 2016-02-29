package com.globussoft.readydoctors.patient.my_health;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
import com.globussoft.readydoctors.patient.adapter.MyPharmacyAdapter;
import com.globussoft.readydoctors.patient.adapter.PharmacyAdapter;
import com.globussoft.readydoctors.patient.model.PharmacyModels;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by GLB-276 on 10/30/2015.
 */
public class MyPharmacies extends Activity
{
    ImageView backImage;
    RelativeLayout add_pharmacy,messages,my_favorite,my_pharmacies;
    ProgressBar progressBar;
    ListView list;
    TextView next,noPharmacies;
    public static ArrayList<PharmacyModels> favoriteList=new ArrayList<PharmacyModels>();
    PharmacyAdapter adapter;
    private String tag_json_obj = "jobj_req";



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_pharmacies);
        initUI();
    }
    public void setEditStatus(boolean value)
    {
        if(value)
        {
            next.setText("Done");
        }
        else
        {
            next.setText("Edit");
            favoriteList.clear();
            if(new ConnectionDetector(MyPharmacies.this).isConnectingToInternet())
            {
                    FetchPharmacyList(Singleton.PatientID);
            }
            else
            {
                Toast.makeText(MyPharmacies.this, "You dont have Internet Connection....!", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void initUI()
    {
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        add_pharmacy=(RelativeLayout)findViewById(R.id.add_pharmacy);
        add_pharmacy.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                    Intent intent = new Intent(getApplicationContext(), SearchPharmacy.class);
                    startActivity(intent);
                    MyPharmacies.this.finish();
            }
        });
        backImage=(ImageView)findViewById(R.id.backImage);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        list = (ListView) findViewById(R.id.list);
        next = (TextView) findViewById(R.id.next);
        noPharmacies= (TextView) findViewById(R.id.no_pharmacy);
        Singleton.editPharmacyStatus=false;
        setEditStatus(Singleton.editPharmacyStatus);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Singleton.editPharmacyStatus) {
                    setEditStatus(Singleton.editPharmacyStatus = false);
                    if (favoriteList.size() > 0) {
                        noPharmacies.setVisibility(View.INVISIBLE);
                        list.setAdapter(new MyPharmacyAdapter(getApplicationContext(), favoriteList));
                    } else
                    {
                        noPharmacies.setVisibility(View.VISIBLE);
                    }

                } else {
                    setEditStatus(Singleton.editPharmacyStatus = true);
                    if (favoriteList.size() > 0) {
                        noPharmacies.setVisibility(View.INVISIBLE);
                        list.setAdapter(new MyPharmacyAdapter(getApplicationContext(), favoriteList));
                    } else
                    {
                        noPharmacies.setVisibility(View.VISIBLE);
                    }
                }

            }
        });
        if(new ConnectionDetector(getApplicationContext()).isConnectingToInternet())
        {
            FetchPharmacyList(Singleton.PatientID);
        }
        else
        {
            Toast.makeText(MyPharmacies.this, "You dont have Internet Connection....!", Toast.LENGTH_SHORT).show();
        }

        list.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {


            }
        });
    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();

    }


    public void FetchPharmacyList(final String userId)
    {
        progressBar.setVisibility(View.VISIBLE);

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest sr = new StringRequest(Request.Method.POST,
                ConstantUrls.UrlMain + ConstantUrls.UrlGetFavoritePharmacies,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {

                        System.out.println("favorite pharmacy response" + response);
                        try
                        {

                            JSONObject json = new JSONObject(response);
                            if (json.getString("code").equals("200"))
                            {
                                JSONArray array = json.getJSONArray("data");
                                favoriteList.clear();
                                for (int i=0;i<array.length();i++)
                                {
                                    PharmacyModels model=new PharmacyModels();
                                    JSONObject obj = array.getJSONObject(i);

                                    model.setFavouritePharmacyId(obj.getString("favouritePharmacyId"));
                                    model.setPharmacyId(obj.getString("pharmacyId"));
                                    model.setPharmacyName(obj.getString("pharmacyName"));
                                    model.setAvailabilty(obj.getString("availabilty"));
                                    model.setCity(obj.getString("city"));
                                    model.setCountry(obj.getString("country"));
                                    model.setLongTermCare(obj.getString("longTermCare"));
                                    model.setMailOrder(obj.getString("mailOrder"));
                                    model.setPhoneNumber(obj.getString("phoneNumber"));
                                    model.setState(obj.getString("state"));
                                    model.setSpeciality(obj.getString("speciality"));
                                    model.setZipcode(obj.getString("zipcode"));
                                    model.setTimeAdded(obj.getString("timeAdded"));
                                    favoriteList.add(model);
                                }
                                if(favoriteList.size()>0)
                                {
                                    noPharmacies.setVisibility(View.INVISIBLE);
                                    progressBar.setVisibility(View.INVISIBLE);
                                    next.setVisibility(View.VISIBLE);
                                    list.setAdapter(new MyPharmacyAdapter(getApplicationContext(), favoriteList));
                                }
                                else
                                {
                                    noPharmacies.setVisibility(View.VISIBLE);
                                    progressBar.setVisibility(View.INVISIBLE);
                                    next.setVisibility(View.GONE);
                                    Toast.makeText(MyPharmacies.this, json.getString("message"), Toast.LENGTH_SHORT).show();
                                }
                            }
                            else
                            {
                                noPharmacies.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.INVISIBLE);
                                next.setVisibility(View.GONE);
                                Toast.makeText(MyPharmacies.this,json.getString("message") , Toast.LENGTH_SHORT).show();
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
                progressBar.setVisibility(View.INVISIBLE);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("userId", userId);
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