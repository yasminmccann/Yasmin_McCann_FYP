package com.example.fyp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Timer;

public class AgilityTestActivity extends AppCompatActivity {

    //TextView timer;

    private ViewGroup mainLayout;
    private ImageView image1;
    private ImageView image2;

    private float xCoOrdinate, yCoOrdinate;

    //Position
    private float ballDownY;
    private float ballDownX;

    //Screen Size
    private int screenWidth;
    private int screenHeight;

    //Initialize Class
    private Handler handler = new Handler();
    private Timer timer = new Timer();

    //score
    private TextView score = null;

    //for net movement along x-axis
    public float x = 0;
    public float y = 0;

    //points
    private int points = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agility_test);
       /* timer =  findViewById(R.id.timer);
        new CountDownTimer(11000, 1000) {

            public void onTick(long millisUntilFinished) {
                timer.setText("seconds left: " + millisUntilFinished / 1000);
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                timer.setText("Stop!");
            }

        }.start();*/

        mainLayout = (RelativeLayout) findViewById(R.id.main);
        image1 = (ImageView) findViewById(R.id.circle1);
        image2 = (ImageView) findViewById(R.id.circle2);

        this.score = (TextView) findViewById(R.id.score);

        //image1.setOnTouchListener(onTouchListener());
        // image2.setOnTouchListener(onTouchListener());

        //retrieving screen size
        WindowManager wm = getWindowManager();
        Display disp = wm.getDefaultDisplay();
        Point size = new Point();
        disp.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;

        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(100);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Render();
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        t.start();

        image1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        xCoOrdinate = view.getX() - event.getRawX();
                        yCoOrdinate = view.getY() - event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        view.animate().x(event.getRawX() + xCoOrdinate).y(event.getRawY() + yCoOrdinate).setDuration(0).start();
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });

        image2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        xCoOrdinate = view.getX() - event.getRawX();
                        yCoOrdinate = view.getY() - event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        view.animate().x(event.getRawX() + xCoOrdinate).y(event.getRawY() + yCoOrdinate).setDuration(0).start();
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });

    }

    public void Render() {
        if (Collision(image1, image2)) {
            points++; //You dont need findView Textview score for that exists in OnCreate Method
            this.score.setText("Score:" + points);
        }
    }

    private boolean Collision(ImageView image1, ImageView image2) {
        Rect r1 = new Rect();
        image2.getHitRect(r1);
        Rect r2 = new Rect();
        image1.getHitRect(r2);
        return r1.intersect(r2);


    }

   // public void changePos() {

        //down
            /*ballDownY += 10;
            if (image2.getY() > screenHeight) {
                ballDownX = (float) Math.floor((Math.random() * (screenWidth - image2.getWidth())));
                ballDownY = -100.0f;

            }*/
       // image2.setY(ballDownY);
       // image2.setX(ballDownX);


        //make net follow finger
        /*mainLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                x = event.getX();
                y = event.getY();

                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    image1.setX(x);
                    image1.setY(y);
                }
                return true;
            }

        });*/

   // }
    /* private View.OnTouchListener onTouchListener() {
        return new View.OnTouchListener() {

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                final int x = (int) event.getRawX();
                final int y = (int) event.getRawY();

                switch (event.getAction() & MotionEvent.ACTION_MASK) {

                    case MotionEvent.ACTION_DOWN:
                        RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams)
                                view.getLayoutParams();

                        xDelta = x - lParams.leftMargin;
                        yDelta = y - lParams.topMargin;
                        break;

                    case MotionEvent.ACTION_MOVE:
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view
                                .getLayoutParams();
                        layoutParams.leftMargin = x - xDelta;
                        layoutParams.topMargin = y - yDelta;
                        layoutParams.rightMargin = 0;
                        layoutParams.bottomMargin = 0;
                        view.setLayoutParams(layoutParams);
                        break;
                }
                mainLayout.invalidate();
                return true;
            }
        };
    }*/
}
