package com.globussoft.readydoctors.patient.my_health;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
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
import com.globussoft.readydoctors.patient.R;
import com.globussoft.readydoctors.patient.Utills.ConnectionDetector;
import com.globussoft.readydoctors.patient.Utills.ConstantUrls;
import com.globussoft.readydoctors.patient.Utills.GPSTracker;
import com.globussoft.readydoctors.patient.model.PharmacyModels;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by GLB-276 on 10/30/2015.
 */
public class SearchPharmacy extends Activity
{
    ImageView backImage,downArrow;
    RelativeLayout retailRlt,mail_orderRlt,long_termRlt,specialityRlt,searchParmacy,search_nearby_me;
    public boolean hideStatus=true;
    public TextView hideTxt, Txt24Hr;
    EditText zipcode,name,phone;
    RadioButton chk24HR;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_pharmacies);
        initUI();
    }
    public void initUI()
    {
        zipcode=(EditText)findViewById(R.id.zipcode);
        name=(EditText)findViewById(R.id.name);
        phone=(EditText)findViewById(R.id.phone);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        searchParmacy=(RelativeLayout)findViewById(R.id.searchRlt);
        searchParmacy.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                validateInputs();


            }
        });
        backImage=(ImageView)findViewById(R.id.backImage);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        hideTxt=(TextView)findViewById(R.id.showHide);
        hideTxt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                hide(hideStatus);

            }
        });

        retailRlt=(RelativeLayout)findViewById(R.id.retailRlt);
        mail_orderRlt=(RelativeLayout)findViewById(R.id.mail_orderRlt);
        long_termRlt=(RelativeLayout)findViewById(R.id.long_termRlt);
        specialityRlt=(RelativeLayout)findViewById(R.id.specialityRlt);
        chk24HR=(RadioButton)findViewById(R.id.chk24HR);
        Txt24Hr=(TextView)findViewById(R.id.text24hr);
        downArrow=(ImageView)findViewById(R.id.downArrow);
        downArrow.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                hide(hideStatus);

            }
        });

        search_nearby_me=(RelativeLayout)findViewById(R.id.search_nearby_me);
        search_nearby_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                GPSTracker Tracker = new GPSTracker(getApplicationContext());
                if (Tracker.canGetLocation())
                {
                    Intent intent = new Intent(getApplicationContext(),LocationActivity.class);
                    startActivity(intent);
                }
                else
                {
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    showSettingsAlert();
                    System.out.println("Can't get location");
                }


            }
        });

        hide(hideStatus);

    }
    public void showSettingsAlert()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(SearchPharmacy.this);

        //Setting Dialog Title
        alertDialog.setTitle("Enable lacation settings");

        //Setting Dialog Message
        alertDialog.setMessage("Please enable the location settings");

        //On Pressing Setting button
        alertDialog.setPositiveButton("Enable", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                SearchPharmacy.this.startActivity(intent);
            }
        });

        //On pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.cancel();
            }
        });

        alertDialog.show();
    }
    public void hide(boolean hide)
    {
        if(hide)
        {
            hideTxt.setText("SHOW FILTERS");
            hideStatus=false;
            chk24HR.setVisibility(View.GONE);
            Txt24Hr.setVisibility(View.GONE);
            retailRlt.setVisibility(View.GONE);
            mail_orderRlt.setVisibility(View.GONE);
            long_termRlt.setVisibility(View.GONE);
            specialityRlt.setVisibility(View.GONE);
            downArrow.setImageResource(R.drawable.downarrow);
        }
        else
        {
            hideTxt.setText("HIDE FILTERS");
            hideStatus=true;
            retailRlt.setVisibility(View.VISIBLE);
            mail_orderRlt.setVisibility(View.VISIBLE);
            long_termRlt.setVisibility(View.VISIBLE);
            specialityRlt.setVisibility(View.VISIBLE);
            chk24HR.setVisibility(View.VISIBLE);
            Txt24Hr.setVisibility(View.VISIBLE);
            downArrow.setImageResource(R.drawable.up_arrow);
        }
    }
    public void validateInputs()
    {
        //pinCode, pharmacyName, pharmacyContactNumber
        if(zipcode.getText().toString().length()>0||name.getText().toString().length()>0||phone.getText().toString().length()>0)
        {

            if(new ConnectionDetector(getApplicationContext()).isConnectingToInternet())
            {
                searchPharmacies();
            }
            else
            {
                Toast.makeText(SearchPharmacy.this, "You dont have Internet Connection....!", Toast.LENGTH_SHORT).show();
            }

        } else
        {
            zipcode.requestFocus();
            zipcode.setError("Atleast you have to provide the zip code.");
        }


    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(),MyPharmacies.class);
        startActivity(intent);
        SearchPharmacy.this.finish();
    }

    public void searchPharmacies()
    {
        progressBar.setVisibility(View.VISIBLE);

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest sr = new StringRequest(Request.Method.POST,
                ConstantUrls.UrlMain + ConstantUrls.UrlSearchPharmacy,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {

                        System.out.println("searching pharmacy response" + response);
                        try {
                            JSONObject json = new JSONObject(response);


                            if (json.getString("code").equals("200"))
                            {
                                JSONArray array = json.getJSONArray("data");
                                SearchPharmacyResult.searchList.clear();
                                for (int i=0;i<array.length();i++)
                                {
                                    PharmacyModels model=new PharmacyModels();
                                    JSONObject obj = array.getJSONObject(i);

                                    model.setPharmacyId(obj.getString("pharmacyId"));
                                    model.setPharmacyName(obj.getString("pharmacyName"));
                                    model.setAvailabilty(obj.getString("availabilty"));
                                    model.setCity(obj.getString("city"));
                                    model.setCountry(obj.getString("country"));
                                    model.setLongTermCare(obj.getString("longTermCare"));
                                    model.setMailOrder(obj.getString("mailOrder"));
                                    model.setPhoneNumber(obj.getString("phoneNumber"));
                                    model.setState(obj.getString("state"));
                                    model.setSpeciality(obj.getString("speciality"));
                                    model.setZipcode(obj.getString("zipcode"));
                                    SearchPharmacyResult.searchList.add(model);
                                    /*model.setTimeAdded(obj.getString("timeAdded"));
                                    model.setUserId(obj.getString("userId"));
                                    model.setFavouritePharmacyId(obj.getString("favouritePharmacyId"));
                                    model.setPharmacyMailId(obj.getString("pharmacyMailId"));*/

                                }
                                if(SearchPharmacyResult.searchList.size()>0)
                                {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    Intent intent = new Intent(getApplicationContext(),SearchPharmacyResult.class);
                                    startActivity(intent);
                                }
                                else
                                {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(SearchPharmacy.this, "No details found for the given search details, Try again", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else
                            {
                                progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(SearchPharmacy.this, "No details found for the given search details, Try again", Toast.LENGTH_SHORT).show();
                            }

                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        }) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                if(zipcode.getText().toString().length()>0)
                    params.put("pinCode", zipcode.getText().toString());
                if(zipcode.getText().toString().length()>0)
                    params.put("pharmacyName", name.getText().toString());
                if(zipcode.getText().toString().length()>0)
                    params.put("pharmacyContactNumber",phone.getText().toString());

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
