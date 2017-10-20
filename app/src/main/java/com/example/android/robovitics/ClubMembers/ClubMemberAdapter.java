package com.example.android.robovitics.ClubMembers;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.robovitics.R;

import java.util.List;

/**
 * Created by Harshit Maheshwari on 20-10-2017.
 */

public class ClubMemberAdapter extends ArrayAdapter<ClubMembersObject> {
    public ClubMemberAdapter(@NonNull Context context, @NonNull List<ClubMembersObject> objects) {
        super(context,0,0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitem = convertView;

        if(listitem == null){
            listitem = LayoutInflater.from(getContext()).inflate(R.layout.list_item_club_member,parent,false);
        }

        ClubMembersObject object = getItem(position);

        final TextView name = (TextView)listitem.findViewById(R.id.list_name);
        name.setText(object.getsName());

        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),name.getText(),Toast.LENGTH_SHORT).show();
            }
        });

        /*
        TODO: Insert imageView and display the image
         */


        return listitem;
    }
}
