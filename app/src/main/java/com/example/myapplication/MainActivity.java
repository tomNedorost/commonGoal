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
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";
    private static final String KEY_DRIVER_ID = "id";
    private static final String KEY_LOC = "loc";
    private static final String KEY_VIA = "via";
    private static final String KEY_SEATS = "seats";
    private static final String KEY_DRIVER_DATE = "date";
    private static final String BASE_URL = "http://172.16.29.109:80/drivers/";
    private ArrayList<HashMap<String, String>> driverList;

    private TextView mTextMessage;
    public ListView listView, ridesList;
    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String driverDate;
    private String loc;
    private String seats;
    private String via;
    
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
         ArrayList<String> depTime = new ArrayList<String>();
         depTime.add("14:30");
         depTime.add("16:00");
         ArrayList<String> depPlace = new ArrayList<String>();
         depPlace.add("Neutraubling");
         depPlace.add("Pentling");
         ridesList = (ListView) findViewById(R.id.listview);
         listView.setAdapter(new rideAdapter(this, depTime, depPlace));
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
                        String driverDate = driver.getString(KEY_DRIVER_DATE);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put(KEY_DRIVER_ID, driverId.toString());
                        map.put(KEY_DRIVER_DATE, driverDate);
                        driverList.add(map);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    /**
     * Checks whether all files are filled. If so then calls AddMovieAsyncTask.
     * Otherwise displays Toast message informing one or more fields left empty
     */
   /* private void addMovie() {
        // TODO: vor .get die jeweiligen infos aus der activity holen
        if (!STRING_EMPTY.equals(.getText().toString()) &&
                !STRING_EMPTY.equals(.getText().toString()) &&
                !STRING_EMPTY.equals(.getText().toString()) &&
                !STRING_EMPTY.equals(.getText().toString())) {

            driverDate = .getText().toString();
            via = .getText().toString();
            loc = .getText().toString();
            seats = .getText().toString();
            new AddMovieAsyncTask().execute();
        } else {
            Toast.makeText(getApplicationContext(),
                    "One or more fields left empty!",
                    Toast.LENGTH_LONG).show();
        }
    }
    /**
     * AsyncTask for adding a movie

    private class AddMovieAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Populating request parameters
            httpParams.put(KEY_DRIVER_DATE, date);
            httpParams.put(KEY_LOC, genre);
            httpParams.put(KEY_VIA, year);
            httpParams.put(KEY_SEATS, rating);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "add_movie.php", "POST", httpParams);
            try {
                success = jsonObject.getInt(KEY_SUCCESS);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            pDialog.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {
                    if (success == 1) {
                        //Display success message
                        Toast.makeText(AddMovieActivity.this,
                                "Movie Added", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        //Finish ths activity and go back to listing activity
                        finish();

                    } else {
                        Toast.makeText(AddMovieActivity.this,
                                "Some error occurred while adding movie",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
        */
}
