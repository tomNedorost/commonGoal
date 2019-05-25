package com.example.myapplication;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.text.format.DateUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import android.view.MenuItem;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;


public class MainActivity extends AppCompatActivity {
    private TextView mTextMessage;
    public ListView listView, ridesList;
    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    listView.setVisibility(View.VISIBLE);
                    setHomeListView();
                    return true;
                case R.id.navigation_tabel:
                    listView.setVisibility(View.GONE);
                    return true;
                case R.id.navigation_myRides:
                    listView.setVisibility(View.VISIBLE);
                    setMyRides();
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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adapterView, View view, int i, long l) {
                //Toast.makeText(getBaseContext(), items[i].getText(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), RideListActivity.class);
                startActivity(intent);
            }
        });
    }

    public void setHomeListView() {
        listView.setAdapter(new gamesAdapter(this,
                new String[] {"Sonntag 24.02.2019", "Samstag 09.03.2019", "Montag 18.03.2019", "Sonntag 05.04.2019", "Sonntag 21.04.2019", "Sonntag 05.05.2019",
                "Sonntag 19.05.2019" },
                new String[] { "Jahn Regensburg", "Jahn Regensburg", "Jahn Regensburg", "Jahn Regensburg", "Jahn Regensburg", "Jahn Regensburg", "Jahn Regensburg" },
                new String[] { "HSV", "MSV Duisburg", "Greuther Fürth", "Bochum", "FC Magdeburg", "FC Erzgebirge", "SV Sandhausen" }
                //, new int[] {R.drawable.hamburger1, R.drawable.duisburg1, R.drawable.greutherfurth1, R.drawable.bochum1, R.drawable.magdeburg1, R.drawable.erzgebirge1, R.drawable.sandhausen1}
                ));
    }

    public void setMyRides() {
        ridesList = (ListView) findViewById(R.id.listview);
        ridesList.setAdapter(new rideAdapter(this, new String[] { "14:30",
                "16:00" }, new String[] { "Neutraubling", "Pentling" }));
    }
}
