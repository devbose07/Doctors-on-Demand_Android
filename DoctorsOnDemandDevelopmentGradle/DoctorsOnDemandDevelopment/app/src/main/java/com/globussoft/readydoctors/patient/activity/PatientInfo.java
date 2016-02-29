package com.globussoft.readydoctors.patient.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by GLB-276 on 10/27/2015.
 */
public class PatientInfo extends Activity
{
    EditText firstName,lastName,address,weight,height,about;
    RelativeLayout update,cancel;
    TextView dob,label;
    String[] spinner_items = { "Gender","Male", "Female","Other" };
    String[] spinner_bloodgrp = { "Blood Group","O+","O-","A+","A-","B+","B-","AB+","AB-" };
    Spinner spinner,spinnerbloodgrp;
    int year, month, day;
    DatePicker datePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_patient_profile);
        initUI();
    }
    public void initUI()
    {
        spinner = (Spinner)findViewById(R.id.spinnerGender);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.gender_spinner_item, spinner_items);

        spinnerbloodgrp = (Spinner)findViewById(R.id.spinnerbloodgrp);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(),R.layout.gender_spinner_item, spinner_bloodgrp);

        spinner.setAdapter(adapter);
        spinnerbloodgrp.setAdapter(adapter2);

        firstName=(EditText) findViewById(R.id.firstName);
        lastName=(EditText) findViewById(R.id.lastName);
        address=(EditText) findViewById(R.id.address);
        dob=(TextView) findViewById(R.id.dob);
        weight=(EditText) findViewById(R.id.weight);
        height=(EditText) findViewById(R.id.height);


        about=(EditText) findViewById(R.id.about);
        about.setVisibility(View.GONE);
        label=(TextView) findViewById(R.id.label);

        update=(RelativeLayout)findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                validateFields();
            }
        });
        cancel=(RelativeLayout)findViewById(R.id.cancel);
        cancel.setVisibility(View.GONE);


        dob.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Calendar c = Calendar.getInstance();
                int cyear = c.get(Calendar.YEAR);
                int cmonth = c.get(Calendar.MONTH);
                int cday = c.get(Calendar.DAY_OF_MONTH);
                new DatePickerDialog(PatientInfo.this, pickerListener, cyear, cmonth, cday).show();

            }
        });


    }
    private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener(){

        // when dialog box is closed, below method will be called.
        @Override
        public void onDateSet(DatePicker view, int selectedYear,int selectedMonth, int selectedDay)
        {

            datePicker = view;

            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            // Show selected date

            dob.setText(new StringBuilder().append(year).append("/").append(month + 1).append("/").append(day).append(" "));

        }

    };
    public void validateFields()
    {
        String sex=spinner.getSelectedItem().toString();
        String blood=spinnerbloodgrp.getSelectedItem().toString();
        if(sex.equalsIgnoreCase("Male"))
        {
            sex="0";
        }
        else
        if(sex.equalsIgnoreCase("Female"))
        {
            sex="1";
        }
        else
        {}
        Calendar calendar = Calendar.getInstance();
        if(firstName.getText().toString().replace(" ", "").length()>0)
        {
            if(lastName.getText().toString().replace(" ", "").length()>0)
            {
                if(address.getText().toString().replace(" ", "").length()>0)
                {
                    if(dob.getText().toString().length()>0)
                    {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        try
                        {
                            Date strDate = sdf.parse(dob.getText().toString());
                            if ( strDate.getTime()>=System.currentTimeMillis() )
                            {
                                System.out.println("strDate.getTime() "+strDate.getTime()+" >= "+System.currentTimeMillis());
                                Toast.makeText(PatientInfo.this, "Given Date should be less then tody....!", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                System.out.println("strDate.getTime() "+strDate.getTime()+" < "+System.currentTimeMillis());
                                if(weight.getText().toString().replace(" ", "").length()>0)
                                {
                                    if(height.getText().toString().replace(" ","").length()>0)
                                    {
                                        if(!blood.equalsIgnoreCase("Blood Group"))
                                        {
                                            if (!sex.equalsIgnoreCase("Gender"))
                                            {
                                                if(new ConnectionDetector(getApplicationContext()).isConnectingToInternet())
                                                {
                                                    label.setText("Please Wait...");
                                                    updatePatientMeta(Singleton.PatientID, sex, blood);
                                                }
                                                else
                                                {
                                                    Toast.makeText(PatientInfo.this, "You dont have Internet Connection....!", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                            else
                                            {
//                                        spinner.requestFocus();
                                                Toast.makeText(PatientInfo.this, "Please select Gender", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                        else
                                        {

                                            Toast.makeText(PatientInfo.this, "Please select Blood Group", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    else
                                    {
                                        height.requestFocus();
                                        height.setError("Please enter your Height");
                                    }
                                }
                                else
                                {
                                    weight.requestFocus();
                                    weight.setError("Please enter your Weight");
                                }
                            }
                        }
                        catch (ParseException e)
                        {
                            Toast.makeText(PatientInfo.this, "Something went wrong please try again later...!", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }
                    }
                    else
                    {
                        Toast.makeText(PatientInfo.this, "Please select your Date of Birth ", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    address.requestFocus();
                    address.setError("Please enter your Address");
                }
            }
            else
            {
                lastName.requestFocus();
                lastName.setError("Please enter your Last Name");
            }
        }
        else
        {
            firstName.requestFocus();
            firstName.setError("Please enter your First Name");
        }
    }
    public void updatePatientMeta(final String patient_id,final String sex,final String blood)
    {

//        progress.setVisibility(View.VISIBLE);
        System.out.println("Patient Id " + Singleton.PatientID);

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest sr = new StringRequest(Request.Method.POST,
                ConstantUrls.UrlMain + ConstantUrls.UrlPatientMeta,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {

                        System.out.println("update patient Meta response" + response);
                        try
                        {
                            JSONObject json = new JSONObject(response);
                            if (json.getString("code").equals("200"))
                            {
                                Singleton.Info="1";
                                label.setText("Update");
                                Toast.makeText(PatientInfo.this, "Patient Information updated Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), Home.class);
                                startActivity(intent);
                                finish();
                            }
                            else
                            {
                                label.setText("Update");
                                Toast.makeText(PatientInfo.this, json.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e)
                        {
                            e.printStackTrace();
                        }
//                        progress.setVisibility(View.INVISIBLE);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                label.setText("Update");
//                progress.setVisibility(View.INVISIBLE);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("patient_id", patient_id);
                params.put("patientFirstName", firstName.getText().toString());
                params.put("patientLastName", lastName.getText().toString());
                params.put("patientAddress", address.getText().toString());
                params.put("dateOfBirth", dob.getText().toString());
                params.put("patientWeight", weight.getText().toString());
                params.put("patientHeight", height.getText().toString());
                params.put("patientBloodGroup", blood);
                params.put("sex",sex);


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