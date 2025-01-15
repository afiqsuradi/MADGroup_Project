package com.example.madgroup_project;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

import android.widget.Button;
import android.widget.Toast;

import com.example.madgroup_project.data.models.Lab;
import com.example.madgroup_project.data.viewmodel.LabViewModel;

public class LabDetailActivity extends AppCompatActivity {

    private TextView etLabCode, etLabName, etLabSupervisor, etLabCapacity;

    private ImageButton btnBack, btnAddItem, btnSettings;
    private LabViewModel labViewModel;

    private Lab lab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lab_details);
        Intent intent = getIntent();
        int labId = intent.getIntExtra("lab_id", 1);

        labViewModel = new ViewModelProvider(this).get(LabViewModel.class);

        etLabCode = findViewById(R.id.etLabCode);
        etLabName = findViewById(R.id.etLabName);
        etLabSupervisor = findViewById(R.id.etLabSupervisor);
        etLabCapacity = findViewById(R.id.etLabCapacity);
        btnBack = findViewById(R.id.btnBack);
        btnAddItem = findViewById(R.id.btnAddItem);
        btnSettings= findViewById(R.id.btnSettings);

        btnSettings.setOnClickListener(v -> showBottomDialog());
        btnBack.setOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());

        labViewModel.getLabById(labId).observe(this, lab -> {
            if (lab != null) {
                this.lab = lab;
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
                intent.putExtra("lab_id", lab.getId());
                startActivity(intent);
            }
        });
    }


    private void showBottomDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.lab_details_bottom_sheet_options);

        FrameLayout btnDeleteLab = dialog.findViewById(R.id.btnDeleteLab);
        FrameLayout btnEditLab = dialog.findViewById(R.id.btnEditLab);
        btnDeleteLab.setOnClickListener(v -> onDeleteLab());
        btnEditLab.setOnClickListener(v -> {
            Intent intent = new Intent(LabDetailActivity.this, LabEditActivity.class);
            intent.putExtra("lab_id", lab.getId());
            startActivity(intent);
            dialog.dismiss();
        });
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    private void onDeleteLab(){
        labViewModel.delete(lab);
        showToast("Lab successfully deleted.");
        finish();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
