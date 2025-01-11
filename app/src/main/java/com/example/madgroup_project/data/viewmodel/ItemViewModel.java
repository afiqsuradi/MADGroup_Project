package com.example.madgroup_project.data.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.madgroup_project.data.models.Item;
import com.example.madgroup_project.data.repository.ItemRepository;

import java.util.List;

public class ItemViewModel extends AndroidViewModel {

    private ItemRepository repository;
    private LiveData<List<Item>> allItems;

    public ItemViewModel(Application application) {
        super(application);
        repository = new ItemRepository(application);
        allItems = repository.getAllItems();
    }

    public void insert(Item item) {
        repository.insert(item);
    }

    public void update(Item item) {
        repository.update(item);
    }

    public void delete(Item item) {
        repository.delete(item);
    }

    public LiveData<Item> getItemById(int itemId) {
        return repository.getItemById(itemId);
    }

    public LiveData<List<Item>> getItemsByLabId(int labId) {
        return repository.getItemsByLabId(labId);
    }

    public LiveData<List<Item>> getAllItems() {
        return allItems;
    }
    public LiveData<List<Item>> searchItemsByLab(int labId,String query){
        return repository.searchItemsByLab(labId,query);
    }

    public LiveData<List<Item>> searchItems(String query){
        return repository.searchItems(query);
    }
}