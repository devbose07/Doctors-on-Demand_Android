package com.globussoft.readydoctors.patient.my_health;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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
import com.globussoft.readydoctors.patient.R;
import com.globussoft.readydoctors.patient.Utills.AppData;
import com.globussoft.readydoctors.patient.Utills.ConnectionDetector;
import com.globussoft.readydoctors.patient.Utills.ConstantUrls;
import com.globussoft.readydoctors.patient.Utills.Singleton;
import com.globussoft.readydoctors.patient.imagelib.ImageLoader;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by GLB-276 on 12/21/2015.
 */
public class ShowFavProvider extends Activity
{
    ImageView profilePic,backImage;
    TextView next,name, biography, doctorAddress, doctorSex, zipCode,label;
    RelativeLayout selected;
    ImageLoader imageLoader;
    ProgressBar progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_psychologist);
        imageLoader=new ImageLoader(getApplicationContext());

        initUI();
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
    }
    void initUI()
    {
        progress=(ProgressBar)findViewById(R.id.progress);
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
        profilePic = (ImageView) findViewById(R.id.profile_pic);

        if(AppData.favProviderModel.getDoctorProfilePicUrl().equalsIgnoreCase(""))
        {
            profilePic.setImageResource(R.drawable.head_logo);
        }
        else
        {
            imageLoader.DisplayImage(AppData.favProviderModel.getDoctorProfilePicUrl(),profilePic);
        }
//        profilePic.setBackgroundResource(R.drawable.ic_launcher);

        name = (TextView) findViewById(R.id.name);

        doctorAddress = (TextView) findViewById(R.id.doctorAddress);

        doctorSex = (TextView) findViewById(R.id.doctorSex);

        zipCode = (TextView) findViewById(R.id.zipCode);

        biography = (TextView) findViewById(R.id.biography);

        biography.setText(AppData.favProviderModel.getDoctorAbout());
        zipCode.setText(AppData.favProviderModel.getDoctorContactNumber());
        doctorSex.setText(AppData.favProviderModel.getDoctorSex());
        doctorAddress.setText(AppData.favProviderModel.getDoctorAddress());
        name.setText("Dr."+AppData.favProviderModel.getFirstName() + " "+ AppData.favProviderModel.getLastName());
        label=(TextView)findViewById(R.id.label);
        label.setText("Delete Provider");
        selected = (RelativeLayout) findViewById(R.id.selected);
        selected.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(new ConnectionDetector(getApplicationContext()).isConnectingToInternet())
                {
                    deleteProvider(Singleton.PatientID,AppData.favProviderModel.getFavouritesId());
                }
                else
                {
                    Toast.makeText(ShowFavProvider.this, "You dont have Internet Connection....!", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
    public void deleteProvider(final  String userId,final  String favouritesId)
    {
        progress.setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest sr = new StringRequest(Request.Method.POST,
                ConstantUrls.UrlMain + ConstantUrls.UrlRemoveFavoriteProviders,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        System.out.println("get delete Favorite Providers response" + response);
                        try {
                            JSONObject json = new JSONObject(response);
                            if (json.getString("code").equals("200"))
                            {
                                AppData.deletedFav=true;
                                Toast.makeText(getApplicationContext(), json.getString("message"), Toast.LENGTH_SHORT).show();
                                onBackPressed();


                            } else
                            {
                                Toast.makeText(getApplicationContext(), json.getString("message"), Toast.LENGTH_SHORT).show();

                            }

                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                        progress.setVisibility(View.INVISIBLE);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.setVisibility(View.INVISIBLE);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("userId", userId);
                params.put("favouritesId", favouritesId);

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
