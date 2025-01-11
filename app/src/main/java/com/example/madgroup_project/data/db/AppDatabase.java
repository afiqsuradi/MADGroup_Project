package com.example.madgroup_project.data.db;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.madgroup_project.data.dao.ItemDao;
import com.example.madgroup_project.data.dao.LabDao;
import com.example.madgroup_project.data.models.ConditionConverter;
import com.example.madgroup_project.data.models.Item;
import com.example.madgroup_project.data.models.Lab;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Lab.class, Item.class}, version = 1, exportSchema = false)
@TypeConverters({ConditionConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;
    private static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(1);

    public abstract LabDao labDao();
    public abstract ItemDao itemDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "lab_inventory_db")
                            .fallbackToDestructiveMigration()
                            .build();
        }
        return instance;
    }
}