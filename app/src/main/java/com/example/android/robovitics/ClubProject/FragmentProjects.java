package com.example.android.robovitics.ClubProject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.android.robovitics.R;

/**
 * Created by Harshit Maheshwari on 20-10-2017.
 */

public class FragmentProjects extends Fragment {

    private Button proposeNewProject, newProposedProjects;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_projects,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Projects");

        proposeNewProject = (Button)view.findViewById(R.id.propose_new_project);
        proposeNewProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),NewProjectActivity.class);
                startActivity(intent);
            }
        });

        newProposedProjects = (Button)view.findViewById(R.id.proposed_projects);
        newProposedProjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),ProposedProjectsActivity.class);
                startActivity(intent);
            }
        });

    }
}
