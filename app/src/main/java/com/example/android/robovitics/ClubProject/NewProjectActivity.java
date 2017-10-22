package com.example.android.robovitics.ClubProject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.robovitics.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

public class NewProjectActivity extends AppCompatActivity {

    private EditText nameOfTheProject, problemStatement, solutionOffered, membersRequired, researchedWork;
    private CheckBox existingSolutionCheckBox, anonymousCheckbox;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference,counterReference;
    private FirebaseAuth mAuth;

    private String project_number;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.submit_new_proposed_project){
            submitDetails();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.check,menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_project);

        initializeVariables();
    }

    private void initializeVariables(){
        nameOfTheProject = (EditText)findViewById(R.id.propose_new_name);
        problemStatement = (EditText)findViewById(R.id.propose_new_problem_statement);
        solutionOffered = (EditText)findViewById(R.id.propose_new_solution_offered);
        membersRequired = (EditText)findViewById(R.id.propose_new_members_required);
        researchedWork = (EditText)findViewById(R.id.propose_new_research_work);
        existingSolutionCheckBox = (CheckBox)findViewById(R.id.checkbox_existing_solution);
        anonymousCheckbox = (CheckBox)findViewById(R.id.checkbox_anonymous);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("projects").child("new_proposed_projects");
        counterReference = firebaseDatabase.getReference("projects").child("new_project_count");
        mAuth = FirebaseAuth.getInstance();
    }

    private void submitDetails(){
        if(nameOfTheProject.length()!=0 && problemStatement.length()!=0 && solutionOffered.length()!=0
                && membersRequired.length()!=0 && researchedWork.length()!=0){
            counterReference.runTransaction(new Transaction.Handler() {
                @Override
                public Transaction.Result doTransaction(MutableData mutableData) {
                    if(mutableData.getValue() == null){
                        mutableData.setValue(0);
                        project_number = "project_" + (0);
                    }
                    else{
                        int count = mutableData.getValue(Integer.class);
                        mutableData.setValue(count + 1);
                        project_number = "project_" + (count + 1);
                    }
                    return Transaction.success(mutableData);
                }

                @Override
                public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                    if(databaseError == null){
                        databaseReference.child(project_number).child("project_name").setValue(nameOfTheProject.getText().toString());
                        databaseReference.child(project_number).child("problem_statement").setValue(problemStatement.getText().toString());
                        databaseReference.child(project_number).child("solution_offered").setValue(solutionOffered.getText().toString());
                        databaseReference.child(project_number).child("members_required").setValue(membersRequired.getText().toString());
                        databaseReference.child(project_number).child("researched_work").setValue(researchedWork.getText().toString());
                        if(existingSolutionCheckBox.isChecked()){
                            databaseReference.child(project_number).child("existing_solution").setValue("Yes");
                        }
                        else{
                            databaseReference.child(project_number).child("existing_solution").setValue("Yes");
                        }
                        if(anonymousCheckbox.isChecked()){
                            databaseReference.child(project_number).child("submitted_by").setValue("ANONYMOUS");
                        }
                        else{
                            databaseReference.child(project_number).child("submitted_by").setValue(mAuth.getCurrentUser().getDisplayName());
                        }
                        Toast.makeText(NewProjectActivity.this, "Your idea has been proposed.",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else{
                        Toast.makeText(NewProjectActivity.this, "There was a problem proposing your idea. Please try again.",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else{
            Toast.makeText(NewProjectActivity.this, "One or more fields are empty.",Toast.LENGTH_SHORT).show();
        }
    }

}
