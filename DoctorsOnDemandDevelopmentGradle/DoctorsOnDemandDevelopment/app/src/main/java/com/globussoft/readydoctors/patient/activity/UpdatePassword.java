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
public class UpdatePassword extends Activity {

    EditText oldPasswordEDT,newPasswordEDT;
    RelativeLayout update;
    ImageView backImage;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_user_pwd);
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
        oldPasswordEDT=(EditText) findViewById(R.id.oldPassword);
        newPasswordEDT=(EditText) findViewById(R.id.newPassword);
        update=(RelativeLayout)findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateFields();
            }
        });
    }
    public void validateFields()
    {


        if(oldPasswordEDT.getText().toString().length()>8)
        {
            if(newPasswordEDT.getText().toString().length()>8)
            {
                updatePassword(Singleton.PatientID,oldPasswordEDT.getText().toString(),newPasswordEDT.getText().toString());
            }
            else
            {
                newPasswordEDT.requestFocus();
                newPasswordEDT.setError("Please enter a valid Password (min 8 character)");
            }
        }
        else
        {
            oldPasswordEDT.requestFocus();
            oldPasswordEDT.setError("Please enter your Old Password");
        }
    }
    public void updatePassword(final String patientId,final String oldPassword,final String newPassword)
    {
//        progress.setVisibility(View.VISIBLE);



        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest sr = new StringRequest(Request.Method.POST,
                ConstantUrls.UrlMain + ConstantUrls.UrlSavePassword,
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
                                Toast.makeText(UpdatePassword.this, json.getString("message"), Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            }
                            else
                            {
                                Toast.makeText(UpdatePassword.this, json.getString("message"), Toast.LENGTH_SHORT).show();
                                oldPasswordEDT.setText("");
                                newPasswordEDT.setText("");
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
                params.put("patientId", patientId);
                params.put("oldPassword", oldPassword);
                params.put("newPassword", newPassword);
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
