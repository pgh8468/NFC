package com.example.hotelnfc;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class facility_guide extends Fragment {

    View view;

    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;

    public facility_guide() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_facility_guide, null);

        viewPager = view.findViewById(R.id.viewpager);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setupViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());

        viewPagerAdapter.addFragment(new health());
        viewPagerAdapter.addFragment(new restaurant());
        viewPagerAdapter.addFragment(new swimming_pool());

        viewPager.setAdapter(viewPagerAdapter);
    }

}
