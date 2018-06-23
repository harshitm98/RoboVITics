package com.example.android.robovitics.Attendance;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

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
        final AttendanceDetailsObject object = getItem(position);
        TextView type = listItem.findViewById(R.id.list_type);
        type.setText(object.getType());

        TextView dates = listItem.findViewById(R.id.list_date);
        dates.setText(object.getDate());

        TextView attendees = listItem.findViewById(R.id.list_attendees);
        attendees.setText(object.getAttendees());

        TextView absentees = listItem.findViewById(R.id.list_absentees);
        absentees.setText(object.getAbsentees());

        if(object.getType().equals("Lab")){
            LinearLayout layout = listItem.findViewById(R.id.list_reason_layout);
            layout.setVisibility(View.GONE);
            layout = listItem.findViewById(R.id.list_time_layout);
            layout.setVisibility(View.GONE);
        }
        else{
            TextView reason = listItem.findViewById(R.id.list_meeting_reason);
            reason.setText(object.getReason());

            TextView time = listItem.findViewById(R.id.list_time);
            time.setText(object.getTime());
        }

        CardView cardView = listItem.findViewById(R.id.card_view);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AttendanceDetailsActivity.class);
                intent.putExtra("type", object.getType());
                intent.putExtra("time", object.getTime());
                intent.putExtra("date", object.getDate());
                intent.putExtra("attended", object.getAttended());
                intent.putExtra("not_attended", object.getNotAttended());
                context.startActivity(intent);
            }
        });

        return listItem;
    }
}

