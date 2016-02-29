package com.globussoft.readydoctors.patient.pediatrics;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
import com.globussoft.readydoctors.patient.Utills.ConnectionDetector;
import com.globussoft.readydoctors.patient.Utills.ConstantUrls;
import com.globussoft.readydoctors.patient.Utills.Singleton;
import com.globussoft.readydoctors.patient.activity.Home;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by GLB-276 on 12/11/2015.
 */
public class PediatricsScheme extends Activity
{
    RelativeLayout schemeA,schemeB;
    TextView schemeATxt,schemeBTxt,title;
    String cost1=" minutes for ",cost2=" minutes for ";
    TextView header,next;
    String callcost1,calltime1,creditremains1,needtopay1,planid1,callcost2,calltime2,creditremains2,needtopay2,planid2;
    ImageView backImage;
    public boolean gotCost=false;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.select_psychology_scheme);
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

        next=(TextView)findViewById(R.id.next);
        next.setVisibility(View.INVISIBLE);
        if(new ConnectionDetector(getApplicationContext()).isConnectingToInternet())
        {
            GetCalCost(Singleton.PatientID, "" + AppData.appointmentType);
        }
        else
        {
            Toast.makeText(PediatricsScheme.this, "You dont have Internet Connection....!", Toast.LENGTH_SHORT).show();
        }

        title=(TextView)findViewById(R.id.edittext);
        title.setText("APPOINTMENT DURATION");
        header=(TextView)findViewById(R.id.textView1);
        header.setText("How long whould you like to meet with your physician ?");
        schemeATxt=(TextView)findViewById(R.id.schemeATxt);
//        schemeATxt.setText(cost1);

        schemeBTxt=(TextView)findViewById(R.id.schemeBTxt);
//        schemeBTxt.setText(cost2);

        schemeA=(RelativeLayout)findViewById(R.id.schemeA);
        schemeA.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(gotCost)
                {
                    PediatricData.callcost=callcost1;
                    PediatricData.calltime=calltime1;
                    AppData.scheduleTiming=Integer.parseInt(PediatricData.calltime);
                    PediatricData.creditremains=creditremains1;
                    PediatricData.needtopay=needtopay1;
                    PediatricData.planid=planid1;
                    Intent intent = new Intent(getApplicationContext(),PediatricsCalendar.class);
                    startActivity(intent);

                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Please wait...",Toast.LENGTH_SHORT).show();
                }

            }
        });

        schemeB=(RelativeLayout)findViewById(R.id.schemeB);
        schemeB.setVisibility(View.INVISIBLE);
        schemeB.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                PediatricData.callcost=callcost2;
                PediatricData.calltime=calltime2;
                AppData.scheduleTiming=Integer.parseInt(PediatricData.calltime);
                PediatricData.creditremains=creditremains2;
                PediatricData.needtopay=needtopay2;
                PediatricData.planid=planid2;
                Intent intent = new Intent(getApplicationContext(),PediatricsCalendar.class);
                startActivity(intent);
            }
        });

    }
    public void GetCalCost(final String id,final String did)
    {
        RequestQueue queue = Volley.newRequestQueue(PediatricsScheme.this);
        StringRequest sr = new StringRequest(Request.Method.POST,
                ConstantUrls.UrlMain + ConstantUrls.UrlGetUserCalCost,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {

                        System.out.println("getuser cal cost" + response);
                        try
                        {
                            JSONObject json = new JSONObject(response);
                            if (json.getString("code").equals("200"))
                            {
                                JSONArray array=new  JSONArray();
                                if(json.has("data"))
                                {
                                    array=new JSONArray(json.getString("data").toString());

                                    String time	=array.getJSONObject(0).getString("calltime");
                                    String cost =array.getJSONObject(0).getString("callcost");
                                    callcost1=array.getJSONObject(0).getString("callcost");
                                    calltime1=array.getJSONObject(0).getString("calltime");
                                    creditremains1=array.getJSONObject(0).getString("creditremains");
                                    needtopay1=array.getJSONObject(0).getString("needtopay");
                                    planid1=array.getJSONObject(0).getString("pid");
                                    cost1=time+" minutes for $"+cost;

                                    String time_1 =array.getJSONObject(1).getString("calltime");
                                    String cost_1 =array.getJSONObject(1).getString("callcost");
                                    callcost2=array.getJSONObject(1).getString("callcost");
                                    calltime2=array.getJSONObject(1).getString("calltime");
                                    creditremains2=array.getJSONObject(1).getString("creditremains");
                                    needtopay2=array.getJSONObject(1).getString("needtopay");
                                    planid2=array.getJSONObject(1).getString("pid");
                                    cost2=time_1+" minutes for $"+cost_1;

                                    System.out.println(cost1);
                                    System.out.println(cost2);

                                    schemeATxt.setText(cost1);
                                    schemeBTxt.setText(cost2);
                                    gotCost=true;
                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(), "Something went wrong..please try again later..!", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getApplicationContext(), Home.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();
                                }

                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "Something went wrong..please try again later..!", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), Home.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            }

                        } catch (Exception e)
                        {
                            e.printStackTrace();

                                Toast.makeText(getApplicationContext(), "Something went wrong..please try again later..!", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), Home.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();

                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {

                        System.out.println("error "+error.getMessage());

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
                params.put("id", id);
                params.put("did", did);


                return params;
            }


        };
        queue.add(sr);
    }
}