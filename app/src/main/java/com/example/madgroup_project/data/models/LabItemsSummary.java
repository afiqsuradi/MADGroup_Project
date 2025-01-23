package com.example.madgroup_project.data.models;

import androidx.room.ColumnInfo;

public class LabItemsSummary {

    @ColumnInfo(name = "total_items")
    private int totalItems;

    @ColumnInfo(name = "working_items")
    private int workingItems;

    @ColumnInfo(name = "maintenance_items")
    private int maintenanceItems;

    @ColumnInfo(name = "broken_items")
    private int brokenItems;

    public LabItemsSummary(int totalItems, int workingItems, int maintenanceItems, int brokenItems) {
        this.totalItems = totalItems;
        this.workingItems = workingItems;
        this.maintenanceItems = maintenanceItems;
        this.brokenItems = brokenItems;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public int getWorkingItems() {
        return workingItems;
    }

    public int getMaintenanceItems() {
        return maintenanceItems;
    }

    public int getBrokenItems() {
        return brokenItems;
    }
}
