package com.globussoft.readydoctors.doctor.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
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
import com.globussoft.readydoctors.doctor.adapters.EditScheduleAdapter;
import com.globussoft.readydoctors.doctor.models.DoctorSheduleModel;
import com.globussoft.readydoctors.doctor.uttils.ConnectionDetector;
import com.globussoft.readydoctors.doctor.uttils.ConstantTag;
import com.globussoft.readydoctors.doctor.uttils.ConstantUrls;
import com.globussoft.readydoctors.doctor.uttils.MainSingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class EditSchedule extends Fragment {

	RelativeLayout savebtn;
	View rootview;
	ListView list;
	EditScheduleAdapter adapter;
	ProgressBar progress;
	ArrayList<DoctorSheduleModel> array = new ArrayList<DoctorSheduleModel>();
	ArrayList<String> time_array = new ArrayList<String>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootview = inflater.inflate(R.layout.editshedule, container, false);

		InitUI();

		return rootview;
	}

	void InitUI() {
		savebtn = (RelativeLayout) rootview.findViewById(R.id.Save);
		list = (ListView) rootview.findViewById(R.id.list);
		progress = (ProgressBar) rootview.findViewById(R.id.progress);

		System.out.println("doctor_id=" + MainSingleton.doctor_id + " timeZone=" + MainSingleton.timeZone);
		if(new ConnectionDetector(getActivity()).isConnectingToInternet())
		{
			FetchDoctorShedule(MainSingleton.doctor_id,MainSingleton.timeZone);
		}
		else
		{
			Toast.makeText(getActivity(), "You dont have Internet...!", Toast.LENGTH_SHORT).show();
		}


		savebtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(new ConnectionDetector(getActivity()).isConnectingToInternet())
				{
					GetDataFromList();
				}
				else
				{
					Toast.makeText(getActivity(), "You dont have Internet...!", Toast.LENGTH_SHORT).show();
				}


			}
		});
	}

	// fetch upcoming apointment

	public void FetchDoctorShedule(final String doctor_id,final String timeZone) {

		System.out.print("doctor_id="+doctor_id+" timeZone="+timeZone);

		progress.setVisibility(View.VISIBLE);
		array.clear();
		RequestQueue queue = Volley.newRequestQueue(getActivity());
		StringRequest sr = new StringRequest(Request.Method.POST,
				ConstantUrls.UrlMain + ConstantUrls.UrlFetchScheduletime,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {

						System.out
								.println("Fetch schedule response" + response);
						try {
							JSONObject json = new JSONObject(response);
							if (json.getString("code").equals("200")) {


								JSONArray data = json
										.getJSONArray(ConstantTag.tag_Data);

								JSONObject obj=data.getJSONObject(0);

								DoctorSheduleModel model1 = new DoctorSheduleModel();
								model1.setDay(1);


									model1.setStart_time(obj.getString(ConstantTag.Tag_mondayStart).replace("PM","").replace("AM",""));

									model1.setEnd_time(obj.getString(ConstantTag.Tag_mondayEnd).replace("PM","").replace("AM", ""));


								array.add(model1);


								DoctorSheduleModel model2 = new DoctorSheduleModel();
								model2.setDay(2);
								model2.setStart_time(obj.getString(ConstantTag.Tag_tuesdayStart).replace("PM","").replace("AM", ""));
								model2.setEnd_time(obj.getString(ConstantTag.Tag_tuesdayEnd).replace("PM","").replace("AM", ""));
								array.add(model2);


								DoctorSheduleModel model3 = new DoctorSheduleModel();
								model3.setDay(3);
								model3.setStart_time(obj.getString(ConstantTag.Tag_wednesdayStart).replace("PM","").replace("AM", ""));
								model3.setEnd_time(obj.getString(ConstantTag.Tag_wednesdayEnd).replace("PM","").replace("AM", ""));
								array.add(model3);


								DoctorSheduleModel model4 = new DoctorSheduleModel();
								model4.setDay(4);
								model4.setStart_time(obj.getString(ConstantTag.Tag_thursdayStart).replace("PM","").replace("AM", ""));
								model4.setEnd_time(obj.getString(ConstantTag.Tag_thursdayEnd).replace("PM","").replace("AM", ""));
								array.add(model4);


								DoctorSheduleModel model5 = new DoctorSheduleModel();
								model5.setDay(5);
								model5.setStart_time(obj.getString(ConstantTag.Tag_fridayStart).replace("PM","").replace("AM", ""));
								model5.setEnd_time(obj.getString(ConstantTag.Tag_fridayEnd).replace("PM","").replace("AM", ""));
								array.add(model5);

								DoctorSheduleModel model6 = new DoctorSheduleModel();
								model6.setDay(6);
								model6.setStart_time(obj.getString(ConstantTag.Tag_saturdayStart).replace("PM","").replace("AM", ""));
								model6.setEnd_time(obj.getString(ConstantTag.Tag_saturdayEnd).replace("PM","").replace("AM",""));
								array.add(model6);




								System.out.println("array.size=="
										+ array.size());
								adapter = new EditScheduleAdapter(
										getActivity(), array);
								list.setAdapter(adapter);

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
				params.put("doctorId", doctor_id);
				params.put("timeZone",timeZone);
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

	public void UpdateDoctorSchedule(final String doctorid, final String timings,
			final String timeZone) {
		progress.setVisibility(View.VISIBLE);

		RequestQueue queue = Volley.newRequestQueue(getActivity());
		StringRequest sr = new StringRequest(Request.Method.POST,
				ConstantUrls.UrlMain + ConstantUrls.UrlUpdateScheduletime,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {

						System.out.print("doctorid>>"+doctorid);
						System.out.println("UrlUpdateScheduletime response"
								+ response);
						try {
							JSONObject json = new JSONObject(response);
							if (json.getString("code").equals("200")) {
								Toast.makeText(getActivity(), "Updated",
										Toast.LENGTH_SHORT).show();

							} else {

								Toast.makeText(getActivity(),
										"Something went wrong",
										Toast.LENGTH_SHORT).show();
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
				params.put("userId", doctorid);
				params.put("timings", timings);
				params.put("timeZone", timeZone);

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

	void GetDataFromList() {
		JSONArray array1 = new JSONArray();

		for (int i = 0; i < list.getAdapter().getCount(); i++) {
			// day1

			View view1 = getViewByPosition(i, list);



			TextView starttime1 = (TextView) view1.findViewById(R.id.starttime);
			TextView endtime1 = (TextView) view1.findViewById(R.id.endtime);

			if (starttime1.getText().toString().length() > 1
					&& endtime1.getText().toString().length() > 1) {


				try {
					array1.put(ConvertLocalToUtc(starttime1.getText().toString()));
					array1.put(ConvertLocalToUtc(endtime1.getText().toString()));

				} catch (ParseException e) {
					e.printStackTrace();
				}
			} else {
				time_array.add("");
			}

			System.out.println("array1.toString() "+ array1.toString());
		}
		if(new ConnectionDetector(getActivity()).isConnectingToInternet())
		{
			UpdateDoctorSchedule(MainSingleton.doctor_id, array1.toString(), MainSingleton.timeZone);
		}
		else
		{
			Toast.makeText(getActivity(), "You dont have Internet...!", Toast.LENGTH_SHORT).show();
		}


	}

	public View getViewByPosition(int position, ListView listView) {
		final int firstListItemPosition = listView.getFirstVisiblePosition();
		final int lastListItemPosition = firstListItemPosition
				+ listView.getChildCount() - 1;

		if (position < firstListItemPosition || position > lastListItemPosition) {
			return listView.getAdapter().getView(position, null, listView);
		} else {
			final int childIndex = position - firstListItemPosition;
			return listView.getChildAt(childIndex);
		}
	}

	public String ConvertLocalToUtc(String local_time) throws ParseException {
		local_time="2015/12/02 "+local_time;
		String format = "yyyy/MM/dd HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(format);

		//Date temp = new SimpleDateFormat(format).parse(local_time);

		Date date = sdf.parse(local_time.trim());

		// Convert Local Time to UTC (Works Fine)
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date gmtTime = new Date(sdf.format(date));

		SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm:ss");
		String hms = localDateFormat.format(gmtTime);



		return hms;
	}

}
