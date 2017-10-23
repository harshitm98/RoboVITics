package com.example.android.robovitics.ClubProject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.android.robovitics.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ProposedProjectsActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<NewProjectObject> objects;
    private NewProjectAdapter adapter;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference proposedProjectReference;
    private ChildEventListener childEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proposed_projects);
        settingUpVariables();
        displayingTheData();
    }

    private void settingUpVariables(){
        listView = (ListView)findViewById(R.id.proposed_projects_list_layout);
        objects = new ArrayList<>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        proposedProjectReference = firebaseDatabase.getReference("projects").child("new_proposed_projects");
        adapter = new NewProjectAdapter(ProposedProjectsActivity.this,objects);
    }

    private void displayingTheData(){
        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                NewProjectObject projectObject = new NewProjectObject();
                projectObject.setProjectName(dataSnapshot.child("project_name").getValue().toString());
                adapter.add(projectObject);
                listView.setAdapter(adapter);
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
        proposedProjectReference.addChildEventListener(childEventListener);
    }

}
