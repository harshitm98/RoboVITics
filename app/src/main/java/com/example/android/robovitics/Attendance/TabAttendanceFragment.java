package com.example.android.robovitics.Attendance;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.robovitics.ClubMembers.ClubMemberAdapter;
import com.example.android.robovitics.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabAttendanceFragment extends Fragment {

    private ListView listView;

    int position;
    private AttendanceDetailsAdapter labAdapter, meetingAdapter, bothAdapter;
    private ArrayList<AttendanceDetailsObject> attendanceDetailsObjects, labAttendanceObjects, meetingAttendanceObjects;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    public static Fragment getInstance(int position){
        Bundle bundle = new Bundle();
        bundle.putInt("pos", position);
        TabAttendanceFragment tabAttendanceFragment = new TabAttendanceFragment();
        tabAttendanceFragment.setArguments(bundle);
        return tabAttendanceFragment;
    }


    public TabAttendanceFragment() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt("pos");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab_attendance, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView = getView().findViewById(R.id.attendance_list_view);
        attendanceDetailsObjects =   new ArrayList<>();
        labAttendanceObjects =  new ArrayList<>();
        meetingAttendanceObjects =  new ArrayList<>();

        bothAdapter = new AttendanceDetailsAdapter(getContext(), attendanceDetailsObjects);
        labAdapter = new AttendanceDetailsAdapter(getContext(), labAttendanceObjects);
        meetingAdapter = new AttendanceDetailsAdapter(getContext(), meetingAttendanceObjects);

        fillUpTheAdapters();
    }

    private void fillUpTheAdapters(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("meeting");

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                AttendanceDetailsObject object = new AttendanceDetailsObject();
                object.setAbsentees(dataSnapshot.child("absentees").getValue().toString());
                object.setAttended(dataSnapshot.child("attended").getValue().toString());
                object.setAttendees(dataSnapshot.child("attendees").getValue().toString());
                object.setDate(dataSnapshot.child("date").getValue().toString());
                object.setNotAttended(dataSnapshot.child("not_attended").getValue().toString());
                object.setType(dataSnapshot.child("type").getValue().toString());
                if(dataSnapshot.child("type").getValue().toString().equals("Meeting")){
                    object.setReason(dataSnapshot.child("reason").getValue().toString());
                    object.setTime(dataSnapshot.child("time").getValue().toString());
                    meetingAttendanceObjects.add(object);
                }else{
                    labAttendanceObjects.add(object);
                }
                attendanceDetailsObjects.add(object);
                if(position == 0){
                    listView.setAdapter(labAdapter);
                }else if(position == 1) {
                    listView.setAdapter(meetingAdapter);
                }else{
                    listView.setAdapter(bothAdapter);
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
