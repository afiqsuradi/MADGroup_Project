package com.example.madgroup_project.ui.item.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.madgroup_project.R;
import com.example.madgroup_project.data.models.LabItemsSummary;
import com.example.madgroup_project.data.viewmodel.LabViewModel;

public class LabItemDashboardFragment extends Fragment {
    private static final String LAB_ID = "lab_id";
    private LabViewModel labViewModel;

    private TextView tvItemsSummary, tvItemsWorking, tvItemsMaintenance, tvItemsDamaged;

    public static LabItemDashboardFragment newInstance(int param1) {
        LabItemDashboardFragment fragment = new LabItemDashboardFragment();
        Bundle args = new Bundle();
        args.putInt(LAB_ID, param1);
        fragment.setArguments(args);
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
        return inflater.inflate(R.layout.fragment_item_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        if (getArguments() != null) {
            int labId = getArguments().getInt(LAB_ID);
            labViewModel.getLabSummary(labId).observe(getViewLifecycleOwner(), labItemsSummaries -> {
                if (labItemsSummaries != null) updateUiWithLabData(labItemsSummaries);
            });
        }
    }


    private void initViews(View view){
        tvItemsSummary = view.findViewById(R.id.tvItemsSummary);
        tvItemsWorking = view.findViewById(R.id.tvItemsWorking);
        tvItemsMaintenance = view.findViewById(R.id.tvItemsMaintenance);
        tvItemsDamaged = view.findViewById(R.id.tvItemsDamaged);
    }

    private void updateUiWithLabData(LabItemsSummary summary){
        if(summary != null){
            tvItemsSummary.setText(String.valueOf(summary.getTotalItems()));
            tvItemsWorking.setText(String.valueOf(summary.getWorkingItems()));
            tvItemsMaintenance.setText(String.valueOf(summary.getMaintenanceItems()));
            tvItemsDamaged.setText(String.valueOf(summary.getBrokenItems()));
        }


    }
}