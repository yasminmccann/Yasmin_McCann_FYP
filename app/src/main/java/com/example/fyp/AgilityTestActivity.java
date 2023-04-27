package com.example.fyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import java.util.Random;
import java.util.Timer;

public class AgilityTestActivity extends AppCompatActivity {

    private ImageView circle1, circle2;
    private TextView scoreText, timer;
    private int score = 0;

    public FirebaseDatabase firebaseDatabase;
    public DatabaseReference reference;
    public FirebaseAuth mAuth;
    public FirebaseUser mUser;
    public String onlineUserID;

    Date currentTime = Calendar.getInstance().getTime();
    String testName2 = "Agility Test";

    String date = String.valueOf(currentTime);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agility_test);

        ArrayList<Result> resultlist = new ArrayList<>();

        circle1 = findViewById(R.id.circle1);
        circle2 = findViewById(R.id.circle2);
        scoreText = findViewById(R.id.scoreTxtView);
        timer = findViewById(R.id.timerText);

        circle1.setOnTouchListener(new CircleTouchListener());
        circle2.setOnTouchListener(new CircleTouchListener());

        // create the timer and start it
        new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                // update the timer display
                timer.setText("Time remaining: " + millisUntilFinished / 1000 + " seconds");
            }

            public void onFinish() {
                // store the user's score and display it
                SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("score", score);
                editor.apply();
                scoreText.setText("Your score: " + score);
                circle1.setVisibility(View.GONE);
                circle2.setVisibility(View.GONE);
                timer.setText("Done!");
                saveResults();
                Intent intent = new Intent(AgilityTestActivity.this,Test3Results.class);
                startActivity(intent);
            }
        }.start();

        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        int savedScore = prefs.getInt("score", -1);
        if (savedScore != -1) {
            scoreText.setText("Your previous score: " + savedScore);
        }
    }

    private class CircleTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            // detect when the user is dragging the circle
            switch (event.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    // update the circle's position based on the touch event
                    v.setLayoutParams(new ConstraintLayout.LayoutParams(
                            v.getWidth(), v.getHeight()));
                    break;
                case MotionEvent.ACTION_UP:
                    // check if the circles are overlapping
                    if (isOverlap(circle1, circle2)) {
                        // hide both circles and generate new positions
                        circle1.setVisibility(View.INVISIBLE);
                        circle2.setVisibility(View.INVISIBLE);
                        generateNewPositions();
                        // increase the score
                        score++;
                        scoreText.setText("Score: " + score);
                    }
                    break;
            }
            return true;
        }
    }

    private boolean isOverlap(View v1, View v2) {
        // check if the two circles overlap
        Rect r1 = new Rect(v1.getLeft(), v1.getTop(), v1.getRight(), v1.getBottom());
        Rect r2 = new Rect(v2.getLeft(), v2.getTop(), v2.getRight(), v2.getBottom());
        return r1.intersect(r2);
    }

    private void generateNewPositions() {
        // generate new random positions for the circles
        Random rand = new Random();
        int maxX = ((ConstraintLayout) circle1.getParent()).getWidth() - circle1.getWidth();
        int maxY = ((ConstraintLayout) circle1.getParent()).getHeight() - circle1.getHeight();
        float minDistance = 200; // minimum distance between circles
        float distance = 0;
        int x1, y1, x2, y2;
        do {
            x1 = rand.nextInt(maxX);
            y1 = rand.nextInt(maxY);
            x2 = rand.nextInt(maxX);
            y2 = rand.nextInt(maxY);
            // calculate distance between the circles
            distance = (float) Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
        } while (distance < minDistance);
        // set the new positions and make the circles visible again
        circle1.setX(x1);
        circle1.setY(y1);
        circle1.setVisibility(View.VISIBLE);
        circle2.setX(x2);
        circle2.setY(y2);
        circle2.setVisibility(View.VISIBLE);
    }

    public void saveResults() {

        mAuth = FirebaseAuth.getInstance();

        mUser = mAuth.getCurrentUser();
        onlineUserID = mUser.getUid();
        reference = FirebaseDatabase.getInstance().getReference().child("AgilityResults").child(onlineUserID);
        //reference = FirebaseDatabase.getInstance().getReference().child("Tasks").child(onlineUserID);
        int finalScore = score;
        String id = reference.push().getKey();
        Result result = new Result(id, testName2, score, date);
        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("AgilityResults").push().setValue(result).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(AgilityTestActivity.this, "Results was added successfully", Toast.LENGTH_SHORT).show();


                } else {
                    String error = task.getException().toString();
                    Toast.makeText(AgilityTestActivity.this, "Failed: " + error, Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}