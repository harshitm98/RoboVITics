package com.example.android.robovitics.Login;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

    private EditText mEmailId, mPassword;
    private Button newAccount;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new);

        mEmailId = (EditText)findViewById(R.id.email_id_new_account);
        mPassword = (EditText)findViewById(R.id.password_new_account);
        newAccount = (Button)findViewById(R.id.create_new_account_fab);

        mAuth = FirebaseAuth.getInstance();

        newAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewAccount(view);
            }
        });

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

}
