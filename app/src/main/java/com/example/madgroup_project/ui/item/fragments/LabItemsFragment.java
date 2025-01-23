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
import android.widget.SearchView;

import com.example.madgroup_project.R;
import com.example.madgroup_project.data.models.Lab;
import com.example.madgroup_project.data.viewmodel.ItemViewModel;
import com.example.madgroup_project.ui.item.ItemListRecyclerViewAdapter;
import com.example.madgroup_project.utils.Debouncer;

import java.util.ArrayList;


public class LabItemsFragment extends Fragment {

    private static final String LAB_ID = "lab_id";
    private ItemViewModel itemViewModel;
    private ItemListRecyclerViewAdapter itemListRecyclerViewAdapter;
    private RecyclerView itemListRecyclerView;

    private SearchView searchView;

    private Debouncer debouncer;



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
        return inflater.inflate(R.layout.fragment_item_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        setupSearchView();
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
        searchView = getView().findViewById(R.id.searchView);
    }

    private void setupSearchView() {
        debouncer = new Debouncer(1000);
        searchView.setIconifiedByDefault(false);
        searchView.setFocusable(true);
        searchView.requestFocusFromTouch();
        searchView.clearFocus();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                debouncer.cancel();
                handleItemSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                debouncer.debounce(() -> handleItemSearch(newText));
                return false;
            }
        });
    }

    private  void handleItemSearch(String query){
        if (getArguments().getInt(LAB_ID) == -1) return;
        int labId = getArguments().getInt(LAB_ID);
        if (query == "") {
            itemViewModel.getItemsByLabId(labId).observe(getViewLifecycleOwner(), items -> itemListRecyclerViewAdapter.setItems(items));
        } else {
            itemViewModel.searchItemsByLab(labId, query).observe(getViewLifecycleOwner(), items -> itemListRecyclerViewAdapter.setItems(items));

        }
    }
}