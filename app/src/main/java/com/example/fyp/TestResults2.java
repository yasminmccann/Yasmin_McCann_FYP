package com.example.fyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
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

import java.util.ArrayList;

public class TestResults2 extends AppCompatActivity {

    DatabaseReference databaseReference;

    FirebaseDatabase firebaseDatabase;
    public FirebaseAuth mAuth;
    public FirebaseUser mUser;
    public String onlineUserID;
    DatabaseReference fireDBUser;

    LineDataSet lineDataSet= new LineDataSet(null,null);
    ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
    ArrayList<BarEntry> yData;
    //ArrayList<Entry> xData;
    LineData lineData;

    // variable for Text view.
    BarChart lineChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_results2);

        lineChart = findViewById(R.id.lineGraph);

        mAuth = FirebaseAuth.getInstance();

        // below line is used to get the instance
        // of our Firebase database.
        firebaseDatabase = FirebaseDatabase.getInstance();

        mUser = mAuth.getCurrentUser();
        onlineUserID = mUser.getUid();

        //databaseReference = firebaseDatabase.getReference().child("Results").child(onlineUserID);
        fireDBUser = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("SpeechResults");
        lineChart.getDescription().setText("Time");
        renderData();

    }
    public void getData() {
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //xAxis.setValueFormatter(new MyXAxisValueFormatter());
        //xAxis.setLabelsToSkip(0);
        fireDBUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                yData = new ArrayList<>();
                //xData = new ArrayList<>();
                float i =0;
//                final String[] weekdays = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"}; // Your List / array with String Values For X-axis Labels
//
//                // Set the value formatter
//                XAxis xAxis = lineChart.getXAxis();
//                xAxis.setValueFormatter(new MyXAxisValueFormatter(weekdays));


                if (snapshot.hasChildren()) {
                    for (DataSnapshot myDataSnapShot : snapshot.getChildren()) {
                        i = i + 1;
                        Result resultObj = myDataSnapShot.getValue(Result.class);
                        int scoreValue = resultObj.getScore();
                        yData.add(new BarEntry(i, scoreValue));
                    }
                    final BarDataSet lineDataSet = new BarDataSet(yData,"Score");
                    BarData data = new BarData(lineDataSet);
                    lineChart.setData(data);
                    lineChart.notifyDataSetChanged();
                    lineChart.invalidate();
                } else {
                    lineChart.clear();
                    lineChart.invalidate();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void renderData() {
        LimitLine limitLine = new LimitLine(8f, "Average Score");
        limitLine.setLineWidth(2f);
        limitLine.enableDashedLine(10f, 10f, 0f);
        limitLine.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        limitLine.setTextSize(10f);

        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.addLimitLine(limitLine);

        lineChart.getDescription().setText("Time");
        getData();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}