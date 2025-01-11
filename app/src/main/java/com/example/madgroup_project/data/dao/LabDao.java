package com.example.madgroup_project.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.madgroup_project.data.models.Lab;

import java.util.List;

@Dao
public interface LabDao {

    @Insert
    long insert(Lab lab);

    @Update
    void update(Lab lab);

    @Delete
    void delete(Lab lab);


    @Query("SELECT * FROM labs WHERE id = :labId")
    LiveData<Lab> getLabById(int labId);


    @Query("SELECT * FROM labs")
    LiveData<List<Lab>> getAllLabs();


    @Query("SELECT * FROM labs WHERE name LIKE '%' || :query || '%' OR code LIKE '%' || :query || '%'")
    LiveData<List<Lab>> searchLabs(String query);
}