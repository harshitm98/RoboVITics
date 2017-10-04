package com.example.android.robovitics.Login;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.robovitics.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CreateNewActivity extends AppCompatActivity {

    private String TAG = "CreateNewActivity";

    private EditText mEmailId, mPassword, mCheckPassword, names, registrationNumber, roomNumber,phoneNumber, skills;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new);

        mEmailId = (EditText)findViewById(R.id.email_id_new_account);
        mPassword = (EditText)findViewById(R.id.password_new_account);
        mCheckPassword = (EditText)findViewById(R.id.password_retype);
        names = (EditText)findViewById(R.id.name);
        registrationNumber = (EditText)findViewById(R.id.registration_number);
        roomNumber = (EditText)findViewById(R.id.room_number);
        phoneNumber = (EditText)findViewById(R.id.phone_number);
        skills = (EditText)findViewById(R.id.skills);

        mAuth = FirebaseAuth.getInstance();

//        //newAccount.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                createNewAccount(view);
//            }
//        });
        detailsChecker();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.create_account,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.check){

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void createNewAccount(final View view){


        mAuth.createUserWithEmailAndPassword(mEmailId.getText().toString().trim(), mPassword.getText().toString().trim())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            //Toast.makeText(CreateNewActivity.this, "Successfully created new account.",Toast.LENGTH_SHORT).show();
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            user.sendEmailVerification()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d(TAG, "Email sent.");
                                                Snackbar.make(view,"Verification mail sent",Snackbar.LENGTH_SHORT).setAction("Action",null).show();
                                            }
                                        }
                                    });

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(CreateNewActivity.this, "Login Failed!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void detailsChecker(){


        final Drawable icon = getResources().getDrawable(R.drawable.ic_check_circle_black_24dp);
        icon.setBounds(0,0,icon.getIntrinsicWidth(),icon.getIntrinsicHeight());



        mPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(mPassword.getText().length() == 0){
                    mPassword.setError("Password cannot be empty");
                }
                if(mPassword.getText().length()!=0){
                    //mPassword.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_check_circle_black_24dp,0);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mEmailId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(mEmailId.length() == 0){
                    mEmailId.setError("Email cannot be empty");
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mCheckPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!mCheckPassword.getText().toString().equals(mPassword.getText().toString())) {
                    mCheckPassword.setError("Passwords don't match");
                }
                else{
                    mCheckPassword.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_check_circle_black_24dp,0);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {            }
        });

        names.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(names.length() == 0){
                    names.setError("This field cannot be empty");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        roomNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(roomNumber.length() == 0){
                    roomNumber.setError("This field cannot be empty");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        phoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(phoneNumber.length() == 10){
                    phoneNumber.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_check_circle_black_24dp,0);
                }
                else{
                    phoneNumber.setError("Incorrect phone number");
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {            }
        });

        skills.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(skills.length() == 0){
                    skills.setError("This field cannot be empty");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        registrationNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String reg = registrationNumber.getText().toString().trim();
                int z=1;
                if(reg.length() == 9){
                    int k;
                    z=1;
                    for(int r=5;r<9;r++) {
                        k = (int)reg.charAt(r);
                        if (k < 48 || k > 57) {
                            z=0;
                        }
                    }
                    for(int r=3;r<5;r++){
                        k = (int)reg.charAt(r);
                        if(k<65 || k>90){
                            z=0;
                        }
                    }
                    if(z==0) registrationNumber.setError("Incorrect registration number");
                }
                if(registrationNumber.getText().toString().trim().length()!=9){
                    registrationNumber.setError("Incorrect registration number");
                    z=0;
                }
                else if(reg.length()==9 && !(reg.charAt(0) == '1' && (reg.charAt(1) == '3' || reg.charAt(1) == '4' || reg.charAt(1) == '5' || reg.charAt(1) == '6' || reg.charAt(1) == '7'))){
                    registrationNumber.setError("Incorrect registration number");
                    z=0;
                }
                else if(reg.length() == 9 && !(reg.charAt(2) == 'B' || reg.charAt(2) == 'M')){
                    registrationNumber.setError("Incorrect registration number");
                    z=0;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    public void afterAuth(){
        final String email_id, pass, check_pass, phone_number, room_number, skill, registration_number;

        pass = mPassword.getText().toString();
        check_pass = mCheckPassword.getText().toString();
        email_id = mEmailId.getText().toString();
        phone_number = phoneNumber.getText().toString();
        room_number = roomNumber.getText().toString();
        skill = skills.getText().toString();
        registration_number = registrationNumber.getText().toString();

        String uid = mAuth.getCurrentUser().getUid();
    }

}
