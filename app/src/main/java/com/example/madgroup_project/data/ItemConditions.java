package com.example.madgroup_project.data;
import java.util.Arrays;

public enum ItemConditions {
    WORKING("Working"),
    MAINTENANCE("Maintenance"),
    BROKEN("Broken");

    private final String displayName;

    ItemConditions(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }


    public static String[] getAllDisplayNames() {
        return Arrays.stream(values())
                .map(ItemConditions::getDisplayName).toArray(String[]::new);
    }
}