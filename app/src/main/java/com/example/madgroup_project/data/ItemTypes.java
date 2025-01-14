package com.example.madgroup_project.data;


import java.util.Arrays;

public enum ItemTypes {
    COMPUTER("Computer"),
    COMPUTER_ACCESSORY("Computer Accessory"),
    MISCELLANEOUS("Miscellaneous"),
    AUDIO("Audio"),
    NETWORK("Network");

    private final String displayName;

    ItemTypes(String displayName) {
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
                .map(ItemTypes::getDisplayName).toArray(String[]::new);
    }
}