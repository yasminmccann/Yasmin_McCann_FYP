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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateProfile extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference reference;

    EditText name, age, email, gender;

    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        //final TextView greetingTextView = (TextView) findViewById(R.id.greeting);
        final TextView fullNameTextView = (TextView) findViewById(R.id.updateName);
        final TextView emailTextView = (TextView) findViewById(R.id.updateEmail);
        final TextView ageTextView = (TextView) findViewById(R.id.updateAge);
        final TextView genderTextView = (TextView) findViewById(R.id.updateGender);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if (userProfile != null) {
                    String name = userProfile.name;
                    String email = userProfile.email;
                    String age = userProfile.age;
                    String gender = userProfile.gender;

                    //greetingTextView.setText("Welcome back, " + fullName + "!");
                    fullNameTextView.setText(name);
                    emailTextView.setText(email);
                    ageTextView.setText(age);
                    genderTextView.setText(gender);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateProfile.this, "Something went wrong!", Toast.LENGTH_LONG).show();
            }
        });


        reference.child("age").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.d("firebase", String.valueOf(task.getResult().getValue().toString()));
                    //Log.e("firebase", "Error getting data", task.getException());
                } else {
                    // Log.d("firebase", String.valueOf(task.getResult().getValue().toString()));
                    Log.e("firebase", "Error getting data", task.getException());
                }
            }
        });
    }

    public void update(View view) {
    }

    private void showAllUserData() {
        Intent intent = getIntent();
        String user_username = intent.getStringExtra("username");
        String user_name = intent.getStringExtra("name");
        String user_email = intent.getStringExtra("email");
        String user_age = intent.getStringExtra("age");
        String user_gender = intent.getStringExtra("gender");

       /* name.getEditText().setText(user_name);
        email.getEditText().setText(user_email);
        age.getEditText().setText(user_age);
        gender.getEditText().setText(user_gender);*/
    }
}