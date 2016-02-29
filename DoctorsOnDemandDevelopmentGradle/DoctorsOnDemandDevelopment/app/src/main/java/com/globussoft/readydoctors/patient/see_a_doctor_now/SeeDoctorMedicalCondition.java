package com.globussoft.readydoctors.patient.see_a_doctor_now;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.globussoft.readydoctors.patient.R;
import com.globussoft.readydoctors.patient.Utills.AppData;
import com.globussoft.readydoctors.patient.adapter.MedicalConditionAdapter;
import com.globussoft.readydoctors.patient.model.MedicalConditionModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by GLB-276 on 11/21/2015.
 */
public class SeeDoctorMedicalCondition extends Activity
{

    ListView list;
    TextView next,note;
    ImageView backImage;
    ArrayList<MedicalConditionModel> conditions = new ArrayList<MedicalConditionModel>();
    ArrayList<MedicalConditionModel> conditions_selected = new ArrayList<MedicalConditionModel>();
    MedicalConditionAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        System.out.println("PatientID " + SeeDoctorData.patientId);
        setContentView(R.layout.medical_condition);
        SeeDoctorData.medicalConditions="";
        InitUI();
    }
    @Override
    protected void onResume()
    {
        System.out.println("i am in onrewume");
        super.onResume();
        SeeDoctorData.medicalConditions="";
    }
    private void InitUI()
    {
        backImage=(ImageView)findViewById(R.id.backImage);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        list=(ListView) findViewById(R.id.list);
        next=(TextView) findViewById(R.id.next);
        note=(TextView) findViewById(R.id.textView1);
        note.setText(Html.fromHtml("<b>please let us know if you have any medical conditions.</b> Tap all that apply."));

        // adding values
        MedicalConditionModel model = new MedicalConditionModel();
        model.setHeader(true);
        model.setCondition("CONDITIONS");
        conditions.add(model);
        for (int i = 0; i < AppData.medicalcondition_array.length; i++)
        {
            MedicalConditionModel model1 = new MedicalConditionModel();
            model1.setHeader(false);
            model1.setCondition(AppData.medicalcondition_array[i]);
            conditions.add(model1);
        }



        adapter=new MedicalConditionAdapter(getApplicationContext(), conditions);
        list.setAdapter(adapter);

        next.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getConditions();
                Intent intent=new Intent(getApplicationContext(), SeeDoctorPharmacy.class);
                startActivity(intent);
            }
        });
    }

    protected void getConditions()
    {
        System.out.println("selected symtoms");
        conditions_selected.clear();
        for (int i = 0; i < adapter.mCheckStates.size(); i++)
        {
            if (adapter.mCheckStates.get(i, false))
            {
                MedicalConditionModel model = new MedicalConditionModel();
                model.setCondition(conditions.get(i).getCondition());
                conditions_selected.add(model);
                System.out.println("checked "+ conditions.get(i).getCondition() );
            }
        }

        JSONObject jsonObject = new JSONObject();

        JSONArray medical_conditions_array = new JSONArray();
        for (int i = 0; i < conditions_selected.size(); i++)
        {
            medical_conditions_array.put(conditions_selected.get(i).getCondition());
            if (!conditions_selected.get(i).getCondition().toString().equalsIgnoreCase(""))
            {
                if (SeeDoctorData.medicalConditions.equalsIgnoreCase(""))
                {
                    SeeDoctorData.medicalConditions = conditions_selected.get(i).getCondition().toString() + ",";
                }
                else
                {
                    SeeDoctorData.medicalConditions = SeeDoctorData.medicalConditions + conditions_selected.get(i).getCondition() + ",";

                }
            }
        }
        try
        {
            if (medical_conditions_array.length() > 0)
            {
                jsonObject.put("medical_condition",medical_conditions_array);
            }
        }
        catch (JSONException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
//        SeeDoctorData.medical_condition = jsonObject.toString();
        System.out.println("SeeDoctorData.medicalConditions ==> "+SeeDoctorData.medicalConditions);
    }


}
