package com.example.fyp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private ImageView logo;
    private TextView banner, regUser;
    private EditText editTextName, editTextAge, editTextEmail, editTextPassword, editTxtGender;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        editTextName = (EditText) findViewById(R.id.fullName);
        editTextAge = (EditText) findViewById(R.id.age);
        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);
        editTxtGender = (EditText) findViewById(R.id.gender);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        banner = (TextView) findViewById(R.id.banner);
        banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.banner:
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        break;
                    /*case R.id.registerUser:
                        registerUser();*/
                }
            }
        });

        regUser = (Button) findViewById(R.id.registerUser);
        regUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
    }

        private void registerUser() {
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();
            String name = editTextName.getText().toString().trim();
            String age = editTextAge.getText().toString().trim();
            String gender = editTxtGender.getText().toString().trim();

            if (name.isEmpty()) {
                editTextName.setError("Full name is required!");
                editTextName.requestFocus();
                return;
            }

            if (age.isEmpty()) {
                editTextAge.setError("Age is required!");
                editTextAge.requestFocus();
                return;
            }

            if (gender.isEmpty()) {
                editTxtGender.setError("Gender is required!");
                editTxtGender.requestFocus();
                return;
            }

            if (email.isEmpty()) {
                editTextEmail.setError("Email is required!");
                editTextEmail.requestFocus();
                return;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                editTextEmail.setError("Please provide a valid email!");
                editTextEmail.requestFocus();
                return;
            }

            if (password.isEmpty()) {
                editTextPassword.setError("Password is required!");
                editTextPassword.requestFocus();
                return;
            }

            if (password.length() < 6) {
                editTextPassword.setError("Min password length should be 6 characters");
                editTextPassword.requestFocus();
                return;
            }


            progressBar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        User user = new User(name, age, email, gender);

                        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(RegisterActivity.this, "User has been registered successfully!", Toast.LENGTH_LONG).show();
                                    progressBar.setVisibility(View.GONE);

                                    //redirect to login layout
                                }else {
                                    Toast.makeText(RegisterActivity.this, "Failed to register! Try again!", Toast.LENGTH_LONG).show();
                                    progressBar.setVisibility(View.GONE);
                                }
                            }
                        });
                    }else{
                        Toast.makeText(RegisterActivity.this, "Failed to register! Try again!!", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }
            });

            ActionCodeSettings actionCodeSettings =
                    ActionCodeSettings.newBuilder()
                            // URL you want to redirect back to. The domain (www.example.com) for this
                            // URL must be whitelisted in the Firebase Console.
                            .setUrl("https://www.example.com/finishSignUp?cartId=1234")
                            // This must be true
                            .setHandleCodeInApp(true)
                            .setIOSBundleId("com.example.ios")
                            .setAndroidPackageName(
                                    "com.example.android",
                                    true, /* installIfNotAvailable */
                                    "12"    /* minimumVersion */)
                            .build();

        }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    }
