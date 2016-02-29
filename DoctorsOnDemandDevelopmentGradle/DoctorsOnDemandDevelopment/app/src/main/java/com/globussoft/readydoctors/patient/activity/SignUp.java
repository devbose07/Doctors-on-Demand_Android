package com.globussoft.readydoctors.patient.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.globussoft.readydoctors.patient.Utills.MyCustomProgressDialog;
import com.globussoft.readydoctors.patient.video_chat.Consts;
import com.quickblox.auth.QBAuth;
import com.quickblox.auth.model.QBSession;
import com.quickblox.core.QBEntityCallbackImpl;
import com.quickblox.core.QBSettings;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;

public class SignUp extends Activity {

	EditText email, fullname, password;
	TextView dob;
	Button signup_btn;
	ProgressBar progress;
	int year, month, day;
	DatePicker datePicker;
	ImageView agreedTerms;
	boolean termsAgreed=false;
	String emailStr;
	String passwordStr;
	String fullnameStr;
	String dobStr;
	String qId;
	String ownarId;
	public ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign_up);
		InitUI();
		QBSettings.getInstance().fastConfigInit(Consts.APP_ID, Consts.AUTH_KEY, Consts.AUTH_SECRET);


	}
	public void setAgreed(boolean termsAgreed)
	{
		if(termsAgreed)
		{
			agreedTerms.setImageResource(R.drawable.checkbox_checked);
		}
		else
		{
			agreedTerms.setImageResource(R.drawable.checkbox_unchecked);
		}
	}
	void InitUI()
	{
		agreedTerms=(ImageView)findViewById(R.id.agreedTerms);
		setAgreed(termsAgreed);
		agreedTerms.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
				if(termsAgreed)
				{
					setAgreed(termsAgreed=false);
				}
				else
				{
					setAgreed(termsAgreed=true);
				}

			}
		});
		email = (EditText) findViewById(R.id.email);
		fullname = (EditText) findViewById(R.id.name);
		password = (EditText) findViewById(R.id.pass);
		progress = (ProgressBar) findViewById(R.id.progress);
		dob=(TextView)findViewById(R.id.dob);
		dob.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Calendar c = Calendar.getInstance();
				int cyear = c.get(Calendar.YEAR);
				int cmonth = c.get(Calendar.MONTH);
				int cday = c.get(Calendar.DAY_OF_MONTH);
				new DatePickerDialog(SignUp.this, pickerListener, cyear, cmonth, cday).show();

			}
		});

		signup_btn = (Button) findViewById(R.id.signup_btn);
		signup_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				validateInputs();
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
	public void loadeProgressDialog()
	{
		new VeryLongAsyncTask(SignUp.this).execute();
	}

	class VeryLongAsyncTask extends AsyncTask<Void, Void, Void>
	{
		/*private final ProgressDialog progressDialog;*/

		public VeryLongAsyncTask(Context ctx)
		{
			progressDialog = MyCustomProgressDialog.ctor(ctx);
		}

		@Override
		protected void onPreExecute()
		{
			super.onPreExecute();

			progressDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params)
		{
			// sleep for 5 seconds
			try { Thread.sleep(5000); }
			catch (InterruptedException e)
			{ e.printStackTrace(); }

			return null;
		}

		@Override
		protected void onPostExecute(Void result)
		{
			super.onPostExecute(result);

			progressDialog.hide();
		}
	}

	public void validateInputs()
	{

		emailStr=email.getText().toString();
		passwordStr=password.getText().toString();
		fullnameStr=fullname.getText().toString();
		dobStr=dob.getText().toString();

		Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
		Matcher m = p.matcher(emailStr);
		boolean matchFound = m.matches();
		if(emailStr.equalsIgnoreCase(""))
		{
			email.setError("Please enter a Email");
		}
		else
		{
			if(matchFound)
			{
				if(fullnameStr.equalsIgnoreCase(""))
				{
					fullname.requestFocus();
					fullname.setError("Please enter your fullname");
				}
				else
				{
					if(passwordStr.equalsIgnoreCase("")||passwordStr.length()<8)
					{
						password.requestFocus();
						password.setError("Please enter a valid Password [min 8 character]");
					}
					else
					{
						if(dob.getText().toString().length()>0)
						{
							SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
							try
							{
								Date strDate = sdf.parse(dob.getText().toString());
								if (strDate.getTime() >= System.currentTimeMillis())
								{
									Toast.makeText(SignUp.this, "Given Date should be less then tody....!", Toast.LENGTH_SHORT).show();
								}
								else
								{
									if(termsAgreed)
									{
										if(new ConnectionDetector(getApplicationContext()).isConnectingToInternet())
										{

											SignUpToQuickBlox(emailStr, fullnameStr, passwordStr, dobStr);
										}
										else
										{
											Toast.makeText(SignUp.this, "You dont have Internet Connection....!", Toast.LENGTH_SHORT).show();
										}

									}
									else
									{
										Toast.makeText(SignUp.this, "Please check ReadyDoctors terms and Agree to SignUp.  ", Toast.LENGTH_SHORT).show();
									}

								}
							}
							catch (ParseException e)
							{
								Toast.makeText(SignUp.this, "Something went wrong please try again later...!", Toast.LENGTH_SHORT).show();
								onBackPressed();
							}



						}
						else
						{
							Toast.makeText(SignUp.this, "Please select your Date of Birth ", Toast.LENGTH_SHORT).show();
						}

					}
				}
			}
			else
			{
				email.requestFocus();
				email.setError("Please enter a valid Email");
			}
		}
	}
	public void SignUpToQuickBlox(final String email,final String fullnameStr,final String passwordStr,final String dobStr)
	{
		loadeProgressDialog();
		QBAuth.createSession(new QBEntityCallbackImpl<QBSession>()
		{
			@Override
			public void onSuccess(QBSession session, Bundle params)
			{
				System.out.println("createSession onSuccess session " + session.getId());
				final QBUser user = new QBUser(email, "12345678");
				user.setEmail(email);
				user.setFullName(fullnameStr);

				QBUsers.signUp(user, new QBEntityCallbackImpl<QBUser>()
				{
					@Override
					public void onSuccess(QBUser user, Bundle args)
					{
						System.out.println("signUp onSuccess session " + user.getId());
						System.out.println("getEmail " + user.getEmail());
						System.out.println("getId " + user.getId());
						qId = "" + user.getId();
						ownarId = "" + user.getId();
						System.out.println("getFullName " + user.getFullName());
						System.out.println("toString " + user.toString());
						System.out.println("args.toString() " + args.toString());
						CreateAccount(emailStr, fullnameStr, passwordStr, dobStr, qId, ownarId);
					}

					@Override
					public void onError(List<String> errors)
					{
						System.out.println("signUp errors " + errors.toString());
						getUser(email);
					}
				});
			}

			@Override
			public void onError(List<String> errors)
			{
				System.out.println("createSession errors " + errors.toString());
				Toast.makeText(SignUp.this, "Something went wrong please try again later...!", Toast.LENGTH_SHORT).show();
				progressDialog.cancel();
			}
		});


	}
	public void getUser(String email)
	{
		QBUsers.getUserByEmail(email, new QBEntityCallbackImpl<QBUser>()
		{
			@Override
			public void onSuccess(QBUser user, Bundle args)
			{
				System.out.println("========getUserByEmail===== ");
				System.out.println("getEmail " + user.getEmail());
				System.out.println("getId " + user.getId());
				System.out.println("getFullName " + user.getFullName());
				System.out.println("========--------------===== ");
				deleteUser(user.getId(),user.getEmail());
			}

			@Override
			public void onError(List<String> errors)
			{
				System.out.println("========getUserByEmail onError===== ");
				System.out.println("errors " + errors.toString());
				Toast.makeText(SignUp.this, "Something went wrong please try again later...!", Toast.LENGTH_SHORT).show();
				progressDialog.cancel();
			}
		});
	}

	public void deleteUser(final int id,String email)
	{
		QBUser user = new QBUser();
		user.setEmail(email);
		user.setPassword("12345678");

		QBUsers.signIn(user, new QBEntityCallbackImpl<QBUser>()
		{
			@Override
			public void onSuccess(QBUser user, Bundle params)
			{
				System.out.println("signIn "+id+" successfully");
				QBUsers.deleteUser(id, new QBEntityCallbackImpl<QBUser>()
				{
					@Override
					public void onSuccess()
					{
						System.out.println("deleteUser " + id + " Deleted successfully");
						validateInputs();
					}

					@Override
					public void onError(List<String> errors)
					{
						System.out.println("deleteUser " + id + " failed");
						Toast.makeText(SignUp.this, "Something went wrong please try again later...!", Toast.LENGTH_SHORT).show();
						progressDialog.cancel();

					}
				});
			}

			@Override
			public void onError(List<String> errors)
			{
				System.out.println("signIn " + id + " failed");
				Toast.makeText(SignUp.this, "Something went wrong please try again later...!", Toast.LENGTH_SHORT).show();
				progressDialog.cancel();

			}
		});

	}
	public void CreateAccount(final String email, final String name,final String pass,final String dob,final String qId,final String ownarId)
	{
//		progress.setVisibility(View.VISIBLE);
		RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
		StringRequest sr = new StringRequest(Request.Method.POST,
				ConstantUrls.UrlMain + ConstantUrls.UrlSignUP,
				new Response.Listener<String>() 
				{

					@Override
					public void onResponse(String response) 
					{

						progressDialog.cancel();
						System.out.println("register response" + response);
						try
						{
							JSONObject json = new JSONObject(response);
							if (json.getString("code").equals("200")) 
							{
								if(AppData.fromAppointment)
								{
									Intent intent = new Intent(getApplicationContext(), ApplyAppointment.class);
									startActivity(intent);
									finish();
								}
								else
								{
									Intent intent = new Intent(getApplicationContext(), SignIn.class);
									startActivity(intent);
									finish();
								}
							}
							else
							{
								Toast.makeText(getApplicationContext(),json.getString("message"),Toast.LENGTH_SHORT).show();
							}
						} catch (Exception e)
						{
							e.printStackTrace();
						}
//						progress.setVisibility(View.INVISIBLE);
					}
				},
				new Response.ErrorListener()
				{
					@Override
					public void onErrorResponse(VolleyError error)
					{
//						progress.setVisibility(View.INVISIBLE);
						progressDialog.cancel();
					}
				}) {
			@Override
			protected Map<String, String> getParams()
			{
				Map<String, String> params = new HashMap<String, String>();
				params.put("email", email);
				params.put("name", name);
				params.put("password", pass);
				params.put("dateofbirth", dob);
				params.put("role","1");
				params.put("qId",qId);
				params.put("owner_id",ownarId);
				params.put("timeZone", TimeZone.getDefault().getID()+"");
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
