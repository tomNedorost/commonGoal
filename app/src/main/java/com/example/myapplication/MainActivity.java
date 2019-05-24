package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView mTextMessage;
    public ListView listView, rideListView;
    
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    listView.setVisibility(View.VISIBLE);
                    return true;
                case R.id.navigation_tabel:
                    mTextMessage.setText(R.string.title_dashboard);
                    listView.setVisibility(View.GONE);
                    return true;
                case R.id.navigation_myRides:
                    mTextMessage.setText(R.string.title_notifications);
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
        listView.setAdapter(new gamesAdapter(this, new String[] { "data1",
                "data2" }));
    }

    public void setRideList() {
        rideListView = (ListView) findViewById(R.id.rideList);
        listView.setAdapter(new gamesAdapter(this, new String[] { "data1",
                "data2" }));
    }


}
