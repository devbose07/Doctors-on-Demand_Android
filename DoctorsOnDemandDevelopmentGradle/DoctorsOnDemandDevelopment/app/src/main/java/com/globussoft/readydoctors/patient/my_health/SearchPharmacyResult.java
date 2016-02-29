package com.globussoft.readydoctors.patient.my_health;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.globussoft.readydoctors.patient.adapter.SearchPharmacyAdapter;
import com.globussoft.readydoctors.patient.model.PharmacyModels;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by GLB-276 on 10/31/2015.
 */
public class SearchPharmacyResult extends Activity
{
    ImageView backImage;

    public static ArrayList<PharmacyModels> searchList=new ArrayList<PharmacyModels>();

    ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_pharmacy_result);
        initUI();
    }
    public void initUI()
    {
        backImage=(ImageView)findViewById(R.id.backImage);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        System.out.println(searchList.size()+" items retrieved");
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(new SearchPharmacyAdapter(getApplicationContext(), searchList));
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if(new ConnectionDetector(getApplicationContext()).isConnectingToInternet())
                {
                    AddToFavoritePharmacy(searchList.get(position).getPharmacyId(), Singleton.PatientID);
                }
                else
                {
                    Toast.makeText(SearchPharmacyResult.this, "You dont have Internet Connection....!", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        /*Intent intent = new Intent(getApplicationContext(),MeetUs.class);
        startActivity(intent);
        MyHealth.this.finish();*/
    }
    public void AddToFavoritePharmacy(final String pharmacyId,final String userId)
    {
        System.out.println("pharmacyId "+pharmacyId+" userId "+userId);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest sr = new StringRequest(Request.Method.POST,
                ConstantUrls.UrlMain + ConstantUrls.UrlAddFavoritePharmacy,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        System.out.println("searching pharmacy response" + response);
                        try
                        {
                            JSONObject json = new JSONObject(response);


                            if (json.getString("code").equals("200"))
                            {
                                Toast.makeText(SearchPharmacyResult.this, json.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(SearchPharmacyResult.this, json.getString("message"), Toast.LENGTH_SHORT).show();
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
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("pharmacyId", pharmacyId);
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