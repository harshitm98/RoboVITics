package com.example.android.robovitics.FreeSlots;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.robovitics.R;

import java.util.List;

public class FreeSlotsAdapter extends ArrayAdapter<FreeSlotsObject> {

    private Context context;

    public FreeSlotsAdapter(@NonNull Context context,  @NonNull List<FreeSlotsObject> objects) {
        super(context, 0, 0, objects);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;

        if(listItem == null){
            listItem = LayoutInflater.from(getContext()).inflate(R.layout.list_item_free_slots, parent, false);
        }
        final FreeSlotsObject object = getItem(position);
        TextView timeText = listItem.findViewById(R.id.time_free_slots);
        timeText.setText(object.getTimes());

        TextView peepsText = listItem.findViewById(R.id.people_free_slots);
        peepsText.setText(object.getPeopleFree());



        return listItem;
    }
}
