package com.example.android.robovitics;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.android.robovitics.Login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Splash extends Activity {

    private final int SPLASH_LENGTH = 3000;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mAuth = FirebaseAuth.getInstance();

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
                    if(user.isEmailVerified()){
                        Intent intent = new Intent(Splash.this,MainActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Intent intent = new Intent(Splash.this, LoginActivity.class);
                        startActivity(intent);
                        Toast.makeText(Splash.this,"Your email isn't verified", Toast.LENGTH_SHORT);
                    }
                    finish();
                }


            }
        }, SPLASH_LENGTH);
    }
}
