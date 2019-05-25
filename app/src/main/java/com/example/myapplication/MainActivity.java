package com.example.myapplication;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity {
    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";
    private static final String KEY_DRIVER_ID = "id";
    private static final String KEY_DRIVER_NAME = "date";
    private static final String BASE_URL = "http://172.16.29.109:80/drivers/";
    private ArrayList<HashMap<String, String>> driverList;

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
                    new FetchMoviesAsyncTask().execute();
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

    /**
     * Fetches the list of movies from the server
     */
    private class FetchMoviesAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "fetch_all_drivers.php", "GET", null);
            try {
                int success = jsonObject.getInt(KEY_SUCCESS);
                JSONArray drivers;
                if (success == 1) {
                    drivers = jsonObject.getJSONArray(KEY_DATA);
                    Log.i("Drivers", String.valueOf(drivers.length()));
                    //Iterate through the response and populate drivers list
                    for (int i = 0; i < drivers.length(); i++) {
                        JSONObject driver = drivers.getJSONObject(i);
                        Integer driverId = driver.getInt(KEY_DRIVER_ID);
                        String driverDate = driver.getString(KEY_DRIVER_NAME);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put(KEY_DRIVER_ID, driverId.toString());
                        map.put(KEY_DRIVER_NAME, driverDate);
                        driverList.add(map);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
