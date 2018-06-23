package com.example.android.robovitics.Attendance;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.robovitics.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabAttendanceFragment extends Fragment {

    int position;
    private TextView textView;

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
        textView = view.findViewById(R.id.sample_test);
        textView.setText("Fragment " + (position + 1));

    }
}
