package com.example.myapplication;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ToolbarWidgetWrapper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toolbar;

import java.util.ArrayList;

public class RideListActivity extends AppCompatActivity {
    ListView listView;
    private FloatingActionButton addButton;
    ArrayList<String> depTime;
    ArrayList<String> depPlace;
    ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        depPlace = new ArrayList<String>();
        depTime = new ArrayList<String>();
        depPlace.add("Pentling");
        depTime.add("15:30");
        depPlace.add("Thalmassing");
        depTime.add("18:45");
        setContentView(R.layout.activity_ride_list);
        listView = (ListView) findViewById(R.id.rideList);
        setRideList();
        addButton = findViewById(R.id.add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NewRide.class);
                startActivityForResult(intent, 0);
            }
        });
        android.support.v7.widget.Toolbar navLayout = (android.support.v7.widget.Toolbar) View.inflate(this, R.layout.toolbar_with_arrow, null);
        backButton = navLayout.findViewById(R.id.backButton);
        //geht nicht
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Log.v("test", "click");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == RideListActivity.RESULT_OK){
            String time = data.getStringExtra("time");
            String departure = data.getStringExtra("departure");
            depTime.add(time);
            depPlace.add(departure);
            listView.setAdapter(new rideAdapter(this, depTime, depPlace));
        }
    }

    public void setRideList() {
        listView.setAdapter(new rideAdapter(this, depTime, depPlace));
    }
}
