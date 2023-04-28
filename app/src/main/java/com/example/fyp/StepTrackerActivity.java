package com.example.fyp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class StepTrackerActivity extends AppCompatActivity {

    private TrackSteps stepCounter;
    private TextView stepCountText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_tracker);

        stepCountText = findViewById(R.id.step_count_text);

        stepCounter = new TrackSteps(this);
        stepCounter.registerListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stepCounter.unregisterListener();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            stepCountText.setText(String.valueOf(stepCounter.getStepCount()));
        }
    }
}