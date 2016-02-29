package com.globussoft.readydoctors.patient.see_a_doctor_now;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.globussoft.readydoctors.patient.R;
import com.globussoft.readydoctors.patient.Utills.AppData;
import com.globussoft.readydoctors.patient.Utills.AppUtills;
import com.globussoft.readydoctors.patient.Utills.ConnectionDetector;
import com.globussoft.readydoctors.patient.Utills.ConstantUrls;
import com.globussoft.readydoctors.patient.Utills.MyCustomProgressDialog;
import com.globussoft.readydoctors.patient.Utills.Singleton;
import com.globussoft.readydoctors.patient.activity.Home;
import com.globussoft.readydoctors.patient.activity.PaymentActivity;
import com.globussoft.readydoctors.patient.model.CalenderTimeModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

/**
 * Created by GLB-276 on 1/15/2016.
 */
public class SeeDoctorNowConfirmSchedule extends Activity
{

    Button continue_btn;
    TextView time_text;
    ArrayList<Date> shedList = new ArrayList<Date>();
    int selectedDayOfMonth = 0;
    String appointment_start_time;
    String appointment_end_time;
    ArrayList<CalenderTimeModel> scheduleList = new ArrayList<CalenderTimeModel>();
    int dayOfWeek;
    public ProgressDialog progressDialog;
    Date availableDate;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dateconfirm_activity);
        InitUI();
        if(new ConnectionDetector(getApplicationContext()).isConnectingToInternet())
        {
                /*fetch schedules by time*/
                getSchedule(4);

        }
        else
        {
            Toast.makeText(SeeDoctorNowConfirmSchedule.this, "You dont have Internet Connection....!", Toast.LENGTH_SHORT).show();
        }
    }
    public void loadeProgressDialog()
    {
        new VeryLongAsyncTask(SeeDoctorNowConfirmSchedule.this).execute();
    }

    class VeryLongAsyncTask extends AsyncTask<Void, Void, Void>
    {
		/*private final ProgressDialog progressDialog;*/

        public VeryLongAsyncTask(Context ctx)
        {
            progressDialog = MyCustomProgressDialog.ctor(ctx);
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();

            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            // sleep for 5 seconds
            try { Thread.sleep(5000); }
            catch (InterruptedException e)
            { e.printStackTrace(); }

            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);

            progressDialog.hide();
        }
    }
    private void InitUI()
    {
        continue_btn=(Button) findViewById(R.id.continue_btn);
        time_text=(TextView) findViewById(R.id.time);
//        time_text.setText(Singleton.Startdate + " and " + Singleton.Enddate + " On " + Singleton.month + " " + Singleton.date);

        continue_btn.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                /*Intent intent = new Intent(getApplicationContext(), PsychologyVisitPurpose.class);
                startActivity(intent);*/
                try
                {
                    System.out.println("final in Local "+SeeDoctorData.appointmentStartTime + " I  "+ SeeDoctorData.appointmentEndTime);
                    SeeDoctorData.appointmentStartTime=SeeDoctorData.appointmentStartTime+":00";
                    SeeDoctorData.appointmentEndTime=SeeDoctorData.appointmentEndTime+":00";
                    SeeDoctorData.appointmentStartTime=ConvertLocalToUtc(SeeDoctorData.appointmentStartTime);
                    SeeDoctorData.appointmentEndTime=ConvertLocalToUtc(SeeDoctorData.appointmentEndTime);
                    System.out.println("final in UTC "+SeeDoctorData.appointmentStartTime + " I  "+ SeeDoctorData.appointmentEndTime);
                }
                catch (ParseException e)
                {
                    System.out.println("final in UTC exception  "+e);
                }
                Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
                startActivity(intent);

            }
        });
    }
    public String ConvertLocalToUtc(String local_time) throws ParseException
    {
        System.out.println("local_time>>" + local_time + "<<");
//		local_time="2015/12/02 "+local_time;
        String format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format);

        //Date temp = new SimpleDateFormat(format).parse(local_time);

        Date date = sdf.parse(local_time.trim());

        // Convert Local Time to UTC (Works Fine)
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));


        //Date gmtTime = new Date(sdf.format(date));


        String hms = sdf.format(date);

        return hms;
    }
    private void getSchedule(final int departmentId)
    {
        loadeProgressDialog();
        shedList.clear();
        RequestQueue queue = Volley.newRequestQueue(SeeDoctorNowConfirmSchedule.this);
        StringRequest sr = new StringRequest(Request.Method.POST,
                ConstantUrls.UrlMain + ConstantUrls.UrlGetDepartmentSchedulesNew,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {

                        System.out.println("UrlGetDepartmentSchedules"+ response);
                        try
                        {
                            JSONObject json = new JSONObject(response);
                            if (json.getString("code").equals("200"))
                            {
                                if (json.has("data"))
                                {
                                    JSONArray array = json.getJSONArray("data");
                                    for (int i = 0; i < array.length(); i++)
                                    {
                                        CalenderTimeModel model = new CalenderTimeModel();
                                        int n = (Integer) array.get(i);
                                        Date date = new Date();
                                        date = AppUtills.getCurrentDate(n);
                                        if(i==0)
                                            availableDate=date;
                                        shedList.add(date);
//                                        System.out.print("Utc>>>>>" + AppUtills.getDate(n));
                                      /*  System.out.println(" day "
                                                + date.getDate() + " time "
                                                + date.getHours() + ":"
                                                + date.getMinutes());*/
                                    }
                                }
                                Toast.makeText(getApplicationContext(),"Success", Toast.LENGTH_LONG).show();
                                String formattedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                                        .format(Calendar.getInstance()
                                                .getTime());
                                getCurrentTime(formattedDate);
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
                System.out.println("error " + error.getMessage());
            }
        })
        {
            @Override
            protected Map<String, String> getParams()
            {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("departmentId", ""+departmentId);
                params.put("timeZone", TimeZone.getDefault().getID()+"");
                return params;
            }
        };
        queue.add(sr);
    }
    public void getCurrentTime(final String currentTime)
    {
        try
        {
            DateFormat inputDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date1 = inputDF.parse(currentTime);

//            System.out.print("date1"+date1.toString());

            Calendar cal = Calendar.getInstance();
            cal.setTime(date1);

            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);
            int year = cal.get(Calendar.YEAR);
            int Hrs = cal.get(Calendar.HOUR);
            int Hrs2 = cal.get(Calendar.HOUR_OF_DAY);
            int Minutes = cal.get(Calendar.MINUTE);

            appointment_start_time = AppUtills.ConverCalenderToDateOnly(cal);
            appointment_end_time = AppUtills.ConverCalenderToDateOnly(cal);

            dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

            Singleton.date = day + "";
            Singleton.month = AppUtills.getMonthForInt(month);

            selectedDayOfMonth = cal.get(Calendar.DAY_OF_MONTH);

            getScheduleTimings();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    protected void getScheduleTimings()
    {
        System.out.print("AppData.scheduleTiming=" + (AppData.scheduleTiming = 25));
        scheduleList.clear();
        for (int i = 0; i < shedList.size(); i++)
        {
            System.out.println("shed "+shedList.get(i).getDate() + "==" + dayOfWeek);
            if (shedList.get(i).getDate() == selectedDayOfMonth)
            {
                CalenderTimeModel model = new CalenderTimeModel();
                model.setTime("" + String.format("%02d", shedList.get(i).getHours()) + ":"
                        + String.format("%02d", shedList.get(i).getMinutes()));
                Date date = shedList.get(i);

                model.setTimelimit(AppUtills
                        .AddTime(AppData.scheduleTiming, String.format("%02d", date.getHours()) + ":" + String.format("%02d", date.getMinutes())));
                scheduleList.add(model);
            }
            System.out.println("scheduleList sizeeeeeeeeeeeee "+scheduleList.size());
        }

        try
        {
            /*DateFormat inputDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
           String currentTime=""+shedList.get(0);
            Date date1 = inputDF.parse(currentTime);*/

//            System.out.print("date1"+date1.toString());

            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(availableDate.getTime());

            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);
            int year = cal.get(Calendar.YEAR);
            int Hrs = cal.get(Calendar.HOUR);
            int Hrs2 = cal.get(Calendar.HOUR_OF_DAY);
            int Minutes = cal.get(Calendar.MINUTE);

            SeeDoctorData.patientId=Singleton.PatientID;
            System.out.println("PatientID "+SeeDoctorData.patientId);
            System.out.println("Hrs "+Hrs+"Hrs2 "+Hrs2);
            /*SeeDoctorData.appointmentStartTime= year+"-"+(month + 1) + "-" + day +" "+ Hrs2 + ":" +Minutes;
            SeeDoctorData.appointmentEndTime=year+"-"+(month + 1) + "-" + day +" "+AppUtills.AddTime(25, Hrs2 + ":" +Minutes );*/
            SeeDoctorData.appointmentStartTime= year+"-"+(month + 1) + "-" + day +" "+ scheduleList.get(0).getTime();
            SeeDoctorData.appointmentEndTime=year+"-"+(month + 1) + "-" + day +" "+scheduleList.get(0).getTimelimit();

            System.out.println("SeeDoctorData.appointmentStartTime	" + SeeDoctorData.appointmentStartTime);
            System.out.println("SeeDoctorData.appointmentEndTime	" + SeeDoctorData.appointmentEndTime);
        }
        catch (Exception e)
        {
            System.out.println("Day this ParseException-" + e);
        }

        getAutoAllocatedDoctor(
                SeeDoctorData.appointmentStartTime,
                SeeDoctorData.appointmentEndTime, TimeZone.getDefault().getID() + "");
        progressDialog.cancel();

    }

    public void getAutoAllocatedDoctor( final String appointmentStartTime,
                                        final String appointmentEndTime,
                                        final String timeZone)

    {
        RequestQueue queue = Volley.newRequestQueue(SeeDoctorNowConfirmSchedule.this);
        StringRequest sr = new StringRequest(Request.Method.POST,
                ConstantUrls.UrlMain + ConstantUrls.UrlAutoAllocateMedicalDoctor2,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        System.out.println("auto allocate doctor response" + response);
                        try
                        {
                            JSONObject json = new JSONObject(response);
                            if (json.getString("code").equals("200"))
                            {
                                Singleton.Startdate = scheduleList.get(0).getTime();
                                Singleton.Enddate = scheduleList.get(0).getTimelimit();

                                time_text.setText(Singleton.Startdate + " and " + Singleton.Enddate + " On " + Singleton.month + " " + Singleton.date);

                                Toast.makeText(getApplicationContext(), json.getString("message"), Toast.LENGTH_LONG).show();
//
                                /*JSONArray jsonArray=new JSONArray(json.getString("data"));
                                JSONObject json1 = jsonArray.getJSONObject(0);*/

                                SeeDoctorData.doctorID=json.getString("data");
                                System.out.println("doctorID" + SeeDoctorData.doctorID);
                                System.out.println("json.getString(message)" + json.getString("message"));
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), json.getString("message"), Toast.LENGTH_LONG).show();
                                System.out.println("json.getString(message)" + json.getString("message"));
                                Intent intent = new Intent(getApplicationContext(), Home.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                SeeDoctorNowConfirmSchedule.this.finish();
                            }
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
//						progressBar.setVisibility(View.INVISIBLE);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
//						progressBar.setVisibility(View.INVISIBLE);
                        System.out.println("error " + error.getMessage());
                    }
                })
        {
            @Override
            protected Map<String, String> getParams()
            {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("appointmentStartTime", appointmentStartTime);
                params.put("appointmentEndTime", appointmentEndTime);
                params.put("timeZone", timeZone);

                System.out.println("appointmentStartTime" + appointmentStartTime);
                System.out.println("appointmentEndTime"+appointmentEndTime);
                System.out.println("timeZone"+timeZone);

                return params;
            }
        };
        queue.add(sr);
    }
}
