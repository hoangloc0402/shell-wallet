package com.example.shell.shellwallet;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by HoangLoc on 10/04/2017.
 */
public class OverviewFragment extends Fragment {
    public  static TabLayout tabLayout;
    public  static ViewPager viewPager;

    public OverviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_manage_categories,null);
        tabLayout = (TabLayout)v.findViewById(R.id.tabs);
        viewPager = (ViewPager)v.findViewById(R.id.viewpager);

        viewPager.setAdapter(new TabAdapter( getChildFragmentManager()));

        TabAdapter adapter = new TabAdapter(getChildFragmentManager());
        adapter.addFragment(new TabOverview(), getString(R.string.overview));
        adapter.addFragment(new TabHistory(), getString(R.string.history));
        viewPager.setAdapter(adapter);

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });

        return v;
    }

}
