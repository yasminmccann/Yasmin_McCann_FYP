package com.example.fyp;

public class Medication {

        public String id;
        public String name;
        public String dosage;
        public String frequency;
        private String reminderTime;
        //public boolean completed;

        public Medication() {
            // Default constructor required for calls to DataSnapshot.getValue(Medication.class)
        }

        public Medication(String id, String name, String dosage, String frequency, String reminderTime) {
            this.id = id;
            this.name = name;
            this.dosage = dosage;
            this.frequency = frequency;
            this.reminderTime = reminderTime;
        }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(String reminderTime) {
        this.reminderTime = reminderTime;
    }
}

