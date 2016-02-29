package com.globussoft.readydoctors.patient.pediatrics;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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
import com.globussoft.readydoctors.patient.Utills.ConnectionDetector;
import com.globussoft.readydoctors.patient.Utills.ConstantUrls;
import com.globussoft.readydoctors.patient.Utills.Singleton;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by GLB-276 on 10/7/2015.
 */
public class AddChild extends Activity
{
   public boolean hideStatus=true;
   public TextView hideTxt,next;
   public EditText prefix,firstName,midleName,lastName,suffix,dob;
   public View prefixLine,midleNameLine,suffixLine;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_child);
        InitView();
    }
    public void InitView()
    {
        next=(TextView)findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
               validateInputs();

            }
        });

        hideTxt=(TextView)findViewById(R.id.hide);
        hideTxt.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
               hide(hideStatus);

            }
        });

        prefix=(EditText)findViewById(R.id.prefix);
        firstName=(EditText)findViewById(R.id.firstName);
        midleName=(EditText)findViewById(R.id.midleName);
        lastName=(EditText)findViewById(R.id.lastName);
        suffix=(EditText)findViewById(R.id.suffix);
        dob=(EditText)findViewById(R.id.dob);

        prefixLine=(View)findViewById(R.id.prefixLine);
        midleNameLine=(View)findViewById(R.id.midleNameLine);
        suffixLine=(View)findViewById(R.id.suffixLine);

        hide(hideStatus);
    }
    public void hide(boolean hide)
    {
        if(hide)
        {
            hideTxt.setText("Expand");
            hideStatus=false;
            prefix.setVisibility(View.GONE);
            prefixLine.setVisibility(View.GONE);
            midleName.setVisibility(View.GONE);
            midleNameLine.setVisibility(View.GONE);
            suffix.setVisibility(View.GONE);
            suffixLine.setVisibility(View.GONE);
        }
        else
        {
            hideTxt.setText("Collapse");
            hideStatus=true;
            prefix.setVisibility(View.VISIBLE);
            prefixLine.setVisibility(View.VISIBLE);
            midleName.setVisibility(View.VISIBLE);
            midleNameLine.setVisibility(View.VISIBLE);
            suffix.setVisibility(View.VISIBLE);
            suffixLine.setVisibility(View.VISIBLE);
        }
    }

    public void validateInputs()
    {
        if(firstName.getText().toString().length()==0)
        {

            firstName.setError("Enter First Name");
        }
        else
        {
            if(lastName.getText().toString().length()==0)
            {

                lastName.setError("Enter Last Name");
            }
            else
            {
                if(dob.getText().toString().length()==0)
                {

                    dob.setError("Enter Date of Birth");
                }
                else
                {
                    if(new ConnectionDetector(getApplicationContext()).isConnectingToInternet())
                    {
                        addChild();
                    }
                    else
                    {
                        Toast.makeText(AddChild.this, "You dont have Internet Connection....!", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        }

    }

    public void addChild()
    {
//        progress.setVisibility(View.VISIBLE);

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest sr = new StringRequest(Request.Method.POST,
                ConstantUrls.UrlMain + ConstantUrls.UrlCreatechild,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        System.out.println("add child response" + response);
                        try {
                            JSONObject json = new JSONObject(response);


                            if (json.getString("code").equals("200"))
                            {
                                Intent intent = new Intent(getApplicationContext(), Child.class);
                                startActivity(intent);

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
//                        progress.setVisibility(View.INVISIBLE);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                progress.setVisibility(View.INVISIBLE);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("patient_id", Singleton.PatientID);
                params.put("firstName", firstName.getText().toString());
                params.put("lastName", lastName.getText().toString());
                params.put("dob", dob.getText().toString());
                params.put("sex", "male");

//                patient_id, firstName, lastName, dob, sex
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(sr);
    }
}
