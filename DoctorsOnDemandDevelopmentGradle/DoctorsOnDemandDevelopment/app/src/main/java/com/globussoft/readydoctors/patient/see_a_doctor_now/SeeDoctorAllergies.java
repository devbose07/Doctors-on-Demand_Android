package com.globussoft.readydoctors.patient.see_a_doctor_now;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.globussoft.readydoctors.patient.R;
import com.globussoft.readydoctors.patient.adapter.AlergiesAdapter;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by GLB-276 on 11/21/2015.
 */
public class SeeDoctorAllergies extends Activity {

    TextView next_btn;
    AlergiesAdapter adapter;
    ListView list;
    RelativeLayout addanother_layout;
    ArrayList<String> arraylist=new ArrayList<String>();
    ImageView backImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("PatientID " + SeeDoctorData.patientId);
        setContentView(R.layout.allergies);
        SeeDoctorData.allergies="";
        InitUI();
    }
    @Override
    protected void onResume()
    {
        System.out.println("i am in onrewume");
        super.onResume();
        SeeDoctorData.allergies="";
    }
    private void InitUI() {
        backImage=(ImageView)findViewById(R.id.backImage);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        next_btn=(TextView) findViewById(R.id.next);
        list=(ListView) findViewById(R.id.list);

        //code to set adapter to populate list
        View footerView =  ((LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.allergies_footer, null, false);
        list.addFooterView(footerView);
        addanother_layout=(RelativeLayout) footerView.findViewById(R.id.addanother);

        arraylist.add(null);
        adapter=new AlergiesAdapter(getApplicationContext(), arraylist);

        list.setAdapter(adapter);
        next_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                JSONArray ja = new JSONArray();
                for (int i = 0; i < arraylist.size(); i++)
                {
                    View view = list.getChildAt(i);
                    EditText allergiesname = (EditText) view.findViewById(R.id.data);

                    ja.put(allergiesname.getText().toString());

                    if (!allergiesname.getText().toString().toString().equalsIgnoreCase(""))
                    {
                        if (SeeDoctorData.allergies.equalsIgnoreCase("") )
                        {
                            SeeDoctorData.allergies = allergiesname.getText().toString().toString() + ",";
                        } else
                        {
                            SeeDoctorData.allergies = SeeDoctorData.allergies + allergiesname.getText().toString().toString() + ",";
                        }
                    }
                }

//                SeeDoctorData.allergies_json_list=ja.toString();
                System.out.println("SeeDoctorData.allergies   "+SeeDoctorData.allergies);

                Intent intent=new Intent(getApplicationContext(),SeeDoctorSymtoms.class);
                startActivity(intent);

            }
        } );


        addanother_layout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                arraylist.add(null);
                adapter.notifyDataSetChanged();

            }
        });
    }



}
