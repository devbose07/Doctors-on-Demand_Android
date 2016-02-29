package com.globussoft.readydoctors.patient.pediatrics;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
import com.globussoft.readydoctors.patient.adapter.ChildAdapter;
import com.globussoft.readydoctors.patient.model.ChildModel;
import com.globussoft.readydoctors.patient.see_a_doctor_now.SeeDoctorPharmacy;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by GLB-217 on 10/6/2015.
 */

public class Child extends Activity
{
    private String tag_json_obj = "jobj_req";
    ChildAdapter adapter;
    RelativeLayout add_new_btn;
    ListView list;
    ProgressBar progress;
    ArrayList<ChildModel> arraylist = new ArrayList<ChildModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.child);
        InitView();
    }

    void InitView()
    {
        list = (ListView) findViewById(R.id.list);
        add_new_btn = (RelativeLayout) findViewById(R.id.add_new);
        progress= (ProgressBar) findViewById(R.id.progress);
        if(new ConnectionDetector(getApplicationContext()).isConnectingToInternet())
        {
            FetchChild(Singleton.PatientID);
        }
        else
        {
            Toast.makeText(Child.this, "You dont have Internet Connection....!", Toast.LENGTH_SHORT).show();
        }

        add_new_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddChild.class);
                startActivity(intent);
            }
        });
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {

                PediatricData.childId=arraylist.get(position).getId();

				Intent intent = new Intent(getApplicationContext(),SeeDoctorPharmacy.class);
				startActivity(intent);


            }
        });



    }

    public void FetchChild(final String pateintid)
    {
        progress.setVisibility(View.VISIBLE);

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest sr = new StringRequest(Request.Method.POST,ConstantUrls.UrlMain+ConstantUrls.UrlFetchChild,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        System.out.println("child response" + response);
                        try {
                            JSONObject json = new JSONObject(response);
                            if (json.getString("code").equals("200")) {

                                JSONArray array = json.getJSONArray("data");

                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject obj = array.getJSONObject(i);
                                    ChildModel model = new ChildModel();
                                    model.setId(obj.getString("id"));
                                    model.setFirstName(obj.getString("firstName"));
                                    model.setLastName(obj.getString("lastName"));
                                    arraylist.add(model);
                                }

                            }
                            adapter = new ChildAdapter(getApplicationContext(), arraylist);
                            list.setAdapter(adapter);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        progress.setVisibility(View.INVISIBLE);
                    }
                }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                progress.setVisibility(View.INVISIBLE);
            }
        }) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("patient_id", pateintid);


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

