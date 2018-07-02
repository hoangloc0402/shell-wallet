package com.example.shell.shellwallet;

import java.io.Serializable;
import java.util.Calendar;

import static com.example.shell.shellwallet.MainActivity.sContext;
import static com.example.shell.shellwallet.MainActivity.sharedPref;

/**
 * Created by QuocBao on 16/04/2017.
 */

public class HistoryCard implements Serializable{
    public int getIcon() {
        return icon;
    }

    int icon;

    public String getName() {
        return name;
    }

    String name;

    public String getNote() {
        return note;
    }

    String note;

    public float getAmount() {
        return amount;
    }

    float amount;

    public String getDate() {
        return date;
    }

    String date;

    public int getId() {
        return id;
    }

    int id;
    HistoryCard(int icon, String name, String note, float amount, int day, int month, int year, int id){
        this.icon = icon;
        this.name = name;
        this.note = note;
        this.amount = amount;
        Calendar calendar = Calendar.getInstance();
        int thisyear = calendar.get(Calendar.YEAR);
        int thismonth = calendar.get(Calendar.MONTH);
        int thisday = calendar.get(Calendar.DAY_OF_MONTH);
        if(day == thisday && month == thismonth+1 && year == thisyear ){
            this.date = sContext.getString(R.string.today);
        }
        else {
            int dateFormat = sharedPref.getInt("currentDateFormat",0);
            if(dateFormat==1) this.date = month + "/" + day + "/" + year;
            else this.date = day + "/" + month + "/" + year;
        }
        this.id = id;
    };

}
