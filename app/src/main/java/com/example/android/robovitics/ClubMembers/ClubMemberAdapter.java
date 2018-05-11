package com.example.android.robovitics.ClubMembers;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.robovitics.R;

import java.util.List;

/**
 * Created by Harshit Maheshwari on 20-10-2017.
 */

public class ClubMemberAdapter extends ArrayAdapter<ClubMembersObject>{

    private static final String TAG = "ClubMemberAdapter";

    private Context context;

    public ClubMemberAdapter(@NonNull Context context, @NonNull List<ClubMembersObject> objects) {
        super(context,0,0, objects);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitem = convertView;


        if(listitem == null){
            listitem = LayoutInflater.from(getContext()).inflate(R.layout.list_item_club_member,parent,false);
        }

        final ClubMembersObject object = getItem(position);

        final TextView name = (TextView)listitem.findViewById(R.id.list_name);
        name.setText(object.getsName());

        final TextView registration = listitem.findViewById(R.id.reg_number_list);
        registration.setText(object.getRegistrationNumber());

        final TextView room = listitem.findViewById(R.id.room_number_list);
        room.setText(object.getRoomNumber());

        final TextView phone = listitem.findViewById(R.id.list_phone);
        phone.setText(object.getPhoneNumber());

        final Button callButton = listitem.findViewById(R.id.list_call_button);
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", object.getPhoneNumber(), null));
                context.startActivity(intent);
            }
        });

        final CardView cardView = listitem.findViewById(R.id.card_view);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),ProfileActivity.class);
                intent.putExtra("name",object.getsName());
                intent.putExtra("reg",object.getRegistrationNumber());
                intent.putExtra("phone_number",object.getPhoneNumber());
                intent.putExtra("room_number",object.getRoomNumber());
                intent.putExtra("skills",object.getSkills());
                intent.putExtra("email",object.getEmailId());
                intent.putExtra("uid",object.getUID());
                context.startActivity(intent);
            }
        });
        return listitem;
    }
}
