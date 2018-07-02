package com.example.shell.shellwallet;


import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.example.shell.shellwallet.MainActivity.currentLang;
import static com.example.shell.shellwallet.MainActivity.currentTheme;
import static com.example.shell.shellwallet.MainActivity.sharedPref;
import static com.example.shell.shellwallet.R.id.spinnerChangeTheme;
import static com.example.shell.shellwallet.R.id.spinnerDateTime;
import static com.example.shell.shellwallet.R.id.spinnerLanguage;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

    private View myView;
    private Spinner spinnerLang, spinnerDate, spinnerTheme;

    public SettingsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_settings, container, false);

        int themeColor = sharedPref.getInt("currentColor",R.color.colorPrimary);
        TextView chooselang = (TextView)myView.findViewById(R.id.chooselang);
        chooselang.setTextColor(getResources().getColor(themeColor));
        TextView textTheme = (TextView)myView.findViewById(R.id.textTheme);
        textTheme.setTextColor(getResources().getColor(themeColor));
        TextView textDate = (TextView)myView.findViewById(R.id.textDateTime);
        textDate.setTextColor(getResources().getColor(themeColor));
        TextView textDec = (TextView)myView.findViewById(R.id.textDecimalseperator);
        textDec.setTextColor(getResources().getColor(themeColor));

        int flags[] = {R.drawable.flag_uk, R.drawable.flag_vn};
        String[] languageNames= {"English", "Tiếng Việt"};
        spinnerLang = (Spinner)myView.findViewById(spinnerLanguage);
        spinnerLang.setPrompt(getString(R.string.select_language));
        SpinnerAdapter customAdapter = new SpinnerAdapter(getContext(),flags,languageNames);
        spinnerLang.setAdapter(customAdapter);
        spinnerLang.setSelection(currentLang);
        spinnerLang.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                if (pos == 0 && currentLang !=0 ) {
                    Toast.makeText(parent.getContext(), "You have selected English", Toast.LENGTH_SHORT).show();
                    DatabaseHelper mydb = new DatabaseHelper(getContext());
                    mydb.updateLanguageEN();
                    MainActivity.prefEditor.putInt("currentLang", 0);
                    MainActivity.prefEditor.commit();
                    refresh();

                } else if (pos == 1 && currentLang != 1) {
                    Toast.makeText(parent.getContext(), "Bạn đã chọn Tiếng Việt", Toast.LENGTH_SHORT).show();
                    DatabaseHelper mydb = new DatabaseHelper(getContext());
                    mydb.updateLanguageVN();
                    MainActivity.prefEditor.putInt("currentLang", 1);
                    MainActivity.prefEditor.commit();
                    refresh();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });
        final ConstraintLayout languageLayout = (ConstraintLayout)myView.findViewById(R.id.languageLayout);
        languageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                languageLayout.getChildAt(0).setClickable(true);
                languageLayout.getChildAt(0).performClick();
                languageLayout.getChildAt(0).setClickable(false);
            }
        });


        int dateIcon[] = {R.drawable.ic_3112, R.drawable.ic_1231};
        String[] dateFormat= {"31/12/2017", "12/31/2017"};
        spinnerDate = (Spinner)myView.findViewById(spinnerDateTime);
        spinnerDate.setPrompt(getString(R.string.select_date_format));
        SpinnerAdapter customAdapter2 = new SpinnerAdapter(getContext(),dateIcon,dateFormat);
        spinnerDate.setAdapter(customAdapter2);
        final int currentDateFormat = sharedPref.getInt("currentDateFormat",0);
        spinnerDate.setSelection(currentDateFormat);
        spinnerDate.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                if(pos != currentDateFormat){
                    MainActivity.prefEditor.putInt("currentDateFormat", pos);
                    MainActivity.prefEditor.commit();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });
        final ConstraintLayout dateLayout = (ConstraintLayout)myView.findViewById(R.id.DateformatLayout);
        dateLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateLayout.getChildAt(0).setClickable(true);
                dateLayout.getChildAt(0).performClick();
                dateLayout.getChildAt(0).setClickable(false);
            }
        });

        final List<MyTheme> themes = new ArrayList<>();
        themes.add(new MyTheme(R.style.AppTheme,getString(R.string.them1),
                R.color.colorPrimary,R.drawable.custom_ripple_blue,R.drawable.header));
        themes.add(new MyTheme(R.style.AppTheme_Orange,getString(R.string.theme2),
                R.color.colorPrimary3,R.drawable.custom_ripple_orange,R.drawable.header3));
        themes.add(new MyTheme(R.style.AppTheme_Green,getString(R.string.theme3),
                R.color.colorPrimary4,R.drawable.custom_ripple_green,R.drawable.header4));
        themes.add(new MyTheme(R.style.AppTheme_Midnight,getString(R.string.them4),
                R.color.colorPrimary5,R.drawable.custom_ripple_midnight,R.drawable.header5));

        spinnerTheme = (Spinner)myView.findViewById(spinnerChangeTheme);
        spinnerTheme.setPrompt(getString(R.string.select_theme));
        SpinnerAdapter customAdapter3 = new SpinnerAdapter(getContext(),themes);
        spinnerTheme.setAdapter(customAdapter3);
        for(int i=0;i<themes.size();i++){
            if(currentTheme == themes.get(i).id){
                spinnerTheme.setSelection(i);
                break;
            }
        }
        spinnerTheme.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                if(id != currentTheme){
                    MainActivity.prefEditor.putInt("currentTheme", (int)id);
                    MainActivity.prefEditor.putInt("currentColor",themes.get(pos).color);
                    MainActivity.prefEditor.putInt("currentButton",themes.get(pos).button);
                    MainActivity.prefEditor.putInt("currentHeader",themes.get(pos).header);
                    MainActivity.prefEditor.commit();
                    refresh();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });
        final ConstraintLayout themeLayout = (ConstraintLayout)myView.findViewById(R.id.themeLayout);
        themeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                themeLayout.getChildAt(0).setClickable(true);
                themeLayout.getChildAt(0).performClick();
                themeLayout.getChildAt(0).setClickable(false);
            }
        });

        String[] decimal= {"1,234,567.89", "1.234.567,89"};
        int decIcon[] = {R.drawable.ic_dot, R.drawable.ic_comma};
        spinnerDate = (Spinner)myView.findViewById(R.id.spinnerDecimal);
        spinnerDate.setPrompt("Select decimal separator");
        SpinnerAdapter customAdapter4 = new SpinnerAdapter(getContext(),decIcon,decimal);
        spinnerDate.setAdapter(customAdapter4);
        final int currentDecimal = sharedPref.getInt("currentDecimal",0);
        spinnerDate.setSelection(currentDecimal);
        spinnerDate.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                if(pos != currentDecimal){
                    MainActivity.prefEditor.putInt("currentDecimal", pos);
                    MainActivity.prefEditor.commit();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });
        final ConstraintLayout decimalLayout = (ConstraintLayout)myView.findViewById(R.id.DecimalLayout);
        decimalLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decimalLayout.getChildAt(0).setClickable(true);
                decimalLayout.getChildAt(0).performClick();
                decimalLayout.getChildAt(0).setClickable(false);
            }
        });


        return myView;
    }

    public void refresh(){
        Intent refresh = new Intent(this.getActivity(), MainActivity.class);
        refresh.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        refresh.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(refresh);
    }
    public class MyTheme{
        public int id;
        public String name;
        public int color;
        public int button;
        public int header;
        public MyTheme(int id,String name, int color, int button, int header){
            this.id= id;
            this.name = name;
            this.color = color;
            this.button = button;
            this.header = header;
        }
    }
}


