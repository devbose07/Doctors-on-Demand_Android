package com.globussoft.readydoctors.patient.psychology_static_views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
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
import com.globussoft.readydoctors.patient.adapter.DeptDoctorAdapter;
import com.globussoft.readydoctors.patient.model.DeptDoctorModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by GLB-276 on 10/15/2015.
 */
public class OurPsychologists extends Activity
{
    public ArrayList<DeptDoctorModel> arraylist = new ArrayList<DeptDoctorModel>();
    ListView mListView;
    ProgressBar progress;
    DeptDoctorAdapter adapter;
    public static DeptDoctorModel doctormodel;
    ImageView backImage;
    TextView title;
    ;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.our_doctors);
        initUI();
    }

    private void initUI()
    {
        title=(TextView)findViewById(R.id.edittext);
        title.setText("Featured Psychologists");


        mListView = (ListView) findViewById(R.id.list);
        progress = (ProgressBar) findViewById(R.id.progress);

        backImage=(ImageView)findViewById(R.id.backImage);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        arraylist.clear();

//        DeptDoctorAdapter adapter = new DeptDoctorAdapter(getApplicationContext(), arraylist);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new selectDoctor());
        if(new ConnectionDetector(getApplicationContext()).isConnectingToInternet())
        {
            ShowDoctorList(3 + "");
        }
        else
        {
            Toast.makeText(OurPsychologists.this, "You dont have Internet Connection....!", Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(),MeetThePsychologists.class);
        startActivity(intent);
        OurPsychologists.this.finish();
    }
    private class selectDoctor implements ListView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,long id)
        {
            doctormodel = arraylist.get(position);

            Intent intent = new Intent(getApplicationContext(),ShowPsychologists.class);
            startActivity(intent);
            OurPsychologists.this.finish();

        }
    }

    public void ShowDoctorList(final String departmentId) {
        progress.setVisibility(View.VISIBLE);
        arraylist.clear();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest sr = new StringRequest(Request.Method.POST,
                ConstantUrls.UrlMain + ConstantUrls.UrlShowDeptDoctor,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        System.out.println("show doctor response" + response);
                        try {
                            JSONObject json = new JSONObject(response);
                            if (json.getString("code").equals("200")) {

                                JSONArray array = json.getJSONArray("data");
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject obj = array.getJSONObject(i);
                                    DeptDoctorModel model = new DeptDoctorModel();
                                    model.setDepartmentId(obj
                                            .getString("doctor_id"));
                                    model.setFirstName(obj
                                            .getString("FirstName"));
                                    model.setLastName(obj.getString("LastName"));
                                    model.setDepartmentId(obj
                                            .getString("departmentId"));
                                    model.setDoctorAddress(obj
                                            .getString("doctorAddress"));
                                    model.setDoctorContactNumber(obj
                                            .getString("doctorContactNumber"));
                                    model.setDoctorOnlineStatus(obj
                                            .getString("doctorOnlineStatus"));
                                    model.setDoctorAvailableStatus(obj
                                            .getString("doctorAvailableStatus"));
                                    model.setDocotrSex(obj
                                            .getString("doctorSex"));
                                    model.setDoctorAbout(obj
                                            .getString("doctorAbout"));
                                    model.setDoctorAvailableFrom(obj
                                            .getString("doctorAvailableFrom"));
                                    model.setDoctorAvailableFrom(obj
                                            .getString("doctorAvailableTo"));
                                    model.setDoctorProfilePicUrl(obj
                                            .getString("doctorProfilePicUrl"));
                                    model.setZipCode(obj.getString("zipCode"));
                                    model.setLatitude(obj.getString("latitude"));
                                    model.setLongitude(obj
                                            .getString("longitude"));

                                    arraylist.add(model);
                                }

//                                adapter = new DeptDoctorAdapter(getApplicationContext(), arraylist);
                                mListView.setAdapter(adapter);
                            } else {
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        progress.setVisibility(View.INVISIBLE);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.setVisibility(View.INVISIBLE);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("departmentId", departmentId);
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

