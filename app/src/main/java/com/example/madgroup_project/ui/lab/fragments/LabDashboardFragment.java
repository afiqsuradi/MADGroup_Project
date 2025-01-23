package com.example.madgroup_project.ui.lab.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.madgroup_project.R;
import com.example.madgroup_project.data.viewmodel.ItemViewModel;
import com.example.madgroup_project.data.viewmodel.LabViewModel;

public class LabDashboardFragment extends Fragment {
//    private static final String LAB_ID = "lab_id";
    private TextView tvTotalLabs, tvTotalItems, tvTotalItemsWorking, tvTotalItemsMaintenance, tvTotalItemsDamaged;

    private LabViewModel labViewModel;

    public LabDashboardFragment() {
        // Required empty public constructor
    }

    public static LabDashboardFragment newInstance(int labId) {
        LabDashboardFragment fragment = new LabDashboardFragment();
//        Bundle args = new Bundle();
//        args.putInt(LAB_ID, labId);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        labViewModel = new ViewModelProvider(this).get(LabViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lab_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        labViewModel.getAllLabsSummary().observe(getViewLifecycleOwner(), labsSummary -> {
            if (labsSummary != null) {
                tvTotalLabs.setText(String.valueOf(labsSummary.getTotalLabs()));
                tvTotalItems.setText(String.valueOf(labsSummary.getTotalItems()));
                tvTotalItemsWorking.setText(String.valueOf(labsSummary.getWorkingItems()));
                tvTotalItemsMaintenance.setText(String.valueOf(labsSummary.getMaintenanceItems()));
                tvTotalItemsDamaged.setText(String.valueOf(labsSummary.getBrokenItems()));
          }
        });
    }

    public void initView(View view){
        tvTotalLabs = view.findViewById(R.id.tvTotalLabs);
        tvTotalItems = view.findViewById(R.id.tvTotalItems);
        tvTotalItemsWorking = view.findViewById(R.id.tvTotalItemsWorking);
        tvTotalItemsMaintenance = view.findViewById(R.id.tvTotalItemsMaintenance);
        tvTotalItemsDamaged = view.findViewById(R.id.tvTotalItemsDamaged);
    }
}