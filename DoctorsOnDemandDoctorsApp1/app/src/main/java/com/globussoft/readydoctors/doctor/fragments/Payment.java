package com.globussoft.readydoctors.doctor.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
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
import com.globussoft.readydoctors.doctor.adapters.PaymentAdapter;
import com.globussoft.readydoctors.doctor.models.PaymentModel;
import com.globussoft.readydoctors.doctor.uttils.ConnectionDetector;
import com.globussoft.readydoctors.doctor.uttils.ConstantTag;
import com.globussoft.readydoctors.doctor.uttils.ConstantUrls;
import com.globussoft.readydoctors.doctor.uttils.MainSingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Payment extends Fragment {
    View rootView;
    ListView listView;
    ArrayList<PaymentModel> paymentlist = new ArrayList<PaymentModel>();
    ProgressBar progress;
    PaymentAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.payment, container, false);
        initUI();
        if(new ConnectionDetector(getActivity()).isConnectingToInternet())
        {
            FetchPaymentHistory(MainSingleton.doctor_id);
        }
        else
        {
            Toast.makeText(getActivity(), "You dont have Internet...!", Toast.LENGTH_SHORT).show();
        }

        return rootView;

    }

    private void initUI() {
        progress = (ProgressBar) rootView.findViewById(R.id.progress);
        paymentlist.clear();
        listView = (ListView) rootView.findViewById(R.id.list);

    }

    // fetch upcoming apointment

    public void FetchPaymentHistory(final String doctor_id) {
        progress.setVisibility(View.VISIBLE);
        paymentlist.clear();
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequest sr = new StringRequest(Request.Method.POST,
                ConstantUrls.UrlMain + ConstantUrls.UrlPayment,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        System.out.println("Payment" + response);
                        try {
                            JSONObject json = new JSONObject(response);
                            if (json.getString("code").equals("200")) {

                                JSONArray data = json
                                        .getJSONArray(ConstantTag.tag_Data);
                                for (int i = 0; i < data.length(); i++) {

                                    JSONObject obj = data.getJSONObject(i);

                                    PaymentModel model = new PaymentModel();
                                    model.setPaymentId(obj
                                            .getString(ConstantTag.Tag_paymentid));
                                    model.setAcknowledgement(obj
                                            .getString(ConstantTag.Tag_acknowledgement));
                                    model.setAmount(obj
                                            .getString(ConstantTag.Tag_amount));
                                    model.setCorrelationId(obj
                                            .getString(ConstantTag.Tag_correlationId));
                                    model.setMemberId(obj
                                            .getString(ConstantTag.Tag_memberId));
                                    model.setPaymentTime(obj
                                            .getString(ConstantTag.Tag_paymentTime));

                                    paymentlist.add(model);
                                }

                                adapter = new PaymentAdapter(getActivity(),
                                        paymentlist);
                                listView.setAdapter(adapter);

                            } else {
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
                params.put("docid", doctor_id);

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