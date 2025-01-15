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

public class LabCreateActivity extends AppCompatActivity {

    private EditText etLabName, etLabDescription, etLabCode, etLabSupervisor, etLabCapacity;
    private Button btnSaveLab, btnCancel;

    private ImageButton btnBack;
    private LabViewModel labViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lab_create_activity);

        labViewModel = new ViewModelProvider(this).get(LabViewModel.class);

        initViews();
        setupBackButton();
        setupSaveButton();
    }

    private void initViews() {
        etLabName = findViewById(R.id.tvLabName);
        etLabDescription = findViewById(R.id.etLabDescription);
        etLabSupervisor = findViewById(R.id.tvLabSupervisor);
        etLabCapacity = findViewById(R.id.tvLabCapacity);
        etLabCode = findViewById(R.id.tvLabCode);
        btnSaveLab = findViewById(R.id.btnAdd);
        btnBack = findViewById(R.id.btnBack);
        btnCancel = findViewById(R.id.btnCancel);
    }

    private void setupBackButton() {
        btnBack.setOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());
        btnCancel.setOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());
    }

    private void setupSaveButton() {
        btnSaveLab.setOnClickListener(v -> {
            if(validateInput()) {
                saveLab();
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

    private void saveLab() {
        String labName = Objects.requireNonNull(etLabName.getText()).toString().trim();
        String labDescription = Objects.requireNonNull(etLabDescription.getText()).toString().trim();
        String labCode = Objects.requireNonNull(etLabCode.getText()).toString().trim();
        String labSupervisor = Objects.requireNonNull(etLabSupervisor.getText()).toString().trim();
        int labCapacity = Integer.parseInt(Objects.requireNonNull(etLabCapacity.getText()).toString().trim());


        Lab newLab = new Lab(labName, labDescription, labCode, labSupervisor, "Open", labCapacity);
        labViewModel.insert(newLab);

        Intent resultIntent = new Intent();
        showToast("Lab successfully added.");
        setResult(RESULT_OK, resultIntent);
        finish();
    }


    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}


