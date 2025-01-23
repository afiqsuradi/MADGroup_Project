package com.example.madgroup_project.data.models;

import androidx.room.ColumnInfo;

public class LabsSummary {

    @ColumnInfo(name = "total_items")
    private int totalItems;

    @ColumnInfo(name = "working_items")
    private int workingItems;

    @ColumnInfo(name = "maintenance_items")
    private int maintenanceItems;

    @ColumnInfo(name = "broken_items")
    private int brokenItems;

    @ColumnInfo(name = "total_labs")
    private int totalLabs;

    public LabsSummary(int totalItems, int workingItems, int maintenanceItems, int brokenItems, int totalLabs) {
        this.totalItems = totalItems;
        this.workingItems = workingItems;
        this.maintenanceItems = maintenanceItems;
        this.brokenItems = brokenItems;
        this.totalLabs = totalLabs;
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

    public int getTotalLabs() {
        return totalLabs;
    }
}
