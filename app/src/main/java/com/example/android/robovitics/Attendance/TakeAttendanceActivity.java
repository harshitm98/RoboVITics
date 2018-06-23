package com.example.android.robovitics.Attendance;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.android.robovitics.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class TakeAttendanceActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private static final String TAG = "TakeAttendanceActivity";

    private Spinner spinner;
    private LinearLayout linearLayout;

    Button btnDatePicker, btnTimePicker;
    EditText txtDate, txtTime;
    private FloatingActionButton floatingActionButton;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private TextView attendees;

    private IntentIntegrator qrScan;

    private List<String> arrayList, member;
    private String type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_take_attendance);
        settingupmember();
        spinnerSetup();
        timeSetup();
        scanSetup();
        uploadSetup();
    }

    private void spinnerSetup(){
        spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.attendance_option,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        spinner.setSelection(0);
        linearLayout = findViewById(R.id.meeting_linear_layout);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(i == 0){
            linearLayout.setVisibility(View.GONE);
            type = "Lab";
        }
        else{
            linearLayout.setVisibility(View.VISIBLE);
            type = "Meeting";
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    private void timeSetup(){
        btnDatePicker = findViewById(R.id.date_button);
        btnTimePicker = findViewById(R.id.time_button);
        txtDate = findViewById(R.id.linear_layout_date);
        txtTime = findViewById(R.id.linear_layout_time);
        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(TakeAttendanceActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        btnTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(TakeAttendanceActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                txtTime.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });
    }

    private void scanSetup(){
        floatingActionButton = findViewById(R.id.scan_fab);
        attendees = findViewById(R.id.attendance_attendees);
        arrayList = new ArrayList<>();
        qrScan = new IntentIntegrator(TakeAttendanceActivity.this);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qrScan.initiateScan();
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alert = new AlertDialog.Builder(TakeAttendanceActivity.this);
        alert.setTitle("Unsaved changes");
        alert.setMessage("There may be unsaved changes");

        alert.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                TakeAttendanceActivity.super.onBackPressed();
            }
        });
        alert.setNegativeButton("Cancel", null);
        alert.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(TakeAttendanceActivity.this);
                alertDialog.setTitle("You just scanned");
                alertDialog.setMessage(result.getContents());
                alertDialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        arrayList.add(result.getContents());
                        attendees.append("\n" + result.getContents());
                    }
                });
                alertDialog.setNegativeButton("Scan again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        qrScan.initiateScan();
                    }
                });
                alertDialog.show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    private void uploadSetup(){
        FloatingActionButton uploadAction = findViewById(R.id.upload_fab);
        uploadAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(TakeAttendanceActivity.this);
                alertDialog.setTitle("Are you sure?");
                alertDialog.setMessage("Are you sure you want to upload the attendance");
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        uploadTheAttendance();
                    }
                });
                alertDialog.setNegativeButton("No", null);
                alertDialog.show();
            }
        });
    }
    private void uploadTheAttendance(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();

        List<String> notAttended = new ArrayList<>();
        for(int i=0; i<member.size(); i++){
            int flag = 0;
            for(int j=0; j<arrayList.size(); j++){
                if(member.get(i).equals(arrayList.get(j))){
                    flag = 1;
                    break;
                }
            }
            if(flag == 0){
                notAttended.add(member.get(i));
            }
        }
        databaseReference.child("meeting").child(txtDate.getText().toString() + "_" + type).child("date").setValue(txtDate.getText().toString());
        databaseReference.child("meeting").child(txtDate.getText().toString() + "_" + type).child("type").setValue(type);
        if(type.equals("Meeting")){
            databaseReference.child("meeting").child(txtDate.getText().toString() + "_" + type).child("time").setValue(txtTime.getText().toString());
        }
        String attended = "";
        for(int i=0; i<arrayList.size(); i++){
            attended = attended.concat(arrayList.get(i) + ",");
        }
        String notAttend = "";
        for(int i=0; i<notAttended.size(); i++){
            notAttend = notAttend.concat(notAttended.get(i) + ",");
        }
        EditText reason = findViewById(R.id.attendance_reason);
        databaseReference.child("meeting").child(txtDate.getText().toString() + "_" + type).child("attended").setValue(attended);
        databaseReference.child("meeting").child(txtDate.getText().toString() + "_" + type).child("not_attended").setValue(notAttend);
        databaseReference.child("meeting").child(txtDate.getText().toString() + "_" + type).child("reason").setValue(reason.getText().toString());
        databaseReference.child("meeting").child(txtDate.getText().toString() + "_" + type).child("absentees").setValue(notAttended.size());
        databaseReference.child("meeting").child(txtDate.getText().toString() + "_" + type).child("attendees").setValue(arrayList.size());
        Toast.makeText(this, "Attendance uploaded!", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void settingupmember(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        member = new ArrayList<>();
        DatabaseReference profileReference = firebaseDatabase.getReference().child("pending_member");
        profileReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.child("reg").getValue().toString().startsWith("17")){
                    member.add(dataSnapshot.child("name").getValue().toString());
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
