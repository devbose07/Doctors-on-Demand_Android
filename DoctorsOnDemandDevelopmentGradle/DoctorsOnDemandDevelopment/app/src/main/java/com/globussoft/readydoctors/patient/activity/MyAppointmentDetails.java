package com.globussoft.readydoctors.patient.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import com.globussoft.readydoctors.patient.Utills.ConstantUrls;
import com.globussoft.readydoctors.patient.Utills.Singleton;
import com.globussoft.readydoctors.patient.video_chat.SelectVideoCallingUser;

import org.json.JSONObject;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by GLB-217 on 10/1/2015.
 */

public class MyAppointmentDetails extends Activity
{
    TextView time, patient,doctor, status;
    ImageView backImage;
    Button cancel,makeAcall;
    String givenStartDateTimeString,givenEndDateTimeString;
    long startTime,endTime,currentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_appointment_details);
        initUI();

    }

    private void initUI()
    {
        backImage=(ImageView)findViewById(R.id.backImage);
        backImage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
            }
        });
        time = (TextView) findViewById(R.id.time);
        patient = (TextView) findViewById(R.id.patient);
        doctor = (TextView) findViewById(R.id.doctor);
        status = (TextView) findViewById(R.id.status);
        System.out.println("=================Appointment Details==================");
        System.out.println(Singleton.Name);
        System.out.println(AppData.appointmentsModel.getFirstName()+" "+AppData.appointmentsModel.getLastName());
        System.out.println(AppData.appointmentsModel.getAppointment_start_time()+"   To   "+AppData.appointmentsModel.getAppointment_end_time());
        System.out.println(AppData.appointmentsModel.getAppointmentStatus().toString());
        System.out.println("=================Appointment Details=================");
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        patient.setText(Singleton.Name);
        doctor.setText(AppData.appointmentsModel.getFirstName()+" "+AppData.appointmentsModel.getLastName());
       try
       {
           time.setText((givenStartDateTimeString=ConvertUtcToLocal(AppData.appointmentsModel.getAppointment_start_time()))+"   To   "+(givenEndDateTimeString=ConvertUtcToLocal(AppData.appointmentsModel.getAppointment_end_time())));
       }catch (ParseException e)
       {


           System.out.println("gone......>"+e);
       }



        cancel=(Button)findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                cancelAppointment(AppData.appointmentsModel.getAppointment_id());
            }
        });
        makeAcall=(Button)findViewById(R.id.callDoctor);
        makeAcall.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), SelectVideoCallingUser.class);
                startActivity(intent);
            }
        });
        setupButtons();
        switch(Integer.parseInt(AppData.appointmentsModel.getAppointmentStatus().toString()))
        {
            case 0:
                cancel.setVisibility(View.INVISIBLE);
                makeAcall.setVisibility(View.INVISIBLE);
                status.setText("Rejected");
                status.setTextColor(getResources().getColor(R.color.blue_krayola));
                break;
            case 1:
                status.setText("Accepted");
                status.setTextColor(getResources().getColor(R.color.green));
                break;
           /* case 2:
                status.setText("Rejected");
                status.setTextColor(getResources().getColor(R.color.red));
                break;
            case 3:
                status.setText("Cancelled");
                status.setTextColor(getResources().getColor(R.color.red));
                break;
            case 4:
                status.setText("Completed");
                status.setTextColor(getResources().getColor(R.color.red));
                break;*/
            default:

        }
    }
    public void setupButtons()
    {
        //2016-01-23 08:00:00
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try
        {
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println("*********Schedule timings*********** ");
            Date startDate = sdf.parse(givenStartDateTimeString);
            startTime = startDate.getTime();
            System.out.println(givenStartDateTimeString+" start_time in milli :: " + startTime);

            Date endDate = sdf.parse(givenEndDateTimeString);
            endTime = endDate.getTime();
            System.out.println(givenEndDateTimeString+" end_time in milli :: " + endTime);

            Date currentDate = sdf.parse(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    .format(Calendar.getInstance()
                            .getTime()));
            currentTime = Calendar.getInstance().getTimeInMillis();
            System.out.println(currentDate+"Current in milli :: "+currentTime);
            System.out.println("*********___________*********** ");
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        if(currentTime>=startTime&&currentTime<=endTime)
        {
                System.out.println("make a call...................................");
            makeAcall.setVisibility(View.VISIBLE);
            cancel.setVisibility(View.INVISIBLE);
        }
        else if(currentTime>=endTime)
        {
            makeAcall.setVisibility(View.INVISIBLE);
            cancel.setVisibility(View.INVISIBLE);
        }
        else
        {
            makeAcall.setVisibility(View.INVISIBLE);
            cancel.setVisibility(View.VISIBLE);
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

        System.out.print("finalLocalDateString   "+finalLocalDateString);

        return finalLocalDateString;
    }
    public void cancelAppointment(final String appointmentId)
    {
//        progress.setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest sr = new StringRequest(Request.Method.POST,
                ConstantUrls.UrlMain + ConstantUrls.UrlCancelApointment,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {

                        System.out.println("updated patient profile response" + response);
                        try
                        {
                            JSONObject json = new JSONObject(response);
                            if (json.getString("code").equals("200"))
                            {
                                Toast.makeText(MyAppointmentDetails.this, json.getString("message"), Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            }
                            else
                            {
                                Toast.makeText(MyAppointmentDetails.this, json.getString("message"), Toast.LENGTH_SHORT).show();

                            }
                        } catch (Exception e)
                        {
                            e.printStackTrace();
                        }
//                        progress.setVisibility(View.INVISIBLE);
                    }
                }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
//                progress.setVisibility(View.INVISIBLE);
            }
        }) {
            @Override
            protected Map<String, String> getParams()
            {

                Map<String, String> params = new HashMap<String, String>();
                params.put("appointmentId", appointmentId);

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
