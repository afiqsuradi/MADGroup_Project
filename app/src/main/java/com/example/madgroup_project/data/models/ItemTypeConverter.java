package com.example.madgroup_project.data.models;

import androidx.room.TypeConverter;

import com.example.madgroup_project.data.ItemTypes;

public class ItemTypeConverter {
    @TypeConverter
    public String fromType(ItemTypes itemType){
        return itemType.name();
    }
    @TypeConverter
    public ItemTypes toType(String itemTypeString){
        return ItemTypes.valueOf(itemTypeString);
    }
}
