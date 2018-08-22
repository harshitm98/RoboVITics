package com.example.android.robovitics;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.robovitics.Login.DetailsActivity;
import com.example.android.robovitics.Login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Splash extends Activity {

    private final int SPLASH_LENGTH = 2000;
    private int verCode;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mAuth = FirebaseAuth.getInstance();

        updatingVerison();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                FirebaseUser user = mAuth.getCurrentUser();

                if(user == null){
                    Intent intent = new Intent(Splash.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    checkingForDetails();
                }
            }
        }, SPLASH_LENGTH);
    }

    public void updatingVerison() {
        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            verCode = pInfo.versionCode;
            TextView appVersion = findViewById(R.id.app_version);
            appVersion.setText("Version " + version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void checkingForDetails(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("pending_member");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String vVerify, sVerify;
                vVerify = dataSnapshot.child("uid").getValue().toString();
                if (vVerify.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                    sVerify = dataSnapshot.child("verify").getValue().toString();
                    if (sVerify.equals("1")) {
                        String details = dataSnapshot.child("details").getValue().toString();
                        if (details.equals("0")) {
                            Intent i = new Intent(Splash.this, DetailsActivity.class);
                            startActivity(i);
                            finish();
                        } else if (details.equals("1")) {
                            Intent i = new Intent(Splash.this, MainActivity.class);
                            startActivity(i);
                            finish();
                        }
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
