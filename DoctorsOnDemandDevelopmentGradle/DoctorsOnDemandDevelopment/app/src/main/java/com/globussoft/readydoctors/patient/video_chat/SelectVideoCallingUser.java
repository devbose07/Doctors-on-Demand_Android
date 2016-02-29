package com.globussoft.readydoctors.patient.video_chat;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by GLB-276 on 12/18/2015.
 */
public class SelectVideoCallingUser extends Activity
{
    RelativeLayout userRlt;
    TextView schemeATxt;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_calling_select_user);
        InitUI();

        if(new ConnectionDetector(getApplicationContext()).isConnectingToInternet())
        {
            getDoctorQID(AppData.appointmentsModel.getPatient_id(), AppData.appointmentsModel.getDoctor_id());
        }
        else
        {
            Toast.makeText(SelectVideoCallingUser.this, "You dont have Internet Connection....!", Toast.LENGTH_SHORT).show();
        }
    }
    public void InitUI()
    {
        schemeATxt=(TextView)findViewById(R.id.schemeATxt);
        schemeATxt.setText(Singleton.Name);
        userRlt=(RelativeLayout)findViewById(R.id.userRlt);
        userRlt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), VideoCallHome.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                SelectVideoCallingUser.this.finish();
            }
        });
    }
    public static boolean loginSuccess(final boolean login)
    {
        return login;
    }
    public void initVideoCaling()
    {
        if(new ConnectionDetector(getApplicationContext()).isConnectingToInternet())
        {
            startIncomeCallListenerService(Consts.qb_loginID, Consts.qb_password, Consts.LOGIN);
        }
        else
        {
            Toast.makeText(SelectVideoCallingUser.this, "Please check your network status and try again...!", Toast.LENGTH_SHORT).show();
            SelectVideoCallingUser.this.finish();
        }
    }
    public void startIncomeCallListenerService(String login, String password, int startServiceVariant)
    {
        System.out.println("login id "+login+" password "+password);

        Intent tempIntent = new Intent(this, IncomeCallListenerService.class);
        PendingIntent pendingIntent = createPendingResult(Consts.LOGIN_TASK_CODE, tempIntent, 0);
        Intent intent = new Intent(this, IncomeCallListenerService.class);
        intent.putExtra(Consts.USER_LOGIN, login);
        intent.putExtra(Consts.USER_PASSWORD, password);
        intent.putExtra(Consts.START_SERVICE_VARIANT, startServiceVariant);
        intent.putExtra(Consts.PARAM_PINTENT, pendingIntent);
        startService(intent);

    }
    public void getDoctorQID( final String patientId,
                              final String doctorID)
    {
        RequestQueue queue = Volley.newRequestQueue(SelectVideoCallingUser.this);
        StringRequest sr = new StringRequest(Request.Method.POST,
                ConstantUrls.UrlMain + ConstantUrls.UrlGetDoctorQID,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        System.out.println("get QID doctor response" + response);
                        try
                        {
                            JSONObject json = new JSONObject(response);
                            if (json.getString("code").equals("200"))
                            {

                                JSONObject data= json.getJSONObject("data");
                                JSONObject receiverDetails= data.getJSONObject("receiverDetails");
                                JSONObject callerDetails= data.getJSONObject("callerDetails");
                                AppData.apponantName=receiverDetails.getString("name");
                                System.out.println("receiverDetails QID "+receiverDetails.getString("qId"));
//                                Consts.second_user_id=7541146;
                                Consts.second_user_id=Integer.parseInt(receiverDetails.getString("qId"));
                                Consts.second_user_id_list.clear();
                                Consts.second_user_id_list.add(Consts.second_user_id);
                                System.out.println("callerDetails QID "+callerDetails.getString("qId"));
                                initVideoCaling();
                            }
                            else
                            {
                                Toast.makeText(SelectVideoCallingUser.this, json.getString("message"), Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            }
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
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
                params.put("patientId",patientId);
                params.put("doctorID",doctorID );


                System.out.println("patientId" + patientId);
                System.out.println("doctorID"+doctorID);

                return params;
            }
        };
        queue.add(sr);
    }
}
