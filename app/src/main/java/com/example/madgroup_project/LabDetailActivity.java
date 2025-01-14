package com.example.madgroup_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

import android.widget.Button;

import com.example.madgroup_project.data.models.Lab;
import com.example.madgroup_project.data.viewmodel.LabViewModel;

public class LabDetailActivity extends AppCompatActivity {

    private TextView etLabCode, etLabName, etLabSupervisor, etLabCapacity;

    private ImageButton btnBack, btnAddItem;
    private LabViewModel labViewModel;

    private int labId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lab_details);
        Intent intent = getIntent();
        labId = intent.getIntExtra("lab_id", 1);

        labViewModel = new ViewModelProvider(this).get(LabViewModel.class);

        etLabCode = findViewById(R.id.etLabCode);
        etLabName = findViewById(R.id.etLabName);
        etLabSupervisor = findViewById(R.id.etLabSupervisor);
        etLabCapacity = findViewById(R.id.etLabCapacity);
        btnBack = findViewById(R.id.btnBack);
        btnAddItem = findViewById(R.id.btnAddItem);

        btnBack.setOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());

        labViewModel.getLabById(labId).observe(this, lab -> {
            if (lab != null) {
                etLabCode.setText(lab.getCode());
                etLabName.setText(lab.getName());
                etLabSupervisor.setText(lab.getSupervisor());
                etLabCapacity.setText(String.valueOf(lab.getCapacity()));
            }
        });

        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LabDetailActivity.this, ItemCreateActivity.class);
                intent.putExtra("lab_id", labId);
                startActivity(intent);
            }
        });
    }

}
