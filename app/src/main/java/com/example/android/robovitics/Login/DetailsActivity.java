package com.example.android.robovitics.Login;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.robovitics.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DetailsActivity extends AppCompatActivity {

    private EditText phoneNumber, roomNumber, skills;
    private Button submitButton;

    private FirebaseDatabase database;
    private DatabaseReference reference;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    private Typeface boldFont, regularFont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        phoneNumber = findViewById(R.id.phone_number);
        roomNumber = findViewById(R.id.room_number);
        skills = findViewById(R.id.skills);
        submitButton = findViewById(R.id.submit_details);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("pending_member");
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        onChangingListener();
        updateLayout();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitDetails();
            }
        });

    }

    private void onChangingListener(){
        phoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(phoneNumber.length() != 10){
                    phoneNumber.setError("Invalid phone number");
                }
                else{
                    phoneNumber.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_check_circle,0);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        roomNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(roomNumber.length() == 0){
                    roomNumber.setError("This field cannot be empty");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        skills.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(skills.length() == 0){
                    skills.setError("Come on, we all got some skills!");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }

    private void submitDetails(){
        if(phoneNumber.length() == 10 && roomNumber.length() != 0 && skills.length() != 0){
            reference.child(user.getUid()).child("phone_number").setValue("+91" + phoneNumber.getText().toString());
            reference.child(user.getUid()).child("room_number").setValue(roomNumber.getText().toString());
            reference.child(user.getUid()).child("skills").setValue(skills.getText().toString());
            reference.child(user.getUid()).child("details").setValue(1);
            reference.child(user.getUid()).child("attendance_permission").setValue(0);
            Toast.makeText(DetailsActivity.this, "Details submitted.", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(DetailsActivity.this, "One or more fields are empty.", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateLayout(){
        boldFont = Typeface.createFromAsset(getAssets(),"montserrat_bold.ttf");
        regularFont = Typeface.createFromAsset(getAssets(),"montserrat_regular.ttf");
        phoneNumber.setTypeface(regularFont);
        roomNumber.setTypeface(regularFont);
        skills.setTypeface(regularFont);
        submitButton.setTypeface(boldFont);
    }
}
