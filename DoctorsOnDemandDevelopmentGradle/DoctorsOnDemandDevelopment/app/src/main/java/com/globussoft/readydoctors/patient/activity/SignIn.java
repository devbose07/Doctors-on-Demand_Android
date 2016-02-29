package com.globussoft.readydoctors.patient.activity;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
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
import com.globussoft.readydoctors.patient.video_chat.Consts;
import com.globussoft.readydoctors.patient.video_chat.IncomeCallListenerService;

public class SignIn extends Activity
{

	EditText email_field, password_field;
	ProgressBar progress;
	Button signin_btn;
	TextView forgotPwd;
	RelativeLayout recoverylayout;
	 Button clickhere;
	View line;

	EditText recoverymail;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign_in);
		initUI();
	}

	void initUI()
	{
		line = findViewById(R.id.line);
		clickhere=(Button) findViewById(R.id.clickhere);
		clickhere.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				if (recoverymail.getText().toString().length()>0)
				{
					if(new ConnectionDetector(getApplicationContext()).isConnectingToInternet())
					{
						RecoveryPassRequest(recoverymail.getText().toString().trim());
					}
					else
					{
						Toast.makeText(getApplicationContext(),"You dont have Internet...!", Toast.LENGTH_SHORT).show();
					}
				}
				else
				{
					Toast.makeText(getApplicationContext(),"Please enter your registered Email", Toast.LENGTH_SHORT).show();
				}
			}
		});
		recoverymail=(EditText) findViewById(R.id.recoverymail);
		recoverylayout = (RelativeLayout) findViewById(R.id.recoverlayout);
		forgotPwd = (TextView) findViewById(R.id.iforgot);
		forgotPwd.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				recoverylayout.setVisibility(View.VISIBLE);
				line.setVisibility(View.INVISIBLE);
				forgotPwd.setVisibility(View.INVISIBLE);
			}
		});
		email_field = (EditText) findViewById(R.id.email);
//		email_field.setText("hellotest1@dhara.com");
		password_field = (EditText) findViewById(R.id.pass);
//		password_field.setText("Dharasis17");
		progress = (ProgressBar) findViewById(R.id.progress);
		signin_btn = (Button) findViewById(R.id.signin_btn);

		signin_btn.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
				Matcher m = p.matcher(email_field.getText().toString());
				boolean matchFound = m.matches();
				if (email_field.getText().toString().length()>0&&password_field.getText().toString().length()>7&&matchFound)
				{
					if (new ConnectionDetector(getApplicationContext()).isConnectingToInternet())
					{
						SignInRequest(email_field.getText().toString(), password_field.getText().toString());
					}
					else
					{
						Toast.makeText(SignIn.this, "You dont have Internet Connection....!", Toast.LENGTH_SHORT).show();
					}
				}
				else
				{
					Toast.makeText(SignIn.this, "Please double check the login details..!", Toast.LENGTH_SHORT).show();
				}


			}
		});

	}

	@Override
	public void onBackPressed()
	{
		Intent intent = new Intent(getApplicationContext(), LandingScreen.class);
		startActivity(intent);
		SignIn.this.finish();
	}

	public void SignInRequest(final String email, final String pass)
	{
		progress.setVisibility(View.VISIBLE);

		RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
		StringRequest sr = new StringRequest(Request.Method.POST,
				ConstantUrls.UrlMain + ConstantUrls.UrlLogin,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						
						System.out.println("login response" + response);
						try {
							JSONObject json = new JSONObject(response);


							if (json.getString("code").equals("200"))
							{
								AppData.loginStatus=true;
								JSONObject data =json.getJSONObject("data");
								//assign email with qb user id
								Consts.qb_loginID=data.getString("email");
								Singleton.PatientID=data.getString("id");
								Singleton.Name=data.getString("name");
								Singleton.Email=data.getString("email");
								Singleton.TimeZone=data.getString("timeZone");
								Singleton.Dateofbirth=data.getString("dateofbirth");
								Singleton.ActivationKey=data.getString("activationKey");
								Singleton.Status=data.getString("status");
								Singleton.Updated_at=data.getString("updated_at");
								Singleton.Created_at=data.getString("created_at");
								Singleton.Role=data.getString("role");
								Singleton.RegistrationDate=data.getString("RegistrationDate");
								Singleton.PromoCode=data.getString("promoCode");
								Singleton.UsedCoupons=data.getString("usedCoupons");
								Singleton.Credit=data.getString("credit");
								Singleton.FreeMinutes=data.getString("freeMinutes");
								Singleton.Currency=data.getString("currency");
								Singleton.Info=data.getString("info");

								//saving login details to enable automatic login
								saveLoginInstance();


								System.out.println("Singleton.PatientID -" + Singleton.PatientID);
								System.out.println("Singleton.Name -" + Singleton.Name);
								System.out.println("Singleton.Email -" + Singleton.Email);
								System.out.println("Singleton.TimeZone -" + Singleton.TimeZone);
								System.out.println("Singleton.Dateofbirth -" + Singleton.Dateofbirth);
								System.out.println("Singleton.ActivationKey -" + Singleton.ActivationKey);
								System.out.println("Singleton.Status -" + Singleton.Status);
								System.out.println("Singleton.Updated_at -" + Singleton.Updated_at);
								System.out.println("Singleton.Created_at -" + Singleton.Created_at);
								System.out.println("Singleton.Role -" + Singleton.Role);
								System.out.println("Singleton.RegistrationDate -" + Singleton.RegistrationDate);
								System.out.println("Singleton.PromoCode -" + Singleton.PromoCode);
								System.out.println("Singleton.UsedCoupons -" + Singleton.UsedCoupons);
								System.out.println("Singleton.Credit -" + Singleton.Credit);
								System.out.println("Singleton.FreeMinutes -" + Singleton.FreeMinutes);
								System.out.println("Singleton.Currency -" + Singleton.Currency);
								System.out.println("Singleton.Info -" + Singleton.Info);
								

//								initVideoCaling();
								if(Singleton.Info.equalsIgnoreCase("0"))
								{
									Intent intent = new Intent(getApplicationContext(), PatientInfo.class);
									startActivity(intent);
									finish();
								}
								else
								{
									Intent intent = new Intent(getApplicationContext(), Home.class);
									intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
									startActivity(intent);
									finish();
								}
							}
							else
							{
								Toast.makeText(SignIn.this, json.getString("message"), Toast.LENGTH_SHORT).show();
							}
						} catch (Exception e)
						{
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
						Toast.makeText(getApplicationContext(), "Something went wrong..please try again later..!", Toast.LENGTH_LONG).show();
					}
				})
		{
			@Override
			protected Map<String, String> getParams()
			{
				Map<String, String> params = new HashMap<String, String>();
				params.put("email", email);
				params.put("password", pass);

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
	public  void saveLoginInstance()
	{
		SharedPreferences preferences=getSharedPreferences("dod_login_status", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor=preferences.edit();

		editor.putBoolean("loginStatus", AppData.loginStatus);

		editor.putString("PatientID",Singleton.PatientID );
		editor.putString("Name",Singleton.Name );
		editor.putString("Email",Singleton.Email);
		editor.putString("TimeZone",Singleton.TimeZone);
		editor.putString("Dateofbirth",Singleton.Dateofbirth);
		editor.putString("ActivationKey",Singleton.ActivationKey);
		editor.putString("Status",Singleton.Status);
		editor.putString("Updated_at",Singleton.Updated_at);
		editor.putString("Created_at",Singleton.Created_at);
		editor.putString("Role",Singleton.Role);
		editor.putString("RegistrationDate",Singleton.RegistrationDate);
		editor.putString("PromoCode",Singleton.PromoCode);
		editor.putString("UsedCoupons",Singleton.UsedCoupons);
		editor.putString("Credit",Singleton.Credit);
		editor.putString("FreeMinutes",Singleton.FreeMinutes);
		editor.putString("Currency",Singleton.Currency);
		editor.putString("Info",Singleton.Info);

		editor.commit();
	}
	public void initVideoCaling()
	{
		startIncomeCallListenerService(Consts.qb_loginID, Consts.qb_password, Consts.LOGIN);
	}
	public void startIncomeCallListenerService(String login, String password, int startServiceVariant)
	{
		System.out.println("DOD VC" + 2);
		Intent tempIntent = new Intent(this, IncomeCallListenerService.class);
		PendingIntent pendingIntent = createPendingResult(Consts.LOGIN_TASK_CODE, tempIntent, 0);
		Intent intent = new Intent(this, IncomeCallListenerService.class);
		intent.putExtra(Consts.USER_LOGIN, login);
		intent.putExtra(Consts.USER_PASSWORD, password);
		intent.putExtra(Consts.START_SERVICE_VARIANT, startServiceVariant);
		intent.putExtra(Consts.PARAM_PINTENT, pendingIntent);
		startService(intent);
		System.out.println("DOD VC" + 3);
	}
	public void RecoveryPassRequest(final String email)
	{
		progress.setVisibility(View.VISIBLE);
		System.out.println("recovery pass request" + ConstantUrls.UrlMain + "/"+ConstantUrls.UrlRecoveryMail+"/?userEmail="+email);
		RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
		StringRequest sr = new StringRequest(Request.Method.POST,
				ConstantUrls.UrlMain + ConstantUrls.UrlRecoveryMail,
				new Response.Listener<String>()
				{
					@Override
					public void onResponse(String response)
					{

						System.out.println("recovery pass response" + response);
						try
						{
							JSONObject json = new JSONObject(response);
							if (json.getString("code").equals("200"))
							{
								Toast.makeText(getApplicationContext(),json.getString("message"), Toast.LENGTH_SHORT).show();

							} else
							{
								Toast.makeText(getApplicationContext(),json.getString("message"), Toast.LENGTH_SHORT).show();
							}

						} catch (Exception e)
						{
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
			@Override
			protected Map<String, String> getParams()
			{
				Map<String, String> params = new HashMap<String, String>();
				params.put("userEmail", email);
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
