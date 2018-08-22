package com.example.android.robovitics.FreeSlots;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.robovitics.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FreeSlotsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FreeSlotsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FreeSlotsFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "free_slots_fragment";

    private String mParam1;
    private String mParam2;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private String[] tabName = {"MON", "TUE", "WED", "THU", "FRI"};
    private ArrayList<String> realTabName;

    private OnFragmentInteractionListener mListener;

    public FreeSlotsFragment() {
        // Required empty public constructor
    }


    public static FreeSlotsFragment newInstance(String navigation) {
        FreeSlotsFragment fragment = new FreeSlotsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, navigation);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_free_slots, container, false);
        tabLayout = view.findViewById(R.id.tabs_free);
        viewPager = view.findViewById(R.id.container_free);
        realTabName = new ArrayList<>();
        for (String aTabName : tabName) {
            tabLayout.addTab(tabLayout.newTab().setText(aTabName));
            realTabName.add(aTabName);
        }
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager(), tabLayout.getTabCount(), realTabName);
        viewPager.setAdapter(mSectionsPagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                getChildFragmentManager().beginTransaction().addToBackStack(null).commit();
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        return view;
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



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("Free Slots");
        mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager(),tabLayout.getTabCount(),realTabName);
        viewPager.setAdapter(mSectionsPagerAdapter);
    }
}
