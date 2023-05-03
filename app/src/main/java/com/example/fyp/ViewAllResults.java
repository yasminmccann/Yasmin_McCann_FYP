package com.example.fyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;

public class ViewAllResults extends AppCompatActivity {

    CardView fingerTapTest, speechTest, agilityTest, moodTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_results);

        fingerTapTest = (CardView) findViewById(R.id.fingerTapTest);
        fingerTapTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewAllResults.this, Test1Results.class);
                startActivity(intent);
            }
        });

        speechTest = findViewById(R.id.speechTest);
        speechTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewAllResults.this, TestResults2.class);
                startActivity(intent);
            }
        });

        agilityTest = findViewById(R.id.agilityTest);
        agilityTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewAllResults.this, Test3Results.class);
                startActivity(intent);
            }
        });

        moodTest = findViewById(R.id.pegTest);
        moodTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewAllResults.this, MoodResults.class);
                startActivity(intent);
            }
        });
    }


        @Override
        public void onBackPressed() {
            super.onBackPressed();
        }
    }