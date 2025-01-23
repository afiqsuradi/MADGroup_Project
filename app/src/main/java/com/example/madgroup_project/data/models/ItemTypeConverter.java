package com.example.madgroup_project.data.models;

import androidx.room.TypeConverter;

import com.example.madgroup_project.data.ItemConditions;
import com.example.madgroup_project.data.ItemTypes;

public class ItemTypeConverter {
    @TypeConverter
    public String fromType(ItemTypes itemType){
        return itemType == null ? null : itemType.name();
    }
    @TypeConverter
    public ItemTypes toType(String itemTypeString){
        if (itemTypeString == null) {
            return null;
        }
        for (ItemTypes itemType : ItemTypes.values()) {
            if(itemType.name().equals(itemTypeString)){
                return itemType;
            }
        }
        throw new IllegalArgumentException("Unknown Item Type: " + itemTypeString);
    }
}
