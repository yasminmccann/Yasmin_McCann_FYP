package com.example.fyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class FingerTest2 extends AppCompatActivity {

    public int mCounter;
    Button btn;
    TextView txt, testName;
    TextView timer;

    public FirebaseDatabase firebaseDatabase;
    public DatabaseReference reference;
    public FirebaseAuth mAuth;
    public FirebaseUser mUser;
    public String onlineUserID;

    Date currentTime = Calendar.getInstance().getTime();
    String testName1 = "Finger Tap Test";
    String id = String.valueOf(1);

    String date = String.valueOf(currentTime);
    //SimpleDateFormat format = new SimpleDateFormat("dd-mm-yyyy");
    //String date = format(new Date());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finger_test2);

        ArrayList<Result> resultlist = new ArrayList<>();

        btn = (Button) findViewById(R.id.button);
        txt = (TextView) findViewById(R.id.tx);
        testName = (TextView) findViewById(R.id.testName);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCounter++;
                txt.setText(Integer.toString(mCounter));
                testName.setText("Finger Tap Test");
            }
        });


        btn.setEnabled(true);
        timer = findViewById(R.id.timer);
        new CountDownTimer(11000, 1000) { //Set Timer for 10 seconds
            public void onTick(long millisUntilFinished) {
                timer.setText("seconds left: " + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                btn.setEnabled(false);
                timer.setText("Stop!");
                saveResults();
                Intent intent = new Intent(FingerTest2.this,Test1Results.class);
                startActivity(intent);
                //timer.setText("score " + mCounter);
            }
        }.start();


    }

    public void saveResults() {

        mAuth = FirebaseAuth.getInstance();

        mUser = mAuth.getCurrentUser();
        onlineUserID = mUser.getUid();
        reference = FirebaseDatabase.getInstance().getReference().child("FingerTapResults").child(onlineUserID);
        //reference = FirebaseDatabase.getInstance().getReference().child("Tasks").child(onlineUserID);
        int score = mCounter;
        String id = reference.push().getKey();
        Result result = new Result(id, testName1, score, date);
        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("FingerTapResults").push().setValue(result).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(FingerTest2.this, "Results was added successfully", Toast.LENGTH_SHORT).show();


                }else {
                    String error = task.getException().toString();
                    Toast.makeText(FingerTest2.this, "Failed: " + error, Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}

