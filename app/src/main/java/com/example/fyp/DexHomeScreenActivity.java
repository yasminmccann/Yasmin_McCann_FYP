package com.example.fyp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DexHomeScreenActivity extends AppCompatActivity {

    CardView test1, test2, test3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dex_home_screen);

        test1 = (CardView) findViewById(R.id.fingerTapTest);
        test1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DexHomeScreenActivity.this,  FingerTapTestDescriptionPage.class );
                startActivity(intent);
            }
        });

        test2 = (CardView) findViewById(R.id.agilityTest);
        test2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DexHomeScreenActivity.this,  AgilityTestActivity.class );
                startActivity(intent);
            }
        });

        test3 = (CardView) findViewById(R.id.letterTest);
        test3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DexHomeScreenActivity.this,  MainActivity.class );
                startActivity(intent);
            }
        });

    }
}