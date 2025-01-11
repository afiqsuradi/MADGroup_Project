package com.example.madgroup_project.data.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.madgroup_project.data.models.Lab;
import com.example.madgroup_project.data.repository.LabRepository;

import java.util.List;

public class LabViewModel extends AndroidViewModel {

    private LabRepository repository;
    private LiveData<List<Lab>> allLabs;


    public LabViewModel(Application application) {
        super(application);
        repository = new LabRepository(application);
        allLabs = repository.getAllLabs();
    }

    public void insert(Lab lab) {
        repository.insert(lab);
    }

    public void update(Lab lab) {
        repository.update(lab);
    }

    public void delete(Lab lab) {
        repository.delete(lab);
    }
    public LiveData<Lab> getLabById(int labId){
        return repository.getLabById(labId);
    }

    public LiveData<List<Lab>> getAllLabs() {
        return allLabs;
    }

    public LiveData<List<Lab>> searchLabs(String query){
        return repository.searchLabs(query);
    }
}