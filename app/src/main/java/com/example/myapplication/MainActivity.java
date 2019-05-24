package com.example.myapplication;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;

import android.view.MenuItem;

import android.widget.ListView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    private TextView mTextMessage;
    public ListView listView;
    
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //listView.setVisibility(View.VISIBLE);
                    setHomeListView();
                    return true;
                case R.id.navigation_tabel:
                    //listView.setVisibility(View.GONE);
                    return true;
                case R.id.navigation_myRides:
                    setRideList();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        listView = (ListView) findViewById(R.id.listview);
        setHomeListView();

    }

    public void setHomeListView() {
        listView.setAdapter(new gamesAdapter(this, new String[] { "Sonntag 05.05.2019",
                "Sonntag 19.05.2019" }, new String[] { "Jahn Regensburg", "Jahn Regensburg" },
                new String[] { "FC Erzgebirge", "SV Sandhausen" }));
    }

    public void setRideList() {
        listView.setAdapter(new rideAdapter(this, new String[] { "14:30",
                "16:00" }, new String[] { "Neutraubling", "Pentling" }));
    }
}
