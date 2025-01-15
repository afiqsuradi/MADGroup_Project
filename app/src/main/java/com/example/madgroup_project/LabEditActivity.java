package com.example.madgroup_project;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.madgroup_project.data.models.Lab;
import com.example.madgroup_project.data.viewmodel.LabViewModel;

import java.util.Objects;

public class LabEditActivity extends AppCompatActivity {

    private EditText etLabName, etLabDescription, etLabCode, etLabSupervisor, etLabCapacity;
    private Button btnUpdate, btnCancel;

    private ImageButton btnBack;
    private LabViewModel labViewModel;

    private Lab lab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lab_edit_activity);
        Intent intent = getIntent();
        int labId = intent.getIntExtra("lab_id", 1);
        labViewModel = new ViewModelProvider(this).get(LabViewModel.class);

        initViews(labId);
        setupBackButton();
        setupUpdateButton();
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

    private void setupBackButton() {
        btnBack.setOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());
        btnCancel.setOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());
    }

    private void setupUpdateButton() {
        btnUpdate.setOnClickListener(v -> {
            if(validateInput()) {
                updateLab();
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
                TextUtils.isEmpty(labCode) || TextUtils.isEmpty(labSupervisor) || TextUtils.isEmpty(labCapacityString) ) {
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

        lab.setName(labName);
        lab.setDescription(labDescription);
        lab.setCode(labCode);
        lab.setSupervisor(labSupervisor);
        lab.setCapacity(labCapacity);

        labViewModel.update(lab);

        Intent resultIntent = new Intent();
        showToast("Lab successfully updated.");
        setResult(RESULT_OK, resultIntent);
        finish();
    }


    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}


