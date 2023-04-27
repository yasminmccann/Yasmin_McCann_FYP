package com.example.fyp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Test1Results extends AppCompatActivity {

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
        fireDBUser = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Results");

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
                final String[] weekdays = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"}; // Your List / array with String Values For X-axis Labels

        // Set the value formatter
                XAxis xAxis = lineChart.getXAxis();
                xAxis.setValueFormatter(new MyXAxisValueFormatter(weekdays));
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

                        Result resultObj = myDataSnapShot.getValue(Result.class);
                        int scoreValue = resultObj.getScore();
                        yData.add(new Entry(i, scoreValue));

                    }
                    final LineDataSet lineDataSet = new LineDataSet(yData,"Score");
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}