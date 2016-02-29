package com.globussoft.readydoctors.patient.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.globussoft.readydoctors.patient.Utills.ConstantUrls;
import com.globussoft.readydoctors.patient.Utills.Singleton;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by GLB-276 on 12/19/2015.
 */
public class UserSupport extends Activity {

    EditText emailEDT, messageEDT;
    RelativeLayout submit;
    ImageView backImage;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_support);
        initUI();
    }
    public void initUI()
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
        emailEDT=(EditText) findViewById(R.id.email);
        messageEDT=(EditText) findViewById(R.id.message);
        submit=(RelativeLayout)findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateFields();
            }
        });
    }
    public void validateFields()
    {


        if(emailEDT.getText().toString().length()>0)
        {
            if(messageEDT.getText().toString().length()>5)
            {
                submitQuery(emailEDT.getText().toString(),Singleton.PatientID, messageEDT.getText().toString());
            }
            else
            {
                messageEDT.requestFocus();
                messageEDT.setError("Please enter your query.");
            }
        }
        else
        {
            emailEDT.requestFocus();
            emailEDT.setError("Please enter your email.");
        }
    }
    public void submitQuery(final String email,final String userid,final String userquery)
    {
//        progress.setVisibility(View.VISIBLE);



        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest sr = new StringRequest(Request.Method.POST,
                ConstantUrls.UrlMain + ConstantUrls.UrlInserSupport,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {

                        System.out.println("updated patient profile response" + response);
                        try
                        {
                            JSONObject json = new JSONObject(response);
                            if (json.getString("code").equals("200"))
                            {
                                Toast.makeText(UserSupport.this, json.getString("message"), Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            }
                            else
                            {
                                Toast.makeText(UserSupport.this, json.getString("message"), Toast.LENGTH_SHORT).show();
                                emailEDT.setText("");
                                messageEDT.setText("");
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
            public void onErrorResponse(VolleyError error) {
//                progress.setVisibility(View.INVISIBLE);
            }
        }) {
            @Override
            protected Map<String, String> getParams()
            {

                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("userid", userid);
                params.put("userquery", userquery);
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