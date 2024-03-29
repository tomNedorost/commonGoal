package com.example.myapplication;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

public class NewRide extends AppCompatActivity {

    private Button timeButton;
    private TimePickerDialog timePickerDialog;
    private Calendar calendar = Calendar.getInstance();
    private LinearLayout parentLinearLayout;
    private FloatingActionButton submitButton;
    public String setTime;
    public EditText departure;
    public Button time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_ride);
        parentLinearLayout = findViewById(R.id.new_ride);
        String currentDateTimeString = DateUtils.formatDateTime(NewRide.this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME);
        timeButton = findViewById(R.id.time_set);
        timeButton.setText(currentDateTimeString);
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog = new TimePickerDialog(NewRide.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar timeCalendar = Calendar.getInstance();
                        timeCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        timeCalendar.set(Calendar.MINUTE, minute);

                        setTime = DateUtils.formatDateTime(NewRide.this, timeCalendar.getTimeInMillis(), DateUtils.FORMAT_SHOW_TIME);
                        timeButton.setText(setTime);

                    }
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), DateFormat.is24HourFormat(NewRide.this));
                timePickerDialog.show();
            }

        });
        submitButton = findViewById(R.id.submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                departure = findViewById(R.id.departure_place);
                String departurePlace = departure.getText().toString();
                time = findViewById(R.id.time_set);
                String departureTime = time.getText().toString();
                setResult(NewRide.RESULT_OK,
                        new Intent().putExtra("departure", departurePlace).putExtra("time", departureTime));
                finish();
            }
        });


    }

    public void onAddField(View v) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.stop_field, null);
        // Add the new row before the add field button.
        parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount() - 2);
    }

    public void onDelete(View v) {
        parentLinearLayout.removeView((View) v.getParent());
    }
}
