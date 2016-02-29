package com.globussoft.readydoctors.patient.psychological;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.ListView;

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
import com.globussoft.readydoctors.patient.adapter.AppointmentListAdapter;
import com.globussoft.readydoctors.patient.adapter.CalenderTimeAdapter;
import com.globussoft.readydoctors.patient.model.AppointmentListModel;
import com.globussoft.readydoctors.patient.model.CalenderTimeModel;
import com.globussoft.readydoctors.patient.pediatrics.PediatricData;
import com.globussoft.readydoctors.patient.pregnancy_newborns.LactationData;

public class PsychologyCalender extends Activity
{
    ImageView backImage;
    CalendarView cal;
    ListView list,list2;
    CalenderTimeAdapter adapter;
    AppointmentListAdapter adapter2;
    int dayOfWeek;
    ArrayList<CalenderTimeModel> scheduleList = new ArrayList<CalenderTimeModel>();
    ArrayList<AppointmentListModel> appointmentList = new ArrayList<AppointmentListModel>();
    ArrayList<Date> shedList = new ArrayList<Date>();
    TextView next;
    int selectedDayOfMonth = 0;
    String appointment_start_time;
    String appointment_end_time;
    RelativeLayout calenderRlt,listRlt,btnCalender,btnList;
    TextView calenderTxt,listTxt,dateCur,no_schedules_list,no_schedules_calender;
    public ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.calender);
        dateCur=(TextView)findViewById(R.id.date);
        if(new ConnectionDetector(getApplicationContext()).isConnectingToInternet())
        {
            loadeProgressDialog();
            if(PsychologyData.psychologyScheduleType==1)
            {
				/*fetch schedules by doctor*/
                getSchedule(PsychologyData.doctor_id);
            }
            else
            {
				/*fetch schedules by time*/
                getSchedule(AppData.appointmentType);
            }
        }
        else
        {
            Toast.makeText(PsychologyCalender.this, "You dont have Internet Connection....!", Toast.LENGTH_SHORT).show();
        }

        InitUi();
    }

    public void loadeProgressDialog()
    {
        new VeryLongAsyncTask(PsychologyCalender.this).execute();
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

    public void getCurrentTime(final String currentTime)
    {
        try
        {
            DateFormat inputDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date1 = inputDF.parse(currentTime);

            System.out.print("date1"+date1.toString());

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

            dateCur.setText(day+"-"+(month+1)+"-"+year);


            getScheduleTimings();
            list.setAdapter(adapter);
            progressDialog.cancel();

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public String ConvertLocalToUtc(String local_time) throws ParseException
    {
        System.out.print("local_time>>"+local_time+"<<");
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
    void InitUi()
    {
        calenderRlt=(RelativeLayout)findViewById(R.id.calenderRlt);
        listRlt=(RelativeLayout)findViewById(R.id.listRlt);

        calenderTxt=(TextView)findViewById(R.id.tab1Txt);
        listTxt=(TextView)findViewById(R.id.tab2Txt);

        btnCalender=(RelativeLayout)findViewById(R.id.tab1);
        btnList=(RelativeLayout)findViewById(R.id.tab2);


        setupLayout(true);

        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupLayout(false);
            }
        });

        btnCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupLayout(true);
            }
        });



        next=(TextView)findViewById(R.id.next);
        next.setVisibility(View.INVISIBLE);

        no_schedules_list =(TextView)findViewById(R.id.no_schedules_list);
        no_schedules_calender=(TextView)findViewById(R.id.no_schedules_calender);

        cal = (CalendarView) findViewById(R.id.calender);


        list = (ListView) findViewById(R.id.list);
        list2 = (ListView) findViewById(R.id.list2);

        backImage=(ImageView)findViewById(R.id.backImage);
        backImage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
            }
        });



        cal.setOnDateChangeListener(new OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                Calendar selected = Calendar.getInstance();
                selected.setTimeInMillis(view.getDate());
                System.out.println("Selected date" + view.getDate());
                // convert selected item to date format

                appointment_start_time = AppUtills.ConverCalenderToDateOnly(selected);
                appointment_end_time = AppUtills.ConverCalenderToDateOnly(selected);


                dayOfWeek = selected.get(Calendar.DAY_OF_WEEK);

                Singleton.date = dayOfMonth + "";
                Singleton.month = AppUtills.getMonthForInt(month);

                selectedDayOfMonth = selected.get(Calendar.DAY_OF_MONTH);

                dateCur.setText(dayOfMonth + "-" + (month + 1) + "-" + year);

				/*
				 * if (dayOfWeek != 1 && dayOfWeek != 7) {
				 */
                if (new ConnectionDetector(getApplicationContext()).isConnectingToInternet())
                {
                    getScheduleTimings();
                }
                else
                {
                    Toast.makeText(PsychologyCalender.this, "You dont have Internet Connection....!", Toast.LENGTH_SHORT).show();
                }


                list.setAdapter(adapter);
            }
        });

        list.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id)
            {
                Integer position1 = (Integer) view.getTag();

//				LactationData.time_period = "30";

                Singleton.Startdate = scheduleList.get(position1).getTime();
                Singleton.Enddate = scheduleList.get(position1).getTimelimit();

                PsychologyData.appointment_start_time = appointment_start_time + " " + Singleton.Startdate + ":00";
                PsychologyData.appointment_end_time = appointment_end_time + " " + Singleton.Enddate + ":00";
                System.out.println("final "+PsychologyData.appointment_start_time + " I  "+ PsychologyData.appointment_end_time);
                try
                {
                    PsychologyData.appointment_start_time=ConvertLocalToUtc(PsychologyData.appointment_start_time);
                    PsychologyData.appointment_end_time=ConvertLocalToUtc(PsychologyData.appointment_end_time);
                    System.out.println("final in UTC "+PsychologyData.appointment_start_time + " I  "+ PsychologyData.appointment_end_time);
                }
                catch (ParseException e)
                {
                    System.out.println("final in UTC exception  "+e);
                }

                if (PsychologyData.psychologyScheduleType == 1)
                {
                    Intent intent = new Intent(getApplicationContext(), PsychologyDateConfirm.class);
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(getApplicationContext(), SelectPsychologist.class);
                    startActivity(intent);
                }

            }
        });
        list2.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id)
            {
                if(!appointmentList.get(position).isHeader())
                {
                    Integer position1 = (Integer) view.getTag();

//					PsychologyData.time_period = "30";
//
                    Singleton.Startdate = appointmentList.get(position).getTime();
                    Singleton.Enddate = appointmentList.get(position).getTimelimit();

                    PsychologyData.appointment_start_time = appointment_start_time + " " + Singleton.Startdate + ":00";
                    PsychologyData.appointment_end_time = appointment_end_time + " " + Singleton.Enddate + ":00";

                    System.out.println("final "+PsychologyData.appointment_start_time + " I  " + PsychologyData.appointment_end_time);

                    try
                    {
                        PsychologyData.appointment_start_time=ConvertLocalToUtc(PsychologyData.appointment_start_time);
                        PsychologyData.appointment_end_time=ConvertLocalToUtc(PsychologyData.appointment_end_time);
                        System.out.println("final in UTC "+PsychologyData.appointment_start_time + " I  "+ PsychologyData.appointment_end_time);
                    }
                    catch (ParseException e)
                    {
                        System.out.println("final in UTC exception  "+e);
                    }

                    if (PsychologyData.psychologyScheduleType== 1)
                    {
                        Intent intent = new Intent(getApplicationContext(), PsychologyDateConfirm.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Intent intent = new Intent(getApplicationContext(), SelectPsychologist.class);
                        startActivity(intent);
                    }
                }
            }
        });

    }
    public void setupLayout(boolean calender)
    {
        if(calender)
        {
            calenderRlt.setVisibility(View.VISIBLE);
            listRlt.setVisibility(View.INVISIBLE);
            btnCalender.setBackgroundColor(getResources().getColor(R.color.white));
            calenderTxt.setTextColor(getResources().getColor(R.color.dark_pink));

            btnList.setBackgroundColor(getResources().getColor(R.color.dark_pink));
            listTxt.setTextColor(getResources().getColor(R.color.white));
        }
        else
        {
            calenderRlt.setVisibility(View.INVISIBLE);
            listRlt.setVisibility(View.VISIBLE);
            btnCalender.setBackgroundColor(getResources().getColor(R.color.dark_pink));
            calenderTxt.setTextColor(getResources().getColor(R.color.white));

            btnList.setBackgroundColor(getResources().getColor(R.color.white));
            listTxt.setTextColor(getResources().getColor(R.color.dark_pink));

            setupList(shedList);
            list2.setAdapter(adapter2);
        }
    }
    public void setupList(final ArrayList<Date> tempList)
    {
        appointmentList.clear();
        ArrayList<Date> dateList =new ArrayList<Date>();

        for(int i=0;i<tempList.size();i++)
        {


            int day1,day2;
            if(dateList.size()==0)
            {
                dateList.add(tempList.get(i));
                day1=dateList.get(i).getDay();

                AppointmentListModel model1 = new AppointmentListModel();
                model1.setTime("" + shedList.get(i).getHours() + ":" + shedList.get(i).getMinutes());
                Date date1 = shedList.get(i);
                date1.setMinutes(date1.getMinutes() + AppData.scheduleTiming);
                model1.setTimelimit("" + date1.getHours() + ":" + date1.getMinutes());
                model1.setIsHeader(true);
                model1.setmBelongsTo("" + tempList.get(i).getDay());
                model1.setDate(date1);

                appointmentList.add(model1);

                System.out.println("Added Date");

            }
            else
            {
                day1=dateList.get(dateList.size()-1).getDay();
                day2=tempList.get(i).getDay();
                if(day1==day2)
                {

                }
                else
                {
                    dateList.add(tempList.get(i));

                    AppointmentListModel model2 = new AppointmentListModel();
                    model2.setTime("" + shedList.get(i).getHours() + ":" + shedList.get(i).getMinutes());
                    Date date2 = shedList.get(i);
                    date2.setMinutes(date2.getMinutes() + AppData.scheduleTiming);
                    model2.setTimelimit("" + date2.getHours() + ":" + date2.getMinutes());
                    model2.setIsHeader(true);
                    model2.setmBelongsTo("" + tempList.get(i).getDay());
                    model2.setDate(date2);

                    System.out.println("Added Date 2");

                    appointmentList.add(model2);
                }
            }
            AppointmentListModel model = new AppointmentListModel();
            model.setTime("" + shedList.get(i).getHours() + ":" + shedList.get(i).getMinutes());
            Date date = shedList.get(i);
            date.setMinutes(date.getMinutes() + AppData.scheduleTiming);
            model.setTimelimit("" + date.getHours() + ":" + date.getMinutes());
            model.setIsHeader(false);
            model.setmBelongsTo("" + tempList.get(i).getDay());
            model.setDate(date);

            appointmentList.add(model);

            if (appointmentList.size() > 0)
            {
                adapter2 = new AppointmentListAdapter(getApplicationContext(),appointmentList);
                no_schedules_list.setVisibility(View.INVISIBLE);
                list2.setVisibility(View.VISIBLE);
            }
            else
            {
                System.out.println("no schedules available");
                no_schedules_list.setVisibility(View.VISIBLE);
                list2.setVisibility(View.INVISIBLE);
                Toast.makeText(PsychologyCalender.this, "no schedules available....!", Toast.LENGTH_SHORT).show();
            }
        }
        System.out.println("Schedule list size "+appointmentList.size());
        for (int i=0;i<appointmentList.size();i++)
        {
            if(appointmentList.get(i).isHeader())
            {
                Calendar cal = Calendar.getInstance();
                cal.setTime(appointmentList.get(i).getDate());
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                int year = cal.get(Calendar.YEAR);
                int Hrs = cal.get(Calendar.HOUR);
                int Hrs2 = cal.get(Calendar.HOUR_OF_DAY);
                int Minutes = cal.get(Calendar.MINUTE);

                System.out.println("Day "+day+"-"+month+"-"+year);
            }
            else
            {
                System.out.println("Schedule "+appointmentList.get(i).getTime()+" To "+appointmentList.get(i).getTimelimit()+" Belongs to "+appointmentList.get(i).getmBelongsTo());
            }
        }
    }

    protected void getScheduleTimings()
    {
        System.out.print("AppData.scheduleTiming=" + AppData.scheduleTiming);
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

                model.setTimelimit(AppUtills.AddTime(AppData.scheduleTiming, String.format("%02d", date.getHours()) + ":" + String.format("%02d", date.getMinutes())));
                scheduleList.add(model);
            }
        }
        if (scheduleList.size() > 0)
        {
            adapter = new CalenderTimeAdapter(getApplicationContext(),scheduleList);
            no_schedules_calender.setVisibility(View.INVISIBLE);
            list.setVisibility(View.VISIBLE);
        }
        else
        {
            System.out.println("no schedules available");
            no_schedules_calender.setVisibility(View.VISIBLE);
            list.setVisibility(View.INVISIBLE);
        }
    }

    private void getSchedule(final String doctorId)
    {
        shedList.clear();
        System.out.println("fetching by doctor........................");
        RequestQueue queue = Volley.newRequestQueue(PsychologyCalender.this);
        StringRequest sr = new StringRequest(Request.Method.POST,
                ConstantUrls.UrlMain + ConstantUrls.UrlGetDoctorSchedules2,
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
                                        shedList.add(date);



                                        System.out.println(" day "
                                                + date.getDate() + " time "
                                                + date.getHours() + ":"
                                                + date.getMinutes());
                                    }
                                    String formattedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                                            .format(Calendar.getInstance()
                                                    .getTime());
                                    getCurrentTime(formattedDate);
                                }
                                else
                                {
                                    progressDialog.cancel();
                                    Toast.makeText(getApplicationContext(), "Something went wrong..please try again later..!", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getApplicationContext(), Home.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                            else
                            {
                                progressDialog.cancel();
                                Toast.makeText(getApplicationContext(), "Something went wrong..please try again later..!", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), Home.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            }

                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                            progressDialog.cancel();
                            Toast.makeText(getApplicationContext(), "Something went wrong..please try again later..!", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), Home.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }
                    }
                }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {

                System.out.println("error " + error.getMessage());
                progressDialog.cancel();
                Toast.makeText(getApplicationContext(), "Something went wrong..please try again later..!", Toast.LENGTH_LONG).show();
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
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("doctorId", doctorId);
                params.put("timeZone", TimeZone.getDefault().getID()+"");
                return params;
            }
        };
        queue.add(sr);
    }
    private void getSchedule(final int departmentId)
    {
        shedList.clear();
        RequestQueue queue = Volley.newRequestQueue(PsychologyCalender.this);
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
                                        shedList.add(date);
                                        System.out.print("Utc>>>>>" + AppUtills.getDate(n));
                                        System.out.println(" day "
                                                + date.getDate() + " time "
                                                + date.getHours() + ":"
                                                + date.getMinutes());
                                    }
                                    String formattedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                                            .format(Calendar.getInstance()
                                                    .getTime());
                                    getCurrentTime(formattedDate);
                                }
                                else
                                {
                                    progressDialog.cancel();
                                    Toast.makeText(getApplicationContext(), "Something went wrong..please try again later..!", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getApplicationContext(), Home.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();
                                }

                            }
                            else
                            {
                                progressDialog.cancel();
                                Toast.makeText(getApplicationContext(), "Something went wrong..please try again later..!", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), Home.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            }
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                            progressDialog.cancel();
                            Toast.makeText(getApplicationContext(), "Something went wrong..please try again later..!", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), Home.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }
                    }
                }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                System.out.println("error " + error.getMessage());
                progressDialog.cancel();
                Toast.makeText(getApplicationContext(), "Something went wrong..please try again later..!", Toast.LENGTH_LONG).show();
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
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("departmentId", ""+departmentId);
                params.put("timeZone", TimeZone.getDefault().getID()+"");

                System.out.println("departmentId for fetching schedule list" + departmentId);
                return params;
            }
        };
        queue.add(sr);
    }
}
