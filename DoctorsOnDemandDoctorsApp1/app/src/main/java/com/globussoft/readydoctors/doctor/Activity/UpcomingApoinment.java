package com.globussoft.readydoctors.doctor.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.globussoft.readydoctors.doctor.R;
import com.globussoft.readydoctors.doctor.adapters.MedicationAdapter;
import com.globussoft.readydoctors.doctor.adapters.SymtomsAdapter;
import com.globussoft.readydoctors.doctor.models.MedicationModel;
import com.globussoft.readydoctors.doctor.models.SymtomsModel;
import com.globussoft.readydoctors.doctor.uttils.MainSingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UpcomingApoinment extends Activity {

    TextView purpose_reson;
    ListView symtoms_list, allergies_list, medication_list, medical_condition_list;
    ArrayList<String> allergies_array = new ArrayList<String>();
    ArrayList<SymtomsModel> symtoms_array_list = new ArrayList<SymtomsModel>();
    ArrayList<MedicationModel> medication_array_list = new ArrayList<MedicationModel>();
    ArrayList<String> medical_cond_array = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.upcoming_app);
        InitView();
        SetView();

    }


    void InitView() {
        purpose_reson = (TextView) findViewById(R.id.purpose_reson);

        symtoms_list = (ListView) findViewById(R.id.general_symtoms);
        allergies_list = (ListView) findViewById(R.id.allergies_list);
        medication_list = (ListView) findViewById(R.id.medication);
        medical_condition_list = (ListView) findViewById(R.id.medical_condition_list);
    }

    void SetView() {
        allergies_array.clear();
        symtoms_array_list.clear();
        medication_array_list.clear();
        medical_cond_array.clear();
        purpose_reson.setText(MainSingleton.selectedUpcomingModel.getPurposeOfVisit());

        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(MainSingleton.selectedUpcomingModel.getAllergies());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        try {

            JSONArray medication_array = null;
            medication_array = new JSONArray(MainSingleton.selectedUpcomingModel.getMedications());
            for (int i = 0; i < medication_array.length(); i++) {

                JSONObject jsonObject = (JSONObject) medication_array.get(i);
                MedicationModel medicationModel = new MedicationModel();
                medicationModel.setName(jsonObject.getString("name"));
                medicationModel.setTime(jsonObject.getString("time"));
                medication_array_list.add(medicationModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {

                System.out.print("i" + i);
                try {
                    allergies_array.add(jsonArray.getString(i).toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        JSONObject symtoms_obj;
        try {

            symtoms_obj = new JSONObject(MainSingleton.selectedUpcomingModel.getSymptoms());
            if (symtoms_obj != null) {
                JSONArray generalarray = symtoms_obj.getJSONArray("general-symptoms");
                JSONArray relationshiparray = symtoms_obj.getJSONArray("Relationship-symptoms");

                if (generalarray.length() > 0) {
                    SymtomsModel model = new SymtomsModel();
                    model.setType("1");
                    symtoms_array_list.add(model);
                    for (int i = 0; i < generalarray.length(); i++) {
                        SymtomsModel model1 = new SymtomsModel();

                        model1.setName(generalarray.getString(i).toString());
                        symtoms_array_list.add(model1);
                    }
                }

                if (relationshiparray.length() > 0) {
                    SymtomsModel model = new SymtomsModel();
                    model.setType("2");
                    symtoms_array_list.add(model);
                    for (int i = 0; i < relationshiparray.length(); i++) {
                        SymtomsModel model1 = new SymtomsModel();

                        model1.setName(relationshiparray.getString(i).toString());
                        symtoms_array_list.add(model1);
                    }
                }

            }

        } catch (JSONException e)

        {
            e.printStackTrace();
        }

        JSONArray medicalConditions = null;
        try {
            medicalConditions = new JSONArray(MainSingleton.selectedUpcomingModel.getMedicalConditions());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (medicalConditions != null) {
            for (int i = 0; i < medicalConditions.length(); i++) {

                System.out.print("i" + i);
                try {
                    medical_cond_array.add(medicalConditions.getString(i).toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }


        ArrayAdapter listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, allergies_array);
        allergies_list.setAdapter(listAdapter);
        ArrayAdapter medical_cond_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, medical_cond_array);
        medical_condition_list.setAdapter(medical_cond_adapter);

        MedicationAdapter medicationAdapter = new MedicationAdapter(this, medication_array_list);
        medication_list.setAdapter(medicationAdapter);

        System.out.print("symtoms_array_list" + symtoms_array_list.size());
        SymtomsAdapter symtomsAdapter = new SymtomsAdapter(this, symtoms_array_list);
        symtoms_list.setAdapter(symtomsAdapter);
    }


}