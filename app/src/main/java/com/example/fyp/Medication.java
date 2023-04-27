package com.example.fyp;

public class Medication {
        public String name;
        public String dosage;


        public Medication() {
            // Default constructor required for calls to DataSnapshot.getValue(Medication.class)
        }

    public Medication(String name, String dosage) {
        this.name = name;
        this.dosage = dosage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }
}

