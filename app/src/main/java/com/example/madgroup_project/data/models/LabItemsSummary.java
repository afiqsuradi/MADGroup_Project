package com.example.madgroup_project.data.models;

public class LabItemsSummary {
        private int totalItems;
        private int workingItems;
        private int maintenanceItems;
        private int brokenItems;

        public LabItemsSummary(int totalItems, int workingItems, int maintenanceItems, int brokenItems) {
            this.totalItems = totalItems;
            this.workingItems = workingItems;
            this.maintenanceItems = maintenanceItems;
            this.brokenItems = brokenItems;
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
}
