package com.example.madgroup_project;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.example.madgroup_project.data.viewmodel.LabViewModel;
import com.example.madgroup_project.ui.lab.LabCreateActivity;
import com.example.madgroup_project.ui.lab.LabListRecyclerViewAdapter;
import com.example.madgroup_project.ui.lab.fragments.LabDashboardFragment;
import com.example.madgroup_project.utils.Debouncer;
import com.example.madgroup_project.utils.ItemReminderWorker;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String NOTIFICATION_PERMISSION = Manifest.permission.POST_NOTIFICATIONS;
    private static final int REMINDER_INTERVAL_DAYS = 1;
    private ActivityResultLauncher<String> requestPermissionLauncher;
    private RecyclerView labRecyclerView;
    private LabListRecyclerViewAdapter labListRecyclerViewAdapter;
    private LabViewModel labViewModel;
    private Button btnCreateLab;

    private Debouncer debouncer;

    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeViews();
        setupFragment();
        setupRecyclerView();
        setupSearchView();
        setupViewModel();
        setupButton();
        setupPermissionLauncher();
        checkNotificationPermission();
    }

    @Override
    protected void onResume() {
        super.onResume();
        searchView.clearFocus();
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
                handleLabSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                debouncer.debounce(() -> handleLabSearch(newText));
                return false;
            }
        });
    }

    private void handleLabSearch(String query){
        if (query == "") {
            labViewModel.getAllLabs().observe(this, labs -> labListRecyclerViewAdapter.setLabs(labs));
        } else{
            labViewModel.searchLabs(query).observe(this, labs -> labListRecyclerViewAdapter.setLabs(labs));
        }
    }

    private void setupFragment(){
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.fgLabDashboard, LabDashboardFragment.class, null)
                .commit();
    }

    private void initializeViews() {
        btnCreateLab = findViewById(R.id.btnCreateLab);
        labRecyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.searchView);
    }

    private void setupRecyclerView() {
        labRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        labListRecyclerViewAdapter = new LabListRecyclerViewAdapter(new ArrayList<>());
        labRecyclerView.setAdapter(labListRecyclerViewAdapter);
    }

    private void setupViewModel() {
        labViewModel = new ViewModelProvider(this).get(LabViewModel.class);
        labViewModel.getAllLabs().observe(this, labs -> {
            if (labs != null) {
                labListRecyclerViewAdapter.setLabs(labs);
            }
        });
    }

    private void setupButton() {
        btnCreateLab.setOnClickListener(v -> goToCreateLabActivity());
    }

    private void setupPermissionLauncher() {
        requestPermissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(), isGranted -> {
                    if (isGranted) {
                        scheduleItemReminder();
                    } else {
                        showToast("Notification permission is needed to be able to get notification.");
                        Log.w(TAG, "Notification permission denied by user.");
                    }
                });
    }

    private void checkNotificationPermission() {
        if (ContextCompat.checkSelfPermission(this, NOTIFICATION_PERMISSION) == PackageManager.PERMISSION_GRANTED) {
            scheduleItemReminder();
        } else if (shouldShowRequestPermissionRationale(NOTIFICATION_PERMISSION)) {
            showToast("We need the permission to show notification!");
            requestNotificationPermission();
        } else {
            requestNotificationPermission();
        }
    }

    private void requestNotificationPermission() {
        requestPermissionLauncher.launch(NOTIFICATION_PERMISSION);
    }

    private void scheduleItemReminder() {
        try{
            PeriodicWorkRequest reminderWorkRequest = new PeriodicWorkRequest.Builder(
                    ItemReminderWorker.class, REMINDER_INTERVAL_DAYS, TimeUnit.DAYS
            ).build();
            WorkManager.getInstance(getApplicationContext()).enqueue(reminderWorkRequest);
            Log.i(TAG, "Scheduled item reminder worker.");
        } catch(Exception e){
            Log.e(TAG, "Error Scheduling item reminder worker", e);
        }
    }

    public void goToCreateLabActivity() {
        Intent intent = new Intent(this, LabCreateActivity.class);
        startActivity(intent);
    }


    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}