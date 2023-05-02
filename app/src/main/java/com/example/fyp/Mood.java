package com.example.fyp;

public class Mood {

    public String date;
    public int mood;

    public Mood() {}

    public Mood(String date, int mood) {
        this.date = date;
        this.mood = mood;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getMood() {
        return mood;
    }

    public void setMood(int mood) {
        this.mood = mood;
    }
}
