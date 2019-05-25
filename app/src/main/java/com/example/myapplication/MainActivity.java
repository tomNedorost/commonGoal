package com.example.myapplication;
import android.app.ProgressDialog;
import android.content.Context;
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

import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.snapchat.kit.sdk.SnapLogin;
import com.snapchat.kit.sdk.core.controller.LoginStateController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static java.security.AccessController.getContext;


public class MainActivity extends AppCompatActivity {
    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";
    private static final String KEY_DRIVER_ID = "driver_id";
    private static final String KEY_LOC = "driver_loc";
    private static final String KEY_VIA = "driver_via";
    private static final String KEY_SEATS = "driver_seats";
    private static final String KEY_TIME = "driver_time";
    private static final String KEY_DRIVER_DATE = "driver_date";
    private static final String BASE_URL = "http://172.16.29.109:80/drivers/";
    private ProgressDialog pDialog;
    private ArrayList<HashMap<String, String>> driverList;

    private TextView mTextMessage;
    public ListView listView, ridesList;
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
                    new FetchDriversAsyncTask().execute();
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
                listView.getSelectedItem();
                startActivity(intent);
            }
        });
        /*View mLoginButton = SnapLogin.getButton(this.getApplicationContext(), listView);

        View yourView = findViewById(R.id.listview);
        yourView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SnapLogin.getAuthTokenManager(v.getContext()).startTokenGrant();
            }
        });
        final LoginStateController.OnLoginStateChangedListener mLoginStateChangedListener =
                new LoginStateController.OnLoginStateChangedListener() {
                    @Override
                    public void onLoginSucceeded() {
                        // Here you could update UI to show login success
                    }

                    @Override
                    public void onLoginFailed() {
                        // Here you could update UI to show login failure
                    }

                    @Override
                    public void onLogout() {
                        // Here you could update UI to reflect logged out state
                    }
                };

        // Add the LoginStateChangedListener you’ve defined to receive LoginInState updates
       // SnapLogin.getLoginStateController(getContext()).addOnLoginStateListener(mLoginStateChangedListener);
        //To unsubscribe your `LoginStateChangedListener` from updates, use the following line:
        //SnapLogin.getLoginStateController(this).removeOnLoginStateChangedListener(mLoginStateChangedListener);
        */
    }

    public void setHomeListView() {
        listView.setAdapter(new gamesAdapter(this,
                new String[]{"Sonntag 24.02.2019", "Samstag 09.03.2019", "Montag 18.03.2019", "Sonntag 05.04.2019", "Sonntag 21.04.2019", "Sonntag 05.05.2019",
                        "Sonntag 19.05.2019"},
                new String[]{"Jahn Regensburg", "Jahn Regensburg", "Jahn Regensburg", "Jahn Regensburg", "Jahn Regensburg", "Jahn Regensburg", "Jahn Regensburg"},
                new String[]{"HSV", "MSV Duisburg", "Greuther Fürth", "Bochum", "FC Magdeburg", "FC Erzgebirge", "SV Sandhausen"}
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

    private class FetchDriversAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Loading drivers. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
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
                    driverList = new ArrayList<>();
                    drivers = jsonObject.getJSONArray(KEY_DATA);
                    //Iterate through the response and populate drivers list
                    for (int i = 0; i < drivers.length(); i++) {
                        JSONObject driver = drivers.getJSONObject(i);
                        Integer driverId = driver.getInt(KEY_DRIVER_ID);
                        String driverDate = (String) driver.get(KEY_DRIVER_DATE);
                        String driverSeats = (String) driver.get(KEY_SEATS);
                        String driverVia = (String) driver.get(KEY_VIA);
                        String driverTime = (String) driver.get(KEY_TIME);
                        String driverLoc = (String) driver.get(KEY_LOC);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put(KEY_DRIVER_ID, driverId.toString());
                        map.put(KEY_DRIVER_DATE, driverDate);
                        map.put(KEY_LOC, driverLoc);
                        map.put(KEY_SEATS, driverSeats);
                        map.put(KEY_VIA, driverVia);
                        map.put(KEY_TIME, driverTime);
                        driverList.add(map);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(String result) {
            pDialog.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {
                    for (int i = 0; i < driverList.size(); i++) {
                        Log.i("Driver", String.valueOf(driverList.get(i)));
                    }
                }
            });
        }
    }
}