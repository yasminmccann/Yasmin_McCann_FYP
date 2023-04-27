package com.example.fyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MedActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private TextView medicineTextView;
    private EditText medName, dosage;
    private TimePicker timePicker;
    private TextView reminderTextView;
    private Button setReminderButton;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private AlarmManager alarmManager;
    private PendingIntent alarmIntent;

    // Declare Firebase database reference
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med);

        // Initialize views
        medicineTextView = findViewById(R.id.medTxtView);
        timePicker = findViewById(R.id.chooseTimeTxt);
        reminderTextView = findViewById(R.id.reminder_text_view);
        setReminderButton = findViewById(R.id.remindBtn);

        // Initialize AlarmManager
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        // Initialize Firebase database reference
        mDatabase = FirebaseDatabase.getInstance().getReference();

        setReminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setReminder();

            }
        });

//        // Read the medicine reminder time from Firebase
//        String medicineName = medicineTextView.getText().toString().trim();
//        String dosageNum = dosage.getText().toString().trim();
//
//        Medication medication = new Medication(medicineName, dosageNum);
//        mDatabase.child("medication").push().setValue(medication);
//        mDatabase.child("medicine").child(medicineName).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()) {
//                    String reminderTime = snapshot.getValue(String.class);
//                    reminderTextView.setText("Reminder set for: " + reminderTime);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.e(TAG, "onCancelled", error.toException());
//            }
//        });
    }

    private void setReminder() {
        // Get medicine name
        String medicineName = medicineTextView.getText().toString().trim();
        //String dosageNum = dosage.getText().toString().trim();

        //Medication medication = new Medication(medicineName, dosageNum);

        // Get reminder time
        int hour = timePicker.getCurrentHour();
        int minute = timePicker.getCurrentMinute();


        // Create Calendar object with reminder time
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        // Format the reminder time
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        String reminderTime = dateFormat.format(calendar.getTime());

        // Update the reminderTextView with the reminder time
        reminderTextView.setText("Reminder set for: " + reminderTime);

        // Save the reminder time to Firebase
        mDatabase.child("medicine").child(medicineName).setValue(reminderTime);

        // Set the alarm
        Intent intent = new Intent(MedActivity.this, AlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(MedActivity.this, 0, intent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
        //PendingIntent.getActivity(getApplicationContext(), 0, intent,PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT)
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);

        Toast.makeText(this, "Reminder set for " + reminderTime, Toast.LENGTH_SHORT).show();
    }

    private void scheduleAlarm(int hour, int minute, String medicineName) {
        AlarmManager alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra("medicine_name", medicineName);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        long alarmTime = calendar.getTimeInMillis();
        alarmMgr.set(AlarmManager.RTC_WAKEUP, alarmTime, alarmIntent);

        String timeString = String.format(Locale.getDefault(), "%02d:%02d", hour, minute);
        String message = "Reminder set for " + timeString + " (" + medicineName + ")";
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        // Save the reminder to Firebase
        String userId = mUser.getUid();
        DatabaseReference remindersRef = mDatabase.child("reminders").child(userId).push();
        Map<String, Object> reminderData = new HashMap<>();
        reminderData.put("medicine_name", medicineName);
        reminderData.put("alarm_time", ServerValue.TIMESTAMP);
        remindersRef.setValue(reminderData, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@NonNull DatabaseError error, @NonNull DatabaseReference ref) {
                if (error != null) {
                    Log.e(TAG, "Failed to save reminder to Firebase", error.toException());
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}