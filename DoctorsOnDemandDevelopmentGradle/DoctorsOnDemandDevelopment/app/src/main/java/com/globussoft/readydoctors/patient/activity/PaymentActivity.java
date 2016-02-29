package com.globussoft.readydoctors.patient.activity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
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
import com.globussoft.readydoctors.patient.Utills.ConstantUrls;
import com.globussoft.readydoctors.patient.see_a_doctor_now.SeeDoctorData;
import com.globussoft.readydoctors.patient.mediacal.MedicalData;
import com.globussoft.readydoctors.patient.pediatrics.PediatricData;
import com.globussoft.readydoctors.patient.pregnancy_newborns.LactationData;
import com.globussoft.readydoctors.patient.psychological.PsychologyData;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONArray;
import org.json.JSONObject;

public class PaymentActivity extends Activity
{
	// change it once app got live
	//PayPalConfiguration.ENVIRONMENT_SANDBOX
	
	
	private static PayPalConfiguration config = new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
			.clientId("AURFbviSNVYwBiqYtBhgRqUG9Qan0mqv6RLY3NGOQtU_EeJqM-4g6xRWX8Fq_mQcaUE8Hy6zh_PAD3QW");

//	AXXpkIcRZUw3XwYSCcUf4A4TrtHrWj-ohP25QHoab0qUvuVCK2Y79ML5okn9caUXGr9loyWKvBSZWs7Z      //spare
	RelativeLayout payRlt;
	String needToPay="",TransactionID="";
	ImageView backImage;
	TextView next;
	PaymentConfirmation confirm;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		
		super.onCreate(savedInstanceState);

		System.out.println("PatientID " + SeeDoctorData.patientId);

		setContentView(R.layout.paypal_activity);
		next=(TextView)findViewById(R.id.next);
		next.setVisibility(View.INVISIBLE);
		backImage=(ImageView)findViewById(R.id.backImage);
		backImage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
		switch(AppData.appointmentType)
		{
			case 1:
				needToPay= LactationData.needtopay;
				break;
			case 2:
				needToPay= PediatricData.needtopay;
				break;
			case 3:
				needToPay= PsychologyData.needtopay;
				break;
			case 4:
				needToPay= MedicalData.needtopay;
				break;
			case 0:
				needToPay= SeeDoctorData.needtopay;
				break;
			default:

		}
	

		payRlt = (RelativeLayout) findViewById(R.id.payRlt);

		Intent intent = new Intent(PaymentActivity.this, PayPalService.class);

		intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

		startService(intent);

		payRlt.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				checkStatus();


			}
		});
	}
	public void makePayment()
	{
		PayPalPayment payment = new PayPalPayment(new BigDecimal(needToPay), "USD", "Ready Doctor",PayPalPayment.PAYMENT_INTENT_SALE);

		Intent intent = new Intent(PaymentActivity.this, com.paypal.android.sdk.payments.PaymentActivity.class);

		// send the same configuration for restart resiliency
		intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

		intent.putExtra(com.paypal.android.sdk.payments.PaymentActivity.EXTRA_PAYMENT, payment);

		startActivityForResult(intent, 0);
	}
	public void checkStatus()
	{
		String startTime;
		String endTime;
		String timeZone;
		System.out.println("Payment me appnt type on checkStatus[[ " + AppData.appointmentType + " ]]");
		switch(AppData.appointmentType)
		{
			case 1:
				try
				{
					startTime=ConvertUtcToLocal(LactationData.appointment_start_time);
					endTime=ConvertUtcToLocal(LactationData.appointment_end_time);
					timeZone= TimeZone.getDefault().getID()+"";
					ShowFreeDoctorList(AppData.appointmentType+"",startTime,endTime);
				}
				catch(ParseException e)
				{
					Toast.makeText(getApplicationContext(), "something went wrong..please try again later..!", Toast.LENGTH_LONG).show();
					System.out.println("Something went wrong in b4 LactationData");
					Intent intent = new Intent(getApplicationContext(), Home.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
					PaymentActivity.this.finish();
				}
				break;
			case 2:
				try
				{
					startTime=ConvertUtcToLocal(PediatricData.appointment_start_time);
					endTime=ConvertUtcToLocal(PediatricData.appointment_end_time);
					timeZone= TimeZone.getDefault().getID()+"";
					ShowFreeDoctorList(AppData.appointmentType+"",startTime,endTime);
				}
				catch(ParseException e)
				{
					Toast.makeText(getApplicationContext(), "something went wrong..please try again later..!", Toast.LENGTH_LONG).show();
					System.out.println("Something went wrong in b4 PediatricData");
					Intent intent = new Intent(getApplicationContext(), Home.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
					PaymentActivity.this.finish();
				}
				break;
			case 3:
				try
				{
					startTime=ConvertUtcToLocal(PsychologyData.appointment_start_time);
					endTime=ConvertUtcToLocal(PsychologyData.appointment_end_time);
					timeZone= TimeZone.getDefault().getID()+"";
					ShowFreeDoctorList(AppData.appointmentType+"",startTime,endTime);
				}
				catch(ParseException e)
				{
					Toast.makeText(getApplicationContext(), "something went wrong..please try again later..!", Toast.LENGTH_LONG).show();
					System.out.println("Something went wrong in b4 PsychologyData");
					Intent intent = new Intent(getApplicationContext(), Home.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
					PaymentActivity.this.finish();
				}
				break;
			case 4:
				try
				{
					startTime=ConvertUtcToLocal(MedicalData.appointment_start_time);
					endTime=ConvertUtcToLocal(MedicalData.appointment_end_time);
					timeZone= TimeZone.getDefault().getID()+"";
					ShowFreeDoctorList(AppData.appointmentType+"",startTime,endTime);
				}
				catch(ParseException e)
				{
					Toast.makeText(getApplicationContext(), "something went wrong..please try again later..!", Toast.LENGTH_LONG).show();
					System.out.println("Something went wrong in b4 MedicalData");
					Intent intent = new Intent(getApplicationContext(), Home.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
					PaymentActivity.this.finish();
				}
				break;
			case 0:
				try
				{
					startTime="";//ConvertUtcToLocal(SeeDoctorData.appointmentStartTime);
					endTime="";//ConvertUtcToLocal(SeeDoctorData.appointmentEndTime);
					timeZone= TimeZone.getDefault().getID()+"";
					getAutoAllocatedDoctor(startTime,endTime,timeZone);
				}
				catch(Exception e)
				{
					Toast.makeText(getApplicationContext(), "something went wrong..please try again later..!", Toast.LENGTH_LONG).show();
					System.out.println("Something went wrong in b4 getAutoAllocatedDoctor");
					Intent intent = new Intent(getApplicationContext(), Home.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
					PaymentActivity.this.finish();
				}
				break;
			default:

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

		System.out.print("finalLocalDateString   " + finalLocalDateString);

		return finalLocalDateString;
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (resultCode == Activity.RESULT_OK)
		{
			confirm = data.getParcelableExtra(com.paypal.android.sdk.payments.PaymentActivity.EXTRA_RESULT_CONFIRMATION);

			if (confirm != null)
			{
				Log.i("paymentExample", confirm.toJSONObject().toString());

				// TODO: send 'confirm' to your server for verification.
				// see
				// https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
				// for more details.

				confirm.getProofOfPayment().getPaymentId();
				confirm.getProofOfPayment().getState();
	         	confirm.getProofOfPayment().getCreateTime();
				confirm.getProofOfPayment().getTransactionId();

				System.out.println("getProofOfPayment()=" + confirm.getProofOfPayment());
				System.out.println("getTransactionId=" + confirm.getProofOfPayment().getTransactionId());

				System.out.println("getPayment()=" + confirm.getPayment());
				System.out.println("getPaymentId()=" + confirm.getProofOfPayment().getPaymentId());
				System.out.println("getState()=" + confirm.getProofOfPayment().getState());
				System.out.println("getState()=" + confirm.getProofOfPayment().getCreateTime());
				System.out.println("getCreateTime()=" + confirm.getProofOfPayment().getCreateTime());
				GetTransactionID(confirm.getProofOfPayment().getPaymentId());
		/*		switch(AppData.appointmentType)
				{
					case 1:
						LactationData.transactionId=TransactionID;
						LactationData.acknowledgement="Success";
						LactationData.paymentTime=confirm.getProofOfPayment().getCreateTime();
						LactationData.amount=needToPay;
						break;
					case 2:
						PediatricData.transactionId=TransactionID;
						PediatricData.acknowledgement="Success";
						PediatricData.paymentTime=confirm.getProofOfPayment().getCreateTime();
						PediatricData.amount=needToPay;
						break;
					case 3:
						PsychologyData.transactionId=TransactionID;
						PsychologyData.acknowledgement="Success";
						PsychologyData.paymentTime=confirm.getProofOfPayment().getCreateTime();
						PsychologyData.amount=needToPay;
						break;
					case 4:
						MedicalData.transactionId=TransactionID;
						MedicalData.acknowledgement="Success";
						MedicalData.paymentTime=confirm.getProofOfPayment().getCreateTime();
						MedicalData.amount=needToPay;
						break;
					case 0:
						SeeDoctorData.TRANSACTIONID=TransactionID;
						SeeDoctorData.ACK="Success";
						SeeDoctorData.AMT=needToPay;
//						SeeDoctorData.paymentTime=confirm.getProofOfPayment().getCreateTime();
						break;
					default:
				}
				Intent intent = new Intent(getApplicationContext(),ApplyAppointment.class);
				startActivity(intent);*/
			}
		}
		else if (resultCode == Activity.RESULT_CANCELED)
		{
			Log.i("paymentExample", "The user canceled.");
		}
		else if (resultCode == com.paypal.android.sdk.payments.PaymentActivity.RESULT_EXTRAS_INVALID)
		{
			Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
		}
	}

	@Override
	public void onDestroy()
	{
		stopService(new Intent(PaymentActivity.this, PayPalService.class));
		super.onDestroy();
	}

	@Override
	public void onDetachedFromWindow()
	{
		super.onDetachedFromWindow();
	}

	public void getAutoAllocatedDoctor( final String appointmentStartTime,
										final String appointmentEndTime,
										final String timeZone)

	{
		RequestQueue queue = Volley.newRequestQueue(PaymentActivity.this);
		StringRequest sr = new StringRequest(Request.Method.POST,
				ConstantUrls.UrlMain + ConstantUrls.UrlAutoAllocateMedicalDoctor2,
				new Response.Listener<String>()
				{
					@Override
					public void onResponse(String response)
					{
						System.out.println("auto allocate doctor response" + response);
						try
						{
							JSONObject json = new JSONObject(response);
							if (json.getString("code").equals("200"))
							{
								Toast.makeText(getApplicationContext(), json.getString("message"), Toast.LENGTH_LONG).show();

                                JSONObject data = json.getJSONObject("data");

								SeeDoctorData.doctorID=data.getString("doctorId");
								SeeDoctorData.appointmentStartTime=data.getString("appointmentStartTime");
								SeeDoctorData.appointmentEndTime=data.getString("appointmentEndTime");

								if(needToPay.equalsIgnoreCase("0"))
								{
									applyFreeVisit();
								}
								else
								{
									makePayment();
								}
								System.out.println("doctorID" + SeeDoctorData.doctorID);
								System.out.println("json.getString(message)" + json.getString("message"));
							}
							else
							{
								Toast.makeText(getApplicationContext(), json.getString("message"), Toast.LENGTH_LONG).show();
								System.out.println("json.getString(message)" + json.getString("message"));
								Intent intent = new Intent(getApplicationContext(), Home.class);
								intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
								startActivity(intent);
								PaymentActivity.this.finish();
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
				/*params.put("appointmentStartTime", appointmentStartTime);
				params.put("appointmentEndTime", appointmentEndTime);*/
				params.put("timeZone", timeZone);

				System.out.println("appointmentStartTime" + appointmentStartTime);
				System.out.println("appointmentEndTime"+appointmentEndTime);
				System.out.println("timeZone"+timeZone);

				return params;
			}
		};
		queue.add(sr);
	}

	public String ConvertLocalToUtc(String local_time) throws ParseException
	{
		System.out.print("local_time>>"+local_time+"<<");
//		local_time="2015/12/02 "+local_time;
		String format = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(format);

		//Date temp = new SimpleDateFormat(format).parse(local_time);

		Date date = sdf.parse(local_time.trim());

		// Convert Local Time to UTC (Works Fine)
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));


		//Date gmtTime = new Date(sdf.format(date));


		String hms = sdf.format(date);

		return hms;
	}

	public void recheckAppointment( final String doctorId,
									final String appointmentStartTime,
										final String appointmentEndTime,
										final String timeZone)

	{
		RequestQueue queue = Volley.newRequestQueue(PaymentActivity.this);
		StringRequest sr = new StringRequest(Request.Method.POST,
				ConstantUrls.UrlMain + ConstantUrls.UrlAutoAllocateMedicalDoctorCheck,
				new Response.Listener<String>()
				{
					@Override
					public void onResponse(String response)
					{
						System.out.println("auto allocate doctor response" + response);
						try
						{
							JSONObject json = new JSONObject(response);
							if (json.getString("code").equals("200"))
							{
								Toast.makeText(getApplicationContext(), json.getString("message"), Toast.LENGTH_LONG).show();

								JSONObject data = json.getJSONObject("data");


								SeeDoctorData.appointmentStartTime=ConvertLocalToUtc(data.getString("appointmentStartTime"));
								SeeDoctorData.appointmentEndTime=ConvertLocalToUtc(data.getString("appointmentEndTime"));



								Intent intent = new Intent(getApplicationContext(),ApplyAppointment.class);
								startActivity(intent);

								System.out.println("doctorID" + SeeDoctorData.doctorID);
								System.out.println("json.getString(message)" + json.getString("message"));
							}
							else
							{
								Toast.makeText(getApplicationContext(), json.getString("message"), Toast.LENGTH_LONG).show();
								System.out.println("json.getString(message)" + json.getString("message"));
								Intent intent = new Intent(getApplicationContext(), Home.class);
								intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
								startActivity(intent);
								PaymentActivity.this.finish();
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
				params.put("doctorId", doctorId);
				params.put("appointmentStartTime", appointmentStartTime);
				params.put("appointmentEndTime", appointmentEndTime);
				params.put("timeZone", timeZone);

				System.out.println("doctorId" + doctorId);
				System.out.println("appointmentStartTime" + appointmentStartTime);
				System.out.println("appointmentEndTime"+appointmentEndTime);
				System.out.println("timeZone"+timeZone);

				return params;
			}
		};
		queue.add(sr);
	}
	public void ShowFreeDoctorList(final String departmentId,final String appointmentStartTime,final String appointmentEndTime)
	{
		/*progress.setVisibility(View.VISIBLE);
		doctorList.clear();
		*/

		RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
		StringRequest sr = new StringRequest(Request.Method.POST,
				ConstantUrls.UrlMain + ConstantUrls.UrlGetAllFreeDoctorBySchedule,
				new Response.Listener<String>()
				{
					@Override
					public void onResponse(String response)
					{
						System.out.println("show doctor response" + response);
						boolean drFound=false;
						try
						{
							JSONObject json = new JSONObject(response);
							if (json.getString("code").equals("200"))
							{
								JSONArray array = json.getJSONArray("data");
								for (int i = 0; i < array.length(); i++)
								{

									JSONObject obj = array.getJSONObject(i);
									System.out.println("insde for" + obj.getString("doctor_id"));
									switch (Integer.parseInt(departmentId))
									{
										case 1:
											if(LactationData.doctor_id.equalsIgnoreCase(obj.getString("doctor_id")))
											{
												drFound=true;
												break;
											}
											break;
										case 2:
											if(PediatricData.doctor_id.equalsIgnoreCase(obj.getString("doctor_id")))
											{
												drFound=true;
												break;
											}

											break;
										case 3:
											if(PsychologyData.doctor_id.equalsIgnoreCase(obj.getString("doctor_id")))
											{
												drFound=true;
												break;
											}
											break;
										case 4:
											if(MedicalData.doctor_id.equalsIgnoreCase(obj.getString("doctor_id")))
											{
												drFound=true;
												break;
											}
											break;
										default:

									}
								}
								if(drFound)
								{
									if(needToPay.equalsIgnoreCase("0"))
									{
										applyFreeVisit();
									}
									else
									{
										makePayment();
									}

								}
								else
								{
									Toast.makeText(getApplicationContext(),json.getString("message"),Toast.LENGTH_SHORT).show();
									Intent intent = new Intent(getApplicationContext(), Home.class);
									intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
									startActivity(intent);
									finish();
								}
								System.out.println("exit from for dr found "+drFound);
							}
							else
							{
								Toast.makeText(getApplicationContext(),json.getString("message"),Toast.LENGTH_SHORT).show();
								Intent intent = new Intent(getApplicationContext(), Home.class);
								intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
								startActivity(intent);
								finish();
							}

						} catch (Exception e)
						{
							e.printStackTrace();
						}

					}
				}, new Response.ErrorListener()
		{
			@Override
			public void onErrorResponse(VolleyError error)
			{

			}
		}) {
			@Override
			protected Map<String, String> getParams()
			{
				Map<String, String> params = new HashMap<String, String>();
				params.put("departmentId", departmentId);
				params.put("appointmentStartTime", appointmentStartTime);
				params.put("appointmentEndTime", appointmentEndTime);
				params.put("timeZone", TimeZone.getDefault().getID()+"");
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

	public void GetTransactionID( final String paymentId)
	{
		RequestQueue queue = Volley.newRequestQueue(PaymentActivity.this);
		StringRequest sr = new StringRequest(Request.Method.POST,
				ConstantUrls.UrlMain + ConstantUrls.GetTransactionId,
				new Response.Listener<String>()
				{
					@Override
					public void onResponse(String response)
					{
						System.out.println("Transaction id response" + response);
						try
						{
							JSONObject json = new JSONObject(response);
							if (json.getString("code").equals("200"))
							{
								Toast.makeText(getApplicationContext(), json.getString("message"), Toast.LENGTH_LONG).show();
								JSONObject data =json.getJSONObject("data");
								JSONArray transactions=data.getJSONArray("transactions");
								JSONObject obj1 =transactions.getJSONObject(0);
								JSONArray related_resources=obj1.getJSONArray("related_resources");
								JSONObject obj2 =related_resources.getJSONObject(0);
								JSONObject obj3 =obj2.getJSONObject("sale");
								TransactionID=obj3.getString("id");
								System.out.println("final Transaction Id " + TransactionID);
								setupKeys();
							}
							else
							{
								/*Toast.makeText(getApplicationContext(), json.getString("message"), Toast.LENGTH_LONG).show();
								System.out.println("json.getString(message)" + json.getString("message"));
								Intent intent = new Intent(getApplicationContext(), Home.class);
								intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
								startActivity(intent);
								PaymentActivity.this.finish();*/
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
				params.put("paymentid", paymentId);

				System.out.println("paymentId" + paymentId);

				return params;
			}
		};
		queue.add(sr);
	}
	public void setupKeys()
	{
		Intent intent = new Intent(getApplicationContext(),ApplyAppointment.class);

		switch(AppData.appointmentType)
		{
			case 1:
				LactationData.transactionId=TransactionID;
				LactationData.acknowledgement="Success";
				LactationData.paymentTime=confirm.getProofOfPayment().getCreateTime();
				LactationData.amount=needToPay;
				startActivity(intent);
				break;
			case 2:
				PediatricData.transactionId=TransactionID;
				PediatricData.acknowledgement="Success";
				PediatricData.paymentTime=confirm.getProofOfPayment().getCreateTime();
				PediatricData.amount=needToPay;
				startActivity(intent);
				break;
			case 3:
				PsychologyData.transactionId=TransactionID;
				PsychologyData.acknowledgement="Success";
				PsychologyData.paymentTime=confirm.getProofOfPayment().getCreateTime();
				PsychologyData.amount=needToPay;
				startActivity(intent);
				break;
			case 4:
				MedicalData.transactionId=TransactionID;
				MedicalData.acknowledgement="Success";
				MedicalData.paymentTime=confirm.getProofOfPayment().getCreateTime();
				MedicalData.amount=needToPay;
				startActivity(intent);
				break;
			case 0:
				SeeDoctorData.TRANSACTIONID=TransactionID;
				SeeDoctorData.ACK="Success";
				SeeDoctorData.AMT=needToPay;
				recheckAppointment(SeeDoctorData.doctorID,SeeDoctorData.appointmentStartTime,SeeDoctorData.appointmentEndTime,TimeZone.getDefault().getID()+"");
				break;
			default:
		}
		/*Intent intent = new Intent(getApplicationContext(),ApplyAppointment.class);
		startActivity(intent);*/
	}
	public void applyFreeVisit()
	{
		Intent intent = new Intent(getApplicationContext(),ApplyAppointment.class);
		switch(AppData.appointmentType)
		{
			//amount=0&transactionId= 0 &acknowledgement=Free_User
			case 1:
				LactationData.transactionId="0";
				LactationData.acknowledgement="Free_User";
				LactationData.paymentTime="0";
				LactationData.amount="0";

				startActivity(intent);
				break;
			case 2:
				PediatricData.transactionId="0";
				PediatricData.acknowledgement="Free_User";
				PediatricData.paymentTime="0";
				PediatricData.amount="0";
//				Intent intent = new Intent(getApplicationContext(),ApplyAppointment.class);
				startActivity(intent);
				break;
			case 3:
				PsychologyData.transactionId="0";
				PsychologyData.acknowledgement="Free_User";
				PsychologyData.paymentTime="0";
				PsychologyData.amount="0";
				startActivity(intent);
				break;
			case 4:
				MedicalData.transactionId="0";
				MedicalData.acknowledgement="Free_User";
				MedicalData.paymentTime="0";
				MedicalData.amount="0";
				startActivity(intent);
				break;
			case 0:
				SeeDoctorData.TRANSACTIONID="0";
				SeeDoctorData.ACK="Free_User";
				SeeDoctorData.AMT="0";
				recheckAppointment(SeeDoctorData.doctorID,SeeDoctorData.appointmentStartTime,SeeDoctorData.appointmentEndTime,TimeZone.getDefault().getID()+"");
				break;
			default:
		}

	}
}
