package com.example.madgroup_project;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madgroup_project.data.models.Lab;
import com.example.madgroup_project.data.viewmodel.LabViewModel;
import com.example.madgroup_project.ui.lab.LabListRecyclerViewAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView labRecyclerView;
    private LabListRecyclerViewAdapter labListRecyclerViewAdapter;
    private LabViewModel labViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    }
}
