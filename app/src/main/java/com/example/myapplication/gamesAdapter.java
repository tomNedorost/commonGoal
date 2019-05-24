package com.example.myapplication;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class gamesAdapter extends BaseAdapter {
    Context context;
    String[] time, playerOne, playerTwo;
    private static LayoutInflater inflater = null;

    public gamesAdapter(Context context, String[] gameTime, String[] playerOne, String[] playerTwo ) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.time = gameTime;
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return time.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return time[position];
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.game, null);
        TextView gameTimeText = (TextView) vi.findViewById(R.id.gameTime);
        TextView playerOneText = (TextView) vi.findViewById(R.id.playerOne);
        TextView playerTwoText = (TextView) vi.findViewById(R.id.playerTwo);
        gameTimeText.setText(time[position]);
        playerOneText.setText(playerOne[position]);
        playerTwoText.setText(playerTwo[position]);
        return vi;
    }
}
