package com.example.madgroup_project.ui.item;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.madgroup_project.R;
import com.example.madgroup_project.data.ItemConditions;
import com.example.madgroup_project.data.ItemTypes;
import com.example.madgroup_project.data.models.Item;
import com.example.madgroup_project.data.viewmodel.ItemViewModel;
import com.example.madgroup_project.data.viewmodel.LabViewModel;

import org.json.JSONObject;

import java.util.Arrays;

public class ItemCreateActivity extends AppCompatActivity {

    private AutoCompleteTextView actvItemType, actvItemCondition;
    private EditText etItemName, etItemSerialNo;
    private Button btnCancel, btnAdd;
    private ImageButton btnBack;
    private TextView tvLabName;
    private int labId;
    private LabViewModel labViewModel;
    private ItemViewModel itemViewModel;

    private SharedPreferences sharedPreferences;
    private final String draftKey = "item_create_draft";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_create_activity);

        labViewModel = new ViewModelProvider(this).get(LabViewModel.class);
        itemViewModel = new ViewModelProvider(this).get(ItemViewModel.class);
        labId = getIntent().getIntExtra("lab_id", 1);

        sharedPreferences = getSharedPreferences("ItemCreatePrefs", Context.MODE_PRIVATE);


        initViews();
        setupItemTypeDropdown();
        setupItemConditionDropdown();
        setupListeners();
        observeLabData();
        loadDraft();

    }

    private void initViews() {
        actvItemType = findViewById(R.id.actvItemType);
        actvItemCondition = findViewById(R.id.actvItemCondition);
        etItemName = findViewById(R.id.etItemName);
        etItemSerialNo = findViewById(R.id.etItemSerialNo);
        btnCancel = findViewById(R.id.btnCancel);
        btnAdd = findViewById(R.id.btnAdd);
        tvLabName = findViewById(R.id.tvLabName);
        btnBack = findViewById(R.id.btnBack);
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveDraft();
    }

    private void setupListeners(){
        btnCancel.setOnClickListener(v -> {
            clearDraft();
            getOnBackPressedDispatcher().onBackPressed();
        });
        btnBack.setOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());
        btnAdd.setOnClickListener(v -> {
            addItem();
            clearDraft();
        });
    }


    private void setupItemTypeDropdown() {
        setupDropdown(actvItemType, ItemTypes.getAllDisplayNames());
    }

    private void setupItemConditionDropdown() {
        setupDropdown(actvItemCondition, ItemConditions.getAllDisplayNames());
    }

    private void setupDropdown(AutoCompleteTextView autoCompleteTextView, String[] displayNames){
        autoCompleteTextView.setKeyListener(null);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, displayNames);
        autoCompleteTextView.setAdapter(adapter);
    }

    private void observeLabData() {
        labViewModel.getLabById(labId).observe(this, lab -> {
            if (lab != null) {
                labId = lab.getId();
                tvLabName.setText(lab.getName());
            }
        });
    }

    private void addItem() {
        String itemName = etItemName.getText().toString().trim();
        String itemSerialNo = etItemSerialNo.getText().toString().trim();
        String itemType = actvItemType.getText().toString().trim();
        String itemCondition = actvItemCondition.getText().toString().trim();

        if (itemName.isEmpty() || itemSerialNo.isEmpty() || itemType.isEmpty() || itemCondition.isEmpty()) {
            showToast("Please fill all fields");
            return;
        }

        ItemTypes selectedItemType = getItemTypeFromDisplayName(itemType);
        if (selectedItemType == null) {
            showToast("Please select a value for item type");
            return;
        }

        ItemConditions selectedItemCondition = getItemConditionFromDisplayName(itemCondition);
        if (selectedItemCondition == null) {
            showToast("Please select a value for item condition");
            return;
        }

        Item newItem = new Item();
        newItem.setLabId(labId);
        newItem.setName(itemName);
        newItem.setSerialNumber(itemSerialNo);
        newItem.setCondition(selectedItemCondition);
        newItem.setType(selectedItemType);
        itemViewModel.insert(newItem);
        showToast("Item added successfully");
        finish();
    }

    private ItemTypes getItemTypeFromDisplayName(String displayName) {
        return Arrays.stream(ItemTypes.values())
                .filter(type -> type.getDisplayName().equals(displayName))
                .findFirst()
                .orElse(null);
    }

    private ItemConditions getItemConditionFromDisplayName(String displayName) {
        return Arrays.stream(ItemConditions.values())
                .filter(condition -> condition.getDisplayName().equals(displayName))
                .findFirst()
                .orElse(null);
    }


    private void saveDraft() {
        JSONObject draftObject = new JSONObject();
        try {
            draftObject.put("itemName", etItemName.getText().toString());
            draftObject.put("itemSerial", etItemSerialNo.getText().toString());
            draftObject.put("itemType", actvItemType.getText().toString());
            draftObject.put("itemCondition", actvItemCondition.getText().toString());

        } catch (Exception e){
            e.printStackTrace();
        }

        sharedPreferences.edit().putString(draftKey, draftObject.toString()).apply();
    }

    private void loadDraft() {
        String draftString = sharedPreferences.getString(draftKey, null);
        if (draftString != null) {
            try {
                JSONObject draftObject = new JSONObject(draftString);

                etItemName.setText(draftObject.getString("itemName"));
                etItemSerialNo.setText(draftObject.getString("itemSerial"));
                actvItemType.setText(draftObject.getString("itemType"));
                actvItemCondition.setText(draftObject.getString("itemCondition"));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void clearDraft(){
        sharedPreferences.edit().remove(draftKey).apply();
    }


    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}