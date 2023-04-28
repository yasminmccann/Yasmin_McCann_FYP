package com.example.fyp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MoodTracker extends AppCompatActivity {

    private CalendarView calendarView;
    private SeekBar moodSeekBar;
    private TextView moodTextView;
    private Button saveButton;

    private DatabaseReference mDatabase;

    private Date selectedDate;
    private int selectedMood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_tracker);

        // Initialize views
        calendarView = findViewById(R.id.calendar_view);
        moodSeekBar = findViewById(R.id.mood_seek_bar);
        moodTextView = findViewById(R.id.mood_text_view);
        saveButton = findViewById(R.id.save_button);

        // Set up Firebase database reference
        mDatabase = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        // Set up mood seek bar listener
        moodSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                selectedMood = progress;
                updateMoodTextView();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        // Set up save button listener
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveMoodEntry();
            }
        });

        // Set up calendar view listener
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // Convert selected date to Date object
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);
                selectedDate = calendar.getTime();
            }
        });
    }

    private void updateMoodTextView() {
        String[] moodOptions = {"Very sad", "Sad", "Normal", "Happy", "Very happy"};
        moodTextView.setText(moodOptions[selectedMood]);
    }

    private void saveMoodEntry() {
        if (selectedDate != null) {
            // Format selected date as string
            String dateString = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(selectedDate);

            // Save mood entry to Firebase database
            Mood moodEntry = new Mood(dateString, selectedMood);
            mDatabase.child("moodEntries").push().setValue(moodEntry);
            Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}