package com.example.fyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class SpeechGame extends AppCompatActivity implements RecognitionListener {

    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 1;

    private SpeechRecognizer speechRecognizer;
    private TextView wordTextView;
    private TextView timerTextView;
    private Button startButton;
    private int currentWordIndex = 0;
    private int score = 0;
    private String[] words = {"apple", "banana", "cherry", "orange", "grape", "fruit", "honey", "hello"};
    private CountDownTimer timer;

    public FirebaseDatabase firebaseDatabase;
    public DatabaseReference reference;
    public FirebaseAuth mAuth;
    public FirebaseUser mUser;
    public String onlineUserID;

    Date currentTime = Calendar.getInstance().getTime();
    String testName2 = "Pronunciation Test";
    String id = String.valueOf(1);

    String date = String.valueOf(currentTime);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech_game);

        ArrayList<Result> resultlist = new ArrayList<>();

        //testName = (TextView) findViewById(R.id.testName);


        wordTextView = findViewById(R.id.wordTxtView);
        timerTextView = findViewById(R.id.textViewTimer);
        startButton = findViewById(R.id.startBtn);

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        speechRecognizer.setRecognitionListener(this);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPractice();
                startTimer();
            }
        });

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO},
                    REQUEST_RECORD_AUDIO_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startButton.setEnabled(true);
        }
    }

    private void startPractice() {
        if (currentWordIndex >= words.length) {
            displayResults("Score: " + score + "/" + words.length);
            return;
        }

        String word = words[currentWordIndex];
        wordTextView.setText(word);
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say the word \"" + word + "\"");
        speechRecognizer.startListening(intent);
    }

    private void displayResults(String message) {
        wordTextView.setText(message);
        startButton.setEnabled(true);
        timer.cancel();
    }

    private void startTimer() {
        if (timer != null) {
            timer.cancel();
        }

        timer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long secondsRemaining = TimeUnit.SECONDS.convert(millisUntilFinished, TimeUnit.MILLISECONDS);
                timerTextView.setText(String.format(Locale.getDefault(), "%02d:%02d", secondsRemaining / 60, secondsRemaining % 60));
            }

            @Override
            public void onFinish() {
                displayResults("Time's up! Score: " + score + "/" + words.length);
                saveResults();
                speechRecognizer.stopListening();
                Intent intent = new Intent(SpeechGame.this,TestResults2.class);
                startActivity(intent);

            }
        };
        timer.start();
    }



    @Override
    public void onReadyForSpeech(Bundle params) {

    }

    @Override
    public void onBeginningOfSpeech() {
    }

    @Override
    public void onRmsChanged(float rmsdB) {

    }

    @Override
    public void onBufferReceived(byte[] buffer) {

    }

    @Override
    public void onEndOfSpeech() {

    }

    @Override
    public void onError(int error) {
        if (error == SpeechRecognizer.ERROR_NO_MATCH) {
            wordTextView.setText("No match found. Please try again.");
        } else {
            wordTextView.setText("Error occurred. Please try again.");
        }
        startPractice();
    }

    @Override
    public void onResults(Bundle results) {
        ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        String spokenText = "";
        if (matches != null && matches.size() > 0) {
            spokenText = matches.get(0).toLowerCase();
        }

        String word = words[currentWordIndex];
        if (word.equals(spokenText)) {
            wordTextView.setText("Correct!");
            score++;
        } else {
            wordTextView.setText("Incorrect. Please try again.");
        }
        currentWordIndex++;
        startPractice();
    }

    @Override
    public void onPartialResults(Bundle partialResults) {

    }

    @Override
    public void onEvent(int eventType, Bundle params) {

    }

    public void saveResults() {

        mAuth = FirebaseAuth.getInstance();

        mUser = mAuth.getCurrentUser();
        onlineUserID = mUser.getUid();
        reference = FirebaseDatabase.getInstance().getReference().child("SpeechResults").child(onlineUserID);
        //reference = FirebaseDatabase.getInstance().getReference().child("Tasks").child(onlineUserID);
        int finalScore = score;
        String id = reference.push().getKey();
        Result result = new Result(id, testName2, score, date);
        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("SpeechResults").push().setValue(result).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(SpeechGame.this, "Results was added successfully", Toast.LENGTH_SHORT).show();


                } else {
                    String error = task.getException().toString();
                    Toast.makeText(SpeechGame.this, "Failed: " + error, Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}