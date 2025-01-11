package com.example.madgroup_project;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class EquipmentDetailActivity extends AppCompatActivity {

    private TextView categoryTitle;
    private ListView itemListView;
    private Button addItemButton;
    private Button backButton;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.equipment_details);

        categoryTitle = findViewById(R.id.categoryTitle);
        itemListView = findViewById(R.id.itemListView);
        addItemButton = findViewById(R.id.addItemButton);
        backButton = findViewById(R.id.backButton);

        // Get data from intent
        String category = getIntent().getStringExtra("category");
        items = getIntent().getStringArrayListExtra("items");

        // Set category title
        categoryTitle.setText(category);

        // Set up ListView
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        itemListView.setAdapter(adapter);

        // Add item functionality
        addItemButton.setOnClickListener(v -> showAddItemDialog());

        // Back button functionality
        backButton.setOnClickListener(v -> onBackPressed());
    }

    private void showAddItemDialog() {
        EditText input = new EditText(this);
        new AlertDialog.Builder(this)
                .setTitle("Add Item")
                .setView(input)
                .setPositiveButton("Add", (dialog, which) -> {
                    String newItem = input.getText().toString().trim();
                    if (!newItem.isEmpty()) {
                        items.add(newItem);
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(this, "Item cannot be empty", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
