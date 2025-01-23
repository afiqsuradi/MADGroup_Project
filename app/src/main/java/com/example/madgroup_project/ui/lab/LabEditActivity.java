package com.example.madgroup_project.ui.lab;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.madgroup_project.R;
import com.example.madgroup_project.data.models.Lab;
import com.example.madgroup_project.data.viewmodel.LabViewModel;

import org.json.JSONObject;

import java.util.Objects;

public class LabEditActivity extends AppCompatActivity {

    private EditText etLabName, etLabDescription, etLabCode, etLabSupervisor, etLabCapacity;
    private Button btnUpdate, btnCancel;
    private ImageButton btnBack;
    private LabViewModel labViewModel;
    private Lab lab;
    private SharedPreferences sharedPreferences;
    private final String draftKey = "lab_edit_draft";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lab_edit_activity);
        Intent intent = getIntent();
        int labId = intent.getIntExtra("lab_id", -1);


        labViewModel = new ViewModelProvider(this).get(LabViewModel.class);
        sharedPreferences = getSharedPreferences("LabEditPrefs", Context.MODE_PRIVATE);


        initViews(labId);
        setupBackButton();
        setupUpdateButton();
        loadDraft();

    }


    private void initViews(int labId) {
        etLabName = findViewById(R.id.etLabName);
        etLabDescription = findViewById(R.id.etLabDescription);
        etLabSupervisor = findViewById(R.id.etLabSupervisor);
        etLabCapacity = findViewById(R.id.etLabCapacity);
        etLabCode = findViewById(R.id.etLabCode);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnBack = findViewById(R.id.btnBack);
        btnCancel = findViewById(R.id.btnCancel);
        if(labId != -1){
            labViewModel.getLabById(labId).observe(this, lab -> {
                if (lab != null) {
                    this.lab = lab;
                    etLabName.setText(lab.getName());
                    etLabDescription.setText(lab.getDescription());
                    etLabSupervisor.setText(lab.getSupervisor());
                    etLabCapacity.setText(String.valueOf(lab.getCapacity()));
                    etLabCode.setText(lab.getCode());
                }
            });
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveDraft();
    }

    private void setupBackButton() {
        btnBack.setOnClickListener(v -> {
            getOnBackPressedDispatcher().onBackPressed();
        });

        btnCancel.setOnClickListener(v -> {
            clearDraft();
            getOnBackPressedDispatcher().onBackPressed();
        });
    }

    private void setupUpdateButton() {
        btnUpdate.setOnClickListener(v -> {
            if (validateInput()) {
                updateLab();
                clearDraft();
            }
        });
    }

    private boolean validateInput() {
        String labName = Objects.requireNonNull(etLabName.getText()).toString().trim();
        String labDescription = Objects.requireNonNull(etLabDescription.getText()).toString().trim();
        String labCode = Objects.requireNonNull(etLabCode.getText()).toString().trim();
        String labSupervisor = Objects.requireNonNull(etLabSupervisor.getText()).toString().trim();
        String labCapacityString = Objects.requireNonNull(etLabCapacity.getText()).toString().trim();

        if (TextUtils.isEmpty(labName) || TextUtils.isEmpty(labDescription) ||
                TextUtils.isEmpty(labCode) || TextUtils.isEmpty(labSupervisor) || TextUtils.isEmpty(labCapacityString)) {
            showToast("Please fill out all fields.");
            return false;
        }
        try {
            Integer.parseInt(labCapacityString);
        } catch (NumberFormatException e) {
            showToast("Invalid Capacity, please enter a valid number.");
            return false;
        }
        return true;
    }

    private void updateLab() {
        String labName = Objects.requireNonNull(etLabName.getText()).toString().trim();
        String labDescription = Objects.requireNonNull(etLabDescription.getText()).toString().trim();
        String labCode = Objects.requireNonNull(etLabCode.getText()).toString().trim();
        String labSupervisor = Objects.requireNonNull(etLabSupervisor.getText()).toString().trim();
        int labCapacity = Integer.parseInt(Objects.requireNonNull(etLabCapacity.getText()).toString().trim());

        if(lab == null){
            lab = new Lab();
            lab.setName(labName);
            lab.setDescription(labDescription);
            lab.setCode(labCode);
            lab.setSupervisor(labSupervisor);
            lab.setCapacity(labCapacity);
            labViewModel.insert(lab);
        } else {
            lab.setName(labName);
            lab.setDescription(labDescription);
            lab.setCode(labCode);
            lab.setSupervisor(labSupervisor);
            lab.setCapacity(labCapacity);
            labViewModel.update(lab);
        }


        Intent resultIntent = new Intent();
        showToast("Lab successfully updated.");
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    private void saveDraft() {
        JSONObject draftObject = new JSONObject();
        try {
            draftObject.put("labName", etLabName.getText().toString());
            draftObject.put("labDescription", etLabDescription.getText().toString());
            draftObject.put("labCode", etLabCode.getText().toString());
            draftObject.put("labSupervisor", etLabSupervisor.getText().toString());
            draftObject.put("labCapacity", etLabCapacity.getText().toString());

        } catch (Exception e){
            e.printStackTrace();
        }

        sharedPreferences.edit().putString(draftKey, draftObject.toString()).apply();

    }

    private void loadDraft() {
        String draftString = sharedPreferences.getString(draftKey, null);
        if (draftString != null) {
            try {
                JSONObject draftObject = new JSONObject(draftString);
                etLabName.setText(draftObject.getString("labName"));
                etLabDescription.setText(draftObject.getString("labDescription"));
                etLabCode.setText(draftObject.getString("labCode"));
                etLabSupervisor.setText(draftObject.getString("labSupervisor"));
                etLabCapacity.setText(draftObject.getString("labCapacity"));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void clearDraft(){
        sharedPreferences.edit().remove(draftKey).apply();
    }
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}