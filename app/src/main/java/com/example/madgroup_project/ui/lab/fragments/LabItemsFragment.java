package com.example.madgroup_project.ui.lab.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.madgroup_project.R;
import com.example.madgroup_project.data.models.Lab;
import com.example.madgroup_project.data.models.LabItemsSummary;
import com.example.madgroup_project.data.viewmodel.LabViewModel;


public class LabItemsFragment extends Fragment {

    private static final String LAB_ID = "lab_id";
    private Lab lab;
    private LabViewModel labViewModel;


    public static LabItemsFragment newInstance(int param1) {
        LabItemsFragment fragment = new LabItemsFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lab_items, container, false);
    }
}