package com.example.madgroup_project.data.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

@Entity(tableName = "labs")
public class Lab {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "code")
    private String code;

    @ColumnInfo(name = "supervisor")
    private String supervisor;

    @ColumnInfo(name = "status")
    private String status;

    @ColumnInfo(name = "capacity")
    private int capacity;

    @TypeConverters(DateConverter.class)
    @ColumnInfo(name = "created_at")
    private Date createdAt;

    @TypeConverters(DateConverter.class)
    @ColumnInfo(name="updated_at")
    private Date updatedAt;

    // Constructors
    public Lab() {
        this.createdAt= new Date();
        this.updatedAt= new Date();
    }

    public Lab(String name, String code, String supervisor, String status, int capacity) {
        this.name = name;
        this.code = code;
        this.supervisor = supervisor;
        this.status = status;
        this.capacity = capacity;
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}