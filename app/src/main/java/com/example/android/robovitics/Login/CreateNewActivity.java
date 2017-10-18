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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.android.robovitics.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateNewActivity extends AppCompatActivity {

    private String TAG = "CreateNewActivity";

    private EditText mFullName,mEmailId, mPassword, mRePassword, mRegitrationNumber;
    private Button newAccount;
    private ProgressBar progressBar;
    private LinearLayout layout;

    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private String UID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new);

        mFullName = (EditText)findViewById(R.id.full_name);
        mRegitrationNumber = (EditText)findViewById(R.id.create_new_registration_number);
        mEmailId = (EditText)findViewById(R.id.email_id_new_account);
        mPassword = (EditText)findViewById(R.id.password_new_account);
        mRePassword = (EditText)findViewById(R.id.re_password_new_account);
        newAccount = (Button)findViewById(R.id.create_new_account_fab);
        progressBar = (ProgressBar)findViewById(R.id.progress_bar);
        layout = (LinearLayout)findViewById(R.id.create_new_linear_layout);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("pending_member");

        newAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(verify()){
                    progressBar.setVisibility(View.VISIBLE);
                    layout.setVisibility(View.GONE);
                    createNewAccount(view);
                }
            }
        });
        onChangingListener();



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
                            Toast.makeText(CreateNewActivity.this,"New account created successfully!",Toast.LENGTH_SHORT).show();
                            UID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            databaseReference.child(UID).child("reg").setValue(mRegitrationNumber.getText().toString());
                            databaseReference.child(UID).child("name").setValue(mFullName.getText().toString());
                            databaseReference.child(UID).child("verify").setValue(0);
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(CreateNewActivity.this, "Failure! Try again later",
                                    Toast.LENGTH_SHORT).show();
                            layout.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }
    public void onChangingListener(){

        mRegitrationNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(mRegitrationNumber.length() != 9){
                    mRegitrationNumber.setError("Invalid registration number");
                }

                Pattern pattern = Pattern.compile("(1[1-7]([BM][A-Z]{2}[0-9]{4}))");
                Matcher matcher = pattern.matcher(mRegitrationNumber.getText().toString());
                if(matcher.find()){
                    String found = matcher.group();
                    Log.i(TAG,found);
                }
                else{
                    mRegitrationNumber.setError("Invalid registration number");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {            }
        });

        mPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(mPassword.length()  == 0){
                    mPassword.setError("This field cannot be empty!");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        mRePassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(mRePassword.getText().toString().equals(mPassword.getText().toString())){
                    mRePassword.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_check_circle,0);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {            }
        });
    }

    public boolean verify(){
        int flag = 0;
        if(mPassword.length() == 0){
            flag = 1;
        }
        else if(!mRePassword.getText().toString().equals(mPassword.getText().toString())){
            flag = 1;
        }
        else if(mEmailId.length() == 0){
            flag = 1;
        }
        else if(mFullName.length() == 0){
            flag = 1;
        }
        else if(mRegitrationNumber.length() == 0){
            flag = 1;
        }
        if(flag == 1){
            Toast.makeText(CreateNewActivity.this, "Invalid input!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }





}
