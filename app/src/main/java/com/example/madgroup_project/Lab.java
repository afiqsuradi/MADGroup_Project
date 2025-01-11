package com.example.madgroup_project;

import java.util.List;

public class Lab {
    private String name;
    private String description;
    private List<String> equipment; // List to hold lab equipment

    // Constructor to initialize the lab name, description, and equipment
    public Lab(String name, String description, List<String> equipment) {
        this.name = name;
        this.description = description;
        this.equipment = equipment;
    }

    // Getter for lab name
    public String getName() {
        return name;
    }

    // Getter for lab description
    public String getDescription() {
        return description;
    }

    // Getter for lab equipment
    public List<String> getEquipment() {
        return equipment;
    }

    // Optional: Add a method to check if equipment list is available or not
    public boolean hasEquipment() {
        return equipment != null && !equipment.isEmpty();
    }
}
