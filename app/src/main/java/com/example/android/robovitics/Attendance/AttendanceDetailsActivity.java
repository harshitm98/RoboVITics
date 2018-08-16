package com.example.android.robovitics.Attendance;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.TextView;

import com.example.android.robovitics.R;

public class AttendanceDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_attendance_details);
        TextView type, time, date, attended, notAttended;
        type = findViewById(R.id.att_deet_type);
        time = findViewById(R.id.att_deet_time);
        date = findViewById(R.id.att_deet_date);
        attended = findViewById(R.id.att_deet_attend);
        notAttended = findViewById(R.id.att_deet_absent);

        type.setText(getIntent().getExtras().getString("type"));
        time.setText(getIntent().getExtras().getString("time"));
        date.setText(getIntent().getExtras().getString("date"));
        attended.setText(splitter(getIntent().getExtras().getString("attended")));
        notAttended.setText(splitter(getIntent().getExtras().getString("not_attended")));
    }

    private String splitter(String string) {
        String newString = "";
        newString = string.replace(',', '\n');
        return newString;
    }
}
