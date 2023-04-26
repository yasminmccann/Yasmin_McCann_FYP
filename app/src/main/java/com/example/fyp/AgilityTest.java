package com.example.fyp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AgilityTest extends AppCompatActivity implements View.OnTouchListener {


    private Circle circle1;
    private Circle circle2;

    private TextView scoreTextView;
    private TextView countdownTextView;

    private int score = 0;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis = 60000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agility_test2);

        circle1 = findViewById(R.id.circle1);
        circle2 = findViewById(R.id.circle2);

        circle1.setOnTouchListener(this);
        circle2.setOnTouchListener(this);


        scoreTextView = findViewById(R.id.scoreTextView);
        countdownTextView = findViewById(R.id.countdownTextView);

        // Initialize countdown timer for 60 seconds
        countDownTimer = new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                countdownTextView.setText("Time: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                countdownTextView.setText("Time's up!");
                scoreTextView.setText("Final score: " + score);
                // Reset game here
            }
        };
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int action = motionEvent.getAction();

        if (action == MotionEvent.ACTION_DOWN) {
            if (view == circle1) {
                circle1.setColor(Color.GREEN);
                circle2.setColor(Color.RED);
            } else {
                circle1.setColor(Color.RED);
                circle2.setColor(Color.GREEN);
            }
        } else if (action == MotionEvent.ACTION_MOVE) {
            int x = (int) motionEvent.getRawX();
            int y = (int) motionEvent.getRawY();

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
            params.leftMargin = x - view.getWidth() / 2;
            params.topMargin = y - view.getHeight() / 2;
            view.setLayoutParams(params);

            if (checkCollision() == true) {
                // Circles are colliding, increment score and update UI
                //circle1.setVisibility(View.GONE);
                //circle2.setVisibility(View.GONE);
                onGameEvent();
                int screenWidth = getResources().getDisplayMetrics().widthPixels;
                int screenHeight = getResources().getDisplayMetrics().heightPixels;

                /*circle1.setX((float) (Math.random() * screenWidth));
                circle1.setY((float) (Math.random() * screenHeight));
                circle2.setX((float) (Math.random() * screenWidth));
                circle2.setY((float) (Math.random() * screenHeight));*/
            }
            else {
                //Toast.makeText(this, "No Collision Detected", Toast.LENGTH_SHORT).show();
            }
        }

        return true;
           /* int collision = checkCollision();
            if (collision != 0) {
                // Circles are colliding, reposition them to random locations
                circle1.setVisibility(View.INVISIBLE);
                circle2.setVisibility(View.INVISIBLE);
                //int screenWidth = getResources().getDisplayMetrics().widthPixels;
                //int screenHeight = getResources().getDisplayMetrics().heightPixels;

                //circle1.setVisibility(View.VISIBLE);
                //circle2.setVisibility(View.VISIBLE);


                if (collision == 1) {

                   // circle1.setX((float) (Math.random() * screenWidth));
                   // circle1.setY((float) (Math.random() * screenHeight));
                } else {
                    //circle2.setX((float) (Math.random() * screenWidth));
                   // circle2.setY((float) (Math.random() * screenHeight));
                }
            }*/
       /* }

        return true;*/
    }

    public void onGameEvent() {
        // Update score and scoreTextView
        score ++;
        scoreTextView.setText("Score: " + score);
    }

    @Override
    public void onResume() {
        super.onResume();
        countDownTimer.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        countDownTimer.cancel();
    }

   /* @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int action = motionEvent.getAction();

        if (action == MotionEvent.ACTION_DOWN) {
            if (view == circle1) {
                circle1.setColor(Color.GREEN);
                circle2.setColor(Color.RED);
            } else {
                circle1.setColor(Color.RED);
                circle2.setColor(Color.GREEN);
            }
        } else if (action == MotionEvent.ACTION_MOVE) {
            int x = (int) motionEvent.getRawX();
            int y = (int) motionEvent.getRawY();

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
            params.leftMargin = x - view.getWidth() / 2;
            params.topMargin = y - view.getHeight() / 2;
            view.setLayoutParams(params);

            if (checkCollision()) {
                circle1.setVisibility(View.GONE);
                circle2.setVisibility(View.GONE);
            }
        }

        return true;
    }

    private boolean checkCollision() {
        int centerX1 = circle1.getLeft() + circle1.getWidth() / 2;
        int centerY1 = circle1.getTop() + circle1.getHeight() / 2;
        int radius1 = Math.min(circle1.getWidth(), circle1.getHeight()) / 2;

        int centerX2 = circle2.getLeft() + circle2.getWidth() / 2;
        int centerY2 = circle2.getTop() + circle2.getHeight() / 2;
        int radius2 = Math.min(circle2.getWidth(), circle2.getHeight()) / 2;

        double distance = Math.sqrt(Math.pow(centerX2 - centerX1, 2) + Math.pow(centerY2 - centerY1, 2));

        return distance <= radius1 + radius2;

    }*/



    /* private int checkCollision() {
         int centerX1 = circle1.getLeft() + circle1.getWidth() / 2;
         int centerY1 = circle1.getTop() + circle1.getHeight() / 2;
         int radius1 = Math.min(circle1.getWidth(), circle1.getHeight()) / 2;

         int centerX2 = circle2.getLeft() + circle2.getWidth() / 2;
         int centerY2 = circle2.getTop() + circle2.getHeight() / 2;
         int radius2 = Math.min(circle2.getWidth(), circle2.getHeight()) / 2;

         double distance = Math.sqrt(Math.pow(centerX2 - centerX1, 2) + Math.pow(centerY2 - centerY1, 2));

         if (distance <= radius1 + radius2) {
             // Circles are colliding, return the index of the circle that is colliding
             if (centerX1 < centerX2) {
                 return 1;
             } else {
                 return 2;
             }
         }

         // Circles are not colliding
         return 0;
     }*/
    private boolean checkCollision() {
        int centerX1 = circle1.getLeft() + circle1.getWidth() / 2;
        int centerY1 = circle1.getTop() + circle1.getHeight() / 2;
        int radius1 = Math.min(circle1.getWidth(), circle1.getHeight()) / 2;

        int centerX2 = circle2.getLeft() + circle2.getWidth() / 2;
        int centerY2 = circle2.getTop() + circle2.getHeight() / 2;
        int radius2 = Math.min(circle2.getWidth(), circle2.getHeight()) / 2;

        double distance = Math.sqrt(Math.pow(centerX2 - centerX1, 2) + Math.pow(centerY2 - centerY1, 2));

        if (distance <= radius1 + radius2) {
            // Circles are colliding
            return true;
        }

        // Circles are not colliding
        return false;
    }

    /* private void onCollision() {
         score ++;
         scoreTextView.setText("Score: " + score);

         if (countDownTimer == null){
             countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
                 @Override
                 public void onTick(long millisUntilFinished) {
                     timeLeftInMillis = millisUntilFinished;
                     updateTimer();

                 }

                 @Override
                 public void onFinish() {

                     timeLeftInMillis = 0;
                     updateTimer();
                     gameOver();
                 }
             }.start();
         }

     }

     private void updateTimer() {
         int minutes = (int) (timeLeftInMillis / 1000) / 60;
         int seconds = (int) (timeLeftInMillis / 1000) % 60;
         countdownTextView.setText(String.format("Time: %d:%02d", minutes, seconds));
     }
 */
    private void gameOver() {
        countDownTimer.cancel();
        countDownTimer = null;
        score = 0;
        timeLeftInMillis = 60000;
        scoreTextView.setText("Score " + score);
        countdownTextView.setText("Time: 1:00");
    }
}