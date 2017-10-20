package com.example.android.robovitics.ClubMembers;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.robovitics.R;

public class ProfileActivity extends AppCompatActivity {

    private String sUID,sName,sReg,sPhone,sRoom,sSkills,sEmail;
    private TextView eName, eReg, ePhone, eRoom, eSkills, eEmail;
    private FloatingActionButton callingButton, mailingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        sUID = getIntent().getExtras().getString("uid");
        sName = getIntent().getExtras().getString("name");
        sReg = getIntent().getExtras().getString("reg");
        sPhone = getIntent().getExtras().getString("phone_number");
        sRoom = getIntent().getExtras().getString("room_number");
        sSkills = getIntent().getExtras().getString("skills");
        sEmail = getIntent().getExtras().getString("email");

        eName = (TextView)findViewById(R.id.profile_name);
        eReg = (TextView)findViewById(R.id.profile_reg);
        ePhone = (TextView) findViewById(R.id.profile_phone);
        eRoom = (TextView) findViewById(R.id.profile_room);
        eSkills = (TextView)findViewById(R.id.profile_skills);
        eEmail = (TextView)findViewById(R.id.profile_email);

        callingButton = (FloatingActionButton)findViewById(R.id.calling);
        mailingButton = (FloatingActionButton)findViewById(R.id.mailing);

        eName.setText(sName);
        eReg.setText(sReg);
        ePhone.setText(sPhone);
        eRoom.setText(sRoom);
        eSkills.setText(sSkills);
        eEmail.setText(sEmail);

        callingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeCall();
            }
        });

        mailingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMail();
            }
        });
    }

    private void makeCall(){
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", sPhone, null));
        startActivity(intent);
    }

    private void sendMail(){
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto",sEmail, null));
        startActivity(Intent.createChooser(intent, "Send Email"));
    }
}
