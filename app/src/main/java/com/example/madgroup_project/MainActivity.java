package com.example.madgroup_project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madgroup_project.data.viewmodel.LabViewModel;
import com.example.madgroup_project.ui.lab.LabListRecyclerViewAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView labRecyclerView;
    private LabListRecyclerViewAdapter labListRecyclerViewAdapter;
    private LabViewModel labViewModel;

    private Button btnCreateLab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCreateLab = findViewById(R.id.btnCreateLab);
        labRecyclerView = findViewById(R.id.recyclerView);
        labRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        labListRecyclerViewAdapter = new LabListRecyclerViewAdapter(new ArrayList<>());
        labRecyclerView.setAdapter(labListRecyclerViewAdapter);

        labViewModel = new ViewModelProvider(this).get(LabViewModel.class);
        labViewModel.getAllLabs().observe(this, labs -> {
            if (labs != null) {
                labListRecyclerViewAdapter.setLabs(labs);
            }
        });
        btnCreateLab.setOnClickListener(v -> goToCreateLabActivity());

    }

    public void goToCreateLabActivity(){
        Intent intent = new Intent(this, CreateLabActivity.class);
        startActivity(intent);
    }
}
