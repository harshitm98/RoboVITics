package com.example.android.robovitics.ClubProject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.robovitics.ClubMembers.ClubMembersObject;
import com.example.android.robovitics.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import static com.example.android.robovitics.R.color.colorAccent;

/**
 * Created by Harshit Maheshwari on 23-10-2017.
 */

public class NewProjectAdapter extends ArrayAdapter<NewProjectObject> {

    // TODO: In version 2 of this app include the feature to unlike the proposed ideas.

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;
    private ChildEventListener childEventListener;

    public NewProjectAdapter(@NonNull Context context, @NonNull List<NewProjectObject> objects) {
        super(context, 0, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;

        if(listItem == null){
            listItem = LayoutInflater.from(getContext()).inflate(R.layout.list_item_proposed_project,parent,false);
        }

        final NewProjectObject object = getItem(position);

        final TextView projectName = (TextView)listItem.findViewById(R.id.proposed_project_project_name);
        projectName.setText(object.getProjectName());

        final ImageView plusOne = (ImageView)listItem.findViewById(R.id.proposed_project_plus_one);
        checkExistingPlusOne(object,plusOne);

        plusOne.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                plusOneTheThing(object);
                Toast.makeText(getContext(),"+1ed " + projectName.getText().toString(),Toast.LENGTH_SHORT).show();
                DrawableCompat.setTint(plusOne.getDrawable(), ContextCompat.getColor(getContext(), R.color.colorAccent));
            }
        });

        ImageView comment = (ImageView)listItem.findViewById(R.id.proposed_project_comment);

        return listItem;
    }

    private void plusOneTheThing(NewProjectObject object){
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("projects").child("new_proposed_projects");
        auth = FirebaseAuth.getInstance();
        final String projectName = object.getProjectName();
        final String UID = auth.getCurrentUser().getUid();
        final String userName = auth.getCurrentUser().getDisplayName();
        final ClubMembersObject clubMembersObject = new ClubMembersObject();
        clubMembersObject.setsName(userName);
        clubMembersObject.setUID(UID);
        clubMembersObject.setRegistrationNumber(null);
        clubMembersObject.setSkills(null);
        clubMembersObject.setRoomNumber(null);
        clubMembersObject.setPhoneNumber(null);
        clubMembersObject.setEmailId(null);

        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.child("project_name").getValue().toString().equals(projectName)){
                    int count;
                    int projectNumber;
                    count = dataSnapshot.child("number_of_plus_one").getValue(Integer.class);
                    projectNumber = dataSnapshot.child("project_number").getValue(Integer.class);
                    if(count == 0){
                        databaseReference.child("project_" + projectNumber).child("number_of_plus_one").setValue(1);
                        databaseReference.child("project_" + projectNumber).child("plus_one_by").push().setValue(clubMembersObject);
                        Toast.makeText(getContext(),"+1'ed successfully",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        String sa = dataSnapshot.child("plus_one_by").getValue().toString();
                        if(!sa.contains(UID)){
                            count++;
                            databaseReference.child("project_" + projectNumber).child("number_of_plus_one").setValue(count);
                            databaseReference.child("plus_one_by").push().setValue(clubMembersObject);
                        }
                    }
                }
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {            }

            @Override
            public void onCancelled(DatabaseError databaseError) {            }
        };
        databaseReference.addChildEventListener(childEventListener);
    }

    private void checkExistingPlusOne(final NewProjectObject object, final ImageView plusOne){
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("projects").child("new_proposed_projects");
        auth = FirebaseAuth.getInstance();
        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.i("NewProjectAdapter", "Logcat 1:" + dataSnapshot.toString());
                if(dataSnapshot.child("project_name").getValue().toString().equals(object.getProjectName())){
                    Log.i("NewProjectAdapter", "Logcat 2:" + dataSnapshot.toString());
                    if(dataSnapshot.child("number_of_plus_one").getValue(Integer.class) != 0){

                        final String UID = auth.getCurrentUser().getUid();
                        if(dataSnapshot.child("plus_one_by").getValue().toString().contains(UID)){
                            DrawableCompat.setTint(plusOne.getDrawable(), ContextCompat.getColor(getContext(), R.color.colorAccent));
                            //Toast.makeText(getContext(),"You have +1'ed " + object.getProjectName(),Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        databaseReference.addChildEventListener(childEventListener);
    }

}
