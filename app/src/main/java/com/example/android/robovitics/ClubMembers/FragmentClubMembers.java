package com.example.android.robovitics.ClubMembers;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.robovitics.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

/**
 * Created by Harshit Maheshwari on 19-10-2017.
 */

public class FragmentClubMembers extends Fragment {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ChildEventListener childEventListener;
    private Query query;

    private ArrayList<ClubMembersObject> clubMembersObjects;
    private ClubMemberAdapter clubMemberAdapter;
    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_club_member,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Club Members");

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("pending_member");
        query = firebaseDatabase.getReference("pending_member").orderByChild("name");

        listView = (ListView) getView().findViewById(R.id.list_club_members);
        clubMembersObjects = new ArrayList<>();
        clubMemberAdapter = new ClubMemberAdapter(getContext(),clubMembersObjects);

        addingMembersInClubMembersObject();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(),ProfileActivity.class);
                ClubMembersObject object = (ClubMembersObject)listView.getAdapter().getItem(i);
                intent.putExtra("name",object.getsName());
                intent.putExtra("reg",object.getRegistrationNumber());
                intent.putExtra("phone_number",object.getPhoneNumber());
                intent.putExtra("room_number",object.getRoomNumber());
                intent.putExtra("skills",object.getSkills());
                intent.putExtra("email",object.getEmailId());
                intent.putExtra("uid",object.getUID());
                startActivity(intent);
            }
        });

    }

    public void  addingMembersInClubMembersObject(){
        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ClubMembersObject object = new ClubMembersObject();
                if(dataSnapshot.child("verify").getValue().toString().equals("1")){
                    object.setsName(dataSnapshot.child("name").getValue().toString());
                    object.setRegistrationNumber(dataSnapshot.child("reg").getValue().toString());
                    object.setPhoneNumber(dataSnapshot.child("phone_number").getValue().toString());
                    object.setRoomNumber(dataSnapshot.child("room_number").getValue().toString());
                    object.setSkills(dataSnapshot.child("skills").getValue().toString());
                    object.setEmailId(dataSnapshot.child("email").getValue().toString());
                    object.setUID(dataSnapshot.child("uid").getValue().toString());
                    clubMembersObjects.add(object);
                    listView.setAdapter(clubMemberAdapter);
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
        };
        //databaseReference.addChildEventListener(childEventListener);
        query.addChildEventListener(childEventListener);
    }
}
