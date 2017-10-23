package com.example.android.robovitics.ClubProject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.robovitics.R;

import java.util.List;

/**
 * Created by Harshit Maheshwari on 23-10-2017.
 */

public class NewProjectAdapter extends ArrayAdapter<NewProjectObject> {

    public NewProjectAdapter(@NonNull Context context, @NonNull List<NewProjectObject> objects) {
        super(context, 0, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;

        if(listItem == null){
            listItem = LayoutInflater.from(getContext()).inflate(R.layout.list_item_proposed_project,parent,false);
        }

        NewProjectObject object = getItem(position);

        TextView projectName = (TextView)listItem.findViewById(R.id.proposed_project_project_name);
        projectName.setText(object.getProjectName());

        ImageView plusOne = (ImageView)listItem.findViewById(R.id.proposed_project_plus_one);

        ImageView comment = (ImageView)listItem.findViewById(R.id.proposed_project_comment);

        return listItem;

    }
}
