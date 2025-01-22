package com.example.madgroup_project.ui.item.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.madgroup_project.R;
import com.example.madgroup_project.data.models.Lab;
import com.example.madgroup_project.data.viewmodel.ItemViewModel;
import com.example.madgroup_project.data.viewmodel.LabViewModel;
import com.example.madgroup_project.ui.item.ItemListRecyclerViewAdapter;

import java.util.ArrayList;


public class LabItemsFragment extends Fragment {

    private static final String LAB_ID = "lab_id";
    private Lab lab;
    private ItemViewModel itemViewModel;
    private ItemListRecyclerViewAdapter itemListRecyclerViewAdapter;
    private RecyclerView itemListRecyclerView;



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
        itemViewModel = new ViewModelProvider(this).get(ItemViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lab_items, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        if (getArguments() != null) {
            int labId = getArguments().getInt(LAB_ID);
            itemViewModel.getItemsByLabId(labId).observe(getViewLifecycleOwner(), items -> {
               if (items != null){
                    itemListRecyclerViewAdapter.setItems(items);
               }
            });
        }
    }

    private void initViews(){
        itemListRecyclerView = getView().findViewById(R.id.itemListRecyclerView);
        itemListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        itemListRecyclerViewAdapter = new ItemListRecyclerViewAdapter(new ArrayList<>(), this);
        itemListRecyclerView.setAdapter(itemListRecyclerViewAdapter);

    }
}