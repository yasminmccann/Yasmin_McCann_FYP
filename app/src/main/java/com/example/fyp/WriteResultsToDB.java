package com.example.fyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class WriteResultsToDB extends AppCompatActivity {
/*
    public DatabaseReference reference;
    public FirebaseAuth mAuth;
    public FirebaseUser mUser;
    public String onlineUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_results_to_db);

        mAuth = FirebaseAuth.getInstance();

        ArrayList<Result> resultlist = new ArrayList<>();


        mUser = mAuth.getCurrentUser();
        onlineUserID = mUser.getUid();
        reference = FirebaseDatabase.getInstance().getReference().child("Results").child(onlineUserID);

       *//* String mName = testName.getText().toString().trim();
        String mScore = score.getText().toString().trim();
        String mDate = date.getText().toString().trim();
        //Users.child(user.getUid()).child("Date").setValue(ServerValue.TIMESTAMP);
        String id = reference.push().getKey();
        //String date = DateFormat.getDateInstance().format(new Date());

        //Pass data to 2nd activity
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        intent.putExtra("name", getName);
        intent.putExtra("number", getNumber);
        startActivity(intent);

        if (TextUtils.isEmpty(mName)) {
            testName.setError("Test Name is required");
            return;
        }
        if (TextUtils.isEmpty(mScore)) {
            score.setError("Score is required");
            return;
        }
        if (TextUtils.isEmpty(mDate)) {
            date.setError("Date is required");
            return;
        }else {*//*
            Toast.makeText(this, "Adding results", Toast.LENGTH_SHORT).show();

          //  Result result = new Result(id, mName, mScore, mDate);
            FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Results").push().setValue(result).addOnCompleteListener(new OnCompleteListener<Void>() {
                // reference.child(id).setValue(task).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(WriteResultsToDB.this, "Task was added successfully", Toast.LENGTH_SHORT).show();

                    }else {
                        String error = task.getException().toString();
                        Toast.makeText(WriteResultsToDB.this, "Failed: " + error, Toast.LENGTH_SHORT).show();

                    }
                }
            });

    }*/
}