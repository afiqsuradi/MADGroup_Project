package com.example.madgroup_project.data;


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
}