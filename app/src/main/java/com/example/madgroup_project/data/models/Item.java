package com.example.madgroup_project.data.models;
import com.example.madgroup_project.data.ItemConditions;
import com.example.madgroup_project.data.ItemTypes;
import com.google.gson.annotations.SerializedName;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;


import java.util.Date;

@Entity(tableName = "items",
        foreignKeys = @ForeignKey(entity = Lab.class,
                parentColumns = "id",
                childColumns = "lab_id",
                onDelete = ForeignKey.CASCADE)
)
public class Item {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @SerializedName("lab_id")
    @ColumnInfo(name = "lab_id")
    private int labId;

    @SerializedName("name")
    @ColumnInfo(name = "name")
    private String name;

    @SerializedName("type")
    @TypeConverters(ItemTypeConverter.class)
    @ColumnInfo(name = "type")
    private ItemTypes type;

    @SerializedName("serialNumber")
    @ColumnInfo(name = "serial_number")
    private String serialNumber;

    @SerializedName("condition")
    @TypeConverters(ConditionConverter.class)
    @ColumnInfo(name = "condition")
    private ItemConditions condition;

    @TypeConverters(DateConverter.class)
    @ColumnInfo(name = "created_at")
    private Date createdAt;

    @TypeConverters(DateConverter.class)
    @ColumnInfo(name="updated_at")
    private Date updatedAt;

    public Item() {
        this.createdAt= new Date();
        this.updatedAt= new Date();
    }

    public Item(int labId, String name, String serialNumber, ItemConditions condition) {
        this.labId = labId;
        this.name = name;
        this.serialNumber = serialNumber;
        this.condition = condition;
        this.createdAt= new Date();
        this.updatedAt= new Date();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLabId() {
        return labId;
    }

    public void setLabId(int labId) {
        this.labId = labId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemTypes getType() {
        return type;
    }

    public void setType(ItemTypes type) {
        this.type = type;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public ItemConditions getCondition() {
        return condition;
    }

    public void setCondition(ItemConditions condition) {
        this.condition = condition;
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