package com.kala.kala;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class ReminderNewFragment extends Fragment implements OnClickListener {

    public ReminderNewFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_new_reminder, null);
        SharedPreferences pref = MainActivity.sharedPreferences;

        Button addPhotos = (Button)v.findViewById(R.id.reminderNewAddPhotosButton);
        Button addMap = (Button)v.findViewById(R.id.reminderNewAddMapButton);
        TextView participants = (TextView)v.findViewById(R.id.reminderNewParticipants);
        TextView date = (TextView)v.findViewById(R.id.reminderNewDate);
        TextView time = (TextView)v.findViewById(R.id.reminderNewTime);

        addPhotos.setOnClickListener(this);
        addMap.setOnClickListener(this);
        participants.setOnClickListener(this);
        date.setOnClickListener(this);
        time.setOnClickListener(this);

        // Set default participant
        String defaultParticipants = pref.getString("name", "no default name found");
        participants.setText(defaultParticipants);

        // Set default Date
        Date d = new Date();
        String stringDate = java.text.DateFormat.getDateInstance().format(d);
        Log.d("Date : ", stringDate);
        date.setText(stringDate);

        // Set default Time
        Calendar c = Calendar.getInstance();
        String timeFormat = "hh:mm";
        time.setText(DateFormat.format(timeFormat, Calendar.getInstance().getTime()));

        return v;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.reminderNewAddPhotosButton :
                Toast.makeText(getActivity(), "Add Photos", Toast.LENGTH_SHORT).show();
                break;
            case R.id.reminderNewAddMapButton :
                Toast.makeText(getActivity(), "Add Map", Toast.LENGTH_SHORT).show();
                break;
            case R.id.reminderNewParticipants :
                Toast.makeText(getActivity(), "Select Participants", Toast.LENGTH_SHORT).show();
                break;
            case R.id.reminderNewDate :
                Toast.makeText(getActivity(), "Select Date", Toast.LENGTH_SHORT).show();
                break;
            case R.id.reminderNewTime :
                Toast.makeText(getActivity(), "Select Time", Toast.LENGTH_SHORT).show();
                break;
            // similarly for other buttons
        }
    }
}
