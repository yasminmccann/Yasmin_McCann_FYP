package com.example.fyp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MoodResults extends AppCompatActivity {

    DatabaseReference databaseReference;

    FirebaseDatabase firebaseDatabase;
    public FirebaseAuth mAuth;
    public FirebaseUser mUser;
    public String onlineUserID;
    DatabaseReference fireDBUser;

    LineDataSet lineDataSet= new LineDataSet(null,null);
    ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
    List<String> dateLabels = new ArrayList<>();
    ArrayList<Entry> yData;
    //ArrayList<Entry> xData;
    LineData lineData;

    // variable for Text view.
    LineChart lineChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1_results);

        lineChart = findViewById(R.id.lineGraph);

        mAuth = FirebaseAuth.getInstance();

        // below line is used to get the instance
        // of our Firebase database.
        firebaseDatabase = FirebaseDatabase.getInstance();

        mUser = mAuth.getCurrentUser();
        onlineUserID = mUser.getUid();

        //databaseReference = firebaseDatabase.getReference().child("Results").child(onlineUserID);
        fireDBUser = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("moodEntries");

        renderData();
        //getData();

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

               /*//XAxis xAxis = lineChart.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setDrawGridLines(true);
                xAxis.setGranularity(1f);
                xAxis.setGranularityEnabled(true);
                final String xVal[]={"Mon","Tue","Wed", "Thur", "FRi"};
                xAxis.setValueFormatter(new IAxisValueFormatter() {
                    @Override
                    public String getFormattedValue(float value, AxisBase axis) {
                        return xVal[(int) value-1]; // xVal is a string array
                    }

                });*/

                if (snapshot.hasChildren()) {
                    for (DataSnapshot myDataSnapShot : snapshot.getChildren()) {
                        i = i + 1;

                        Mood mood = myDataSnapShot.getValue(Mood.class);
                        int moodValue = mood.getMood();
                        yData.add(new Entry(i, moodValue));

                    }
                    final LineDataSet lineDataSet = new LineDataSet(yData,"Mood");
                    LineData data = new LineData(lineDataSet);

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
        LimitLine limitLine = new LimitLine(55f, "Average Score");
        limitLine.setLineWidth(2f);
        limitLine.enableDashedLine(10f, 10f, 0f);
        limitLine.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        limitLine.setTextSize(10f);

        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.addLimitLine(limitLine);

        lineChart.getDescription().setText("Time");
        getData();
    }

    private void showChart(ArrayList<Entry> dataVals){

        lineDataSet.setValues(dataVals);
        lineDataSet.setLabel("DataSet 1");
        iLineDataSets.clear();
        iLineDataSets.add(lineDataSet);
        lineData = new LineData(iLineDataSets);
        lineChart.clear();
        lineChart.setData(lineData);
        lineChart.invalidate();

    }

//    DatabaseReference databaseReference;
//
//    FirebaseDatabase firebaseDatabase;
//    public FirebaseAuth mAuth;
//    public FirebaseUser mUser;
//    public String onlineUserID;
//    DatabaseReference fireDBUser;
//
//    ArrayList<PieEntry> yvalues;
//    int conta;
//    String nomVoto;
//    float numVoto;
//
//    // variable for Text view.
//    PieChart pieChart;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_mood_results);
//
//        pieChart = findViewById(R.id.pieChart);
//
//        mAuth = FirebaseAuth.getInstance();
//
//        // below line is used to get the instance
//        // of our Firebase database.
//        firebaseDatabase = FirebaseDatabase.getInstance();
//
//        mUser = mAuth.getCurrentUser();
//        onlineUserID = mUser.getUid();
//
//        fireDBUser = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Results");
//
//        pieChart.setUsePercentValues(true);
//
//        yvalues = new ArrayList<PieEntry>();
//
//        fireDBUser.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                yvalues.clear();
//                conta = 0;
//                float i =0;
//
//                if (dataSnapshot.hasChildren()) {
//                    for (DataSnapshot myDataSnapShot : dataSnapshot.getChildren()){
//                        i = i + 1;
//
//                        Mood mood = myDataSnapShot.getValue(Mood.class);
//                        int moodValue = mood.getMood();
//                        yvalues.add(new PieEntry(i, moodValue));
//                    }
//                }
//                PieDataSet dataSet = new PieDataSet(yvalues, " - Moods");
//                PieData data = new PieData(dataSet);
//
//                data.setValueFormatter(new PercentFormatter());
//                pieChart.setData(data);
//                dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
//                data.setValueTextSize(13f);
//                data.setValueTextColor(Color.WHITE);
//                Description description = new Description();
//                description.setText("Mood Tracker Results:");
//                pieChart.setDescription(description);
//                pieChart.animateX(3000);
//
//                pieChart.setCenterText("MOOD TRACKER");
//                pieChart.getCenterText();
//
//                //pieChart.setDrawHoleEnabled(true);
//                //pieChart.setTransparentCircleRadius(58f);
//                //pieChart.setHoleRadius(58f);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.d(TAG, "Error trying to get classified ad for update " +
//                        ""+databaseError);
//            }
//        });
//
//    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}