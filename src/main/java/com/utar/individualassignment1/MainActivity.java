package com.utar.individualassignment1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button equalBreak = findViewById(R.id.button);
        //redirect to equal break page
        equalBreak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to the new activity
                Intent intent = new Intent(MainActivity.this, EqualBreak.class);
                startActivity(intent);
            }
        });

        Button ByPercentage = findViewById(R.id.button2);
        //redirect to break by percentage page
        ByPercentage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to the new activity
                Intent intent = new Intent(MainActivity.this, ByPercentage.class);
                startActivity(intent);
            }
        });

        Button ByRatio = findViewById(R.id.button4);
        //redirect to break by ratio page
        ByRatio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to the new activity
                Intent intent = new Intent(MainActivity.this, ByRatio.class);
                startActivity(intent);
            }
        });

        Button ByAmount = findViewById(R.id.button3);
        //redirect to break by amount page
        ByAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to the new activity
                Intent intent = new Intent(MainActivity.this, ByAmount.class);
                startActivity(intent);
            }
        });

    }

}