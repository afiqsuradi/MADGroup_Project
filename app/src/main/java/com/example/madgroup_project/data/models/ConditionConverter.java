package com.example.madgroup_project.data.models;
import com.example.madgroup_project.data.ItemConditions;

import androidx.room.TypeConverter;


public class ConditionConverter {

    @TypeConverter
    public String fromCondition(ItemConditions condition) {
        return condition == null ? null : condition.name();
    }

    @TypeConverter
    public ItemConditions toCondition(String conditionName) {
        if (conditionName == null) {
            return null;
        }
        for (ItemConditions condition : ItemConditions.values()) {
            if(condition.name().equals(conditionName)){
                return condition;
            }
        }
        throw new IllegalArgumentException("Unknown Item Condition: " + conditionName);
    }
}