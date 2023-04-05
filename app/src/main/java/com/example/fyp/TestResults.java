package com.example.fyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class TestResults extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;

    // creating a variable for our
    // Database Reference for Firebase.
    DatabaseReference databaseReference;

    public FirebaseAuth mAuth;
    public FirebaseUser mUser;
    public String onlineUserID;
    DatabaseReference fireDBUser;

    // variable for Text view.
    LineChart mpLineChart;
    int colorArray[] = {R.color.color1, R.color.color2, R.color.color3, R.color.color4};
    String[] legendName = {"Finger Tap Test", "Dog"};
    int[] colorClassArray = new int[]{Color.BLUE, Color.CYAN, Color.GREEN, Color.MAGENTA};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_results); //activity_list

        mAuth = FirebaseAuth.getInstance();

        // below line is used to get the instance
        // of our Firebase database.
        firebaseDatabase = FirebaseDatabase.getInstance();

        mUser = mAuth.getCurrentUser();
        onlineUserID = mUser.getUid();

        //databaseReference = firebaseDatabase.getReference().child("Results").child(onlineUserID);
        fireDBUser = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Results");

        fireDBUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot resultSnapshot : snapshot.getChildren()) {
                    Result resultObj = resultSnapshot.getValue(Result.class);
                    Toast.makeText(TestResults.this, "Success", Toast.LENGTH_SHORT).show();
                    Log.w("RESULT", resultObj.getId() +
                            " " + resultObj.getTestName() + " " + resultObj.getScore() + " " + resultObj.getDate());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(TestResults.this, "Failed", Toast.LENGTH_SHORT).show();
                Log.w("DBError", "Cancel Access DB");
            }
        });

        mpLineChart = (LineChart) findViewById(R.id.lineGraph);
        LineDataSet lineDataSet1 = new LineDataSet(dataValues1(), "Data Set 1");
        LineDataSet lineDataSet2 = new LineDataSet(dataValues2(), "Data Set 2");
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet1);
        dataSets.add(lineDataSet2);

        //mpLineChart.setBackgroundColor(Color.GREEN);
        mpLineChart.setNoDataText("No Data");
        mpLineChart.setNoDataTextColor(Color.BLUE);

        mpLineChart.setDrawGridBackground(true);
        mpLineChart.setDrawBorders(true);
        mpLineChart.setBorderColor(Color.BLACK);
        mpLineChart.setBorderWidth(3);

        lineDataSet1.setLineWidth(4);
        lineDataSet1.setColor(Color.RED);
        lineDataSet1.setDrawCircles(true);
        lineDataSet1.setDrawCircleHole(true);
        lineDataSet1.setCircleColor(Color.GRAY);
        lineDataSet1.setCircleColorHole(Color.GREEN);
        lineDataSet1.setCircleRadius(10);
        lineDataSet1.setCircleHoleRadius(10);
        lineDataSet1.setValueTextSize(10);
        lineDataSet1.setValueTextColor(Color.BLUE);
        //lineDataSet1.enableDashedLine(5,10,0);
        lineDataSet1.setColors(colorArray, TestResults.this);

        Description description = new Description();
        description.setText("Results");
        description.setTextColor(Color.BLUE);
        description.setTextSize(20);
        mpLineChart.setDescription(description);

        Legend legend = mpLineChart.getLegend();
        legend.setEnabled(true);
        legend.setTextColor(Color.RED);
        legend.setTextSize(15);
        legend.setForm(Legend.LegendForm.LINE);
        legend.setFormSize(20);
        legend.setXEntrySpace(15);
        legend.setFormToTextSpace(10);

        LegendEntry[] legendEntries = new LegendEntry[2];

        for (int i = 0; i < legendEntries.length; i++) {
            LegendEntry entry = new LegendEntry();
            entry.formColor = colorClassArray[i];
            entry.label = String.valueOf(legendName[i]);
            legendEntries[i] = entry;
        }

        legend.setCustom(legendEntries);

        LineData data = new LineData(dataSets);
        mpLineChart.setData(data);
        mpLineChart.invalidate();

    }

    private ArrayList<Entry> dataValues1()
    {
        ArrayList<Entry> dataVals = new ArrayList<Entry>();
        dataVals.add(new Entry(0, 20));
        dataVals.add(new Entry(1, 24));
        dataVals.add(new Entry(2, 2));
        dataVals.add(new Entry(3, 10));
        dataVals.add(new Entry(4, 20));

        return dataVals;
    }

    private ArrayList<Entry> dataValues2()
    {
        ArrayList<Entry> dataVals = new ArrayList<Entry>();
        dataVals.add(new Entry(0, 12));
        dataVals.add(new Entry(2, 16));
        dataVals.add(new Entry(3, 23));
        dataVals.add(new Entry(5, 1));
        dataVals.add(new Entry(7, 18));

        return dataVals;
    }
}


       /* databaseReference.child("Users").child(onlineUserID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                }
            }
        });
*/


    /*    // below line is used to get
        // reference for our database.
        databaseReference = firebaseDatabase.getReference().child("Results").child("score");

        // initializing our object class variable.
        retrieveTV = findViewById(R.id.idTVRetrieveData);

        // calling method
        // for getting data.
        getdata();*/

        /*private void addUserResults() {

            Result result = new Result(key, testName, score, date);
            FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Results").push().setValue(result).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(TestResults.this, "Results saved ", Toast.LENGTH_SHORT).show();

                    } else {
                        String error = task.getException().toString();
                        Toast.makeText(TestResults.this, "Failed: " + error, Toast.LENGTH_SHORT).show();
                    }
                }
            });*/

        /*public void saveScoreToFirebase() {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("User").push();

            String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

            User userInformation = new User(email, mLastLocation.getLatitude(), mLastLocation.getLongitude());

            ref.setValue(userInformation);
        }*/

   /* private void getdata() {

        // calling add value event listener method
        // for getting the values from database.
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // this method is call to get the realtime
                // updates in the data.
                // this method is called when the data is
                // changed in our Firebase console.
                // below line is for getting the data from
                // snapshot of our database.
                String value = snapshot.getValue(String.class);

                // after getting the value we are setting
                // our value to our text view in below line.
                retrieveTV.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.
                Toast.makeText(TestResults.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });*/


