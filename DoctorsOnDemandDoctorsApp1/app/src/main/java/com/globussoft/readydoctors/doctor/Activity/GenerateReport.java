package com.globussoft.readydoctors.doctor.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.globussoft.readydoctors.doctor.R;
import com.globussoft.readydoctors.doctor.uttils.ConnectionDetector;
import com.globussoft.readydoctors.doctor.uttils.ConstantUrls;
import com.globussoft.readydoctors.doctor.uttils.MainSingleton;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by GLB-276 on 2/2/2016.
 */
public class GenerateReport extends Activity
{
    ImageView backButton;
    Button submit;
    EditText instructions,prescriptions;
    ProgressBar progress;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.generate_report);
        InitView();
    }

    private void InitView()
    {
        progress = (ProgressBar) findViewById(R.id.progress);
        backButton= (ImageView) findViewById(R.id.backImage);
        backButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

        instructions= (EditText) findViewById(R.id.instructions);

        prescriptions= (EditText) findViewById(R.id.prescriptions);

        submit= (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (instructions.getText().toString().length() > 0 || prescriptions.getText().toString().length() > 0)
                {
                    if(new ConnectionDetector(getApplicationContext()).isConnectingToInternet())
                    {
                        generateReport(MainSingleton.appointment_id, MainSingleton.doctor_id, MainSingleton.patientId, prescriptions.getText().toString(), instructions.getText().toString());
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"You dont have Internet...!", Toast.LENGTH_SHORT).show();
                    }

                }
                else
                {
                    instructions.setError("Write your instructions..!");
                    prescriptions.setError("Write your prescriptions..!");
                }
            }
        });


    }
    //change apoinment status
    public void generateReport(final String appointmentId,final String doctorId,final String patientId,final String suggestedMedicines,final String instructions)
    {
        progress.setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest sr = new StringRequest(Request.Method.POST,
                ConstantUrls.UrlMain + ConstantUrls.UrlGenerateReport,
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
                                finish();
                                Toast.makeText(getApplicationContext(), json.getString("message"), Toast.LENGTH_LONG).show();

                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), json.getString("message"), Toast.LENGTH_LONG).show();
                            }

                        }
                        catch (Exception e)
                        {
                            System.out.print("Error ?");
                            finish();
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
                finish();
            }
        }) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("appointmentId", appointmentId);
                params.put("doctorId", doctorId);
                params.put("patientId", patientId);
                params.put("suggestedMedicines", suggestedMedicines);
                params.put("instructions", instructions);
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
