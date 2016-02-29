package com.globussoft.readydoctors.patient.video_chat;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.quickblox.videochat.webrtc.QBRTCTypes;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by GLB-276 on 12/18/2015.
 */
public class VideoCallHome extends Activity
{
    RelativeLayout submit;
    ImageView add_to_fav,rate1,rate2,rate3,rate4,rate5;
    TextView make_call,drName,title;
    EditText editbox;
    int rate=0;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_calling_home);
        InitUI();
        System.out.println("select user");
    }
    public void InitUI()
    {
        title=(TextView)findViewById(R.id.title);
        title.setText("Logged in as "+Singleton.Name);
        drName=(TextView)findViewById(R.id.textView1);
        drName.setText(AppData.apponantName);
        editbox=(EditText)findViewById(R.id.editText1);
        editbox.setHint("Enter your Review");
        submit=(RelativeLayout)findViewById(R.id.submitRlt);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (editbox.getText().toString().length()<1)
                {
                    editbox.setError("Please specify the reason");
                }
                else
                {
                   if(rate==0)
                   {
                       Toast.makeText(getApplicationContext(),"Please rate the review....!",Toast.LENGTH_SHORT).show();
                   }
                    else
                   {
                       Toast.makeText(getApplicationContext(),"review submitted successfully....!",Toast.LENGTH_SHORT).show();
                       String appointmentId=AppData.appointmentsModel.getAppointment_id();
                       String ratings=rate+"";
                       String review=editbox.getText().toString();
                       String doctorId=AppData.appointmentsModel.getDoctor_id()+"";
                       String patientId=AppData.appointmentsModel.getPatient_id()+"";
                       if(new ConnectionDetector(getApplicationContext()).isConnectingToInternet())
                       {
                           giveRatings(appointmentId, ratings, review, doctorId, patientId);
                       }
                       else
                       {
                           Toast.makeText(VideoCallHome.this, "You dont have Internet Connection....!", Toast.LENGTH_SHORT).show();
                       }

                   }
                }

            }

        });

        make_call=(TextView)findViewById(R.id.make_call);
        make_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(new ConnectionDetector(getApplicationContext()).isConnectingToInternet())
                {
                    makeCall();
                }
                else
                {
                    Toast.makeText(VideoCallHome.this, "You dont have Internet Connection....!", Toast.LENGTH_SHORT).show();
                }

            }

        });
        rate1=(ImageView)findViewById(R.id.rate1);
        rate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupRatings(rate=1);
            }

        });
        rate2=(ImageView)findViewById(R.id.rate2);
        rate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupRatings(rate=2);
            }

        });
        rate3=(ImageView)findViewById(R.id.rate3);
        rate3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupRatings(rate=3);
            }

        });
        rate4=(ImageView)findViewById(R.id.rate4);
        rate4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupRatings(rate=4);
            }

        });
        rate5=(ImageView)findViewById(R.id.rate5);
        rate5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupRatings(rate=5);
            }

        });
        add_to_fav=(ImageView)findViewById(R.id.add_to_fav);
        add_to_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(new ConnectionDetector(getApplicationContext()).isConnectingToInternet())
                {
                    addToFavorite(AppData.appointmentsModel.getPatient_id(), AppData.appointmentsModel.getDoctor_id());
                }
                else
                {
                    Toast.makeText(VideoCallHome.this, "You dont have Internet Connection....!", Toast.LENGTH_SHORT).show();
                }

            }

        });


    }
    public void setupRatings(int rate)
    {
        switch (rate)
        {
            case 1:
                rate1.setImageResource(R.drawable.rating_menu_selected);
                rate2.setImageResource(R.drawable.rating_menu);
                rate3.setImageResource(R.drawable.rating_menu);
                rate4.setImageResource(R.drawable.rating_menu);
                rate5.setImageResource(R.drawable.rating_menu);
                break;
            case 2:
                rate1.setImageResource(R.drawable.rating_menu_selected);
                rate2.setImageResource(R.drawable.rating_menu_selected);
                rate3.setImageResource(R.drawable.rating_menu);
                rate4.setImageResource(R.drawable.rating_menu);
                rate5.setImageResource(R.drawable.rating_menu);
                break;
            case 3:
                rate1.setImageResource(R.drawable.rating_menu_selected);
                rate2.setImageResource(R.drawable.rating_menu_selected);
                rate3.setImageResource(R.drawable.rating_menu_selected);
                rate4.setImageResource(R.drawable.rating_menu);
                rate5.setImageResource(R.drawable.rating_menu);
                break;
            case 4:
                rate1.setImageResource(R.drawable.rating_menu_selected);
                rate2.setImageResource(R.drawable.rating_menu_selected);
                rate3.setImageResource(R.drawable.rating_menu_selected);
                rate4.setImageResource(R.drawable.rating_menu_selected);
                rate5.setImageResource(R.drawable.rating_menu);
                break;
            case 5:
                rate1.setImageResource(R.drawable.rating_menu_selected);
                rate2.setImageResource(R.drawable.rating_menu_selected);
                rate3.setImageResource(R.drawable.rating_menu_selected);
                rate4.setImageResource(R.drawable.rating_menu_selected);
                rate5.setImageResource(R.drawable.rating_menu_selected);
                break;

        }

    }

    public void makeCall()
    {
        QBRTCTypes.QBConferenceType qbConferenceType = null;

        try
        {
            qbConferenceType = QBRTCTypes.QBConferenceType.QB_CONFERENCE_TYPE_VIDEO;

            Map<String, String> userInfo = new HashMap<>();
            userInfo.put("any_custom_data", "some data");
            userInfo.put("my_avatar_url", "avatar_reference");

            CallActivity.start(getApplicationContext(),
                    qbConferenceType,
                    Consts.second_user_id_list,
                    userInfo,
                    Consts.CALL_DIRECTION_TYPE.OUTGOING);
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), "Something went wrong please try again later..!", Toast.LENGTH_LONG).show();
        }
    }
    public void addToFavorite( final String userId,final String doctorId)
    {
        RequestQueue queue = Volley.newRequestQueue(VideoCallHome.this);
        StringRequest sr = new StringRequest(Request.Method.POST,
                ConstantUrls.UrlMain + ConstantUrls.UrlAddFavoriteProviders,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {


                        System.out.println("Add doctor response" + response);
                        try
                        {
                            JSONObject json = new JSONObject(response);
                            if (json.getString("code").equals("200"))
                            {
                                Toast.makeText(getApplicationContext(),json.getString("message"), Toast.LENGTH_LONG).show();
                                /*Intent intent = new Intent(getApplicationContext(), SelectVideoCallingUser.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                SelectVideoCallingUser.this.finish();*/
                                add_to_fav.setImageResource(R.drawable.favorite_selected);

                            }
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
//						progressBar.setVisibility(View.INVISIBLE);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
//						progressBar.setVisibility(View.INVISIBLE);
                        System.out.println("error " + error.getMessage());
                    }
                })
        {
            @Override
            protected Map<String, String> getParams()
            {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("userId",userId);
                params.put("doctorId",doctorId );




                System.out.println("userId" + userId);
                System.out.println("doctorId"+doctorId);

                return params;
            }
        };
        queue.add(sr);
    }
    public void giveRatings(final String appointmentId,final String ratings,final String review,final String doctorId,final String patientId)
    {
        RequestQueue queue = Volley.newRequestQueue(VideoCallHome.this);
        StringRequest sr = new StringRequest(Request.Method.POST,
                ConstantUrls.UrlMain + ConstantUrls.UrlGiveRatings,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {


                        System.out.println("Add doctor response" + response);
                        try
                        {
                            JSONObject json = new JSONObject(response);
                            if (json.getString("code").equals("200"))
                            {
                                Toast.makeText(getApplicationContext(),json.getString("message"), Toast.LENGTH_LONG).show();
                                editbox.setText("");
                                editbox.setHint("Enter your Review");
                                rate1.setImageResource(R.drawable.rating_menu);
                                rate2.setImageResource(R.drawable.rating_menu);
                                rate3.setImageResource(R.drawable.rating_menu);
                                rate4.setImageResource(R.drawable.rating_menu);
                                rate5.setImageResource(R.drawable.rating_menu);
                                rate=0;
                            }
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
//						progressBar.setVisibility(View.INVISIBLE);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
//						progressBar.setVisibility(View.INVISIBLE);
                        System.out.println("error " + error.getMessage());
                    }
                })
        {
            @Override
            protected Map<String, String> getParams()
            {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("appointmentId",appointmentId);
                params.put("ratings",ratings );
                params.put("review",review);
                params.put("doctorId",doctorId );
                params.put("patientId",patientId );

                System.out.println("appointmentId" + appointmentId);
                System.out.println("ratings"+ratings);
                System.out.println("review"+review);
                System.out.println("doctorId"+doctorId);
                System.out.println("patientId"+patientId);

                return params;
            }
        };
        queue.add(sr);
    }
}
