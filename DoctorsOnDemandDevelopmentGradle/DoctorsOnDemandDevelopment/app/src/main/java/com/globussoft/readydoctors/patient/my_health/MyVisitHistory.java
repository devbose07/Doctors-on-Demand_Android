package com.globussoft.readydoctors.patient.my_health;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
import com.globussoft.readydoctors.patient.Utills.AppData;
import com.globussoft.readydoctors.patient.Utills.ConnectionDetector;
import com.globussoft.readydoctors.patient.Utills.ConstantTag;
import com.globussoft.readydoctors.patient.Utills.ConstantUrls;
import com.globussoft.readydoctors.patient.Utills.Singleton;
import com.globussoft.readydoctors.patient.activity.Home;
import com.globussoft.readydoctors.patient.adapter.MyVisitHistoryAdapter;
import com.globussoft.readydoctors.patient.model.VisitHistoryModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by GLB-276 on 10/30/2015.
 */
public class MyVisitHistory extends Activity
{
    ImageView backImage;
    RelativeLayout visit_history,messages,my_favorite,my_pharmacies;
    ListView list;
    ProgressBar progress;
    ArrayList<VisitHistoryModel> arrayList=new ArrayList<VisitHistoryModel>();
    TextView noAppointments;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_visit_history);
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

        list = (ListView) findViewById(R.id.list);
        progress= (ProgressBar) findViewById(R.id.progress);
        noAppointments= (TextView) findViewById(R.id.no_appointments);

//        ApoinmentHistory(Singleton.PatientID);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                AppData.visitHistoryModel = arrayList.get(position);
                Toast.makeText(getApplicationContext(),"oops.....!",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), VisitHistoryDetails
                        .class);
                startActivity(intent);
            }
        });

    }
    @Override
    protected void onResume()
    {
        System.out.println("i am in onrewume");
        super.onResume();
        if(new ConnectionDetector(getApplicationContext()).isConnectingToInternet())
        {
            ApoinmentHistory(Singleton.PatientID);
        }
        else
        {
            Toast.makeText(getApplicationContext(),"You don't have internet...!",Toast.LENGTH_SHORT).show();
            onBackPressed();
        }


    }
    void openPdf()
    {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/PDF";

        File file = new File(path, "demo.pdf");

        intent.setDataAndType( Uri.fromFile(file), "application/pdf" );
        startActivity(intent);
    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        /*Intent intent = new Intent(getApplicationContext(),MeetUs.class);
        startActivity(intent);
        MyHealth.this.finish();*/
    }

    public void ApoinmentHistory(final String patient_id)
    {
        progress.setVisibility(View.VISIBLE);
        arrayList.clear();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest sr = new StringRequest(Request.Method.POST,
                ConstantUrls.UrlMain + ConstantUrls.UrlGetPatientAppointmentHistory,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        System.out.println("appointments history response" + response);
                        try {
                            JSONObject json = new JSONObject(response);
                            if (json.getString("code").equals("200"))
                            {
//                                Toast.makeText(getApplicationContext(),json.getString("message"),Toast.LENGTH_SHORT).show();
                                JSONArray data = json.getJSONArray(ConstantTag.tag_Data);
                                for (int i = 0; i < data.length(); i++)
                                {
                                    JSONObject obj = data.getJSONObject(i);
                                    VisitHistoryModel model = new VisitHistoryModel();
                                    model.setAppointmentId(obj.getString("appointmentId"));
                                    model.setPatientName(obj.getString("patientFirstName") + " " + obj.getString("patientLastName"));
                                    model.setPatientAddress(obj.getString("patientAddress"));
                                    model.setPatientDateOfBirth(obj.getString("patientDateOfBirth"));
                                    model.setDoctorName("Dr." + obj.getString("FirstName") + " " + obj.getString("LastName"));
                                    model.setInstructions(obj.getString("instructions"));
                                    model.setMedicationsReport(obj.getString("medicationsReport"));
                                    model.setAppointment_start_time(obj.getString("appointment_start_time"));
                                    model.setAppointment_end_time(obj.getString("appointment_end_time"));

                                    arrayList.add(model);
                                }
                                if(arrayList.size()<=0)
                                {
                                    Toast.makeText(getApplicationContext(), "You don't have any appointment history..!", Toast.LENGTH_SHORT).show();
                                   /* Intent intent = new Intent(getApplicationContext(), Home.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();*/
                                    noAppointments.setVisibility(View.VISIBLE);
                                }
                                else
                                {
                                    MyVisitHistoryAdapter adapter = new MyVisitHistoryAdapter( getApplicationContext(), arrayList);
                                    list.setAdapter(adapter);
                                }


                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),json.getString("message"),Toast.LENGTH_SHORT).show();
                                /*Intent intent = new Intent(getApplicationContext(), Home.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();*/
                                noAppointments.setVisibility(View.VISIBLE);
                            }
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),"Something went wrong...!",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), Home.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                        }
                        progress.setVisibility(View.INVISIBLE);
                    }
                }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                progress.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(),"Something went wrong...!",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Home.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        })
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("userId", patient_id);

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(sr);
    }
}
