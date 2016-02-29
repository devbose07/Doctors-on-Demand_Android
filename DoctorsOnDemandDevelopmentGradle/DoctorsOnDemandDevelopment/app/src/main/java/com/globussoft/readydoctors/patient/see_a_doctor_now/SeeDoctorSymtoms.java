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
import com.globussoft.readydoctors.patient.adapter.SymtomsAdapter;
import com.globussoft.readydoctors.patient.model.SelectedSymtoms;
import com.globussoft.readydoctors.patient.model.SymtomsModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by GLB-276 on 11/21/2015.
 */
public class SeeDoctorSymtoms extends Activity
{

    ListView list;
    TextView next,note;
    ArrayList<SymtomsModel> symptoms = new ArrayList<SymtomsModel>();
    ArrayList<SelectedSymtoms> symptoms_selected = new ArrayList<SelectedSymtoms>();
    SymtomsAdapter adapter;
    ImageView backImage;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        System.out.println("PatientID " + SeeDoctorData.patientId);
        setContentView(R.layout.symtoms);
        SeeDoctorData.generalSymptoms="";
        SeeDoctorData.headNeckSymptoms="";
        SeeDoctorData.digestiveTrack="";
        InitUI();
        System.out.println("Symtoms");
    }
    @Override
    protected void onResume()
    {
        System.out.println("i am in onrewume");
        super.onResume();
        SeeDoctorData.generalSymptoms="";
        SeeDoctorData.headNeckSymptoms="";
        SeeDoctorData.digestiveTrack="";
    }

    private void InitUI()
    {
        backImage=(ImageView)findViewById(R.id.backImage);
        backImage.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                onBackPressed();
            }
        });
        System.out.println("Symtoms " + symptoms.size());
        list = (ListView) findViewById(R.id.list);
        note=(TextView) findViewById(R.id.textView1);
        note.setText(Html.fromHtml("<b>Do you have any of these symtoms.</b> Tap all that apply."));
        next = (TextView) findViewById(R.id.next);

        // adding values
        SymtomsModel model = new SymtomsModel();
        model.setHeader(true);
        model.setmSymtom("GENERAL SYMTOMS");
        symptoms.add(model);
        for (int i = 0; i < AppData.symptoms_genaral.length; i++)
        {
            SymtomsModel model1 = new SymtomsModel();

            model1.setHeader(false);
            model1.setmSymtom(AppData.symptoms_genaral[i]);
            model1.setmBelongsTo("GENERAL SYMTOMS");
            symptoms.add(model1);
        }

        SymtomsModel model2 = new SymtomsModel();
        model2.setHeader(true);
        model2.setmSymtom("HEAD AND NECK");
        symptoms.add(model2);
        for (int i = 0; i < AppData.symptoms_head_neck.length; i++)
        {
            SymtomsModel model3 = new SymtomsModel();
            model3.setHeader(false);
            model3.setmSymtom(AppData.symptoms_head_neck[i]);
            model3.setmBelongsTo("HEAD AND NECK");
            symptoms.add(model3);
        }


        SymtomsModel model5 = new SymtomsModel();
        model5.setHeader(true);
        model5.setmSymtom("DIGESTIVE TRACK");
        symptoms.add(model5);
        for (int i = 0; i < AppData.symptoms_degestive_track.length; i++)
        {
            SymtomsModel model6 = new SymtomsModel();
            model6.setHeader(false);
            model6.setmSymtom(AppData.symptoms_degestive_track[i]);
            model6.setmBelongsTo("DIGESTIVE TRACK");
            symptoms.add(model6);
        }

        // setadapter
        adapter = new SymtomsAdapter(getApplicationContext(), symptoms);
        list.setAdapter(adapter);

        next.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                getSymtoms();
                Intent intent = new Intent(getApplicationContext(),SeeDoctorMedicalCondition.class);
                startActivity(intent);

            }
        });
    }
    public void getSymtoms()
    {
        System.out.println("selected symtoms");
        symptoms_selected.clear();
        for (int i = 0; i < adapter.mCheckStates.size(); i++)
        {
            if (adapter.mCheckStates.get(i, false))
            {
                SelectedSymtoms model = new SelectedSymtoms();
                model.setSymtom(symptoms.get(i).getmSymtom());
                model.setSymtomHeader(symptoms.get(i).getmBelongsTo());
                symptoms_selected.add(model);
                System.out.println("checked "
                        + symptoms.get(i).getmSymtom() + " Belongs to "
                        + symptoms.get(i).getmBelongsTo());
            }
        }

        JSONObject jsonObject = new JSONObject();

        JSONArray general_symtoms_array = new JSONArray();
        JSONArray head_neck_array = new JSONArray();
        JSONArray symptoms_degestive_track = new JSONArray();
        for (int i = 0; i < symptoms_selected.size(); i++)
        {
            if (symptoms_selected.get(i).getSymtomHeader().equalsIgnoreCase("GENERAL SYMTOMS"))
            {
                general_symtoms_array.put(symptoms_selected.get(i).getSymtom());
                /*SeeDoctorData.generalSymptoms=general_symtoms_array.toString();*/
                if (!symptoms_selected.get(i).getSymtom().toString().equalsIgnoreCase(""))
                {
                    if (SeeDoctorData.generalSymptoms.equalsIgnoreCase("") )
                    {
                        SeeDoctorData.generalSymptoms = symptoms_selected.get(i).getSymtom() + ",";
                    } else
                    {
                        SeeDoctorData.generalSymptoms = SeeDoctorData.generalSymptoms + symptoms_selected.get(i).getSymtom() + ",";
                    }
                }


            }
            if (symptoms_selected.get(i).getSymtomHeader().equalsIgnoreCase("HEAD AND NECK"))
            {
                head_neck_array.put(symptoms_selected.get(i).getSymtom());
                SeeDoctorData.headNeckSymptoms=head_neck_array.toString();

                if (!symptoms_selected.get(i).getSymtom().toString().equalsIgnoreCase(""))
                {
                    if (SeeDoctorData.headNeckSymptoms.equalsIgnoreCase("") )
                    {
                        SeeDoctorData.headNeckSymptoms = symptoms_selected.get(i).getSymtom() + ",";
                    } else
                    {
                        SeeDoctorData.headNeckSymptoms = SeeDoctorData.headNeckSymptoms + symptoms_selected.get(i).getSymtom() + ",";
                    }
                }
            }

            if (symptoms_selected.get(i).getSymtomHeader().equalsIgnoreCase("DIGESTIVE TRACK"))
            {
                symptoms_degestive_track.put(symptoms_selected.get(i).getSymtom());
                SeeDoctorData.digestiveTrack=symptoms_degestive_track.toString();
                if (!symptoms_selected.get(i).getSymtom().toString().equalsIgnoreCase(""))
                {
                    if (SeeDoctorData.digestiveTrack.equalsIgnoreCase("") )
                    {
                        SeeDoctorData.digestiveTrack = symptoms_selected.get(i).getSymtom() + ",";
                    } else
                    {
                        SeeDoctorData.digestiveTrack = SeeDoctorData.digestiveTrack + symptoms_selected.get(i).getSymtom() + ",";
                    }
                }
            }

        }

        System.out.println("SeeDoctorData.generalSymptoms "+SeeDoctorData.generalSymptoms);
        System.out.println("SeeDoctorData.digestiveSymptoms "+SeeDoctorData.digestiveTrack);
        System.out.println("SeeDoctorData.headNeckSymptoms "+SeeDoctorData.headNeckSymptoms);

    }
}
