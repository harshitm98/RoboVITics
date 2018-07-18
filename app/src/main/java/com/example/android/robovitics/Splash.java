package com.example.android.robovitics;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

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
                    Intent intent = new Intent(Splash.this,MainActivity.class);
                    startActivity(intent);
                    finish();
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

}
