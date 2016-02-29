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
import com.globussoft.readydoctors.patient.Utills.ConnectionDetector;
import com.globussoft.readydoctors.patient.Utills.ConstantUrls;
import com.globussoft.readydoctors.patient.Utills.Singleton;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by GLB-276 on 12/19/2015.
 */
public class BusinessRequirements extends Activity
{

    EditText firstName,lastName,companyName,companyMail,numPeople,organisationType,anyOtherInfo;
    RelativeLayout submit;
    ImageView backImage;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.businesss_requirements);
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

        firstName=(EditText) findViewById(R.id.firstName);
        lastName=(EditText) findViewById(R.id.lastName);
        companyName=(EditText) findViewById(R.id.companyName);
        companyMail=(EditText) findViewById(R.id.companyMail);
        numPeople=(EditText) findViewById(R.id.numPeople);
        organisationType=(EditText) findViewById(R.id.organisationType);
        anyOtherInfo=(EditText) findViewById(R.id.anyOtherInfo);

        submit=(RelativeLayout)findViewById(R.id.saveRlt);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateFields();
            }
        });
    }
    public void validateFields()
    {
        if(firstName.getText().toString().length()>0)
        {
            if(lastName.getText().toString().length()>0)
            {
                if(companyName.getText().toString().length()>0)
                {
                    if(companyMail.getText().toString().length()>0)
                    {
                        if(numPeople.getText().toString().length()>0)
                        {
                            if(organisationType.getText().toString().length()>0)
                            {
                                String fName=firstName.getText().toString();
                                String lName=firstName.getText().toString();
                                String cName=firstName.getText().toString();
                                String cMail=firstName.getText().toString();
                                String nPeople=firstName.getText().toString();
                                String oType=firstName.getText().toString();
                                String oInfo=firstName.getText().toString();
                                if(new ConnectionDetector(getApplicationContext()).isConnectingToInternet())
                                {
                                    saveBusinessRequirements(fName, lName, cName, cMail, nPeople, oType, oInfo, Singleton.PatientID);
                                }
                                else
                                {
                                    Toast.makeText(BusinessRequirements.this, "You dont have Internet Connection....!", Toast.LENGTH_SHORT).show();
                                }

                            }
                            else
                            {
                                organisationType.requestFocus();
                                organisationType.setError("Please enter thr Type of Organisation.");
                            }
                        }
                        else
                        {
                            numPeople.requestFocus();
                            numPeople.setError("Please enter Number of People.");
                        }
                    }
                    else
                    {
                        companyMail.requestFocus();
                        companyMail.setError("Please enter your Company Mail.");
                    }
                }
                else
                {
                    companyName.requestFocus();
                    companyName.setError("Please enter your Company Name.");
                }
            }
            else
            {
                lastName.requestFocus();
                lastName.setError("Please enter your Last Name.");
            }
        }
        else
        {
            firstName.requestFocus();
            firstName.setError("Please enter your First Name.");
        }
    }
    public void saveBusinessRequirements(final String userFName,final String userLName,final String CompanyName,
                                         final String companyMail,final String peopleCount,final String orgType,
                                         final String requiredinfo,final String userid)
    {

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest sr = new StringRequest(Request.Method.POST,
                ConstantUrls.UrlMain + ConstantUrls.UrlApplyBusiness,
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
                                Toast.makeText(BusinessRequirements.this, json.getString("message"), Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            }
                            else
                            {
                                Toast.makeText(BusinessRequirements.this, json.getString("message"), Toast.LENGTH_SHORT).show();

                            }
                        } catch (Exception e)
                        {
                            e.printStackTrace();
                        }
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
                params.put("userFName", userFName);
                params.put("userLName", userLName);
                params.put("CompanyName", CompanyName);
                params.put("companyMail", companyMail);
                params.put("peopleCount", peopleCount);
                params.put("orgType", orgType);
                params.put("requiredinfo", requiredinfo);
                params.put("userid", userid);


                System.out.println("userFName " +userFName);
                System.out.println("userLName "+userLName);
                System.out.println("CompanyName "+CompanyName);
                System.out.println("companyMail "+companyMail);
                System.out.println("peopleCount "+peopleCount);
                System.out.println("orgType "+orgType);
                System.out.println("requiredinfo "+requiredinfo);
                System.out.println("userid "+userid);

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