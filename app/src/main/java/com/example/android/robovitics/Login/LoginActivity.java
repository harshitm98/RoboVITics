package com.example.android.robovitics.Login;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.robovitics.MainActivity;
import com.example.android.robovitics.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends Activity {

    private final String TAG = "LoginActivity";

    private EditText emailId, password;
    private TextView newAccountText, forgotPasswordText, clubName;
    private Button signInButton;
    private ProgressBar progressBar;
    private LinearLayout layout;

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ChildEventListener mChildEventListener;

    private Typeface regularFont;

    private String sVerify;
    public static String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        regularFont = Typeface.createFromAsset(getAssets(), "quicksandreg.ttf");
        signInButton = findViewById(R.id.sign_in);
        emailId = findViewById(R.id.email_id);

        password = findViewById(R.id.password);

        newAccountText = findViewById(R.id.text_new_user);
        newAccountText.setTypeface(regularFont);
        newAccountText.setPaintFlags(newAccountText.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        forgotPasswordText = findViewById(R.id.text_forgot_password);
        forgotPasswordText.setTypeface(regularFont);
        forgotPasswordText.setPaintFlags(newAccountText.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        progressBar = findViewById(R.id.login_progress);
        layout = findViewById(R.id.login_linear_layout);

        clubName = findViewById(R.id.club_name);
        clubName.setTypeface(regularFont);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("pending_member");
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(emailId.length() != 0 && password.length()!= 0){
                    login();
                }
                else{
                    Toast.makeText(LoginActivity.this,"Fields cannot be empty!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        newAccountText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this,CreateNewActivity.class);
                startActivity(i);
            }
        });

        forgotPasswordText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this,ForgotPasswordActivity.class);
                startActivity(i);
            }
        });


//        editorDelete();

    }

    public void login() {
        final String mEmail, mPassword;

        mEmail = emailId.getText().toString().trim();
        mPassword = password.getText().toString().trim();

        layout.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(mEmail, mPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            user = mAuth.getCurrentUser();
                            if(user!=null) {
                                listener();
                            }
                            else{
                                Toast.makeText(LoginActivity.this,"Problem logging in. Try again later",Toast.LENGTH_LONG).show();
                                layout.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                            }


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            layout.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                        }

                    }
                });
    }


    public void listener() {

        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.i(TAG, dataSnapshot.toString());
                String vVerify;
                vVerify = dataSnapshot.child("uid").getValue().toString();
                if (vVerify.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                    sVerify = dataSnapshot.child("verify").getValue().toString();
                    userName = dataSnapshot.child("name").getValue().toString();
                    if (sVerify.equals("0")) {
                        Toast.makeText(LoginActivity.this, "Your account hasn't been verified.", Toast.LENGTH_LONG).show();
                        layout.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                    } else if (sVerify.equals("1")) {
                        String details = dataSnapshot.child("details").getValue().toString();
                        if (details.equals("0")) {
                            Intent i = new Intent(LoginActivity.this, DetailsActivity.class);
                            startActivity(i);
                            finish();
                        } else if (details.equals("1")) {
                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(i);
                            finish();
                        }

                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.i(TAG, dataSnapshot.toString());
                String vVerify;
                vVerify = dataSnapshot.child("uid").getValue().toString();
                if (vVerify.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                    sVerify = dataSnapshot.child("verify").getValue().toString();
                    if (sVerify.equals("1")) {
                        String details = dataSnapshot.child("details").getValue().toString();
                        if (details.equals("0")) {
                            Intent i = new Intent(LoginActivity.this, DetailsActivity.class);
                            startActivity(i);
                            finish();
                        } else if (details.equals("1")) {
                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(i);
                            finish();
                        }
                    }
                }
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
        };
        databaseReference.addChildEventListener(mChildEventListener);
    }

}
