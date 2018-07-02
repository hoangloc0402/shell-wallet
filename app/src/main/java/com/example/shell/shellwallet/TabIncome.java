package com.example.shell.shellwallet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import java.util.ArrayList;

import static com.example.shell.shellwallet.MainActivity.sharedPref;


/**
 * Created by HoangLoc on 09/04/2017.
 */

public class TabIncome extends Fragment{
    GridView gridView;
    GridViewAdapter gridViewAdapter;
    ArrayList<Category> categoryList;
    DatabaseHelper mydb;

    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_income_expense, container, false);

        mydb = new DatabaseHelper(getContext());
        categoryList=mydb.getIncomeCategory();
        gridView = (GridView) rootView.findViewById(R.id.grid_view_cat);
        gridViewAdapter = new GridViewAdapter(getContext(),categoryList);
        gridView.setAdapter(gridViewAdapter);
        Button addButton = (Button) rootView.findViewById(R.id.add_category);
        int themeColor = sharedPref.getInt("currentColor",R.color.colorPrimary);
        int buttoncolor = sharedPref.getInt("currentButton",R.drawable.custom_ripple_blue);
        addButton.setBackgroundResource(buttoncolor);
        addButton.setText(R.string.add_income_category);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),AddCategoryActivity.class);
                intent.putExtra("type",0);
                intent.putExtra("edit",0);
                getActivity().startActivityForResult(intent,2);
            }
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),AddCategoryActivity.class);
                intent.putExtra("type",0);
                intent.putExtra("edit",1);
                intent.putExtra("cat", categoryList.get(position));
                getActivity().startActivityForResult(intent,2);
            }
        });

        return rootView;
    }
}