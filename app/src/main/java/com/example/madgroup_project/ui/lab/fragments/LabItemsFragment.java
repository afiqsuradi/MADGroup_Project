package com.example.madgroup_project.ui.lab.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.madgroup_project.R;
import com.example.madgroup_project.data.models.Lab;
import com.example.madgroup_project.data.viewmodel.LabViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LabItemsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LabItemsFragment extends Fragment {

    private static final String LAB_ID = "lab_id";
    private Lab lab;
    private LabViewModel labViewModel;

    public static LabDashboardFragment newInstance(int param1) {
        LabDashboardFragment fragment = new LabDashboardFragment();
        Bundle args = new Bundle();
        args.putInt(LAB_ID, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        labViewModel = new ViewModelProvider(this).get(LabViewModel.class);
        if (getArguments() != null) {
            int labId = getArguments().getInt(LAB_ID);
            labViewModel.getLabById(labId).observe(this, lab -> {
                if (lab != null) this.lab = lab;
            });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lab_items, container, false);
    }
}