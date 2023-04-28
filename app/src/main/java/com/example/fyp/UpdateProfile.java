package com.example.fyp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class UpdateProfile extends AppCompatActivity {

    EditText nameTxt, ageTxt, genderTxt;

    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    DatabaseReference dbRef;

    Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        if (user != null) {
//            for (UserInfo profile : user.getProviderData()) {
//                // Id of the provider (ex: google.com)
//                String providerId = profile.getProviderId();
//
//                // UID specific to the provider
//                String uid = profile.getUid();
//
//                // Name, email address, and profile photo Url
//                String name = profile.getDisplayName();
//                String email = profile.getEmail();
//            }
//        }
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        dbRef = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());


        //final TextView greetingTextView = (TextView) findViewById(R.id.greeting);
        nameTxt = findViewById(R.id.updateName);
        ageTxt = findViewById(R.id.updateAge);
        genderTxt = findViewById(R.id.updateGender);
        updateProfile();

        update = findViewById(R.id.updateUser);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
            }
        });

    }

    private void updateProfile() {
        String name = nameTxt.getText().toString().trim();
        String age = ageTxt.getText().toString().trim();
        String gender = genderTxt.getText().toString().trim();

        if (name.isEmpty()) {
            nameTxt.setError("Full name is required!");
            nameTxt.requestFocus();
            return;
        }

        if (age.isEmpty()) {
            ageTxt.setError("Age is required!");
            ageTxt.requestFocus();
            return;
        }

        if (gender.isEmpty()) {
            genderTxt.setError("Gender is required!");
            genderTxt.requestFocus();
            return;
        }

        HashMap<String, Object> updatedUserData = new HashMap<>();
        updatedUserData.put("name", name);
        updatedUserData.put("gender", gender);
        updatedUserData.put("age", age);

        dbRef.updateChildren(updatedUserData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // Data updated successfully
                Toast.makeText(UpdateProfile.this, "Successfully updated", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // An error occurred while updating data
                Toast.makeText(UpdateProfile.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}