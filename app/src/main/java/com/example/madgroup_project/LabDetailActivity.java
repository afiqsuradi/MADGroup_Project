package com.example.madgroup_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.madgroup_project.EquipmentDetailActivity;
import com.example.madgroup_project.R;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.widget.Button;

public class LabDetailActivity extends AppCompatActivity {

    private TextView labName, labDescription, totalComputers, totalKeyboards, totalDesktops;
    private List<String> computers = new ArrayList<>();
    private List<String> keyboards = new ArrayList<>();
    private List<String> desktops = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lab_details);

        labName = findViewById(R.id.labName);
        labDescription = findViewById(R.id.labDescription);
        totalComputers = findViewById(R.id.totalComputers);
        totalKeyboards = findViewById(R.id.totalKeyboards);
        totalDesktops = findViewById(R.id.totalDesktops);

        // Example data
        computers.add("Computer 1");
        computers.add("Computer 2");
        keyboards.add("Keyboard 1");
        desktops.add("Desktop 1");
        desktops.add("Desktop 2");
        desktops.add("Desktop 3");

        // Set totals
        totalComputers.setText("Total Computers: " + computers.size());
        totalKeyboards.setText("Total Keyboards: " + keyboards.size());
        totalDesktops.setText("Total Desktops: " + desktops.size());

        // Set click listeners
        totalComputers.setOnClickListener(v -> openDetailActivity("Computers", computers));
        totalKeyboards.setOnClickListener(v -> openDetailActivity("Keyboards", keyboards));
        totalDesktops.setOnClickListener(v -> openDetailActivity("Desktops", desktops));

        // Back Button functionality
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> onBackPressed());
    }

    private void openDetailActivity(String category, List<String> items) {
        Intent intent = new Intent(this, EquipmentDetailActivity.class);
        intent.putExtra("category", category);
        intent.putStringArrayListExtra("items", new ArrayList<>(items));
        startActivity(intent);
    }
}
