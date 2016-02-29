package com.globussoft.readydoctors.doctor.Activity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
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
import com.globussoft.readydoctors.doctor.uttils.ConnectionDetector;
import com.globussoft.readydoctors.doctor.uttils.ConstantTag;
import com.globussoft.readydoctors.doctor.uttils.ConstantUrls;
import com.globussoft.readydoctors.doctor.uttils.MainSingleton;
import com.globussoft.readydoctors.doctor.videochat.Consts;
import com.globussoft.readydoctors.doctor.videochat.IncomeCallListenerService;
import com.globussoft.readydoctors.doctor.R;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends Activity {
	EditText email, password,recoverymail;
	TextView forgotPwd;
	Button signIn,clickhere;
	ProgressBar progress;
	RelativeLayout recoverylayout;
	View line;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.login_activity);
		initUI();
	}

	private void initUI() {
		clickhere=(Button) findViewById(R.id.clickhere);
		line = findViewById(R.id.line);
		email = (EditText) findViewById(R.id.email);
//		email.setText("dinanaththakur@globussoft.com");
		recoverylayout = (RelativeLayout) findViewById(R.id.recoverlayout);
		password = (EditText) findViewById(R.id.pass);
//		password.setText("dinanaththakur@globussoft.com");
		forgotPwd = (TextView) findViewById(R.id.iforgot);
		progress = (ProgressBar) findViewById(R.id.progress);
		// forgotPwd.setText(Html.fromHtml("<U>I Forgot My Password<U>"));
		
		recoverymail=(EditText) findViewById(R.id.recoverymail);
		
		forgotPwd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				recoverylayout.setVisibility(View.VISIBLE);
				line.setVisibility(View.INVISIBLE);
				forgotPwd.setVisibility(View.INVISIBLE);
			}
		});
		signIn = (Button) findViewById(R.id.signin_btn);
		signIn.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				signIn();

			}
		});
		
		
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


	}

	protected void signIn()
	{
		if (email.getText().length() > 0)
		{
			if (password.getText().length() > 0)
			{
				if(new ConnectionDetector(getApplicationContext()).isConnectingToInternet())
				{
					SignInRequest(email.getText().toString().trim(), password.getText().toString().trim());
				}
				else
				{
					Toast.makeText(getApplicationContext(),"You dont have Internet...!", Toast.LENGTH_SHORT).show();
				}

			}
			else
			{
				Toast.makeText(getApplicationContext(),"Please enter Password", Toast.LENGTH_SHORT).show();
			}
		}
		else
		{
			Toast.makeText(getApplicationContext(),
					"Please enter your registered Email", Toast.LENGTH_SHORT)
					.show();
		}

	}

	public void SignInRequest( final String email, final String pass)
	{
		progress.setVisibility(View.VISIBLE);

		RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
		StringRequest sr = new StringRequest(Request.Method.POST,
				ConstantUrls.UrlMain + ConstantUrls.UrlLogin,
				new Response.Listener<String>()
				{
					@Override
					public void onResponse(String response)
					{

						System.out.println("login response" + response);
						try
						{
							JSONObject json = new JSONObject(response);
							if (json.getString("code").equals("200"))
							{
								Consts.qb_loginID=email;
								JSONObject obj=json.getJSONObject(ConstantTag.tag_Data);
								MainSingleton.doctor_id=obj.getString(ConstantTag.Tag_Id);
								MainSingleton.name=obj.getString(ConstantTag.Tag_Name);
								MainSingleton.email=obj.getString(ConstantTag.Tag_Email);
							//	MainSingleton.timeZone=obj.getString(ConstantTag.Tag_Timezone);
								MainSingleton.dateofbirth=obj.getString(ConstantTag.tag_Dateofbirth);


								initVideoCaling();
								Intent intent = new Intent(getApplicationContext(),MainActivity.class);
								startActivity(intent);

								finish();

							}
							else
							{
								Toast.makeText(getApplicationContext(),json.getString("message"),Toast.LENGTH_LONG).show();
							}

						}
						catch (Exception e)
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
			protected Map<String, String> getParams() {
				Map<String, String> params = new HashMap<String, String>();
				params.put("email", email);
				params.put("password", pass);

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
	
	
	
	public void RecoveryPassRequest(final String email) {
		progress.setVisibility(View.VISIBLE);

		RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
		StringRequest sr = new StringRequest(Request.Method.POST,
				ConstantUrls.UrlMain + ConstantUrls.UrlRecoveryMail,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {

						System.out.println("recovery pass response" + response);
						try {
							JSONObject json = new JSONObject(response);
							if (json.getString("code").equals("200")) {
								Toast.makeText(getApplicationContext(),
										"Please check your registered Email", Toast.LENGTH_SHORT)
										.show();
								
							} else {
								
								Toast.makeText(getApplicationContext(),
										"This email is not registered with us", Toast.LENGTH_SHORT)
										.show();
							}

						} catch (Exception e) {
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
				params.put("email", email);
				

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

	public void initVideoCaling()
	{
		System.out.println("DOD VC" + 1);
//		initProgressDialog();
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
}
