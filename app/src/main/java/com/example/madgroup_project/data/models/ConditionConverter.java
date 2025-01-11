package com.example.madgroup_project.data.models;
import com.example.madgroup_project.data.ItemConditions;
import androidx.room.TypeConverter;


public class ConditionConverter {
    @TypeConverter
    public static ItemConditions fromString(String value) {
        return ItemConditions.valueOf(value);
    }

    @TypeConverter
    public static String itemConditionToString(ItemConditions value) {
        return value.name();
    }
}