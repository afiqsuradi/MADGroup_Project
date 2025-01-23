package com.example.madgroup_project.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.madgroup_project.data.models.Lab;
import com.example.madgroup_project.data.models.LabItemsSummary;
import com.example.madgroup_project.data.models.LabsSummary;

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


    @Query("SELECT COUNT(*) AS total_items, COUNT(CASE WHEN condition = 'WORKING' THEN 1 END) AS working_items, COUNT(CASE WHEN condition = 'MAINTENANCE' THEN 1 END) AS maintenance_items, COUNT(CASE WHEN condition = 'BROKEN' THEN 1 END) AS broken_items FROM items WHERE lab_id = :labId")
    LiveData<LabItemsSummary> getLabSummary(int labId);

    @Query("SELECT " +
            "COUNT(*) AS total_items, " +
            "COUNT(CASE WHEN condition = 'WORKING' THEN 1 END) AS working_items, " +
            "COUNT(CASE WHEN condition = 'MAINTENANCE' THEN 1 END) AS maintenance_items, " +
            "COUNT(CASE WHEN condition = 'BROKEN' THEN 1 END) AS broken_items, " +
            "(SELECT COUNT(*) FROM labs) AS total_labs " +
            "FROM items")
    LiveData<LabsSummary> getAllLabsSummary();
}