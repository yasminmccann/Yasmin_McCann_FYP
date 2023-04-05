package com.example.fyp;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ReadAndWriteData {

//    private static final String TAG = "ReadAndWriteData";

   /* // [START declare_database_ref]
    private DatabaseReference mDatabase;
    // [END declare_database_ref]

    public ReadAndWriteData(DatabaseReference database) {
        // [START initialize_database_ref]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END initialize_database_ref]
    }

    // [START rtdb_write_new_user]
    public void writeNewUser(String name, String age, String email, String gender, String userId) {
        User user = new User(name, age, email, gender);

        mDatabase.child("users").child(userId).setValue(user);
    }
    // [END rtdb_write_new_user]

    public void writeNewUserWithTaskListeners(String name, String age, String email, String gender, String userId) {
        User user = new User(name, age, email, gender);

        // [START rtdb_write_new_user_task]
        mDatabase.child("users").child(userId).setValue(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Write was successful!
                        // ...
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Write failed
                        // ...
                    }
                });
        // [END rtdb_write_new_user_task]
    }*/

    /*private void addResultEventListener(DatabaseReference mResultReference) {
        // [START post_value_event_listener]
        ValueEventListener resultListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                Result result = dataSnapshot.getValue(Result.class);
                // ..
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        mResultReference.addValueEventListener(resultListener);
        // [END post_value_event_listener]
    }*/

    // [START write_fan_out]
    /*private void writeNewResult(String userId, String testName, int score, Date date) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        String key = mDatabase.child("results").push().getKey();
        Result result = new Result(userId, testName, score, date);
        Map<String, Object> resultValues = result.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/results/" + key, resultValues);
        childUpdates.put("/user-results/" + userId + "/" + key, resultValues);

        mDatabase.updateChildren(childUpdates);
    }*/
    // [END write_fan_out]

  /*  private String getUid() {
        return "";
    }

    // [START post_stars_transaction]
    private void onStarClicked(DatabaseReference resultRef) {
        resultRef.runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                Result r = mutableData.getValue(Result.class);
                if (r == null) {
                    return Transaction.success(mutableData);
                }

                // Set value and report transaction success
                mutableData.setValue(r);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean committed,
                                   DataSnapshot currentData) {
                // Transaction completed
                Log.d(TAG, "postTransaction:onComplete:" + databaseError);
            }
        });
    }*/
    // [END post_stars_transaction]

    /*// [START post_stars_increment]
    private void onStarClicked(String uid, String key) {
        Map<String, Object> updates = new HashMap<>();
        updates.put("results/"+key+"/stars/"+uid, true);
        updates.put("results/"+key+"/starCount", ServerValue.increment(1));
        updates.put("user-results/"+uid+"/"+key+"/stars/"+uid, true);
        updates.put("user-results/"+uid+"/"+key+"/starCount", ServerValue.increment(1));
        mDatabase.updateChildren(updates);
    }*/
    // [END post_stars_increment]





}
