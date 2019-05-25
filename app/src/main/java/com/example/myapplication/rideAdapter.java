package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class rideAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> rideTime, ridePlace;
    private static LayoutInflater inflater = null;
    private Button requestBtn;

    public rideAdapter(Context context, ArrayList<String> rideTime, ArrayList<String> ridePlace) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.rideTime = rideTime;
        this.ridePlace = ridePlace;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return ridePlace.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return ridePlace.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.ride_elements, null);
        TextView rideTimeText = (TextView) vi.findViewById(R.id.rideTime);
        TextView ridePlaceText = (TextView) vi.findViewById(R.id.ridePlace);
        rideTimeText.setText(rideTime.get(position));
        ridePlaceText.setText(ridePlace.get(position));
        requestBtn = vi.findViewById(R.id.requestButton);
        requestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, request.class);
                intent.putExtra("time", rideTime.get(position));
                intent.putExtra("place", ridePlace.get(position));
                context.startActivity(intent);

            }
        });
        return vi;
    }
}
