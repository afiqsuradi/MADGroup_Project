package com.example.madgroup_project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class CreateLabActivity extends AppCompatActivity {

    private EditText etLabName, etLabDescription, etLabEquipment;
    private Button btnSaveLab, backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_lab);

        etLabName = findViewById(R.id.etLabName);
        etLabDescription = findViewById(R.id.etLabDescription);
        etLabEquipment = findViewById(R.id.etLabEquipment);
        btnSaveLab = findViewById(R.id.btnSaveLab);
        backButton = findViewById(R.id.backButton); // Find the back button

        // Handle the Back Button Click
        backButton.setOnClickListener(v -> {
            onBackPressed(); // Call the default back functionality
        });

        btnSaveLab.setOnClickListener(v -> {
            String labName = etLabName.getText().toString().trim();
            String labDescription = etLabDescription.getText().toString().trim();
            String equipmentText = etLabEquipment.getText().toString().trim();

            if (labName.isEmpty() || labDescription.isEmpty()) {
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            ArrayList<String> equipmentList = new ArrayList<>(Arrays.asList(equipmentText.split(",")));

            Intent resultIntent = new Intent();
            resultIntent.putExtra("lab_name", labName);
            resultIntent.putExtra("lab_description", labDescription);
            resultIntent.putStringArrayListExtra("lab_equipment", equipmentList);
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }
}


