package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class RideListActivity extends AppCompatActivity {
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_list);
        listView = (ListView) findViewById(R.id.rideList);
        setRideList();
    }

    public void setRideList() {
        listView.setAdapter(new rideAdapter(this, new String[] { "14:30",
                "16:00" }, new String[] { "Neutraubling", "Pentling" }));
    }
}
