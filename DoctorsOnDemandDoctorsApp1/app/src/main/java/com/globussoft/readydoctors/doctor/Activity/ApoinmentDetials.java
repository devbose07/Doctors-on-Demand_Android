package com.globussoft.readydoctors.doctor.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.globussoft.readydoctors.doctor.R;
import com.globussoft.readydoctors.doctor.models.AppointmentsModel;
import com.globussoft.readydoctors.doctor.models.MedicationModel;
import com.globussoft.readydoctors.doctor.models.SymtomsModel;
import com.globussoft.readydoctors.doctor.uttils.ConnectionDetector;
import com.globussoft.readydoctors.doctor.uttils.ConstantUrls;
import com.globussoft.readydoctors.doctor.uttils.MainSingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ApoinmentDetials extends Activity
{
//    LinearLayout status_button_layout;
    TextView purpose_reson, name, address, blood, weight;
    ListView symtoms_list, medication_list, medical_condition_list;
    ArrayList<String> allergies_array = new ArrayList<String>();
    ArrayList<SymtomsModel> symtoms_array_list = new ArrayList<SymtomsModel>();
    ArrayList<MedicationModel> medication_array_list = new ArrayList<MedicationModel>();
    ArrayList<String> medical_cond_array = new ArrayList<String>();
    ProgressBar progress;
    TextView allergies_list;
    Button generatereport,reject;
    public ArrayList<AppointmentsModel> appList = new ArrayList<AppointmentsModel>();
    RelativeLayout general_symtoms_rel, relationshipSymptoms_rel, headNeckSymptoms_rel, breastSymtoms_rel, babySymptoms_rel, nippleSymptoms_rel,
            chestSymptoms_rel, pelvisSymptoms_rel, skinSymptoms_rel, muscleSymptoms_rel;
    TextView general_Symptoms_data, relationshipSymptoms_data, headNeckSymptoms_data, breastSymtoms_data, babySymptoms_data, nippleSymptoms_data,
            chestSymptoms_data, pelvisSymptoms_data, skinSymptoms_data, muscleSymptoms_data;

    ImageView backButton;
    long startTime,endTime,currentTime;
    String givenStartDateTimeString,givenEndDateTimeString;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.appointment_details);
        InitView();
 }


    void InitView()
    {
        backButton= (ImageView) findViewById(R.id.backImage);
//        status_button_layout=(LinearLayout)findViewById(R.id.status_button_layout);
        progress = (ProgressBar) findViewById(R.id.progress);
        purpose_reson = (TextView) findViewById(R.id.purpose_reson);
        name = (TextView) findViewById(R.id.name);
        address = (TextView) findViewById(R.id.address);
        blood = (TextView) findViewById(R.id.blood);
        weight = (TextView) findViewById(R.id.weight);

        allergies_list = (TextView) findViewById(R.id.allergies_list);
        medication_list = (ListView) findViewById(R.id.medication);
        medical_condition_list = (ListView) findViewById(R.id.medical_condition_list);

        generatereport= (Button) findViewById(R.id.generatereport);
        reject= (Button) findViewById(R.id.reject);
        //medication relative
        general_symtoms_rel = (RelativeLayout) findViewById(R.id.general_symtoms_rel);
        relationshipSymptoms_rel = (RelativeLayout) findViewById(R.id.relationshipSymptoms_rel);
        headNeckSymptoms_rel = (RelativeLayout) findViewById(R.id.headNeckSymptoms_rel);
        breastSymtoms_rel = (RelativeLayout) findViewById(R.id.breastSymtoms_rel);
        babySymptoms_rel = (RelativeLayout) findViewById(R.id.babySymptoms_rel);
        nippleSymptoms_rel = (RelativeLayout) findViewById(R.id.nippleSymptoms_rel);
        chestSymptoms_rel = (RelativeLayout) findViewById(R.id.chestSymptoms_rel);
        pelvisSymptoms_rel = (RelativeLayout) findViewById(R.id.pelvisSymptoms_rel);
        skinSymptoms_rel = (RelativeLayout) findViewById(R.id.skinSymptoms_rel);
        muscleSymptoms_rel = (RelativeLayout) findViewById(R.id.muscleSymptoms_rel);

        //medication_data
        general_Symptoms_data = (TextView) findViewById(R.id.general_Symptoms_data);
        relationshipSymptoms_data = (TextView) findViewById(R.id.relationshipSymptoms_data);
        headNeckSymptoms_data = (TextView) findViewById(R.id.headNeckSymptoms_data);
        breastSymtoms_data = (TextView) findViewById(R.id.breastSymtoms_data);
        babySymptoms_data = (TextView) findViewById(R.id.babySymptoms_data);
        nippleSymptoms_data = (TextView) findViewById(R.id.nippleSymptoms_data);
        chestSymptoms_data = (TextView) findViewById(R.id.chestSymptoms_data);
        pelvisSymptoms_data = (TextView) findViewById(R.id.pelvisSymptoms_data);
        skinSymptoms_data = (TextView) findViewById(R.id.skinSymptoms_data);
        muscleSymptoms_data = (TextView) findViewById(R.id.muscleSymptoms_data);
        appList.add(MainSingleton.selectedappointmentsModel);
        System.out.println("applist size " + appList.size() + " app id " + appList.get(0).getAppointment_id());
        SetView();


        generatereport.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                System.out.println("Accept.....");
                if(new ConnectionDetector(getApplicationContext()).isConnectingToInternet())
                {
                    Intent intent = new Intent(ApoinmentDetials.this, GenerateReport.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"You dont have Internet...!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        reject.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                System.out.println("getTransactionId() "+appList.get(0).getTransactionId());
                if(appList.get(0).getTransactionId()!=null)
                {
                    if(new ConnectionDetector(getApplicationContext()).isConnectingToInternet())
                    {
                        getAccessTocken();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"You dont have Internet...!", Toast.LENGTH_SHORT).show();
                    }

                }
                else
                {
                    if(new ConnectionDetector(getApplicationContext()).isConnectingToInternet())
                    {
                        ChangeFreeAppointStatus(appList.get(0).getAppointment_id(),"0");
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"You dont have Internet...!", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        try
        {
            givenStartDateTimeString=ConvertUtcToLocal(appList.get(0).getAppointment_start_time());
            givenEndDateTimeString=ConvertUtcToLocal(appList.get(0).getAppointment_end_time());
            checkStatus();
        }
        catch(ParseException e)
        {

        }

    }
    public void checkStatus()
    {
        //2016-01-23 08:00:00
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try
        {
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println("*********Schedule timings*********** ");
            Date startDate = sdf.parse(givenStartDateTimeString);
            startTime = startDate.getTime();
            System.out.println(givenStartDateTimeString+" start_time in milli :: " + startTime);

            Date endDate = sdf.parse(givenEndDateTimeString);
            endTime = endDate.getTime();
            System.out.println(givenEndDateTimeString + " end_time in milli :: " + endTime);

            Date currentDate = sdf.parse(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    .format(Calendar.getInstance()
                            .getTime()));
            currentTime = Calendar.getInstance().getTimeInMillis();
            System.out.println(currentDate+"Current in milli :: "+currentTime);
            System.out.println("*********___________*********** ");
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        if(currentTime>endTime)
        {
            System.out.println("current is greater");
            generatereport.setVisibility(View.VISIBLE);
            reject.setVisibility(View.INVISIBLE);
        }
        else
        {
            System.out.println("current is lesser");
            generatereport.setVisibility(View.INVISIBLE);
            reject.setVisibility(View.VISIBLE);
        }
    }

    public String ConvertUtcToLocal(String originalUTCTime) throws ParseException
    {
        System.out.print("Time in UTC <"+ originalUTCTime+">");

        String format = "yyyy-MM-dd HH:mm:ss";

        SimpleDateFormat sdf = new SimpleDateFormat(format);

        Date date = sdf.parse(originalUTCTime.trim());

        long utcStamp = date.getTime();

        // Add/Substract Zone offset into UTC time
        long localTimeStamp = utcStamp + Calendar.getInstance().get(Calendar.ZONE_OFFSET);

        Timestamp timestamp = new Timestamp(localTimeStamp);

        Date finalLocatDate = new Date(timestamp.getTime());

        String finalLocalDateString = sdf.format(finalLocatDate);

        System.out.print("finalLocalDateString   "+finalLocalDateString);

        return finalLocalDateString;
    }

    void SetView()
    {
        MainSingleton.patientId=appList.get(0).getPatient_id();
        if (appList.get(0).getGeneralSymptoms() != null)
        {
            general_symtoms_rel.setVisibility(View.VISIBLE);
            general_Symptoms_data.setText(appList.get(0).getGeneralSymptoms());
        }

        if (appList.get(0).getRelationshipSymptoms() != null)
        {
            relationshipSymptoms_rel.setVisibility(View.VISIBLE);
            relationshipSymptoms_data.setText(appList.get(0).getRelationshipSymptoms());
        }
        if (appList.get(0).getHeadNeckSymptoms() != null)
        {
            headNeckSymptoms_rel.setVisibility(View.VISIBLE);
            headNeckSymptoms_data.setText(appList.get(0).getHeadNeckSymptoms());
        }
        if (appList.get(0).getBreastSymtoms() != null) {
            breastSymtoms_rel.setVisibility(View.VISIBLE);
            breastSymtoms_data.setText(appList.get(0).getBreastSymtoms());
        }
        if (appList.get(0).getBabySymptoms() != null) {
            babySymptoms_rel.setVisibility(View.VISIBLE);
            babySymptoms_data.setText(appList.get(0).getBabySymptoms());
        }
        if (appList.get(0).getNippleSymptoms() != null) {
            nippleSymptoms_rel.setVisibility(View.VISIBLE);
            nippleSymptoms_data.setText(appList.get(0).getNippleSymptoms());
        }
        if (appList.get(0).getChestSymptoms() != null) {
            chestSymptoms_rel.setVisibility(View.VISIBLE);
            chestSymptoms_data.setText(appList.get(0).getChestSymptoms());
        }

        if (appList.get(0).getPelvisSymptoms() != null) {
            pelvisSymptoms_rel.setVisibility(View.VISIBLE);
            pelvisSymptoms_data.setText(appList.get(0).getPelvisSymptoms());
        }

        if (appList.get(0).getSkinSymptoms() != null) {
            skinSymptoms_rel.setVisibility(View.VISIBLE);
            skinSymptoms_data.setText(appList.get(0).getSkinSymptoms());
        }

        if (appList.get(0).getMuscleSymptoms() != null) {
            muscleSymptoms_rel.setVisibility(View.VISIBLE);
            muscleSymptoms_data.setText(appList.get(0).getMuscleSymptoms());
        }


        //visit pupose
        purpose_reson.setText(appList.get(0).getPurposeOfVisit());
        allergies_list.setText(appList.get(0).getAllergies());
        //set user details
        name.setText(appList.get(0).getPatientFirstName() + " " + appList.get(0).getPatientLastName());
        address.setText(appList.get(0).getPatientAddress());
        blood.setText(appList.get(0).getPatientBloodGroup());
        weight.setText(appList.get(0).getPatientWeight());

    }
    //change apoinment status
    public void ChangeAppointStatus(final String appointment_id, final String status)
    {

        progress.setVisibility(View.VISIBLE);
        appList.clear();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest sr = new StringRequest(Request.Method.POST,
                ConstantUrls.UrlMain + ConstantUrls.UrlChangeApointmentStatus,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {

                        System.out.println("apoinment status response" + response);
                        try
                        {
                            JSONObject json = new JSONObject(response);
                            if (json.getString("code").equals("200"))
                            {
                                Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
//                                status_button_layout.setVisibility(View.INVISIBLE);
                            }
                            else
                            {

                            }

                        } catch (Exception e)
                        {
                            System.out.print("Error ?");

                            e.printStackTrace();
                        }
                        progress.setVisibility(View.INVISIBLE);
                    }
                }, new Response.ErrorListener()
            {
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    progress.setVisibility(View.INVISIBLE);
                }
            })
            {
                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("appointmentId", appointment_id);
                    params.put("status", status);
                    params.put("refundtranctionid", "0");
                    params.put("refundamount", "0");
                    params.put("refundack", "0");
                    params.put("refunddate", "0");

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
    //change free apoinment status
    public void ChangeFreeAppointStatus(final String appointmentId, final String status)
    {

        progress.setVisibility(View.VISIBLE);
        appList.clear();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest sr = new StringRequest(Request.Method.POST,
                ConstantUrls.UrlMain + ConstantUrls.UrlChangeApointmentStatus,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {

                        System.out.println("apoinment ChangeFreeAppointStatus response" + response);
                        try
                        {
                            JSONObject json = new JSONObject(response);
                            if (json.getString("code").equals("200"))
                            {
                                Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();

                            }
                            else
                            {

                            }

                        } catch (Exception e)
                        {
                            System.out.print("Error ?");

                            e.printStackTrace();
                        }
                        progress.setVisibility(View.INVISIBLE);
                    }
                }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                progress.setVisibility(View.INVISIBLE);
            }
        })
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                //,,,
                params.put("appointmentId", appointmentId);
                params.put("status", status);
                params.put("refundtranctionid", "0");
                params.put("refundamount", "0");
                params.put("refundack", "0");
                params.put("refunddate", "0");

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

    //CALL FOR PAYMENT REFUND
    public void refundpayment(final String transactionID,final String accessToken)
    {

        progress.setVisibility(View.VISIBLE);
//        appList.clear();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest sr = new StringRequest(Request.Method.POST,"https://api.sandbox.paypal.com/v1/payments/sale/"+transactionID+"/refund",
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {

                        System.out.println("apointment paypal refund response" + response);
                        try
                        {
                            JSONObject json = new JSONObject(response);
                            if (json.getString("code").equals("200"))
                            {
                                Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
//                                status_button_layout.setVisibility(View.INVISIBLE);
//                                ChangeAppointStatus(appList.get(0).getAppointment_id(),"0");
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"failed to refund",Toast.LENGTH_LONG).show();
                                System.out.print("code ? "+json.getString("code"));
//                                status_button_layout.setVisibility(View.INVISIBLE);
                            }

                        }
                        catch (Exception e)
                        {
                            System.out.print("Exception ? "+e);

                            e.printStackTrace();
                        }
                        progress.setVisibility(View.INVISIBLE);
                    }
                }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                System.out.print("onErrorResponse VolleyError "+error.getMessage());
                progress.setVisibility(View.INVISIBLE);
            }
        })
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                params.put("Authorization", "Bearer "+accessToken);
                return params;
            }
        };
        queue.add(sr);
    }

    //Get Access Token
    public void getAccessTocken()
    {

        progress.setVisibility(View.VISIBLE);
//        appList.clear();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest sr = new StringRequest(Request.Method.POST,
                ConstantUrls.UrlMain + ConstantUrls.UrlGetAccessTocken,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {

                        System.out.println("GetAccessTocken response" + response);
                        try
                        {
                            JSONObject json = new JSONObject(response);
                            if (json.getString("code").equals("200"))
                            {
                                Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
                                JSONObject data = json.getJSONObject("data");
                                String accessTocken=data.getString("access_token");

                                refundpayment(appList.get(0).getTransactionId(), accessTocken);
                            }
                            else
                            {

                            }

                        }
                        catch (Exception e)
                        {
                            System.out.print("Error ?");

                            e.printStackTrace();
                        }
                        progress.setVisibility(View.INVISIBLE);
                    }
                }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                progress.setVisibility(View.INVISIBLE);
            }
        }) {
           /* @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("appointmentId", appointment_id);
                params.put("status", status);

                return params;
            }*/

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                params.put("Authorization", "Bearer {accessToken}");
                return params;
            }
        };
        queue.add(sr);
    }














    String GetJsonDate(JSONObject obj, String key) throws JSONException {
        String data = null;
        if (obj.has(key)) {
            if (!obj.getString(key).equals("null"))
                data = obj.getString(key);
        }


        return data;

    }




}
