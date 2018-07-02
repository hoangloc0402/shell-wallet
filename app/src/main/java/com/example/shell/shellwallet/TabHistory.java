package com.example.shell.shellwallet;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by HoangLoc on 10/04/2017.
 */

public class TabHistory extends Fragment {
    DatabaseHelper mydb;
    SharedPreferences sharedPreferences;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_history, container, false);

        mydb = new DatabaseHelper(getContext());
        sharedPreferences = getContext().getSharedPreferences(getString(R.string.preperences_file), Context.MODE_PRIVATE);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_history);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        //use a linear layout manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        int currentwallerid = sharedPreferences.getInt(getString(R.string.wallet_current),0);
        mAdapter = new RecyclerViewAdapter(currentwallerid,getContext(),getActivity());
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }
}
