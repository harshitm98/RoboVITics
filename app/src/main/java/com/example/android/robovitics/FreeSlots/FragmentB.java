package com.example.android.robovitics.FreeSlots;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.android.robovitics.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class FragmentB extends Fragment {


    int dayReal;

    private OnFragmentInteractionListener mListener;

    private static final String TAG = "FragmentB";

    public FragmentB() {
        // Required empty public constructor
    }
    private ListView listView;
    private FreeSlotsAdapter adapterMon;
    private FreeSlotsAdapter adapterTue;
    private FreeSlotsAdapter adapterWed;
    private FreeSlotsAdapter adapterThu;
    private FreeSlotsAdapter adapterFri;

    private ArrayList<FreeSlotsObject> objectsMon, objectsTue, objectsWed, objectsThu, objectsFri;

    public static FragmentB newInstance(int tabSelected) {
        FragmentB fragment = new FragmentB();
        Bundle args = new Bundle();
        args.putInt("fragment_b", tabSelected);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_b, container, false);
        listView = view.findViewById(R.id.free_slots_list_view);
        dayReal = getArguments().getInt("fragment_b");
        objectsMon = new ArrayList<>();
        objectsTue = new ArrayList<>();
        objectsWed = new ArrayList<>();
        objectsThu = new ArrayList<>();
        objectsFri = new ArrayList<>();
        adapterMon = new FreeSlotsAdapter(getActivity(), objectsMon);
        adapterTue = new FreeSlotsAdapter(getActivity(), objectsTue);
        adapterWed = new FreeSlotsAdapter(getActivity(), objectsWed);
        adapterThu = new FreeSlotsAdapter(getActivity(), objectsThu);
        adapterFri = new FreeSlotsAdapter(getActivity(), objectsFri);
        fillingUpAdapters();
        return view;
    }

    private void fillingUpAdapters(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("freeslots");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String day = "0";
                String time, people;
                for(DataSnapshot child: dataSnapshot.getChildren()){
                    if(child.child("day").exists()){
                        day = child.child("day").getValue().toString();
                    }else{
                        for(DataSnapshot child1: dataSnapshot.child("free").getChildren()){

                            time = child1.getKey();
                            time = time.replace(" to ", "-");
                            time = time.replace("08", "8");
                            time = time.replace("09", "8");
                            people =child1.getValue().toString();
                            FreeSlotsObject object = new FreeSlotsObject();
                            object.setTimes(time);
                            object.setPeopleFree(people);
                            switch (day){
                                case "0":
                                    objectsMon.add(object);
                                    break;
                                case "1":
                                    objectsTue.add(object);
                                    break;
                                case "2":
                                    objectsWed.add(object);
                                    break;
                                case "3":
                                    objectsThu.add(object);
                                    break;
                                case "4":
                                    objectsFri.add(object);
                                    break;
                            }
                            switching();
                        }
                    }
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

    public void switching(){
        switch (dayReal){
            case 0:
                listView.setAdapter(adapterMon);
                break;
            case 1:
                listView.setAdapter(adapterTue);
                break;
            case 2:
                listView.setAdapter(adapterWed);
                break;
            case 3:
                listView.setAdapter(adapterThu);
                break;
            case 4:
                listView.setAdapter(adapterFri);
                break;
        }
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


}
