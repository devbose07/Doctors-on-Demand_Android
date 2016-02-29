package com.globussoft.readydoctors.doctor.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
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
import com.globussoft.readydoctors.doctor.R;
import com.globussoft.readydoctors.doctor.adapters.RatingAdapter;
import com.globussoft.readydoctors.doctor.models.RatingModel;
import com.globussoft.readydoctors.doctor.uttils.ConnectionDetector;
import com.globussoft.readydoctors.doctor.uttils.ConstantTag;
import com.globussoft.readydoctors.doctor.uttils.ConstantUrls;
import com.globussoft.readydoctors.doctor.uttils.MainSingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Review extends Fragment {
	View rootView;
	ListView listView;
	ArrayList<RatingModel> ratingList = new ArrayList<RatingModel>();
	ProgressBar progress;
	RatingAdapter adapter;
	TextView no_Data;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.review_fragment, container, false);
		initUI();
		if(new ConnectionDetector(getActivity()).isConnectingToInternet())
		{
			FetchReviews(MainSingleton.doctor_id);
		}
		else
		{
			Toast.makeText(getActivity(), "You dont have Internet...!", Toast.LENGTH_SHORT).show();
		}

		return rootView;

	}

	private void initUI() {
		progress = (ProgressBar) rootView.findViewById(R.id.progress);
		ratingList.clear();
		listView = (ListView) rootView.findViewById(R.id.list);
		no_Data= (TextView) rootView.findViewById(R.id.no_data);
	}

	// fetch upcoming apointment

	public void FetchReviews(final String doctor_id) {
		no_Data.setVisibility(View.INVISIBLE);
		progress.setVisibility(View.VISIBLE);
		ratingList.clear();
		RequestQueue queue = Volley.newRequestQueue(getActivity());
		StringRequest sr = new StringRequest(Request.Method.POST,
				ConstantUrls.UrlMain + ConstantUrls.UrlDoctorReviews,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {

						System.out.println("UrlDoctorReviews" + response);
						try {
							JSONObject json = new JSONObject(response);
							if (json.getString("code").equals("200")) {

								JSONArray data = json
										.getJSONArray(ConstantTag.tag_Data);
								for (int i = 0; i < data.length(); i++) {

									JSONObject obj = data.getJSONObject(i);

									RatingModel model = new RatingModel();
									model.setByName(obj
											.getString(ConstantTag.Tag_patientFirstName));
									model.setComment(obj
											.getString(ConstantTag.Tag_Comment));
									model.setRating(Integer.parseInt(obj
											.getString(ConstantTag.Tag_Rating)));
									ratingList.add(model);
								}

								adapter = new RatingAdapter(getActivity(),
										ratingList);
								listView.setAdapter(adapter);

							} else {
								no_Data.setVisibility(View.VISIBLE);
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