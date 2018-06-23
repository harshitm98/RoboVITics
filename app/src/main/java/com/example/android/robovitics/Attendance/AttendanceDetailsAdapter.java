package com.example.android.robovitics.Attendance;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.android.robovitics.R;

import java.util.List;

public class AttendanceDetailsAdapter extends ArrayAdapter<AttendanceDetailsObject>{

    private static final String TAG = "AttendanceDetailsAdapter";

    private Context context;

    public AttendanceDetailsAdapter(@NonNull Context context, @NonNull List<AttendanceDetailsObject> objects) {
        super(context, 0, 0, objects);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;

        if(listItem == null){
            listItem = LayoutInflater.from(getContext()).inflate(R.layout.list_item_attendance_details, parent, false);
        }

        return super.getView(position, convertView, parent);
    }
}
