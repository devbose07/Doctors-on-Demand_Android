package com.globussoft.readydoctors.patient.pediatrics;

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
import com.globussoft.readydoctors.patient.Utills.AppData;
import com.globussoft.readydoctors.patient.Utills.ConnectionDetector;
import com.globussoft.readydoctors.patient.Utills.ConstantUrls;
import com.globussoft.readydoctors.patient.activity.Home;
import com.globussoft.readydoctors.patient.adapter.DeptDoctorAdapter;
import com.globussoft.readydoctors.patient.model.DoctorModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

/**
 * Created by GLB-276 on 12/11/2015.
 */
public class SelectPhysician extends Activity
{
    public ArrayList<DoctorModel> doctorList = new ArrayList<DoctorModel>();

    ListView mListView;
    ProgressBar progress;
    DeptDoctorAdapter adapter;
    TextView next,label;
    ImageView backImage;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.select_psychologist);

        initUI();

    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
    }
    void initUI()
    {
        label=(TextView)findViewById(R.id.textView1);
        label.setText("Please select a physician:");
        backImage=(ImageView)findViewById(R.id.backImage);
        backImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        next=(TextView)findViewById(R.id.next);
        next.setVisibility(View.INVISIBLE);
        mListView = (ListView) findViewById(R.id.list);
        progress = (ProgressBar) findViewById(R.id.progress);
        doctorList.clear();
        mListView.setOnItemClickListener(new selectPsychologist());
        if(new ConnectionDetector(getApplicationContext()).isConnectingToInternet())
        {

            if (PediatricData.pediatricsScheduleType == 1)
            {
                ShowDoctorList("2");
            }
            else
            {
                String startTime="";
                String endTime="";

                try
                {
                    startTime=ConvertUtcToLocal(PediatricData.appointment_start_time);
                    endTime=ConvertUtcToLocal(PediatricData.appointment_end_time);
                }
                catch (ParseException e)
                {

                }



                ShowFreeDoctorList("2", startTime,endTime);

            }
        }
        else
        {
            Toast.makeText(SelectPhysician.this, "You dont have Internet Connection....!", Toast.LENGTH_SHORT).show();
        }

    }
    public String ConvertUtcToLocal(String originalUTCTime) throws ParseException
    {
        System.out.print("Time in UTC <"+ originalUTCTime+">");

        String format = "yyyy-MM-dd HH:mm:ss";

        SimpleDateFormat sdf = new SimpleDateFormat(format);

        Date date = sdf.parse(originalUTCTime.trim());

        long utcStamp = date.getTime();

        // Add/Substract Zone offset into UTC time
        long localTimeStamp = utcStamp + Calendar.getInstance().get(Calendar.ZONE_OFFSET);

        Timestamp timestamp = new Timestamp(localTimeStamp);

        Date finalLocatDate = new Date(timestamp.getTime());

        String finalLocalDateString = sdf.format(finalLocatDate);

        System.out.print("finalLocalDateString   " + finalLocalDateString);

        return finalLocalDateString;
    }
    private class selectPsychologist implements ListView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,long id)
        {
            AppData.selecteddoctormodel = doctorList.get(position);

            Intent intent = new Intent(getApplicationContext(),ShowPhysician.class);
            startActivity(intent);
        }
    }

    public void ShowDoctorList(final String departmentId)
    {
        progress.setVisibility(View.VISIBLE);
        doctorList.clear();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest sr = new StringRequest(Request.Method.POST,
                ConstantUrls.UrlMain + ConstantUrls.UrlShowDeptDoctorNew,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        System.out.println("show doctor response" + response);
                        try
                        {
                            JSONObject json = new JSONObject(response);
                            if (json.getString("code").equals("200"))
                            {
                                JSONArray array = json.getJSONArray("data");
                                for (int i = 0; i < array.length(); i++)
                                {
                                    JSONObject obj = array.getJSONObject(i);
                                    DoctorModel model = new DoctorModel();

                                    model.setMeta_id(obj.getString("meta_id"));
                                    model.setDoctor_id(obj.getString("doctor_id"));
                                    model.setFirstName(obj.getString("FirstName"));
                                    System.out.println("FirstName" + obj.getString("FirstName"));
                                    model.setLastName(obj.getString("LastName"));
                                    model.setDepartmentId(obj.getString("departmentId"));
                                    model.setDoctorAddress(obj.getString("doctorAddress"));
                                    model.setDoctorContactNumber(obj.getString("doctorContactNumber"));
                                    model.setDoctorOnlineStatus(obj.getString("doctorOnlineStatus"));
                                    model.setDoctorAvailableStatus(obj.getString("doctorAvailableStatus"));
                                    model.setDoctorSex(obj.getString("doctorSex"));
                                    model.setDoctorAbout(obj.getString("doctorAbout"));
                                    model.setDoctorAvailableFrom(obj.getString("doctorAvailableFrom"));
                                    model.setDoctorAvailableTo(obj.getString("doctorAvailableTo"));
                                    model.setLatitude(obj.getString("latitude"));
                                    model.setLongitude(obj.getString("longitude"));
                                    model.setDoctorRegistrationDate(obj.getString("doctorRegistrationDate"));
                                    model.setDoctorProfilePicUrl(obj.getString("doctorProfilePicUrl"));
                                    model.setDoctorStatus(obj.getString("doctorStatus"));
                                    model.setUpdated_at(obj.getString("updated_at"));
                                    model.setCreated_at(obj.getString("created_at"));
                                    doctorList.add(model);
                                }
                                for (int i = 0;i<doctorList.size();i++ )
                                {
                                    System.out.println("bang bang "+doctorList.get(i).getFirstName());
                                }
                                adapter = new DeptDoctorAdapter(getApplicationContext(), doctorList);
                                mListView.setAdapter(adapter);
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),json.getString("message"),Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), Home.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            }

                        } catch (Exception e)
                        {
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
                params.put("departmentId", departmentId);
                params.put("timeZone", TimeZone.getDefault().getID()+"");
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

    public void ShowFreeDoctorList(final String departmentId,final String appointmentStartTime,final String appointmentEndTime)
    {
        progress.setVisibility(View.VISIBLE);
        doctorList.clear();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest sr = new StringRequest(Request.Method.POST,
                ConstantUrls.UrlMain + ConstantUrls.UrlGetAllFreeDoctorBySchedule,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        System.out.println("show doctor response" + response);
                        try {
                            JSONObject json = new JSONObject(response);
                            if (json.getString("code").equals("200"))
                            {
                                JSONArray array = json.getJSONArray("data");
                                for (int i = 0; i < array.length(); i++)
                                {
                                    JSONObject obj = array.getJSONObject(i);
                                    DoctorModel model = new DoctorModel();

                                    model.setMeta_id(obj.getString("meta_id"));
                                    model.setDoctor_id(obj.getString("doctor_id"));
                                    model.setFirstName(obj.getString("FirstName"));
                                    System.out.println("FirstName" + obj.getString("FirstName"));
                                    model.setLastName(obj.getString("LastName"));
                                    model.setDepartmentId(obj.getString("departmentId"));
                                    model.setDoctorAddress(obj.getString("doctorAddress"));
                                    model.setDoctorContactNumber(obj.getString("doctorContactNumber"));
                                    model.setDoctorOnlineStatus(obj.getString("doctorOnlineStatus"));
                                    model.setDoctorAvailableStatus(obj.getString("doctorAvailableStatus"));
                                    model.setDoctorSex(obj.getString("doctorSex"));
                                    model.setDoctorAbout(obj.getString("doctorAbout"));
                                    model.setDoctorAvailableFrom(obj.getString("doctorAvailableFrom"));
                                    model.setDoctorAvailableTo(obj.getString("doctorAvailableTo"));
                                    model.setLatitude(obj.getString("latitude"));
                                    model.setLongitude(obj.getString("longitude"));
                                    model.setDoctorRegistrationDate(obj.getString("doctorRegistrationDate"));
                                    model.setDoctorProfilePicUrl(obj.getString("doctorProfilePicUrl"));
                                    model.setDoctorStatus(obj.getString("doctorStatus"));
                                    model.setUpdated_at(obj.getString("updated_at"));
                                    model.setCreated_at(obj.getString("created_at"));
                                    doctorList.add(model);
                                }
                                for (int i = 0;i<doctorList.size();i++ )
                                {
                                    System.out.println("bang bang "+doctorList.get(i).getFirstName());
                                }
                                adapter = new DeptDoctorAdapter(getApplicationContext(), doctorList);
                                mListView.setAdapter(adapter);
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),json.getString("message"),Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), Home.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            }

                        } catch (Exception e)
                        {
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
                params.put("departmentId", departmentId);
                params.put("appointmentStartTime", appointmentStartTime);
                params.put("appointmentEndTime", appointmentEndTime);
                params.put("timeZone", TimeZone.getDefault().getID()+"");

                System.out.println("departmentId for fetching free doc list" + departmentId);

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
