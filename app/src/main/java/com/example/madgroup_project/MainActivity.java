package com.example.madgroup_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LabAdapter labAdapter;
    private List<Lab> labList = new ArrayList<>();
    private Button btnCreateLab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btnCreateLab = findViewById(R.id.btnCreateLab);

        // Example lab data
        labList.add(new Lab("Lab 1", "This is the first lab.", List.of("Beakers", "Bunsen Burner")));
        labList.add(new Lab("Lab 2", "This is the second lab.", List.of("Microscope", "Test Tubes")));

        labAdapter = new LabAdapter(labList, lab -> {
            Intent intent = new Intent(MainActivity.this, LabDetailActivity.class);
            intent.putExtra("lab_name", lab.getName());
            intent.putExtra("lab_description", lab.getDescription());
            intent.putStringArrayListExtra("lab_equipment", new ArrayList<>(lab.getEquipment()));
            startActivity(intent);
        });

        recyclerView.setAdapter(labAdapter);

        // Handle "Create New Lab" button click
        btnCreateLab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CreateLabActivity.class);
            startActivityForResult(intent, 1); // Request code 1 for result
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String labName = data.getStringExtra("lab_name");
            String labDescription = data.getStringExtra("lab_description");
            ArrayList<String> labEquipment = data.getStringArrayListExtra("lab_equipment");

            // Add the new lab to the list and refresh the adapter
            labList.add(new Lab(labName, labDescription, labEquipment));
            labAdapter.notifyDataSetChanged();
        }
    }
}
