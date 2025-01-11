package com.example.madgroup_project.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.madgroup_project.data.dao.ItemDao;
import com.example.madgroup_project.data.db.AppDatabase;
import com.example.madgroup_project.data.models.Item;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ItemRepository {
    private ItemDao itemDao;
    private LiveData<List<Item>> allItems;
    private ExecutorService executorService;


    public ItemRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        itemDao = database.itemDao();
        allItems=itemDao.getAllItems();
        executorService= Executors.newSingleThreadExecutor();
    }
    public void insert(Item item) {
        executorService.execute(()->itemDao.insert(item));
    }

    public void update(Item item) {
        executorService.execute(()->itemDao.update(item));
    }

    public void delete(Item item) {
        executorService.execute(()->itemDao.delete(item));
    }


    public LiveData<Item> getItemById(int itemId) {
        return itemDao.getItemById(itemId);
    }

    public LiveData<List<Item>> getItemsByLabId(int labId) {
        return itemDao.getItemsByLabId(labId);
    }

    public LiveData<List<Item>> getAllItems(){
        return allItems;
    }

    public LiveData<List<Item>> searchItemsByLab(int labId, String query){
        return itemDao.searchItemsByLab(labId,query);
    }

    public LiveData<List<Item>> searchItems(String query){
        return itemDao.searchItems(query);
    }
}