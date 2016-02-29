package com.globussoft.readydoctors.patient.see_a_doctor_now;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import com.globussoft.readydoctors.patient.Utills.Singleton;
import com.globussoft.readydoctors.patient.activity.Home;
import com.globussoft.readydoctors.patient.meet_us.MeetUs;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by GLB-276 on 11/21/2015.
 */
public class SeeDoctorSelectPatient extends Activity
{

    //
    TextView mNext,mNoThanks,mWho;
    RelativeLayout mMe,mMyChild,mSomeOne;
    ImageView next,mSchedule,backImage;
    CheckBox meChk,myChildChk,someOneChk;
    AlertDialog alert;
    String appointment_start_time;
    String appointment_end_time;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        String formattedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                        .format(Calendar.getInstance()
                        .getTime());
        getCurrentTime(formattedDate);
        System.out.println("formattedDate "+formattedDate);
        setContentView(R.layout.select_medical_patient);
        initUI();
    }
    public void getCurrentTime(final String currentTime)
    {
        try {
            DateFormat inputDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date1 = inputDF.parse(currentTime);

            Calendar cal = Calendar.getInstance();
            cal.setTime(date1);

            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);
            int year = cal.get(Calendar.YEAR);
            int Hrs = cal.get(Calendar.HOUR);
            int Hrs2 = cal.get(Calendar.HOUR_OF_DAY);
            int Minutes = cal.get(Calendar.MINUTE);

            SeeDoctorData.patientId=Singleton.PatientID;
            System.out.println("PatientID "+SeeDoctorData.patientId);
            SeeDoctorData.appointmentStartTime= year+"-"+(month + 1) + "-" + day +" "+ Hrs + ":" +Minutes;
            SeeDoctorData.appointmentEndTime=year+"-"+(month + 1) + "-" + day +" "+AppUtills.AddTime(25, Hrs + ":" +Minutes );
            System.out.println("SeeDoctorData.appointmentStartTime	" + SeeDoctorData.appointmentStartTime);
            System.out.println("SeeDoctorData.appointmentEndTime	" + SeeDoctorData.appointmentEndTime);

            if(new ConnectionDetector(getApplicationContext()).isConnectingToInternet())
            {
               /* getAutoAllocatedDoctor(
                        SeeDoctorData.appointmentStartTime,
                        SeeDoctorData.appointmentEndTime, TimeZone.getDefault().getID()+"");*/
            }
            else
            {
                Toast.makeText(SeeDoctorSelectPatient.this, "You dont have Internet Connection....!", Toast.LENGTH_SHORT).show();
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    void initUI()
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
        SeeDoctorData.patient="0";

        mWho=(TextView)findViewById(R.id.who);
        String who="Who is </br>the Patient ?";
        mWho.setText(Html.fromHtml(who));
        mNoThanks=(TextView)findViewById(R.id.nothanks);
        String noThanks = "<u> No Thanks, I'm Just Browsing....</u>";
        mNoThanks.setText(Html.fromHtml(noThanks));
        mNoThanks.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(getApplicationContext(), MeetUs.class);
                startActivity(intent);

            }
        });
        meChk=(CheckBox)findViewById(R.id.meChk);
        myChildChk=(CheckBox)findViewById(R.id.myChildChk);
        someOneChk=(CheckBox)findViewById(R.id.someOneChk);
        mNext=(TextView)findViewById(R.id.next);
        mNext.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                System.out.println("check");
                AppData.appointmentType=0;

                if (SeeDoctorData.patient.equalsIgnoreCase("2"))
                {
                    ShowDialog1();
                }
                else
                {
                    Intent intent=new Intent(getApplicationContext(), SeeDoctorScheduleType.class);
                    startActivity(intent);
                }
            }
        });

        mMe=(RelativeLayout)findViewById(R.id.me_Rlt);
        meChk.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                SeeDoctorData.patient="0";

                meChk.setChecked(true);
                myChildChk.setChecked(false);
                someOneChk.setChecked(false);


            }
        });
        mMyChild=(RelativeLayout)findViewById(R.id.my_child_Rlt);
        myChildChk.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                SeeDoctorData.patient="1";
                meChk.setChecked(false);
                myChildChk.setChecked(true);
                someOneChk.setChecked(false);

            }
        });
        mSomeOne=(RelativeLayout)findViewById(R.id.some_one_Rlt);
        someOneChk.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

                SeeDoctorData.patient="2";
                meChk.setChecked(false);
                myChildChk.setChecked(false);
                someOneChk.setChecked(true);

            }
        });



    }
    public void ShowDialog1()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(SeeDoctorSelectPatient.this);
        LayoutInflater inflater = this.getLayoutInflater();
        View convertView = (View) inflater.inflate(R.layout.price_dialog, null);
        TextView title=(TextView) convertView.findViewById(R.id.title);
        title.setText("Create New Account");
        TextView txt=(TextView) convertView.findViewById(R.id.desc);
        txt.setText("So sorry for the trouble but you need to create a new account in the name of the person visiting the doctor.");
        TextView yourCost=(TextView) convertView.findViewById(R.id.yourCost);
        yourCost.setVisibility(View.INVISIBLE);
        TextView cost1Txt=(TextView) convertView.findViewById(R.id.cost1);
        cost1Txt.setVisibility(View.INVISIBLE);

        TextView cost2Txt=(TextView) convertView.findViewById(R.id.cost2);
        cost2Txt.setVisibility(View.INVISIBLE);

        TextView apply=(TextView) convertView.findViewById(R.id.apply);
        apply.setVisibility(View.INVISIBLE);
        TextView buttonTxt=(TextView) convertView.findViewById(R.id.letsgoTxt);
        apply.setText("OK!");
        RelativeLayout continuebtn=(RelativeLayout) convertView.findViewById(R.id.letsgo);

        builder.setView(convertView);
        alert = builder.create();
        alert.show();
        continuebtn.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), Home.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                alert.dismiss();
            }
        });
    }
    public void getAutoAllocatedDoctor( final String appointmentStartTime,
                              final String appointmentEndTime,
                              final String timeZone)

    {



        RequestQueue queue = Volley.newRequestQueue(SeeDoctorSelectPatient.this);
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
                                SeeDoctorSelectPatient.this.finish();
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
