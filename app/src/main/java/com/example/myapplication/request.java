package com.example.myapplication;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class request extends AppCompatActivity {
    private TextView timeSet, placeSet;
    private FloatingActionButton checkBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        Intent intent = getIntent();
        String time = intent.getStringExtra("time");
        String place = intent.getStringExtra("place");
        timeSet = findViewById(R.id.request_time);
        placeSet = findViewById(R.id.request_place);
        timeSet.setText(time);
        placeSet.setText(place);
        checkBtn = findViewById(R.id.submit_request);
        checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
