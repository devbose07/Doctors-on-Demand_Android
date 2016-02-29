package com.globussoft.readydoctors.patient.my_health;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

import com.globussoft.readydoctors.patient.Utills.AppData;
import com.globussoft.readydoctors.patient.Utills.ConnectionDetector;
import com.globussoft.readydoctors.patient.Utills.ConstantUrls;
import com.globussoft.readydoctors.patient.Utills.Singleton;
import com.globussoft.readydoctors.patient.adapter.FavProviderAdapter;
import com.globussoft.readydoctors.patient.model.FavProviderModel;
import com.globussoft.readydoctors.patient.R;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by GLB-276 on 10/30/2015.
 */
public class MyFavoriteProviders extends Activity
{
    ImageView backImage;
    RelativeLayout visit_history,messages,my_favorite,my_pharmacies;
    ArrayList<FavProviderModel> providersList=new ArrayList<FavProviderModel>();
    ProgressBar progress;
    ListView list;
    TextView no_favorite_doctors;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_favorite_providers);
        AppData.deletedFav=false;
        initUI();
        if(new ConnectionDetector(getApplicationContext()).isConnectingToInternet())
        {

                getFavoriteProviders(Singleton.PatientID, "" + 0, "" + 20);
        }
        else
        {
            Toast.makeText(MyFavoriteProviders.this, "You dont have Internet Connection....!", Toast.LENGTH_SHORT).show();
        }

    }
    public void initUI()
    {
        progress=(ProgressBar)findViewById(R.id.progress);
        backImage=(ImageView)findViewById(R.id.backImage);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        no_favorite_doctors=(TextView)findViewById(R.id.no_favorite_doctors);
        list=(ListView)findViewById(R.id.list);
        list.setOnItemClickListener(new selectProvider());
    }
    private class selectProvider implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,long id)
        {
            AppData.favProviderModel = providersList.get(position);

            Intent intent = new Intent(getApplicationContext(),ShowFavProvider.class);
            startActivity(intent);

        }
    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();

    }
    @Override
    protected void onResume()
    {
        System.out.println("i am in onrewume");
        super.onResume();

        if(new ConnectionDetector(getApplicationContext()).isConnectingToInternet())
        {
            if(AppData.deletedFav)
            {
                getFavoriteProviders(Singleton.PatientID, "" + 0, "" + 20);
                AppData.deletedFav=false;
            }
        }
        else
        {
            Toast.makeText(MyFavoriteProviders.this, "You dont have Internet Connection....!", Toast.LENGTH_SHORT).show();
        }
    }
    //userId, offset, limit
    public void getFavoriteProviders(final  String userId,final String offset,final String limit)
    {
        progress.setVisibility(View.VISIBLE);
        providersList.clear();

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest sr = new StringRequest(Request.Method.POST,
                ConstantUrls.UrlMain + ConstantUrls.UrlGetFavoriteProviders,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        System.out.println("get Favorite Providers response" + response);
                        try
                        {
                            JSONObject json = new JSONObject(response);
                            if (json.getString("code").equals("200"))
                            {

                                JSONArray data = json.getJSONArray("data");
                                for (int i = 0; i < data.length(); i++)
                                {
                                    JSONObject obj = data.getJSONObject(i);
                                    FavProviderModel model = new FavProviderModel();

                                    model.setFavouritesId(obj.getString("favouritesId"));
                                    model.setDoctorId(obj.getString("doctorId"));
                                    model.setPatientId(obj.getString("patientId"));
                                    model.setCreatedAt(obj.getString("createdAt"));
                                    model.setMeta_id(obj.getString("meta_id"));
                                    model.setDoctor_id(obj.getString("doctor_id"));
                                    model.setFirstName(obj.getString("FirstName"));
                                    model.setLastName(obj.getString("LastName"));
                                    model.setDepartmentId(obj.getString("departmentId"));
                                    model.setDoctorAddress(obj.getString("doctorAddress"));
                                    model.setDoctorContactNumber(obj.getString("doctorContactNumber"));
                                    model.setDoctorOnlineStatus(obj.getString("doctorOnlineStatus"));
                                    model.setDoctorAvailableStatus(obj.getString("doctorAvailableStatus"));
                                    model.setDoctorSex(obj.getString("doctorSex"));
                                    model.setDoctorAbout(obj.getString("doctorAbout"));
                                    model.setDoctorAvailableFrom(obj.getString("doctorAvailableFrom"));
                                    model.setDoctorAvailableTo(obj.getString("doctorAvailableTo"));
                                    model.setLatitude(obj.getString("latitude"));
                                    model.setLongitude(obj.getString("longitude"));
                                    model.setDoctorRegistrationDate(obj.getString("doctorRegistrationDate"));
                                    model.setDoctorProfilePicUrl(obj.getString("doctorProfilePicUrl"));
                                    model.setDoctorStatus(obj.getString("doctorStatus"));
                                    model.setUpdated_at(obj.getString("updated_at"));
                                    model.setCreated_at(obj.getString("created_at"));


                                    providersList.add(model);
                                }
                                if(providersList.size()>0)
                                {
                                    FavProviderAdapter adapter = new FavProviderAdapter(getApplicationContext(), providersList);
                                    list.setAdapter(adapter);
                                    no_favorite_doctors.setVisibility(View.INVISIBLE);
                                    list.setVisibility(View.VISIBLE);
                                }
                                else
                                {
                                    no_favorite_doctors.setVisibility(View.VISIBLE);
                                    list.setVisibility(View.INVISIBLE);
                                    Toast.makeText(getApplicationContext(), json.getString("message"), Toast.LENGTH_SHORT).show();
                                }


                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "No Favorite Providers", Toast.LENGTH_SHORT).show();
                                no_favorite_doctors.setVisibility(View.VISIBLE);
                            }

                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Something went wrong...!", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }
                        progress.setVisibility(View.INVISIBLE);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {

                Toast.makeText(getApplicationContext(), "Something went wrong...!", Toast.LENGTH_SHORT).show();
               onBackPressed();
        }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("userId", userId);
                params.put("offset", offset);
                params.put("limit", limit);

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
