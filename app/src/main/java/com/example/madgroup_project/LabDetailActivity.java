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
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;
import com.example.madgroup_project.data.models.Lab;
import com.example.madgroup_project.data.viewmodel.LabViewModel;
import com.example.madgroup_project.ui.lab.LabFragmentPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import java.util.Objects;

public class LabDetailActivity extends AppCompatActivity {

    private TextView tvLabCode, tvLabName, tvLabSupervisor, tvLabCapacity, tvLabDescription;
    private ImageButton btnBack, btnAddItem, btnSettings;
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private LabViewModel labViewModel;
    private LabFragmentPagerAdapter labFragmentPagerAdapter;
    private Lab lab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lab_details);
        initViews();
        setupViewModel();
        loadLabDetails();
        setupClickListeners();
    }


    private void initViews() {
        tvLabCode = findViewById(R.id.tvLabCode);
        tvLabName = findViewById(R.id.tvLabName);
        tvLabSupervisor = findViewById(R.id.tvLabSupervisor);
        tvLabCapacity = findViewById(R.id.tvLabCapacity);
        tvLabDescription = findViewById(R.id.tvLabDescription);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        btnBack = findViewById(R.id.btnBack);
        btnAddItem = findViewById(R.id.btnAddItem);
        btnSettings = findViewById(R.id.btnSettings);
    }


    private void setupViewModel() {
        labViewModel = new ViewModelProvider(this).get(LabViewModel.class);
    }


    private void loadLabDetails() {
        int labId = getIntent().getIntExtra("lab_id", 1);
        labViewModel.getLabById(labId).observe(this, lab -> {
            if (lab != null) {
                this.lab = lab;
                updateUiWithLabData(lab);
                setupViewPager(lab);
            }
        });
    }

    private void updateUiWithLabData(Lab lab){
        tvLabCode.setText(lab.getCode());
        tvLabName.setText(lab.getName());
        tvLabSupervisor.setText(lab.getSupervisor());
        tvLabCapacity.setText(String.valueOf(lab.getCapacity()));
        tvLabDescription.setText(lab.getDescription());
    }
    private void setupViewPager(Lab lab){
        labFragmentPagerAdapter = new LabFragmentPagerAdapter(this, lab);
        viewPager.setAdapter(labFragmentPagerAdapter);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Dashboard");
                    break;
                case 1:
                    tab.setText("Items");
                    break;
            }
        }).attach();
    }
    private void setupClickListeners() {
        btnSettings.setOnClickListener(v -> showBottomDialog());
        btnBack.setOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());
        btnAddItem.setOnClickListener(v -> navigateToItemCreation());

    }
    private void navigateToItemCreation(){
        Intent intent = new Intent(LabDetailActivity.this, ItemCreateActivity.class);
        intent.putExtra("lab_id", Objects.requireNonNull(lab).getId());
        startActivity(intent);
    }

    private void showBottomDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.lab_details_bottom_sheet_options);

        FrameLayout btnDeleteLab = dialog.findViewById(R.id.btnDeleteLab);
        FrameLayout btnEditLab = dialog.findViewById(R.id.btnEditLab);
        btnDeleteLab.setOnClickListener(v -> onDeleteLab(dialog));
        btnEditLab.setOnClickListener(v -> onEditLab(dialog));

        dialog.show();
        setupDialogWindow(dialog);

    }
    private void setupDialogWindow(Dialog dialog){
        Objects.requireNonNull(dialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }


    private void onDeleteLab(Dialog dialog) {
        labViewModel.delete(lab);
        showToast("Lab successfully deleted.");
        dialog.dismiss();
        finish();
    }

    private void onEditLab(Dialog dialog){
        Intent intent = new Intent(LabDetailActivity.this, LabEditActivity.class);
        intent.putExtra("lab_id", lab.getId());
        startActivity(intent);
        dialog.dismiss();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}