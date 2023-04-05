package com.example.fyp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ResultData extends AppCompatActivity {

    public DatabaseReference reference;
    public FirebaseAuth mAuth;
    public FirebaseUser mUser;
    public String onlineUserID;
    private ProgressDialog loader;
    private Button addButton;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_data);

        mAuth = FirebaseAuth.getInstance();

        loader = new ProgressDialog(this);

        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        ArrayList<Result> resultlist = new ArrayList<>();


        mUser = mAuth.getCurrentUser();
        onlineUserID = mUser.getUid();
        reference = FirebaseDatabase.getInstance().getReference().child("Results").child(onlineUserID);

        // Result result = new Result(id, testName, score, date);
        addButton = findViewById(R.id.add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTask();
            }
        });

    }


    private void addTask() {
        AlertDialog.Builder myDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);

        View myView = inflater.inflate(R.layout.add_task, null);
        myDialog.setView(myView);

        final AlertDialog dialog = myDialog.create();
        dialog.setCancelable(false);


        final EditText testName = myView.findViewById(R.id.test);
        final EditText score = myView.findViewById(R.id.score);
        final EditText date = myView.findViewById(R.id.date);
        Button save = myView.findViewById(R.id.saveBtn);
        Button cancel = myView.findViewById(R.id.cancelBtn);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
/*        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mName = testName.getText().toString().trim();
                String mScore = score.getText().toString().trim();
                String mDate = date.getText().toString().trim();
                String id = reference.push().getKey();
                //String date = DateFormat.getDateInstance().format(new Date());


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
                }else {
                    loader.setMessage("Adding your task");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();

                    Result result = new Result(id, mName, mScore, mDate);
                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Results").push().setValue(result).addOnCompleteListener(new OnCompleteListener<Void>() {
                        // reference.child(id).setValue(task).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(ResultData.this, "Task was added successfully", Toast.LENGTH_SHORT).show();
                                loader.dismiss();

                            }else {
                                String error = task.getException().toString();
                                Toast.makeText(ResultData.this, "Failed: " + error, Toast.LENGTH_SHORT).show();
                                loader.dismiss();
                            }
                        }
                    });*/

            /*    }
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    }*/
    }
}