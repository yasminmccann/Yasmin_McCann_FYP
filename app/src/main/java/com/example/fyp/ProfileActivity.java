package com.example.fyp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    private Button logout;
    private Button delete;
    //ProgressBar bar;

    private FirebaseUser user;
    private DatabaseReference reference;
    private TextView webSite;

    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        logout = (Button) findViewById(R.id.signOut);
        webSite = (TextView) findViewById(R.id.parkinsonsWebsite);

        webSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, ParkinsonsIrelandLink.class);
                startActivity(intent);
            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        //final TextView greetingTextView = (TextView) findViewById(R.id.greeting);
        final TextView fullNameTextView = (TextView) findViewById(R.id.userName);
        final TextView emailTextView = (TextView) findViewById(R.id.email);
        final TextView ageTextView = (TextView) findViewById(R.id.age);
        final TextView genderTextView = (TextView) findViewById(R.id.gender);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if(userProfile != null){
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
                Toast.makeText(ProfileActivity.this, "Something went wrong!", Toast.LENGTH_LONG).show();
            }
        });

      //delete = findViewById(R.id.delete);
      //bar = findViewById(R.id.progressBar2);

     /* delete.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              AlertDialog.Builder dialog = new AlertDialog.Builder(ProfileActivity.this);
              dialog.setTitle("Are you sure?");
              dialog.setMessage("Deleting this account will result in removing all your data");
              dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {
                      //bar.setVisibility(View.VISIBLE);
                      user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                          @Override
                          public void onComplete(@NonNull Task<Void> task) {
                              //bar.setVisibility(View.GONE);
                              if(task.isSuccessful()){
                                  Toast.makeText(ProfileActivity.this, "Account deleted", Toast.LENGTH_LONG).show();

                                  Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                                  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                  startActivity(intent);
                              }else{
                                  Toast.makeText(ProfileActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                              }
                          }
                      });
                  }
              });
              dialog.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {
                      dialog.dismiss();
                  }
              });

              AlertDialog alertDialog =dialog.create();
              alertDialog.show();

          }
      });*/

        reference.child("age").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.d("firebase", String.valueOf(task.getResult().getValue().toString()));
                    //Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                   // Log.d("firebase", String.valueOf(task.getResult().getValue().toString()));
                    Log.e("firebase", "Error getting data", task.getException());
                }
            }
        });


    }
}

