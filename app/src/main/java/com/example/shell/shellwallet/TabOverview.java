package com.example.shell.shellwallet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

/**
 * Created by HoangLoc on 10/04/2017.
 */

public class TabOverview extends Fragment{
    FloatingActionMenu menuFAB;
    FloatingActionButton IncomeFAB, expenseFAB;
    private View myView;
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    SharedPreferences sharedPreferences;
    int currentwallerid;
    public TabOverview() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.tab_overview, container, false);
        sharedPreferences = getActivity().getSharedPreferences(getString(R.string.preperences_file), Context.MODE_PRIVATE);
        initfloatingbutton();
        mRecyclerView = (RecyclerView) myView.findViewById(R.id.recycler_overview);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        //use a linear layout manager
        //mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        currentwallerid = sharedPreferences.getInt(getString(R.string.wallet_current),0);
        mAdapter = new RecyclerViewAdapter(currentwallerid);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy){
                if (dy<0 && menuFAB.isMenuHidden()){
                    menuFAB.showMenu(true);
                }
                else if(dy > 0 && menuFAB.isShown()){
                    menuFAB.hideMenu(true);
                }
            }
        });

        return myView;
    }
    private void initfloatingbutton(){
        menuFAB = (FloatingActionMenu) myView.findViewById(R.id.menu);
        IncomeFAB = (FloatingActionButton) myView.findViewById(R.id.menu_income);
        expenseFAB = (FloatingActionButton) myView.findViewById(R.id.menu_expense);
        menuFAB.setMenuButtonShowAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.jump_from_down));
        menuFAB.setMenuButtonHideAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.jump_to_down));
        //Inflate the layout for this fragment
        expenseFAB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),AddActivity.class);
                intent.putExtra("type",1);
                intent.putExtra("edit",0);
                getActivity().startActivityForResult(intent,1);
            }
        });
        IncomeFAB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),AddActivity.class);
                intent.putExtra("type",0);
                intent.putExtra("edit",0);
                getActivity().startActivityForResult(intent,1);

            }
        });
    }
    @Override

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(myView !=null) {
            if (isVisibleToUser && RecyclerViewAdapter.needupdateFlag ==1) {
                RecyclerViewAdapter.needupdateFlag = 0;
                mAdapter = new RecyclerViewAdapter(currentwallerid);
                mRecyclerView.setAdapter(mAdapter);
            }
        }
    }

}
